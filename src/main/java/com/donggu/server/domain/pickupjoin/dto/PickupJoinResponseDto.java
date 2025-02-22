package com.donggu.server.domain.pickupjoin.dto;

import com.donggu.server.domain.pickupjoin.domain.PickupJoin;
import com.donggu.server.domain.pickupjoin.domain.Status;
import com.donggu.server.domain.user.domain.Gender;
import com.donggu.server.domain.user.domain.Position;

public record PickupJoinResponseDto(
        Long pickupJoinId,
        String name,
        int age,
        Gender gender,
        double height,
        double weight,
        Position position,
        Status status
) {
    public static PickupJoinResponseDto from(PickupJoin pickupJoin) {
        return new PickupJoinResponseDto(pickupJoin.getId(), pickupJoin.getName(), pickupJoin.getAge(), pickupJoin.getGender(), pickupJoin.getHeight(), pickupJoin.getWeight(), pickupJoin.getPosition(), pickupJoin.getStatus());
    }
}
