package com.finshark.backend.entities;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "app_user")
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;

    private String token;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "portfolio", 
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id") }, 
            inverseJoinColumns = {@JoinColumn(name = "stock_id", referencedColumnName = "id") })
    private List<Stock> stocks = new ArrayList<>();
}