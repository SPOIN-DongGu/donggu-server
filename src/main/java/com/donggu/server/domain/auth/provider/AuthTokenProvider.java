package com.donggu.server.domain.auth.provider;

import com.donggu.server.domain.auth.token.AuthToken;
import com.donggu.server.domain.user.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

@Component
public class AuthTokenProvider {

    @Value("${jwt.tokenExpiry}")
    private Long expiredMs;
    private final SecretKey secretKey;

    @Autowired
    public AuthTokenProvider(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public AuthToken createAccessToken(User user) {
        if (user.getUsername()==null) {
            return AuthToken.of("");
        }

        return createAccessToken(user.getUsername());
    }

    public AuthToken createAccessToken(String username) {
        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();

        return AuthToken.of(token);
    }
}
