//package com.cg.service;
//
//import java.security.Principal;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.cg.DTO.CartDetails;
//import com.cg.DTO.OrderRequest;
//import com.cg.DTO.OrderResponse;
//import com.cg.DTO.UserProfileDto;
//import com.cg.client.CartServiceClient;
//import com.cg.client.UserProfileClient;
//import com.cg.client.WalletServiceClient;
//import com.cg.entity.OrderItem;
//import com.cg.entity.OrderStatus;
//import com.cg.entity.Orders;
//import com.cg.entity.PaymentStatus;
//import com.cg.exception.OrderException;
//import com.cg.repo.OrderRepo;
//
//import jakarta.transaction.Transactional;
//
//@Service
//public class OrderServiceImpl implements OrderService {
//	
//	Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
//
//	@Autowired
//	private OrderRepo orderRepo;
//
//	@Autowired
//	private CartServiceClient cartServiceClient;
//
//	@Autowired
//	private WalletServiceClient walletServiceClient;
//	@Autowired
//	private UserProfileClient userProfileClient;
//
//	@Override
//	@Transactional
//	public OrderResponse placeOrder(OrderRequest orderRequest,Principal principal) throws OrderException {
//		String name = principal.getName();
//		
//		// Call the User Profile service to get user details
//		UserProfileDto userProfile = userProfileClient.getUserProfileByUsername(name);
//		
//		int userId = userProfile.getProfileId();
//		
//		
//		
//		log.info("Placing order for user: {}", userId);
//
//		CartDetails cartDetails = cartServiceClient.getCartByUserId(userId);
//		log.debug("Cart details retrieved: {}", cartDetails);
//
//		//System.out.println(cartDetails);
//
//		double totalAmount = cartDetails.getTotalPrice();
//		log.debug("Total amount to be paid: {}", totalAmount);
//		
//		//System.out.println(totalAmount);
//		// Call Wallet Service to make the payment
//		log.info("Calling wallet service to process payment");
//		boolean response = walletServiceClient.pay(userId, totalAmount);
//
//		System.out.println(response);
//		// If payment successful, create an Order
//		if (response) {
//			log.info("Payment successful for user: {}", userId);
//			Orders order = new Orders();
//			order.setUserId(userId);
//			order.setTotalAmount(totalAmount);
//			order.setOrderStatus(OrderStatus.PAID);
//			order.setPaymentStatus(PaymentStatus.PAID);
//
//			// Convert products from Cart to OrderItems
//			List<OrderItem> orderItems = cartDetails.getCartItems().stream().map(product -> {
//				OrderItem orderItem = new OrderItem();
//				orderItem.setProductId(product.getProductId());
//				orderItem.setQuantity(product.getQuantity());
//				orderItem.setPrice(product.getPrice()/product.getQuantity());
//				orderItem.setTotalPrice(product.getPrice());
//				log.debug("Order item created: {}", orderItem);
//				return orderItem;
//			}).collect(Collectors.toList());
//
//			order.setOrderItems(orderItems);
//			orderRepo.save(order); // Save the order and order items
//			log.info("Order saved with ID: {}", order.getOrderId());
//			
//			// Return order response
//			return new OrderResponse(order.getOrderId(), order.getUserId(), order.getTotalAmount(),
//					order.getOrderStatus(), order.getPaymentStatus(), orderItems);
//		} else {
//			log.error("Payment failed for user: {}", userId);
//			throw new OrderException("Payment failed!");
//		}
//
//	}
//
//	@Override
//	public OrderResponse getOrderById(int orderId) throws OrderException {
//		log.info("Fetching order details for order ID: {}", orderId);
//		Orders order = orderRepo.findById(orderId).orElseThrow(() -> {
//			log.error("Order not found with ID: {}", orderId);
//			return new OrderException("Order not found!");
//		});
//
//		log.debug("Order details retrieved: {}", order);
//		return new OrderResponse(order.getOrderId(), order.getUserId(), order.getTotalAmount(),
//				order.getOrderStatus(), order.getPaymentStatus(), order.getOrderItems());
//	}
//
//}

package com.cg.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cg.DTO.CartDetails;
import com.cg.DTO.CartItem;
import com.cg.DTO.OrderResponse;
import com.cg.DTO.PaymentResponse;
import com.cg.DTO.UserProfileDto;
import com.cg.client.CartServiceClient;
import com.cg.client.UserProfileClient;
import com.cg.client.WalletServiceClient;
import com.cg.entity.OrderItem;
import com.cg.entity.OrderStatus;
import com.cg.entity.Orders;
import com.cg.entity.PaymentStatus;
import com.cg.exception.OrderException;
import com.cg.repo.OrderRepo;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private CartServiceClient cartServiceClient;

	@Autowired
	private WalletServiceClient walletServiceClient;

	@Autowired
	private UserProfileClient userProfileClient;

	@Override
	@Transactional
	public ResponseEntity<PaymentResponse> placeOrder(int productId, Principal principal) throws OrderException {
		logger.info("Starting order placement for user: {}", principal.getName());
		String username = principal.getName();

		UserProfileDto userProfile = userProfileClient.getUserProfileByUsername(username);
		if (userProfile == null) {
			logger.error("User profile not found for username: {}", username);
			throw new OrderException("User profile not found.");
		}

		int userId = userProfile.getProfileId();
		CartDetails cartDetails = cartServiceClient.getCartByUserId(userId);
		if (cartDetails == null) {
			logger.error("Cart details not found for userId: {}", userId);
			throw new OrderException("Cart details not found.");
		}

		Optional<CartItem> item = cartDetails.getCartItems().stream().filter(e -> e.getProductId() == productId)
				.findFirst();
		double totalAmount = 0;
		if (item.isPresent()) {
			totalAmount = item.get().getPrice();
		}



			ResponseEntity<PaymentResponse> payment = walletServiceClient.pay(userId, totalAmount, productId);
			System.out.println(payment.getBody());
		

        if (!payment.getBody().getPaymentStatus().equals("Success")) {
            logger.error("Payment failed for userId: {}", userId);
            throw new OrderException("Payment failed!");
        }

		Orders order = new Orders();
		order.setUserId(userId);
		order.setTotalAmount(totalAmount);
		order.setOrderStatus(OrderStatus.PAID);
		order.setPaymentStatus(PaymentStatus.PAID);

		List<OrderItem> orderItems = cartDetails.getCartItems().stream().map(product -> {
			OrderItem orderItem = new OrderItem();
			orderItem.setProductId(product.getProductId());
			orderItem.setQuantity(product.getQuantity());
			orderItem.setPrice(product.getPrice() / product.getQuantity());
			orderItem.setTotalPrice(product.getPrice());
			return orderItem;
		}).collect(Collectors.toList());

		order.setOrderItems(orderItems);
		orderRepo.save(order);

		logger.info("Order placed successfully with orderId: {}", order.getOrderId());
//		return new OrderResponse(order.getOrderId(), order.getUserId(), order.getTotalAmount(), order.getOrderStatus(),
//				order.getPaymentStatus(), orderItems);
		return payment;
	}

	@Override
	public OrderResponse getOrderById(int orderId) throws OrderException {
		logger.info("Fetching order with orderId: {}", orderId);
		Orders order = orderRepo.findById(orderId).orElseThrow(() -> {
			logger.error("Order not found with orderId: {}", orderId);
			return new OrderException("Order not found!");
		});
		return new OrderResponse(order.getOrderId(), order.getUserId(), order.getTotalAmount(), order.getOrderStatus(),
				order.getPaymentStatus(), order.getOrderItems());
	}

	public boolean isSuccess(int orderId) throws OrderException {
		logger.info("Checking success status for orderId: {}", orderId);
		Orders order = orderRepo.findById(orderId).orElseThrow(() -> {
			logger.error("Order not found with orderId: {}", orderId);
			return new OrderException("Order not found!");
		});
		return order.getPaymentStatus() == PaymentStatus.PAID;
	}

	@Override
	public void deleteOrder(int orderId, int userId) throws OrderException {
		logger.info("Attempting to delete order with orderId: {} for userId: {}", orderId, userId);
		Orders order = orderRepo.findById(orderId).orElseThrow(() -> {
			logger.error("Order not found with orderId: {}", orderId);
			return new OrderException("Order not found!");
		});

		if (order.getUserId() != userId) {
			logger.error("Unauthorized attempt to delete order with orderId: {} by userId: {}", orderId, userId);
			throw new OrderException("You are not authorized to delete this order.");
		}

		if (order.getOrderStatus() == OrderStatus.SHIPPED || order.getOrderStatus() == OrderStatus.DELIVERED) {
			logger.error("Order with orderId: {} cannot be deleted as it has been shipped or delivered.", orderId);
			throw new OrderException("Order cannot be deleted as it has already been shipped or delivered.");
		}

		orderRepo.delete(order);
		logger.info("Order with orderId: {} successfully deleted.", orderId);
	}

	@Override
	public void cancelOrder(int orderId, int userId) throws OrderException {
		logger.info("Attempting to cancel order with orderId: {} for userId: {}", orderId, userId);
		Orders order = orderRepo.findById(orderId).orElseThrow(() -> {
			logger.error("Order not found with orderId: {}", orderId);
			return new OrderException("Order not found!");
		});

		if (order.getUserId() != userId) {
			logger.error("Unauthorized attempt to cancel order with orderId: {} by userId: {}", orderId, userId);
			throw new OrderException("You are not authorized to cancel this order.");
		}

		if (order.getOrderStatus() == OrderStatus.SHIPPED || order.getOrderStatus() == OrderStatus.DELIVERED) {
			logger.error("Order with orderId: {} cannot be canceled as it has been shipped or delivered.", orderId);
			throw new OrderException("Order cannot be canceled as it has already been shipped or delivered.");
		}

		order.setOrderStatus(OrderStatus.CANCELED);
		orderRepo.save(order);
		logger.info("Order with orderId: {} successfully canceled.", orderId);
	}

	@Override
	public List<OrderResponse> viewAllOrders() throws OrderException {
		logger.info("Fetching all orders.");
		List<Orders> orders = orderRepo.findAll();
		if (orders.isEmpty()) {
			logger.error("No orders found.");
			throw new OrderException("No orders found.");
		}

		return orders.stream()
				.map(order -> new OrderResponse(order.getOrderId(), order.getUserId(), order.getTotalAmount(),
						order.getOrderStatus(), order.getPaymentStatus(), order.getOrderItems()))
				.collect(Collectors.toList());
	}

	@Override
	public List<OrderResponse> viewOrderByUserId(int userId) throws OrderException {
		logger.info("Fetching orders for userId: {}", userId);
		List<Orders> orders = orderRepo.findByUserId(userId);
		if (orders.isEmpty()) {
			logger.error("No orders found for userId: {}", userId);
			throw new OrderException("No orders found for this user.");
		}

		return orders.stream()
				.map(order -> new OrderResponse(order.getOrderId(), order.getUserId(), order.getTotalAmount(),
						order.getOrderStatus(), order.getPaymentStatus(), order.getOrderItems()))
				.collect(Collectors.toList());
	}
}
