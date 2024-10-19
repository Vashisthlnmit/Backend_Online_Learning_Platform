package com.onlineLearningPlatform.demo.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretkey;
    @Value("${application.security.jwt.expiration}")
    private long expiration;
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    public  <V, K> String generateToken(HashMap<String,Object> claims, UserDetails userDetails) {
        return buildToken(claims,userDetails,expiration);
    }
    private  String buildToken(HashMap<String,Object> extraclaims, UserDetails userDetails, long expiration) {
        var authorities=userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts
                .builder()
                .setClaims(extraclaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(getSignInKey())
                .setSubject(userDetails.getUsername())
                .claim("authorities",authorities)
                .compact();
    }
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }
    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSignInKey() {
        byte[] keyBytes= Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    public boolean isTokenvalid(String token, UserDetails user) {
        final String username=extractUsername(token);
        return  (username.equals(user.getUsername()) && !isTokenexpired(token));
    }
    private boolean isTokenexpired(String token) {
        return  extractExpiration(token).before(new Date());
    }
}
