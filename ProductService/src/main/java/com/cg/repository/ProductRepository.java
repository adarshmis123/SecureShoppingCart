package com.cg.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	 Optional<Product> findByProductName(String prodName);
	 List<Product> findByCategory(String category);
	 List<Product> findByProductType(String prodType);
}
