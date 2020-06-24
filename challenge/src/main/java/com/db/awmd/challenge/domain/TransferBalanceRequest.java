package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
