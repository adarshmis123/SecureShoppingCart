package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.UserProfile;

public interface UserRepository extends JpaRepository<UserProfile, Integer> {
	UserProfile findByUsername(String username);
}