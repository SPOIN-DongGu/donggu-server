package com.donggu.server.domain.pickupjoin.dto;

import com.donggu.server.domain.pickupjoin.domain.Status;
import com.donggu.server.domain.user.domain.Gender;
import com.donggu.server.domain.user.domain.Position;

public record PickupJoinResponseDto(
        String name,
        Gender gender,
        double height,
        double weight,
        Position position,
        Status status
) {
    public static PickupJoinResponseDto of(String fullName, Gender gender, double height, double weight, Position position, Status status) {
        return new PickupJoinResponseDto(fullName, gender, height, weight, position, status);
    }
}
