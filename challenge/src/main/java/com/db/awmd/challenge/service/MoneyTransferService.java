package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.TransferBalanceRequest;

public interface MoneyTransferService {

	void sendMoney(TransferBalanceRequest transferBalanceRequest);

}
