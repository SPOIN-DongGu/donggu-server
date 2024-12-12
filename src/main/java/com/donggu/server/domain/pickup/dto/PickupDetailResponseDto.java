package com.donggu.server.domain.pickup.dto;

import com.donggu.server.domain.pickupjoin.domain.Status;
import com.donggu.server.domain.user.domain.Gender;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record PickupDetailResponseDto(
        @NotNull Long id,
        @NotNull LocalDate date,
        @NotNull LocalTime startTime,
        LocalTime endTime,
        String region,
        @NotNull String location,
        Gender gender,
        int gameType,
        Long price,
        @NotNull Integer maxParticipant,
        Integer currentParticipant,
        Status status
) {
    public static PickupDetailResponseDto of(Long id, LocalDate date, LocalTime startTime, LocalTime endTime, String region, String location, Gender gender, int gameType, Long price, Integer maxParticipant, Integer currentParticipant, Status status) {
        return new PickupDetailResponseDto(id, date, startTime, endTime, region, location, gender, gameType, price, maxParticipant, currentParticipant, status);
    }
}
