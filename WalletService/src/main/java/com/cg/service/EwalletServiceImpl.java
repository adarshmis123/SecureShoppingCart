package com.cg.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.PaymentResponse;
import com.cg.dto.StatementResponse;
import com.cg.entity.Ewallet;
import com.cg.entity.Statement;
import com.cg.exception.WalletException;
import com.cg.repository.EwalletRepository;
import com.cg.repository.StatementRepository;

@Service
public class EwalletServiceImpl implements EwalletService {

	@Autowired
	private EwalletRepository ewalletRepository;

	@Autowired
	private StatementRepository statementRepository;

	@Override
	public PaymentResponse pay(Integer userId, double amount, Integer orderId) throws WalletException {
		Ewallet wallet = ewalletRepository.findByUserId(userId)
				.orElseThrow(() -> new WalletException("Wallet not found for user: " + userId));

		double currentBalance = wallet.getCurrentBalance();
		PaymentResponse paymentResponse = new PaymentResponse();

		if (currentBalance >= amount) {
			// Deduct the amount from the wallet
			wallet.setCurrentBalance(currentBalance - amount);
			ewalletRepository.save(wallet);

			// Add a new transaction statement
			Statement statement = new Statement();
			statement.setEwallet(wallet);
			statement.setTransactionType("Payment");
			statement.setAmount(amount);
			statement.setDate(new Date());
			statement.setOrderId(orderId);

			statement = statementRepository.save(statement);

			// Prepare the response with transaction details
			paymentResponse.setStatementId(statement.getStatementId());
			paymentResponse.setTransactionType(statement.getTransactionType());
			paymentResponse.setAmount(statement.getAmount());
			paymentResponse.setDate(statement.getDate());
			paymentResponse.setOrderId(orderId);
			paymentResponse.setPaymentStatus("Success");
		} else {
			paymentResponse.setPaymentStatus("Failed - Insufficient funds");
			paymentResponse.setOrderId(orderId);
		}

		return paymentResponse;
	}

	@Override
	public List<StatementResponse> getStatementsByUserId(Integer userId) throws WalletException {
		Ewallet wallet = ewalletRepository.findByUserId(userId)
				.orElseThrow(() -> new WalletException("Wallet not found for user: " + userId));

		List<Statement> statements = statementRepository.findByEwallet(wallet);

		return statements.stream().map(statement -> {
			StatementResponse response = new StatementResponse();
			response.setStatementId(statement.getStatementId());
			response.setTransactionType(statement.getTransactionType());
			response.setAmount(statement.getAmount());
			response.setDate(statement.getDate());
			response.setOrderId(statement.getOrderId());
			return response;
		}).collect(Collectors.toList());
	}

	@Override
	public double getBalance(Integer userId) throws WalletException {
		Ewallet wallet = ewalletRepository.findByUserId(userId)
				.orElseThrow(() -> new WalletException("Wallet not found for user: " + userId));
		return wallet.getCurrentBalance();
	}

	
	@Override
    public void createWallet(Integer userId) throws WalletException {
        // Check if a wallet already exists for the user
        if (ewalletRepository.existsByUserId(userId)) {
            throw new WalletException("Wallet already exists for userId: " + userId);
        }
 
        // If wallet doesn't exist, create a new one
        Ewallet newWallet = new Ewallet();
        newWallet.setUserId(userId);
        newWallet.setCurrentBalance(0.00); // New wallet starts with a balance of 0
        ewalletRepository.save(newWallet);
    }
 
    @Override
    public void addMoney(Integer userId, double amount) throws WalletException {
        // Check if the wallet exists, and create one if not (optional behavior)
        if (!ewalletRepository.existsByUserId(userId)) {
            createWallet(userId); // If wallet doesn't exist, create it
        }
 
        Ewallet wallet = ewalletRepository.findByUserId(userId)
            .orElseThrow(() -> new WalletException("Wallet not found for user: " + userId));
 
        // Add the money to the wallet
        double newBalance = wallet.getCurrentBalance() + amount;
        wallet.setCurrentBalance(newBalance);
 
        // Save the updated wallet
        ewalletRepository.save(wallet);
 
        // Add a new statement for this deposit
        Statement statement = new Statement();
        statement.setEwallet(wallet);
        statement.setTransactionType("Deposit");
        statement.setAmount(amount);
        statement.setDate(new Date());
        statement.setOrderId(null);  // No order ID for direct deposits
 
        statementRepository.save(statement);
    }
}