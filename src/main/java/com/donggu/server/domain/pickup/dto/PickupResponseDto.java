package com.donggu.server.domain.pickup.dto;

import com.donggu.server.domain.user.domain.Gender;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PickupResponseDto(
        @NotNull LocalDateTime dateTime,
        String region,
        @NotNull String location,
        Gender gender,
        Long price,
        @NotNull Integer maxParticipant,
        Integer currentParticipant
) {
    public static PickupResponseDto of(LocalDateTime dateTime, String region, String location, Gender gender, Long price, Integer maxParticipant, Integer currentParticipant) {
        return new PickupResponseDto(dateTime, region, location, gender, price, maxParticipant, currentParticipant);
    }
}