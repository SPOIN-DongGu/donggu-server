package com.donggu.server.domain.user.dto;

public record UserJoinRequestDto(
        String username,
        String password
) {
}
