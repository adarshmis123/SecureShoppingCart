package com.cg.service;

import java.security.Principal;
import java.util.List;

import com.cg.dto.CartItemRequestDTO;
import com.cg.exception.CartServiceException;
import com.cg.model.Cart;

public interface CartService {
	
	
	public Cart addItemToCartUpdated(CartItemRequestDTO request, Principal principal) throws Exception;
	
	public Cart deleteItemFromCart(int userId, int cartItemId) throws CartServiceException;
	
	public Cart deleteItemFromCartBasedOnItemQuantity(Principal principal, int cartItemId, int quantity)
			throws CartServiceException ;
	
	public Cart getCartByUserId(int userId) throws CartServiceException;
	
	public List<Cart> getAllCart();
	

}
