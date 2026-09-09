package com.example.hellomyme.global.utils;


import com.example.hellomyme.global.apipayload.domain.AuthErrorStatus;
import com.example.hellomyme.global.apipayload.exception.GeneralException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
public class JWTUtil {

    private final SecretKey key;
    private final long accessTokenExpireTime;

    public JWTUtil(@Value("${jwt.token.secret}") String secret,
                   @Value("${jwt.token.expiration.access}") long accessTokenExpireTime) {

        this.key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

        this.accessTokenExpireTime = accessTokenExpireTime;

    }

    //JWT 발급
    public String createAccessToken(Long userId, String username, String role){

        Date now = new Date();
        Date expireDate = new Date(now.getTime() + accessTokenExpireTime);

        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .claim("role", role)
                .claim("tokenType", "ACCESS")
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(key)
                .compact();
    }

    public Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //
    public void validateToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new GeneralException(AuthErrorStatus.TOKEN_MISSING);
        }
        try {
            getClaims(token);
        } catch (ExpiredJwtException e) {
            throw new GeneralException(AuthErrorStatus.EXPIRED_TOKEN);
        } catch (JwtException e) {
            throw new GeneralException(AuthErrorStatus.INVALID_TOKEN);
        }
    }


}
