package com.donggu.server.domain.auth.dto;

public record AuthUserDto(
        String name,
        String email
) {
    public static AuthUserDto of(String name, String email) {
        return new AuthUserDto(name, email);
    }
}
