package com.donggu.server.domain.user.dto;

import com.donggu.server.domain.user.domain.Position;
import com.donggu.server.domain.user.domain.Sex;
import jakarta.validation.constraints.Email;

public record UserJoinRequestDto(
        String name,
        @Email String email,
        String password,
        String region,
        Sex sex,
        double height,
        double weight,
        Position position
) {
}
