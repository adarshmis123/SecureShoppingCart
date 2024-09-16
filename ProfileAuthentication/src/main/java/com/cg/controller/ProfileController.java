package com.cg.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.UserDto;
import com.cg.entity.UserProfile;
import com.cg.exception.UserProfileException;
import com.cg.service.UserService;

@RestController
@RequestMapping("/profileController")
public class ProfileController {

	@Autowired
	UserService userService;

	@GetMapping("/hello")
	@PreAuthorize("hasAnyAuthority('Admin')")
	public ResponseEntity<?> hello() {
		String str = "hii profile controller";
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasAnyAuthority('Admin')")
	public ResponseEntity<?> deleteByUsername(Principal principal) throws UserProfileException {
		String userName = principal.getName();
		userService.deleteByUsername(userName);
		return new ResponseEntity<String>("user is deleted", HttpStatus.OK);
	}

	@GetMapping("/getByName/{username}")
	@PreAuthorize("hasAnyAuthority('Admin','User')")
	public UserProfile getUserProfileByUsername(@PathVariable String username) throws Exception {
		return userService.getUserProfileByUsername(username);
	}

	@GetMapping("/getAllProfiles")
	@PreAuthorize("hasAnyAuthority('Admin')")
	public List<UserProfile> getAllProfiles() throws UserProfileException {
		return userService.getAllProfiles();
	}

	@GetMapping("/getById/{profileId}")
	public UserProfile getUserProfileByProfileId(@PathVariable int profileId) throws UserProfileException {
		return userService.getUserProfileByProfileId(profileId);
	}

	@PostMapping("/update")
	@PreAuthorize("hasAnyAuthority('user')")
	public String updateProfile(@RequestBody UserDto userDto) {
		return userService.updateProfile(userDto);
	}

}
