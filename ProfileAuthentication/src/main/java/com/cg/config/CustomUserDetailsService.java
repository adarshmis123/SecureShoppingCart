package com.cg.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.cg.entity.UserProfile;
import com.cg.repository.UserProfileRepository;

//import com.cg.entity.UserCredential;
//import com.cg.repository.UserCredentialRepository;
@Component
public class CustomUserDetailsService implements UserDetailsService {

	 @Autowired
	    private UserProfileRepository repository;

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        Optional<UserProfile> credential = repository.findUserIdByUsername(username);
	        System.out.println(credential.isPresent());
	        return credential.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + username));
	    }
}
