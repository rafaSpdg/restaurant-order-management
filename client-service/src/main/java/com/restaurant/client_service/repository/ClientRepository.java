package com.restaurant.client_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.client_service.model.Client;
import java.util.Optional;


public interface ClientRepository extends JpaRepository <Client, Long> {
    
    Optional<Client> findByEmail(String email);
}
