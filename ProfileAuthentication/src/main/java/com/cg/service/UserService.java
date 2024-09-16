package com.cg.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.dto.UserDto;
import com.cg.entity.UserProfile;
import com.cg.exception.UserProfileException;

@Service
public interface UserService {

	String deleteByUsername(String username) throws UserProfileException;

	UserProfile getUserProfileByUsername(String username) throws UserProfileException;

	List<UserProfile> getAllProfiles() throws UserProfileException;

	UserProfile getUserProfileByProfileId(int profileId) throws UserProfileException;

	String updateProfile(UserDto userDto);
}
