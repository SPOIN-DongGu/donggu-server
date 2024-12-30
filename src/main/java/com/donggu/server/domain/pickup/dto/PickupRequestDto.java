package com.donggu.server.domain.pickup.dto;

import com.donggu.server.domain.user.domain.Gender;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

// 픽업 게임 등록 시 받는 dto
public record PickupRequestDto(
        @NotNull LocalDate date,
        @NotNull LocalTime startTime,
        LocalTime endTime,
        String region,
        @NotNull String location,
        Gender gender,
        int gameType,
        Long price,
        @NotNull Integer maxParticipant
) {
}
