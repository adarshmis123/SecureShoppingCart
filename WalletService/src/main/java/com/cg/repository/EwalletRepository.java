package com.cg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Ewallet;

@Repository
public interface EwalletRepository extends JpaRepository<Ewallet, Integer> {
    Optional<Ewallet> findByUserId(Integer userId);
    boolean existsByUserId(Integer userId);
}