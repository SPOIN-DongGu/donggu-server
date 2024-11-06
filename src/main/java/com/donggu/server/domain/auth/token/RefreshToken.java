package com.donggu.server.domain.auth.token;

public record RefreshToken(
        String token
) {
    public static RefreshToken of(String token) {
        return new RefreshToken(token);
    }
}
