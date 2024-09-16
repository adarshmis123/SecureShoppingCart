package com.cg.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "userlogin")
public class UserProfile {

	@Id
	private int profileId;
	private String username;
	private String emailId;
	private long mobileNumber;
	private String about;
	private LocalDate dateOfBirth;
	private String gender;
	private String role;
	private String password;
}
