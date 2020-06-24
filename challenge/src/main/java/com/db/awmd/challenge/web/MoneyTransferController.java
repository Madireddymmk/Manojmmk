package com.db.awmd.challenge.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.awmd.challenge.domain.TransferBalanceRequest;
import com.db.awmd.challenge.service.MoneyTransferServiceImpl;

@RestController
@RequestMapping("/v2/transfer")
public class MoneyTransferController {
	
	
	@Autowired
	MoneyTransferServiceImpl moneyTransferServiceImpl;

	@RequestMapping("/sendmoney")
	public void sendMoney(@RequestBody @Valid TransferBalanceRequest transferBalanceRequest) {

		moneyTransferServiceImpl.sendMoney(transferBalanceRequest);

	}

}
