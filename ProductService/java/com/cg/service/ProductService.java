package com.cg.service;

import java.util.List;
import java.util.Optional;

import com.cg.entity.Product;
import com.cg.exception.ExceptionHandle;

public interface ProductService {
	
	void addProducts(Product product);
	List<Product> getAllProducts() throws ExceptionHandle;
	Optional<Product> getProductById(int prodId) throws ExceptionHandle;
	Optional<Product> getProductByName(String prodName) throws ExceptionHandle;
	String updateProducts(Product product) throws ExceptionHandle;
	String deleteProductById(int prodId) throws ExceptionHandle;
	List<Product> getProductByCategory(String category) throws ExceptionHandle;
	List<Product> getProductByType(String prodType) throws ExceptionHandle;
	

}
