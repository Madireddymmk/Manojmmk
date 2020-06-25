/**
 * @copyRights 2020 DeutscheBank.All rights are reserved.you should not
 * disclose the information outside,otherwise terms and conditions apply.
 */

package com.db.awmd.challenge.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferBalanceRequest;
import com.db.awmd.challenge.exception.InsufficientBalanceException;
import com.db.awmd.challenge.repository.AccountsRepositoryInMemory;

/**
 * @author DeutscheBank
 * @Description: This Class is used to Transfer the money between two different
 *               accounts
 */

@Component
public class MoneyTransferServiceImpl implements MoneyTransferService {

	@Autowired
	AccountsRepositoryInMemory accountsRepositoryInMemory;

	@Autowired
	EmailNotificationService notificationService;

	/**
	 * @author DeutscheBank
	 * @Description: This method performs transaction between two different accounts
	 *               and after successful transaction it will send the notification
	 *               by using Email notification services.
	 */

	@Override
	public synchronized void sendMoney(TransferBalanceRequest transferBalanceRequest)
			throws InsufficientBalanceException {

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
		} else {
			throw new InsufficientBalanceException(
					"Account id " + fromAccount.getAccountId() + " Account doesnt have enough balance");
		}
	}

}
