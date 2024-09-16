package com.cg.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.CartItemRequestDTO;
import com.cg.exception.CartServiceException;
import com.cg.model.Cart;
import com.cg.service.CartService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {

	@Autowired
	CartService cartService;

	@GetMapping("/hello")
	@PreAuthorize("hasAnyAuthority('Admin','User')")
	public ResponseEntity<String> hello() {
		return new ResponseEntity<>("i am in cart", HttpStatus.OK);
	}

//    @PostMapping("/add/{userId}/{productId}/{quantity}")
//    public ResponseEntity<Cart> addItemToCartUpdated(@PathVariable int userId,@PathVariable int productId,@PathVariable int quantity,@RequestHeader("Authorization") String token) { 
//    	Cart itemToCart = cartService.addItemToCartUpdated(userId, productId, quantity, token);
//    	return new ResponseEntity<>(itemToCart,HttpStatus.OK);
//    } 
//    
//    
//
//    @DeleteMapping("/deleteCartItem/{userId}/{itemCartId}")
//    public ResponseEntity<Cart> deleteItemFromCart(@PathVariable int userId,@PathVariable int itemCartId) { 
//    	Cart cart = cartService.deleteItemFromCart(userId, itemCartId);
//    	return new ResponseEntity<>(cart,HttpStatus.OK);
//    } 
//    
//    @DeleteMapping("/deleteCartItemBasedOnItemQuantity/{userId}/{itemCartId}/{quantity}")
//    public ResponseEntity<Cart> deleteItemFromCartBasedOnItemQuantity(@PathVariable int userId,@PathVariable int itemCartId,@PathVariable int quantity) { 
//    	Cart cart = cartService.deleteItemFromCartBasedOnItemQuantity(userId, itemCartId,quantity);
//    	return new ResponseEntity<>(cart,HttpStatus.OK);
//    } 
//    
//    @GetMapping("/getCartByUserId/{userId}")
//    public ResponseEntity<Cart> getCartByUserId(@PathVariable int userId) { 
//    	Cart cart = cartService.getCartByUserId(userId);
//    	return new ResponseEntity<>(cart,HttpStatus.OK);
//    } 
//    
//    
//    @GetMapping("/getAll")
//    public ResponseEntity<List<Cart>> getAllCart() { 
//    	List<Cart> allCart = cartService.getAllCart();
//    	return new ResponseEntity<>(allCart,HttpStatus.OK);
//    } 

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('User')")
	public ResponseEntity<Cart> addItemToCartUpdated(@RequestBody CartItemRequestDTO request, Principal pricipal)
			throws Exception {
		Cart itemToCart = cartService.addItemToCartUpdated(request, pricipal);
		return new ResponseEntity<>(itemToCart, HttpStatus.OK);
	}

	// by using this method whole item will be deleted
	@DeleteMapping("/deleteCartItem/{userId}/{itemCartId}")
	@PreAuthorize("hasAnyAuthority('User')")
	public ResponseEntity<Cart> deleteItemFromCart(@PathVariable int userId, @PathVariable int itemCartId)
			throws CartServiceException {
		Cart cart = cartService.deleteItemFromCart(userId, itemCartId);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	// by using this method only quantity will be deleted
	@DeleteMapping("/deleteCartItemBasedOnItemQuantity/{itemCartId}/{quantity}")
	@PreAuthorize("hasAnyAuthority('User')")
	public ResponseEntity<Cart> deleteItemFromCartBasedOnItemQuantity(Principal principal, @PathVariable int itemCartId,
			@PathVariable int quantity) throws CartServiceException {
		Cart cart = cartService.deleteItemFromCartBasedOnItemQuantity(principal, itemCartId, quantity);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	@GetMapping("/getCartByUserId/{userId}")
	@PreAuthorize("hasAnyAuthority('User')")
	public ResponseEntity<Cart> getCartByUserId(@PathVariable int userId) throws CartServiceException {
		log.error("i am inside getCartByUserId method");
		Cart cart = cartService.getCartByUserId(userId);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	@PreAuthorize("hasAnyAuthority('Admin')")
	public ResponseEntity<List<Cart>> getAllCart() {
		List<Cart> allCart = cartService.getAllCart();
		return new ResponseEntity<>(allCart, HttpStatus.OK);
	}

}
