package com.cg.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.DTO.OrderResponse;
import com.cg.DTO.PaymentResponse;
import com.cg.exception.OrderException;
import com.cg.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    
    @GetMapping("/hello")
    @PreAuthorize("hasAnyAuthority('Admin','User')")
	public ResponseEntity<String> hello() {
		return new ResponseEntity<>("i am in order service", HttpStatus.OK);
	}

    @PostMapping("/place/{productId}")
    @PreAuthorize("hasAnyAuthority('User')")
    public ResponseEntity<PaymentResponse> placeOrder(@PathVariable int productId,Principal principal) {
        return orderService.placeOrder(productId,principal);
    }

    @GetMapping("/get/{orderId}")
    @PreAuthorize("hasAnyAuthority('Admin','User')")
    public OrderResponse getOrderById(@PathVariable int orderId) {
        return orderService.getOrderById(orderId);
    }
    
    
    
    @DeleteMapping("/delete/{orderId}/{userId}")
    @PreAuthorize("hasAnyAuthority('User')")
    public void deleteOrder(@PathVariable int orderId,@PathVariable int userId) throws OrderException {
    	orderService.deleteOrder(orderId, userId);
    }
    
    @GetMapping("/displayall")
    @PreAuthorize("hasAnyAuthority('Admin','User')")
   public List<OrderResponse> viewAllOrders() throws OrderException{
    return orderService.viewAllOrders();
    }
    
    @GetMapping("/display/{userId}")
    @PreAuthorize("hasAnyAuthority('Admin','User')")
    public List<OrderResponse> viewOrders(@PathVariable int userId) throws OrderException{
		return orderService.viewOrderByUserId(userId);
    	
    }
    
    @DeleteMapping("/cancel/{orderId}/{userId}")
    @PreAuthorize("hasAnyAuthority('User')")
    public void cancelOrder(@PathVariable int orderId,@PathVariable int userId) throws OrderException{
    	orderService.cancelOrder(orderId, userId);
    }
}
