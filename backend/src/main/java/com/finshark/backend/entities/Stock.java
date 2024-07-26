package com.finshark.backend.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private double purchase;

    private double dividend;

    private String industry;

    private Long marketCap;
    
    private String companyName;

    @ManyToMany(mappedBy = "stocks")
    private List<AppUser> appUsers;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.REMOVE)
    private List<Comment> comments;
    
}
