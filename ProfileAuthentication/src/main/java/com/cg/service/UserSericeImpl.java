package com.cg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.dto.UserDto;
import com.cg.entity.UserProfile;
import com.cg.exception.UserProfileException;
import com.cg.repository.UserProfileRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserSericeImpl implements UserService {

	@Autowired
	UserProfileRepository userProfileRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String deleteByUsername(String username) {
		userProfileRepository.deleteByUsername(username);
		return "user is deleted";

	}

	@Override
	public UserProfile getUserProfileByUsername(String username) throws UserProfileException {
		Optional<UserProfile> optionalUserProfile = userProfileRepository.getUserProfileByUsername(username);
		if (optionalUserProfile.isPresent()) {
			return optionalUserProfile.get();
		} else {
			throw new UserProfileException("User not found with username: " + username);
		}
	}

	@Override
	public List<UserProfile> getAllProfiles() throws UserProfileException {
		// TODO Auto-generated method stub
		List<UserProfile> list = userProfileRepository.findAll();
		if (list.isEmpty()) {
			throw new UserProfileException("There are no profiles in the table");
		} else {
			return list;
		}
	}

	@Override
	public UserProfile getUserProfileByProfileId(int profileId) throws UserProfileException {
		// TODO Auto-generated method stub
		UserProfile userProfile = userProfileRepository.getUserProfileByProfileId(profileId);
		if (userProfile == null) {
			throw new UserProfileException("profile Id not found");
		} else {
			return userProfile;
		}
	}

	@Override
	public String updateProfile(UserDto userDto) {
		// TODO Auto-generated method stub
		int id = userDto.getProfileId();
		Optional<UserProfile> byId = userProfileRepository.findById(id);
		if (byId.isPresent()) {
			UserProfile profile = UserProfile.builder().profileId(userDto.getProfileId())
					.username(userDto.getUsername()).emailId(userDto.getEmailId())
					.mobileNumber(userDto.getMobileNumber()).about(userDto.getAbout())
					.dateOfBirth(userDto.getDateOfBirth()).gender(userDto.getGender()).role("user")
					.password(passwordEncoder.encode(userDto.getPassword())).addresses(userDto.getAddresses()).build();
			userProfileRepository.save(profile);
			return "user profile updated successfully";

		} else {
			return "user not found";
		}
	}
}
