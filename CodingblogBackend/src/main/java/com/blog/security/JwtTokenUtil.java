package com.blog.security;

import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

    private String secretKey = "F772F35D1497CC960164CE284D2A85EC202D0F6D976AD117EE6A8B60C4692DC2"; // Set your secret key here
    private long validityInMilliseconds = 3600000; // 1 hour

    // Generate Token
    @SuppressWarnings("deprecation")
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Validate Token
    public boolean validateToken(String token) {      
        SignatureAlgorithm sa = SignatureAlgorithm.HS256;
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        JwtParser jwtParser = Jwts.parser()
            .verifyWith(secretKeySpec)
            .build();
        try {
            jwtParser.parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Get Username from Token
    public String getUsernameFromToken(String token) {

    SignatureAlgorithm sa = SignatureAlgorithm.HS256;
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        JwtParser jwtParser = Jwts.parser()
            .verifyWith(secretKeySpec)
            .build();
        try {
            Jws<Claims> jws= (Jws<Claims>) jwtParser.parse(token);
            Claims claims = jws.getBody();
             return claims.getSubject(); // Assuming the username is stored in the "subject" claim
            
        } catch (Exception e) {
            return null;
        }

      
    }
}

