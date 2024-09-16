package com.cg.dto;

import java.util.Date;
import java.util.List;

import com.cg.entity.Ewallet;
import com.cg.entity.Statement;

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