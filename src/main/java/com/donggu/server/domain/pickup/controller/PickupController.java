package com.donggu.server.domain.pickup.controller;

import com.donggu.server.domain.pickup.dto.PickupResponseDto;
import com.donggu.server.domain.pickup.service.PickupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pickup")
@RequiredArgsConstructor
public class PickupController {

    private final PickupService pickupService;

    @GetMapping
    @Operation(summary = "[전체] 픽업 게임 전체 조회", description = "등록된 픽업 게임 목록을 조회합니다")
    public ResponseEntity<List<PickupResponseDto>> getAllPickup() {
        return ResponseEntity.ok(pickupService.getAllPickup());
    }

    @GetMapping("/detail/{pickupId}")
    @Operation(summary = "[유저] 픽업 게임 상세 조회", description = "특정 픽업 게임의 상세 정보를 조회합니다")
    public ResponseEntity<PickupResponseDto> getPickup(@PathVariable("pickupId") Long pickupId) {
        return ResponseEntity.ok(pickupService.getPickup(pickupId));
    }
}
