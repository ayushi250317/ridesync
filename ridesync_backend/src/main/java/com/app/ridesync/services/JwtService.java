package com.app.ridesync.services;

import java.security.Key;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY="e6cbbccd3c26a59bba8ee0f77e560f6be857b1093d734f4627ede0b367c1f8c1";

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken
            (Map<String, Object> extraClaims,
             UserDetails userDetails) {
        return Jwts
                .builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                .compact();
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token,UserDetails userDetails) {
        final String username=extractUserEmail(token);
        return (username.equals(userDetails.getUsername()));
    }
    private Key getSignInKey() {
        byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
