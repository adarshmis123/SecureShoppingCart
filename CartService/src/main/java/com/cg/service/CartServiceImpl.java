package com.cg.service;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cg.client.ProductClient;
import com.cg.client.UserProfileClient;
import com.cg.dto.CartItemRequestDTO;
import com.cg.dto.Product;
import com.cg.dto.ProductDTO;
import com.cg.dto.UserProfileDto;
import com.cg.exception.CartServiceException;
import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.repository.CartRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartServiceImpl implements CartService  {
	@Autowired
	private CartRepository cartRepository;
//	@Autowired
//	private RestTemplate restTemplate;

//	public Cart addItemToCart(int userId, int productId, int quantity, String token) {
//		try {
//			if (token != null && token.startsWith("Bearer ")) {
//				token = token.substring(7);
//			}
//			HttpHeaders headers = new HttpHeaders();
//			headers.set("Authorization", "Bearer " + token);
//			HttpEntity<String> entity = new HttpEntity<>(headers);
//
//			String url = "http://productservice/product/prodid/" + productId;
//			ResponseEntity<ProductDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, ProductDTO.class);
//			ProductDTO productDto = response.getBody();
//			System.out.println(productDto);
//
//			// create or fetch existing cart
//			Cart cart = cartRepository.findByUserId(userId).orElse(new Cart());
//			cart.setUserId(userId);
//
//			// create cart item and add it
//			CartItem cartItem = new CartItem();
//			cartItem.setCart(cart);
//			cartItem.setProductId(productId);
//			cartItem.setQuantity(quantity);
//			cartItem.setPrice(productDto.getPrice() * quantity);
//
//			cart.getCartItems().add(cartItem);
//			cart.setTotalPrice(cart.getTotalPrice() + cartItem.getPrice());
//
//			return cartRepository.save(cart);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}
//		return new Cart();
//	}
//	
//
//	public Cart addItemToCartUpdated(int userId, int productId, int quantity, String token) {
//		try {
//			if (token != null && token.startsWith("Bearer ")) {
//				token = token.substring(7);
//			}
//			HttpHeaders headers = new HttpHeaders();
//			headers.set("Authorization", "Bearer " + token);
//			HttpEntity<String> entity = new HttpEntity<>(headers);
//
//			String url = "http://productservice/product/prodid/" + productId;
//			ResponseEntity<ProductDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, ProductDTO.class);
//			ProductDTO productDto = response.getBody();
//			System.out.println(productDto);
//
//			// create or fetch existing cart
//			Cart cart = cartRepository.findByUserId(userId).orElse(new Cart());
//			cart.setUserId(userId);
//
//			// checking if the product already exist in the cart
//
//			CartItem existinCartItem = cart.getCartItems().stream().filter(item -> item.getProductId() == productId)
//					.findFirst().orElse(null);
//
//			if (existinCartItem != null) {
//				// update the quantity and price of the existing cart item
//				existinCartItem.setQuantity(existinCartItem.getQuantity() + quantity);
//				existinCartItem.setPrice(existinCartItem.getPrice() + productDto.getPrice() * quantity);
//			} else {
//				// create cart item and add it
//				CartItem cartItem = new CartItem();
//				cartItem.setCart(cart);
//				cartItem.setProductId(productId);
//				cartItem.setQuantity(quantity);
//				cartItem.setPrice(productDto.getPrice() * quantity);
//
//				cart.getCartItems().add(cartItem);
//				cart.setTotalPrice(cart.getTotalPrice() + cartItem.getPrice());
//			}
//
//			// update the total price of the cart
//			cart.setTotalPrice(cart.getCartItems().stream().mapToDouble(e -> e.getPrice()).sum());
//
//			return cartRepository.save(cart);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}
//		return new Cart();
//	}
//
//	public Cart deleteItemFromCart(int userId, int cartItemId) {
//		// fetch cart by user id
//
//		Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
//
//		// find and remove the CartItem
//		CartItem cartItem = cart.getCartItems().stream().filter(e -> e.getId() == cartItemId).findFirst()
//				.orElseThrow(() -> new RuntimeException("Item not found"));
//
//		cart.getCartItems().remove(cartItem);
//		cart.setTotalPrice(cart.getTotalPrice() - cartItem.getPrice());
//		return cartRepository.save(cart);
//	}
//
//	// this is updated method which will work based on quantity
//	public Cart deleteItemFromCartBasedOnItemQuantity(int userId, int cartItemId, int quantity) {
//		// fetch cart by user id
//
//		Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
//
//		// find and remove the CartItem
//		CartItem cartItem = cart.getCartItems().stream().filter(e -> e.getId() == cartItemId).findFirst()
//				.orElseThrow(() -> new RuntimeException("Item not found"));
//
//		if (cartItem.getQuantity() > quantity) {
//			// reduce the quantity and price of the cart item
//			double pricePerUnit = cartItem.getPrice() / cartItem.getQuantity();
//			cartItem.setQuantity(cartItem.getQuantity() - quantity);
//			cartItem.setPrice(cartItem.getPrice() - pricePerUnit * quantity);
//		} else {
//			// remove the cart item if the quantity to remove
//			// is grater or equal to the current quantity
//			cart.getCartItems().remove(cartItem);
//		}
//
//		cart.setTotalPrice(cart.getCartItems().stream().mapToDouble(e -> e.getPrice()).sum());
//		return cartRepository.save(cart);
//	}
//	
//	
//	public Cart getCartByUserId(int userId) {
//		return cartRepository.findByUserId(userId).orElseThrow(()-> new RuntimeException("Cart not found"));
//	}
//	
//	public List<Cart> getAllCart(){
//		return cartRepository.findAll();
//	}

//	public Cart addItemToCartUpdated(CartItemRequestDTO request, String token, Principal principal) throws CartServiceException {
//		String name = principal.getName();
//		log.info(name);
//
//		try {
//			if (token != null && token.startsWith("Bearer ")) {
//				token = token.substring(7);
//			}
//			HttpHeaders headers = new HttpHeaders();
//			headers.set("Authorization", "Bearer " + token);
//			HttpEntity<String> entity = new HttpEntity<>(headers);
//
//			String url = "http://productservice/product/prodid/" + request.getProductId();
//			ResponseEntity<ProductDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, ProductDTO.class);
//			ProductDTO productDto = response.getBody();
//			if(productDto==null) {
//				 throw new CartServiceException("you are getting null form product service");
//			}
//			log.info(productDto.toString());
//
//			String urlForUser = "http://profileauthentication/profileController/getByName/" + name;
//			ResponseEntity<UserProfileDto> userResponse = restTemplate.exchange(urlForUser, HttpMethod.GET, entity,
//					UserProfileDto.class);
//			UserProfileDto userData = userResponse.getBody();
//			if(userData==null) {
//				 throw new CartServiceException("you are getting null form user service");
//			}
//			System.out.println(userData);
//			int userId = userData.getProfileId();
//
//			log.info(userId + "");
//
//			// create or fetch existing cart
//			Cart cart = cartRepository.findByUserId(userId).orElse(new Cart());
//			cart.setUserId(userId);
//
//			// checking if the product already exist in the cart
//
//			CartItem existinCartItem = cart.getCartItems().stream()
//					.filter(item -> item.getProductId() == request.getProductId()).findFirst().orElse(null);
//
//			if (existinCartItem != null) {
//				// update the quantity and price of the existing cart item
//				existinCartItem.setQuantity(existinCartItem.getQuantity() + request.getQuantity());
//				existinCartItem.setPrice(existinCartItem.getPrice() + productDto.getPrice() * request.getQuantity());
//			} else {
//				// create cart item and add it
//				CartItem cartItem = new CartItem();
//				cartItem.setCart(cart);
//				cartItem.setProductId(request.getProductId());
//				cartItem.setQuantity(request.getQuantity());
//				cartItem.setPrice(productDto.getPrice() * request.getQuantity());
//
//				cart.getCartItems().add(cartItem);
//				cart.setTotalPrice(cart.getTotalPrice() + cartItem.getPrice());
//			}
//
//			// update the total price of the cart
//			cart.setTotalPrice(cart.getCartItems().stream().mapToDouble(e -> e.getPrice()).sum());
//
//			return cartRepository.save(cart);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}
//		return new Cart();
//	}

	@Autowired
	private ProductClient productClient;

	@Autowired
	private UserProfileClient userProfileClient;

	@Override
	public Cart addItemToCartUpdated(CartItemRequestDTO request, Principal principal)
			throws Exception {
		String name = principal.getName();
		log.info(name);

		// Call the User Profile service to get user details
		UserProfileDto userProfile = userProfileClient.getUserProfileByUsername(name);

		// Call the Product service to get product details
		ResponseEntity<ProductDTO> produc1 = productClient.getProductById(request.getProductId());
	
		ProductDTO product = produc1.getBody();
		System.out.println(product);
		if(product!=null) {
		Cart cart = cartRepository.findByUserId(userProfile.getProfileId()).orElse(new Cart());
		cart.setUserId(userProfile.getProfileId());

		CartItem existingItem = cart.getCartItems().stream()
				.filter(item -> item.getProductId() == request.getProductId()).findFirst().orElse(null);

		if (existingItem != null) {
			updateExistingCartItem(existingItem, request.getQuantity(), product.getPrice());
		} else {
			addNewCartItem(cart, request, product.getPrice());
		}

		cart.setTotalPrice(cart.getCartItems().stream().mapToDouble(CartItem::getPrice).sum());
		return cartRepository.save(cart);

//			if (existinCartItem != null) {
//				// update the quantity and price of the existing cart item
//				existinCartItem.setQuantity(existinCartItem.getQuantity() + request.getQuantity());
//				existinCartItem.setPrice(existinCartItem.getPrice() + productDto.getPrice() * request.getQuantity());
//			} else {
//				// create cart item and add it
//				CartItem cartItem = new CartItem();
//				cartItem.setCart(cart);
//				cartItem.setProductId(request.getProductId());
//				cartItem.setQuantity(request.getQuantity());
//				cartItem.setPrice(productDto.getPrice() * request.getQuantity());
//
//				cart.getCartItems().add(cartItem);
//				cart.setTotalPrice(cart.getTotalPrice() + cartItem.getPrice());
//			}

		// update the total price of the cart
		// cart.setTotalPrice(cart.getCartItems().stream().mapToDouble(e ->
		// e.getPrice()).sum());

		// return cartRepository.save(cart);

		//return new Cart();
		}
		else
			throw new CartServiceException("product not available");
		}
		

	private void updateExistingCartItem(CartItem existingItem, int quantity, double pricePerUnit) {
		existingItem.setQuantity(existingItem.getQuantity() + quantity);
		existingItem.setPrice(existingItem.getPrice() + pricePerUnit * quantity);
	}

	private void addNewCartItem(Cart cart, CartItemRequestDTO request, double productPrice) {
		CartItem cartItem = new CartItem();
		cartItem.setCart(cart);
		cartItem.setProductId(request.getProductId());
		cartItem.setQuantity(request.getQuantity());
		cartItem.setPrice(productPrice * request.getQuantity());

		cart.getCartItems().add(cartItem);
		cart.setTotalPrice(cart.getTotalPrice() + cartItem.getPrice());
	}

	
	
@Override
	public Cart deleteItemFromCart(int userId, int cartItemId) throws CartServiceException {
		// fetch cart by user id

		Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new CartServiceException("Cart not found"));

		// find and remove the CartItem
		CartItem cartItem = cart.getCartItems().stream().filter(e -> e.getId() == cartItemId).findFirst()
				.orElseThrow(() -> new CartServiceException("Item not found"));

		cart.getCartItems().remove(cartItem);
		cart.setTotalPrice(cart.getTotalPrice() - cartItem.getPrice());
		return cartRepository.save(cart);
	}

	// this is updated method which will work based on quantity


    @Override
	public Cart deleteItemFromCartBasedOnItemQuantity(Principal principal, int cartItemId, int quantity)
			throws CartServiceException {
		// fetch cart by user id

		String name = principal.getName();
//
//		if (token != null && token.startsWith("Bearer ")) {
//			token = token.substring(7);
//		}
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Authorization", "Bearer " + token);
//		HttpEntity<String> entity = new HttpEntity<>(headers);

//		String urlForUser = "http://profileauthentication/profileController/getByName/" + name;
//		ResponseEntity<UserProfileDto> userResponse = restTemplate.exchange(urlForUser, HttpMethod.GET, entity,
//				UserProfileDto.class);
//		UserProfileDto userData = userResponse.getBody();
//		System.out.println(userData);
//		int userId = userData.getProfileId();
		
		// Call the User Profile service to get user details
		UserProfileDto userProfile = userProfileClient.getUserProfileByUsername(name);
		

		Cart cart = cartRepository.findByUserId(userProfile.getProfileId()).orElseThrow(() -> new CartServiceException("Cart not found"));

		// find and remove the CartItem
		CartItem cartItem = cart.getCartItems().stream().filter(e -> e.getId() == cartItemId).findFirst()
				.orElseThrow(() -> new CartServiceException("Item not found"));

		if (cartItem.getQuantity() > quantity) {
			// reduce the quantity and price of the cart item
			double pricePerUnit = cartItem.getPrice() / cartItem.getQuantity();
			cartItem.setQuantity(cartItem.getQuantity() - quantity);
			cartItem.setPrice(cartItem.getPrice() - pricePerUnit * quantity);
		} else {
			// remove the cart item if the quantity to remove
			// is grater or equal to the current quantity
			cart.getCartItems().remove(cartItem);
		}

		cart.setTotalPrice(cart.getCartItems().stream().mapToDouble(e -> e.getPrice()).sum());
		return cartRepository.save(cart);
	}

    
    @Override
	public Cart getCartByUserId(int userId) throws CartServiceException {
		return cartRepository.findByUserId(userId).orElseThrow(() -> new CartServiceException("Cart not found"));
	}

    @Override
	public List<Cart> getAllCart() {
		return cartRepository.findAll();
	}

}
