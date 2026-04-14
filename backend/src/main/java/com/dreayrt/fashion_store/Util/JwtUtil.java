package com.dreayrt.fashion_store.Util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET="dreayrt-individual-project-project-individual-dreayrt";
    private final long EXPIRATION_TIME=30 * 60 * 1000;
    private final long REFRESH_TIME_TOKEN=7L * 24 * 60 * 60 * 1000 ;
    public String generateAccessToken(String username,String role) {


        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .claim("type","access_token")
                .claim("role",role)
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }
    public String generateRefreshToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+REFRESH_TIME_TOKEN))
                .claim("type","refresh_token")
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }
    public String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public String extractType(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("type", String.class);
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }
}
