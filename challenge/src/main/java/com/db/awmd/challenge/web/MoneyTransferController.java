
/**
 * @copyRights 2020 DeutscheBank.All rights are reserved.you should not
 * disclose the information outside,otherwise terms and conditions apply.
 */
package com.db.awmd.challenge.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.awmd.challenge.domain.TransferBalanceRequest;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.service.MoneyTransferServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author DeutscheBank
 * @Description: This class is used to handle the https requests in the form of
 *               json and do the transfer related functionality
 */
@RestController
@RequestMapping("/v2/transfer")
@Slf4j
public class MoneyTransferController {

	@Autowired
	MoneyTransferServiceImpl moneyTransferServiceImpl;

	/**
	 * @author DeutscheBank
	 * @Description: This method gets the data in json format, validate and bind the
	 *               data to the bean and pass it as a parameter to the send money
	 */

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public synchronized void sendMoney(@RequestBody @Valid TransferBalanceRequest transferBalanceRequest) {
		log.info("Transfering Money {}", transferBalanceRequest);
		try {
			moneyTransferServiceImpl.sendMoney(transferBalanceRequest);
		} catch (Exception e) {
		}

	}
}
