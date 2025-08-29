package io.github.nitishc.grievance.user_service.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value(("${app.static.value}"))
    private String SECRET_KEY;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username, String role) {
        Map<String, String> claims = new HashMap<>();   //set role in user token
        claims.put("role",role);
        return createToken(claims, username);
    }
    public String generateToken(String username, String role, String department) {
        Map<String, String> claims = new HashMap<>();
        claims.put("role",role);
        claims.put("department", department);
        return createToken(claims, username);
    }

    private String createToken(Map<String, String> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60* 150)) // 15 minutes expiration time
                .signWith(getSigningKey())
                .compact();
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserEmail(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

//    public Date extractExpiration(String token) {
//        return extractAllClaims(token).getExpiration();
//    }
    public Boolean isTokenExpired(String token) {
        return !extractAllClaims(token).getExpiration().before(new Date());
    }

}
