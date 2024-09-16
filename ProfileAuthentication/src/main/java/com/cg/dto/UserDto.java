package com.cg.dto;

import java.time.LocalDate;
import java.util.List;

import com.cg.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private int profileId;
	private String username;
	private String emailId;
	private long mobileNumber;
	private String about;
	private LocalDate dateOfBirth;
	private String gender;
	private String password;
	private List<Address> addresses;
}
