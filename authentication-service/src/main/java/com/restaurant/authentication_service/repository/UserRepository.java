package com.restaurant.authentication_service.repository;

import com.restaurant.authentication_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    
    // Optional é uma classe de Java8 que representa valores que podem ou nao estar presentes, evitando NullPointerException
    Optional<User> findByUsername(String username);

    // outra opção: User findByUsername(String username); -> retorna diretamente o user.
    // Aqui lançará exceção EmptyResultDataAccessException, e teremos de tratar a exceção
    // Podemos tambem usar @Nullable -> pode retornar null -> temos de tratar do NullPointerException
}
