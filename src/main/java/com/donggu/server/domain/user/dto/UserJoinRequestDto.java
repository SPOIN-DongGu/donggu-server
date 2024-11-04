package com.donggu.server.domain.user.dto;

import com.donggu.server.domain.user.domain.Position;
import com.donggu.server.domain.user.domain.Role;

public record UserJoinRequestDto(
        String name,
        String email,
        String password,
        Role role,
        String region,
        double height,
        double weight,
        Position position
) {
}
