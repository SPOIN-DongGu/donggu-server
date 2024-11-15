package com.donggu.server.domain.pickup.dto;

import com.donggu.server.domain.user.domain.Gender;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

// 픽업 게임 조회 시 반환 하는 dto
public record PickupResponseDto(
        @NotNull LocalDate date,
        @NotNull LocalTime startTime,
        LocalTime endTime,
        String region,
        @NotNull String location,
        Gender gender,
        int gameType,
        Long price,
        @NotNull Integer maxParticipant,
        Integer currentParticipant
) {
    public static PickupResponseDto of(LocalDate date, LocalTime startTime, LocalTime endTime, String region, String location, Gender gender, int gameType, Long price, Integer maxParticipant, Integer currentParticipant) {
        return new PickupResponseDto(date, startTime, endTime, region, location, gender, gameType, price, maxParticipant, currentParticipant);
    }
}
