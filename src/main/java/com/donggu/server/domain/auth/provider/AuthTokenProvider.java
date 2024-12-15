package com.donggu.server.domain.auth.provider;

import com.donggu.server.domain.auth.token.AccessToken;
//import com.donggu.server.domain.auth.token.RefreshToken;
import com.donggu.server.domain.user.domain.Role;
import com.donggu.server.domain.user.domain.User;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
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

    @Value("${jwt.accessExpirationTime}")
    private Long ACCESS_EXPIRATION_TIME;
    /*@Value("${jwt.refreshExpirationTime}")
    private Long REFRESH_EXPIRATION_TIME;*/
    private final SecretKey secretKey;

    @Autowired
    public AuthTokenProvider(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public AccessToken generateAccessToken(User user) {
        if (user.getEmail()==null) {
            return AccessToken.of("");
        }

        return generateAccessToken(user.getEmail(), user.getRole());
    }

    public AccessToken generateAccessToken(String username, Role role) {
        String token = Jwts.builder()
                .subject(username)
                .claim("type", "access")
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();

        return AccessToken.of(token);
    }

    /*public RefreshToken createRefreshToken(User user) {
        if (user.getEmail()==null) {
            return RefreshToken.of("");
        }

        return createRefreshToken(user.getEmail());
    }

    public RefreshToken createRefreshToken(String username) {
        String token = Jwts.builder()
                .subject(username)
                .claim("type", "refresh")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();

        return RefreshToken.of(token);
    }

    public long getRefreshExpirationTime() {
        return REFRESH_EXPIRATION_TIME;
    }*/

    public Boolean validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration()
                    .after(new Date());
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
