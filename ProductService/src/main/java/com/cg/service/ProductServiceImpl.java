package com.cg.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.Product;
import com.cg.exception.ProductException;
import com.cg.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired
	private ProductRepository productRepository;

	@Override
	public void addProducts(Product product) {
		 productRepository.save(product);
		 logger.info("added");
	}

	@Override
	public List<Product> getAllProducts() throws ProductException {
		List<Product> all =  productRepository.findAll();
		if(all.isEmpty()) {
			logger.error("product is empty");
			throw new ProductException("no product are present in it");
		}
		else{
			logger.info("displayed products");
			return all;
		}
	}

	@Override
	public Optional<Product> getProductById(int prodId) throws ProductException {
		Optional<Product> byId = productRepository.findById(prodId);
		if(byId.isPresent()) {
			logger.info("product is displayed by using id");
			return byId;
		}
		else {
			logger.error("product not found");
			throw new ProductException("product Id not found");
		}	
	}

	@Override
	public Optional<Product> getProductByName(String prodName) throws ProductException {
		Optional<Product> byProductName = productRepository.findByProductName(prodName);
		if(byProductName.isPresent()) {
			logger.info("product is displayed by using product name");
			return byProductName;
		}
		else {
			logger.error("product name is not found");
			throw new ProductException("product name not found");
		}
	}

	@Override
	public String updateProducts(Product product) throws ProductException {
		if(product==null || product.getProductId()==0) {
			logger.error("no id is found");
			throw new ProductException("product and productId not found");
		}
		Optional<Product> byId = productRepository.findById(product.getProductId());
		if(byId.isPresent()) {
			logger.error("product is already found");
			throw new ProductException("product is already present");
		}
		 productRepository.save(product);
		 logger.info("updating the product");

		 return "updated successfully";
			}

	@Override
	public String deleteProductById(int prodId) throws ProductException  {	
		if(productRepository.existsById(prodId)) {
			productRepository.deleteById(prodId);
			logger.info("product is deleting successfully");
			return "deleted successfully";
			
		}
		else {
			logger.error("product id is not found");
			throw new ProductException("product Id is not present");
		}
	}

	@Override
	public List<Product> getProductByCategory(String category) throws ProductException {
		List<Product> productByCategory = productRepository.findByCategory(category);
		if(productByCategory.isEmpty()) {
			logger.error("product category is not found");
			throw new ProductException("no products are present in this category");
		}
		else {
			logger.info("product is displayed by category");
			return productByCategory;
		}
	}

	@Override
	public List<Product> getProductByType(String prodType) throws ProductException {
		List<Product> productByType = productRepository.findByProductType(prodType);
		if(productByType.isEmpty()) {
			logger.error("product type is not found");
			throw new ProductException("no products are present in this type");
		}
		else {
			logger.info("product is displayed by type");
			return productByType;
		}
	
	}
}
