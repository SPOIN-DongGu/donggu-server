package com.donggu.server.domain.pickup.controller;

import com.donggu.server.domain.pickup.dto.PickupResponseDto;
import com.donggu.server.domain.pickup.service.PickupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pickup")
@RequiredArgsConstructor
public class PickupController {

    private final PickupService pickupService;

    // [일반/관리자] 픽업 게임 전체 조회
    @GetMapping("/")
    @Operation(summary = "픽업 게임 전체 조회", description = "전체 접근 기능: 등록된 픽업 게임 목록을 조회합니다")
    public ResponseEntity<List<PickupResponseDto>> getAllPickup() {
        return ResponseEntity.ok(pickupService.getAllPickup());
    }

    // [일반/관리자] 픽업 게임 게임별 조회
    @GetMapping("/{pickupId}")
    @Operation(summary = "픽업 게임 상세 조회", description = "전체 접근 기능: 특정 픽업 게임의 상세 정보를 조회합니다")
    public ResponseEntity<PickupResponseDto> getPickup(@PathVariable Long pickupId) {
        return ResponseEntity.ok(pickupService.getPickup(pickupId));
    }
}
