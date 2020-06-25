package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferBalanceRequest;
import com.db.awmd.challenge.repository.AccountsRepositoryInMemory;
import com.db.awmd.challenge.service.AccountsService;
import com.db.awmd.challenge.service.MoneyTransferServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MoneyTransferControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private AccountsService accountsService;

	@Autowired
	AccountsRepositoryInMemory accountRepositoryInMemory;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	MoneyTransferServiceImpl moneyTransferServiceImpl;

	@Before
	public void prepareMockMvc() {
		this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
		accountsService.getAccountsRepository().clearAccounts();
	}

	@Test
	public void sendMoney() throws Exception {
		this.mockMvc
				.perform(post("/v2/transfer").contentType(MediaType.APPLICATION_JSON)
						.content("{\"fromAccountNumber\":\"Id-123\",\"toAccountNumber\" :\"Id-124\",\"balance\":1000}"))
				.andExpect(status().isCreated());

		Account account1 = new Account("1001", new BigDecimal(50000));
		Account account2 = new Account("2002", new BigDecimal(2000));
		accountRepositoryInMemory.save(account1);
		accountRepositoryInMemory.save(account2);

		TransferBalanceRequest transferBalanceRequest = new TransferBalanceRequest(account1.getAccountId(),
				account2.getAccountId(), new BigDecimal(3000));

		moneyTransferServiceImpl.sendMoney(transferBalanceRequest);

		assertThat(accountRepositoryInMemory.getAccount(account1.getAccountId()).getBalance())
				.isEqualTo(new BigDecimal(47000));

		assertThat(accountRepositoryInMemory.getAccount(account2.getAccountId()).getBalance())
				.isEqualTo(new BigDecimal(5000));
	}
}