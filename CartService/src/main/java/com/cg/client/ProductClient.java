package com.cg.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cg.dto.Product;
import com.cg.dto.ProductDTO;

@Service
@FeignClient(name = "ProductService")
public interface ProductClient {

    @GetMapping("/product/prodid/{productId}")
    ResponseEntity<ProductDTO> getProductById(@PathVariable("productId") int productId) throws Exception;
}

