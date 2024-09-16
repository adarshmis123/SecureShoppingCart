package com.cg.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.entity.Address;
import com.cg.entity.UserProfile;
//import com.cg.entity.UserCredential;
//import com.cg.repository.UserCredentialRepository;
import com.cg.repository.UserProfileRepository;

@Service
public class AuthService {

	@Autowired
	private UserProfileRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	public String saveUser(UserProfile credential) {
		credential.setPassword(passwordEncoder.encode(credential.getPassword()));
		Optional<UserProfile> byId = repository.findById(credential.getProfileId());
		if(byId.isPresent()) {
			return "user already added in the data base";
		}
		repository.save(credential);
		return "user added to the system";
	}

	public String generateToken(String username) {
		return jwtService.generateToken(username);
	}

	public void validateToken(String token) {
		jwtService.validateToken(token);
	}

}
