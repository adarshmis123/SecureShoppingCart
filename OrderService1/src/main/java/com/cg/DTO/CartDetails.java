package com.cg.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartDetails {
	//private int cartId;
	//private int userId;
	private List<CartItem> cartItems;
	private double totalPrice;

//	@Override
//	public String toString() {
//		return "CartDetails{" + "cartId=" + cartId + ", userId=" + userId + ", cartItems=" + cartItems + ", totalPrice="
//				+ totalPrice + '}';
//	}
}
