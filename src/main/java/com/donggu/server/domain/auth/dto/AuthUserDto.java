package com.donggu.server.domain.auth.dto;

public record AuthUserDto(
        String email
) {
    public static AuthUserDto of(String email) {
        return new AuthUserDto(email);
    }
}
