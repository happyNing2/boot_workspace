package com.ex.basic.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("test1234")
    private String secretKey;
    private final long expirationMs = 1000 * 60 * 60;

    // 토큰 생성
    public String generateToken(String username, String role) {
        System.out.println("secretKey : " + secretKey);
        Claims claims = Jwts.claims();
        claims.put("username", username);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getClaims(token);
        System.out.println("username : " + claims.get("username", String.class));
        return claims.get("username", String.class);
    }
}
