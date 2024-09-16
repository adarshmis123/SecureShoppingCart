package com.cg.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class PaymentResponse {
	private Integer statementId;
	private String transactionType;
	private double amount;
	private Date date;
	private Integer orderId;
	private String paymentStatus;

	
}