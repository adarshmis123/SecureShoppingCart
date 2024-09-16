package com.cg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Ewallet;
import com.cg.entity.Statement;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Integer> {
    List<Statement> findByEwallet(Ewallet ewallet);
}