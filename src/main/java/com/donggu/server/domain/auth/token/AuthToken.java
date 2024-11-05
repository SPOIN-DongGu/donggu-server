package com.donggu.server.domain.auth.token;

public record AuthToken(
        String token
) {
    public static AuthToken of(String token) {
        return new AuthToken(token);
    }
}
