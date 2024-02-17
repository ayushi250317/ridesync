package com.app.ridesync.services;

import java.security.Key;

import org.springframework.stereotype.Service;

import com.app.ridesync.entities.User;

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

    public Integer extractUserId(String token) {
        return Integer.parseInt(extractClaim(token, Claims::getSubject));
    }

    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    public String generateToken
            (Map<String, Object> extraClaims,
             User user) {
        return Jwts
                .builder().setClaims(extraClaims)
                .setSubject(user.getUserId().toString())
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

    public boolean isTokenValid(String token,User user) {
        final Integer userId=extractUserId(token);
        return userId.equals(user.getUserId());
    }
    private Key getSignInKey() {
        byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
