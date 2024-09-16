package com.cg.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entity.Product;
import com.cg.exception.ExceptionHandle;
import com.cg.service.ProductService;

@RestController

public class ProductController {
	
	@Autowired
	private ProductService prodServ;
	
	@PostMapping("/add")
	public void addProducts(@RequestBody Product product) {
		prodServ.addProducts(product);
	}
	
	@GetMapping("/find")
	public ResponseEntity<List<Product>>  getAllProducts() throws ExceptionHandle {
		List<Product> Products = prodServ.getAllProducts();
		ResponseEntity<List<Product>> product = new ResponseEntity<List<Product>>(Products, HttpStatus.OK);
		return product;
	}
	
	@GetMapping("/prodid/{prodId}")
	public ResponseEntity<Optional<Product>> getProductById(@PathVariable int prodId) throws ExceptionHandle {
		 Optional<Product> productById = prodServ.getProductById(prodId);
		 ResponseEntity< Optional<Product>> product = new ResponseEntity<Optional<Product>>(productById, HttpStatus.OK);
		 return product;
	}
	
    @GetMapping("/prodname/{prodName}")
	public ResponseEntity<Optional<Product>> getProductByName(@PathVariable String prodName) throws ExceptionHandle {
		Optional<Product> productByName = prodServ.getProductByName(prodName);
		ResponseEntity<Optional<Product>> product = new ResponseEntity<Optional<Product>>(productByName, HttpStatus.OK);
		return product;
	}
    @PutMapping("/update")
    public ResponseEntity<String> updateProducts(@RequestBody Product product) throws ExceptionHandle {
    	
		String updateProducts = prodServ.updateProducts(product);
		ResponseEntity<String> string = new ResponseEntity<String>(updateProducts, HttpStatus.OK);
		return string;
	}
    @DeleteMapping("/delete/{prodId}")
	public ResponseEntity<String> deleteProductById(@PathVariable int prodId) throws ExceptionHandle {
		String deleteProductById = prodServ.deleteProductById(prodId);	
		ResponseEntity<String> string = new ResponseEntity<String>(deleteProductById,HttpStatus.OK);
		return string;
	}
	@GetMapping("/cat/{category}")
    public ResponseEntity<List<Product>>getProductByCategory(@PathVariable String category) throws ExceptionHandle {
		 List<Product> productByCategory = prodServ.getProductByCategory(category);
		 ResponseEntity<List<Product>> product = new ResponseEntity<List<Product>>(productByCategory, HttpStatus.OK);
		 return product;
	}
    @GetMapping("/type/{prodType}")
	public ResponseEntity<List<Product>> getProductByType(@PathVariable String prodType) throws ExceptionHandle {
		 List<Product> productByType = prodServ.getProductByType(prodType);
		 ResponseEntity<List<Product>> product = new ResponseEntity<List<Product>>(productByType, HttpStatus.OK);
		 return product;
	}
	

}
