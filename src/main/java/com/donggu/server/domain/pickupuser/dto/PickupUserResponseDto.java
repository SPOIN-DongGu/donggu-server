package com.donggu.server.domain.pickupuser.dto;

import com.donggu.server.domain.pickupuser.domain.Status;
import com.donggu.server.domain.user.domain.Gender;
import com.donggu.server.domain.user.domain.Position;

public record PickupUserResponseDto(
        String fullName,
        Gender gender,
        double height,
        double weight,
        Position position,
        Status status
) {
    public static PickupUserResponseDto of(String fullName, Gender gender, double height, double weight, Position position, Status status) {
        return new PickupUserResponseDto(fullName, gender, height, weight, position, status);
    }
}
