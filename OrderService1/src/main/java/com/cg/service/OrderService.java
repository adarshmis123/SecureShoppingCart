package com.cg.service;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cg.DTO.OrderResponse;
import com.cg.DTO.PaymentResponse;
import com.cg.exception.OrderException;

public interface OrderService {

	ResponseEntity<PaymentResponse> placeOrder(int productId, Principal principal) throws OrderException;

	OrderResponse getOrderById(int orderId) throws OrderException;

	void deleteOrder(int orderId, int userId) throws OrderException;

	List<OrderResponse> viewAllOrders() throws OrderException;

	List<OrderResponse> viewOrderByUserId(int userId) throws OrderException;

	void cancelOrder(int orderId, int userId) throws OrderException;
}
