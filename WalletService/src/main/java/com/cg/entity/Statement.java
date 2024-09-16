package com.cg.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statementId;
 
    private String transactionType;  // E.g., 'Payment', 'Deposit', etc.
    private double amount;
    private Date date;
    private Integer orderId;
 
    // Many-to-one mapping with Ewallet
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walletId", nullable = false)
    private Ewallet ewallet;
    
}