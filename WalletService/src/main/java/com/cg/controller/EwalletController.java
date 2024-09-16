package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.PaymentResponse;
import com.cg.dto.StatementResponse;
import com.cg.exception.WalletException;
import com.cg.service.EwalletService;

@RestController
@RequestMapping("/ewallet")
public class EwalletController {
 
    @Autowired
    private EwalletService ewalletService;
    
    @GetMapping("/hello")
    @PreAuthorize("hasAnyAuthority('Admin','User')")
	public ResponseEntity<String> hello() {
		return new ResponseEntity<>("i am in cart", HttpStatus.OK);
	}
 
    @PostMapping("/createWallet/{userId}")
    @PreAuthorize("hasAnyAuthority('User')")
    public ResponseEntity<String> createWallet(@PathVariable Integer userId) throws WalletException {
        ewalletService.createWallet(userId);
        return ResponseEntity.ok("Wallet created successfully for userId: " + userId);
    }
    
    @PostMapping("/pay/{userId}/{amount}/{orderId}")
    @PreAuthorize("hasAnyAuthority('User')")
   public ResponseEntity<PaymentResponse> pay(@PathVariable Integer userId, @PathVariable double amount, @PathVariable Integer orderId) throws WalletException {
        PaymentResponse paymentResponse = ewalletService.pay(userId, amount, orderId);
        return ResponseEntity.ok(paymentResponse);
    }
 
    @GetMapping("/statements/{userId}")
    @PreAuthorize("hasAnyAuthority('Admin','User')")
    public ResponseEntity<List<StatementResponse>> getStatementsForUser(@PathVariable Integer userId) throws WalletException {
        List<StatementResponse> statementResponses = ewalletService.getStatementsByUserId(userId);
        return ResponseEntity.ok(statementResponses);
    }
    
    
    
    @GetMapping("/balance/{userId}")
    @PreAuthorize("hasAnyAuthority('User')")
    public ResponseEntity<Double> getCurrentBalance(@PathVariable Integer userId) throws WalletException {
        double currentBalance = ewalletService.getBalance(userId);
        return ResponseEntity.ok(currentBalance);
    }
    
    
    
    @PostMapping("/addMoney/{userId}/{amount}")
    @PreAuthorize("hasAnyAuthority('User')")
    public ResponseEntity<String> addMoneyToWallet(@PathVariable Integer userId, @PathVariable double amount) throws WalletException {
        ewalletService.addMoney(userId, amount);
        return ResponseEntity.ok("Money added successfully. New balance: " + ewalletService.getBalance(userId));
    }
}