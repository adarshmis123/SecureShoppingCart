package com.cg.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class StatementResponse {
	private Integer statementId;
	private String transactionType;
	private double amount;
	private Date date;
	private Integer orderId;

	
}