package com.donggu.server.domain.user.dto;

import com.donggu.server.domain.user.domain.Position;
import com.donggu.server.domain.user.domain.Gender;

public record UserJoinRequestDto(
        String username,
        String password,
        String fullName,
        String region,
        Gender sex,
        double height,
        double weight,
        Position position
) {
}
