package com.db.awmd.challenge.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferBalanceRequest;
import com.db.awmd.challenge.repository.AccountsRepositoryInMemory;


@Component
public class MoneyTransferServiceImpl implements MoneyTransferService{
	
	@Autowired
	AccountsRepositoryInMemory accountsRepositoryInMemory;

	@Autowired
	EmailNotificationService notificationService;

	@Override
	public synchronized void sendMoney(TransferBalanceRequest transferBalanceRequest) {

		String fromAccountNumber = transferBalanceRequest.getFromAccountNumber();
		String toAccountNumber = transferBalanceRequest.getToAccountNumber();
		BigDecimal amount = transferBalanceRequest.getAmount();

		Account fromAccount = accountsRepositoryInMemory.getAccount(fromAccountNumber);
		Account toAccount = accountsRepositoryInMemory.getAccount(toAccountNumber);

		if (fromAccount.getBalance().compareTo(BigDecimal.ONE) == 1
				&& fromAccount.getBalance().compareTo(amount) == 1) {
			
			fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
			accountsRepositoryInMemory.save(fromAccount);
			
			toAccount.setBalance(toAccount.getBalance().add(amount));
			accountsRepositoryInMemory.save(toAccount);
			
			String transferDescription = "Success";
			
			notificationService.notifyAboutTransfer(fromAccount, transferDescription);

		}
	}


}
