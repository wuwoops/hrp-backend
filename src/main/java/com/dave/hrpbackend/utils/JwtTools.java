package com.dave.hrpbackend.utils;

import com.dave.hrpbackend.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;


public class JwtTools {
    private final SecretKey secretKey;
    private final JwtParser jwtParser;

    public JwtTools(String secretKeyStr) {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyStr.getBytes());
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }


    public Claims parseToken(String jwt) throws JwtException {
        return jwtParser.parseSignedClaims(jwt).getPayload();
    }

    public String createLoginAccessToken(CustomUserDetails user) {
        // 計算過期時間
        Date expirationMillis = Date.from(
                Instant.now().plus(Duration.ofHours(1)));
        // 準備 payload 內容
        Claims claims = Jwts.claims()
                .issuedAt(new Date())
                .expiration(expirationMillis)
                .add("username", user.getUsername())
                .add("authorities", user.getAuthorities())
                .add("nickname", user.getNickname())
                .add("id", user.getId())
                .build();

        // 簽名後產生 JWT
        return Jwts.builder()
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }
}
