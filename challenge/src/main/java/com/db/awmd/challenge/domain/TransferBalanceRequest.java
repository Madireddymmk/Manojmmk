/**
 * @copyRights 2020 DeutscheBank.All rights are reserved.you should not
 * disclose the information outside,otherwise terms and conditions apply.
 */
package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author DeutscheBank
 * @Description: This Class is used to bind the incoming data coming from the MoneyTransferController
 */

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransferBalanceRequest {

	
	@NotNull
	private String fromAccountNumber;

	@NotNull
	private String toAccountNumber;

	@NotNull
	@Min(value = 0, message = "Transfer amount should be positive")
	private BigDecimal amount;

}
