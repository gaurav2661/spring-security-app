package org.example.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY ="ukHb3dxkzLnsjZ6M6N1Jr25mGhYL/OhRRUByxURlxjMwXnoKoxUzY281QncNapM4";
    public String extractUsername(String token) {
        return extractClaims(token ,Claims::getSubject);
    }
    public String generateToken(Map<String,Object> extraClaim,
                                UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaim)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);

    }
    public boolean isTokenValid(String token,UserDetails userDetails) {
        final String username = extractUsername(token);
        return (Objects.equals(username, userDetails.getUsername()) && isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).after(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token,Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims,T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
