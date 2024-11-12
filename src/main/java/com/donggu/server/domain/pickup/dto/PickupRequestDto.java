package com.donggu.server.domain.pickup.dto;

import com.donggu.server.domain.user.domain.Gender;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PickupRequestDto(
        @NotNull LocalDateTime dateTime,
        String region,
        @NotNull String location,
        Gender gender,
        Long price,
        @NotNull Integer maxParticipant
) {
}
