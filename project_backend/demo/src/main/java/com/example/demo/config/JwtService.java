package com.example.demo.config;

import com.example.demo.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final String SECRET_KEY = "fWx9uZ2t5q8JrL0mN3xV6bYpA2dG5hQs";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 8; // 8 saat

    // Yeni token oluşturucu: User objesini kullanarak user_isn bilgisini ekler
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_isn", user.getId());  // 👈 user_isn token'a ekleniyor
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername()) // sub = username
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject(); // sub
    }

    public Integer extractUserIsn(String token) {
        return extractAllClaims(token).get("user_isn", Integer.class);
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }
}
