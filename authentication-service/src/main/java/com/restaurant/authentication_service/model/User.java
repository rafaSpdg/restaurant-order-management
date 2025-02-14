package com.restaurant.authentication_service.model;

import jakarta.persistence.*;


@Entity //define que a classe representa uma tabela na base de dados
@Table(name = "users") //nomeia a tabela como 'users'
public class User {
    

    @Id // chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // gera automaticamente id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;


    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    

    
}
