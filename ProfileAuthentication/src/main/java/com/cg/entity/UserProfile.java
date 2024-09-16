package com.cg.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "userlogin")
@Builder
public class UserProfile {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@NotNull(message = "Profile Id is required")
	//@Min(value = 1, message = "Profile Id must be greaterthan zero")
	private int profileId;
	//@NotBlank(message = "username is required")
	//@Size(max = 30, message = "Username must be less than or equal to 30 characters")
	private String username;
	//@Email(message="Valid email is required")
	private String emailId;
	//@Column(unique=true)
	//@NotNull(message = "Mobile number is required")
	//@Min(value=6000000000L, message="invalid mobile number")
	//@Max(value=9999999999L,message="invalid mobile number")
	private long mobileNumber;
	private String about;
	private LocalDate dateOfBirth;
	private String gender;
	private String role;
	private String password;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="profileId")
    private List<Address> addresses;
	
	
	
}
