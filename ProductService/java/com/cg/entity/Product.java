package com.cg.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
	
	@Id
    private int productId;
	@Min(value = 2, message = "it should be greater than two characters")
	@Max(value = 30,message = "it should be less than 30 characters")
	@NotBlank(message = "it should not be empty")
    private String productType;
	@Min(value = 2, message = "it should be greater than two characters")
	@Max(value = 30,message = "it should be less than 30 characters")
	@NotBlank(message = "it should not be empty")
    private String productName;
	@Min(value = 2, message = "it should be greater than two characters")
	@Max(value = 30,message = "it should be less than 30 characters")
	@NotBlank(message = "it should not be empty")
    private String category;
    private double rating;
	@Min(value = 2, message = "it should be greater than two characters")
	@Max(value = 30,message = "it should be less than 30 characters")
	@NotBlank(message = "it should not be empty")
    private String review;
    private double price;
	@Min(value = 2, message = "it should be greater than two characters")
	@Max(value = 30,message = "it should be less than 30 characters")
	@NotBlank(message = "it should not be empty")
    private String description;
	@Min(value = 2, message = "it should be greater than two characters")
	@Max(value = 30,message = "it should be less than 30 characters")
	@NotBlank(message = "it should not be empty")
    private String specification;
}

