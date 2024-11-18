package com.donggu.server.domain.pickupjoin.dto;

import com.donggu.server.domain.user.domain.Gender;
import com.donggu.server.domain.user.domain.Position;

public record PickupJoinRequestDto(
        String name,
        Gender gender,
        double height,
        double weight,
        Position position
) {
}