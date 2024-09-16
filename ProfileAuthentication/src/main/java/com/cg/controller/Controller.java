package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.AuthRequest;
import com.cg.dto.ResponseDto;
import com.cg.entity.UserProfile;
import com.cg.repository.UserProfileRepository;
//import com.cg.entity.UserCredential;
//import com.cg.repository.UserCredentialRepository;
import com.cg.service.AuthService;
@RestController
@RequestMapping("/auth")
public class Controller {

	  @Autowired
	    private AuthService service;
	    @Autowired
	    private UserProfileRepository userRepo;
	    @Autowired
	    private AuthenticationManager authenticationManager;

	    @PostMapping("/register")
	    public String addNewUser(@RequestBody UserProfile user) {
	        return service.saveUser(user);
	    }

	    @PostMapping("/login")
	    public ResponseDto getToken(@RequestBody AuthRequest authRequest) {
	    	System.out.println("yes .."+authRequest.getUsername()+"  "+authRequest.getPassword());
	        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
	        System.out.println(authenticate.isAuthenticated());
	        if (authenticate.isAuthenticated()) {
	        	String token=
	        	service.generateToken(authRequest.getUsername());
	        	UserProfile user=
	        	userRepo.findUserIdByUsername(authRequest.getUsername()).get();
	        	ResponseDto resDto=new ResponseDto();
	        	resDto.setToken(token);
	        	resDto.setRole(user.getRole());
	        	return resDto;
	        } else {
	            throw new RuntimeException("invalid access");
	        }
	    }

	    @GetMapping("/validate")
	    public String validateToken(@RequestParam("token") String token) {
	        service.validateToken(token);
	        return "Token is valid";
	    }
	   
}
