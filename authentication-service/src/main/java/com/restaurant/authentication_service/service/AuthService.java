package com.restaurant.authentication_service.service;

import org.springframework.stereotype.Service;

import com.restaurant.authentication_service.dto_data_transfer_object.RegisterRequest;
import com.restaurant.authentication_service.model.User;
import com.restaurant.authentication_service.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;


// para validar credenciais e gerar JWT
@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // constructor
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public String authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User " + username + " not found"));
        return "User authenticated: " + user.getUsername();
    }

    public boolean registerUser(RegisterRequest registerRequest) {
        
        if(userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return false; //user já existe
        }

        // cria um novo user com senha encriptada
        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(newUser);

        return true;
    }

    

}
