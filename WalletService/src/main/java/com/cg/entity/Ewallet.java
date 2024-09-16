package com.cg.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Ewallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer walletId;
 
    private Integer userId;  // Reference to the user
    private double currentBalance;
 
    // One-to-many mapping with Statement
    @OneToMany(mappedBy = "ewallet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Statement> statements;
 
}