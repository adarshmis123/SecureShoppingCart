package com.cg.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.Product;
import com.cg.exception.ExceptionHandle;
import com.cg.repository.ProductRepository;

@Service
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
	public List<Product> getAllProducts() throws ExceptionHandle {
		List<Product> all =  productRepository.findAll();
		if(all.isEmpty()) {
			logger.error("product is empty");
			throw new ExceptionHandle("no product are present in it");
		}
		else{
			logger.info("displayed products");
			return all;
		}
	}

	@Override
	public Optional<Product> getProductById(int prodId) throws ExceptionHandle {
		Optional<Product> byId = productRepository.findById(prodId);
		if(byId.isPresent()) {
			logger.info("product is displayed by using id");
			return byId;
		}
		else {
			logger.error("product not found");
			throw new ExceptionHandle("product Id not found");
		}	
	}

	@Override
	public Optional<Product> getProductByName(String prodName) throws ExceptionHandle {
		Optional<Product> byProductName = productRepository.findByProductName(prodName);
		if(byProductName.isPresent()) {
			logger.info("product is displayed by using product name");
			return byProductName;
		}
		else {
			logger.error("product name is not found");
			throw new ExceptionHandle("product name not found");
		}
	}

	@Override
	public String updateProducts(Product product) throws ExceptionHandle {
		if(product==null || product.getProductId()==0) {
			logger.error("no id is found");
			throw new ExceptionHandle("product and productId not found");
		}
		Optional<Product> byId = productRepository.findById(product.getProductId());
		if(byId.isPresent()) {
			logger.error("product is already found");
			throw new ExceptionHandle("product is already present");
		}
		 productRepository.save(product);
		 logger.info("updating the product");

		 return "updated successfully";
			}

	@Override
	public String deleteProductById(int prodId) throws ExceptionHandle  {	
		if(productRepository.existsById(prodId)) {
			productRepository.deleteById(prodId);
			logger.info("product is deleting successfully");
			return "deleted successfully";
			
		}
		else {
			logger.error("product id is not found");
			throw new ExceptionHandle("product Id is not present");
		}
	}

	@Override
	public List<Product> getProductByCategory(String category) throws ExceptionHandle {
		List<Product> productByCategory = productRepository.findByCategory(category);
		if(productByCategory.isEmpty()) {
			logger.error("product category is not found");
			throw new ExceptionHandle("no products are present in this category");
		}
		else {
			logger.info("product is displayed by category");
			return productByCategory;
		}
	}

	@Override
	public List<Product> getProductByType(String prodType) throws ExceptionHandle {
		List<Product> productByType = productRepository.findByProductType(prodType);
		if(productByType.isEmpty()) {
			logger.error("product type is not found");
			throw new ExceptionHandle("no products are present in this type");
		}
		else {
			logger.info("product is displayed by type");
			return productByType;
		}
	
	}
}
