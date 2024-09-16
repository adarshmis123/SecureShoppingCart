package com.cg.service;

import java.util.List;

import com.cg.dto.PaymentResponse;
import com.cg.dto.StatementResponse;
import com.cg.exception.WalletException;

public interface EwalletService {
	   void createWallet(Integer userId) throws WalletException;  
    PaymentResponse pay(Integer userId, double amount, Integer orderId) throws WalletException;
    List<StatementResponse> getStatementsByUserId(Integer userId) throws WalletException;
    double getBalance(Integer userId) throws WalletException;
    void addMoney(Integer userId, double amount) throws WalletException;
}