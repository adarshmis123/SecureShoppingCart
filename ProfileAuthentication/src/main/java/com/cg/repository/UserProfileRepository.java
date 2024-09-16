package com.cg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

	Optional<UserProfile> findUserIdByUsername(String username);

	Optional<UserProfile> getUserProfileByUsername(String username);

	UserProfile findByUsername(String username);

	String deleteByUsername(String username);

	UserProfile getUserProfileByProfileId(int profileId);

}
