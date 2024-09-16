package com.cg.service;

import java.util.List;
import java.util.Optional;

import com.cg.entity.Product;
import com.cg.exception.ProductException;

public interface ProductService {
	
	void addProducts(Product product);
	List<Product> getAllProducts() throws ProductException;
	Optional<Product> getProductById(int prodId) throws ProductException;
	Optional<Product> getProductByName(String prodName) throws ProductException;
	String updateProducts(Product product) throws ProductException;
	String deleteProductById(int prodId) throws ProductException;
	List<Product> getProductByCategory(String category) throws ProductException;
	List<Product> getProductByType(String prodType) throws ProductException;
	

}
