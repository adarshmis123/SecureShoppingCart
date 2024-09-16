package com.cg.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cg.DTO.PaymentResponse;

@FeignClient(name = "walletservice")
public interface WalletServiceClient {
	@PostMapping("/ewallet/pay/{userId}/{amount}/{orderId}")
	ResponseEntity<PaymentResponse> pay(@PathVariable int userId, @PathVariable double amount,@PathVariable int orderId);
}
