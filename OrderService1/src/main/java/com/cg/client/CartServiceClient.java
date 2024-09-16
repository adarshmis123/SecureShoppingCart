package com.cg.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cg.DTO.CartDetails;

@FeignClient(name = "cartservice")
public interface CartServiceClient {
    @GetMapping("/cart/getCartByUserId/{userId}")
    CartDetails getCartByUserId(@PathVariable int userId);
}
