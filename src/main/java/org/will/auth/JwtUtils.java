package org.will.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.will.Constants.Constants;
import org.will.model.entity.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.ResourceBundle;

public class JwtUtils {

    private static final int expiration = 24 * 60 * 60 * 1000;

    public static String generateToken(User user) {

        String secret = Constants.getSecret();

        Date expirationDate = new Date(System.currentTimeMillis() + expiration);

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .id(user.getPassword())
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public static UserLoginBean getUserLoginBean(User user) {
        String secret = Constants.getSecret();

        Date expirationDate = new Date(System.currentTimeMillis() + expiration);

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .id(user.getPassword())
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(key)
                .compact();

        return new UserLoginBean(user.getUsername(), user.getId(),  expirationDate, user.getRole().name(), token);
    }

    public static String getUsernameFromToken(String token) {

        String secret = Constants.getSecret();

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }



}
