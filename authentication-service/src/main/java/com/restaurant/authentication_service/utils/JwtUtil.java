package com.restaurant.authentication_service.utils;

import java.util.Date;
import java.util.function.Function;


import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

// Classe que gera e valida tokens JWT

@Component
public class JwtUtil {

    // temos de usar um objeto Key pois signWith() está deprecado para receber uma
    // String. Ao inves tem de ser uma Key (key)
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // gera uma chave segura com pelo menos 256 bits

    //gera token com duração de 1h
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //extrai o username do token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // verifica se o token ainda é válido
    public boolean isTokenValid(String token) {
        return extractExpiration(token).after(new Date());
    }

    //claim é um conjunto de pares chave : valor (ex.: role : admin)
    private <T> T extractClaim(String token, Function <Claims, T> claimsResolver) {
        
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claimsResolver.apply(claims);
    }
}
