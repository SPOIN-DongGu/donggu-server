package com.donggu.server.domain.pickup.controller;

import com.donggu.server.domain.pickup.dto.PickupRequestDto;
import com.donggu.server.domain.pickup.dto.PickupResponseDto;
import com.donggu.server.domain.pickup.service.PickupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pickup")
@RequiredArgsConstructor
public class PickupController {

    private final PickupService pickupService;

    // [관리자] 픽업 게임 등록
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    @Operation(summary = "픽업 게임 생성", description = "관리자 전용 기능: 게임 정보를 기재해 픽업 게임을 생성합니다\n시작/종료 시각은 HH:mm:ss형식으로 작성")
    public ResponseEntity<Void> registerPickup(@RequestBody PickupRequestDto dto) {
        pickupService.registerPickup(dto);
        return ResponseEntity.ok().build();
    }

    // [관리자] 픽업 게임 수정
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{pickupId}")
    @Operation(summary = "픽업 게임 수정", description = "관리자 전용 기능: 등록된 픽업 게임의 정보를 수정합니다")
    public ResponseEntity<Void> updatePickup(@PathVariable Long pickupId, @RequestBody PickupRequestDto dto) {
        pickupService.updatePickup(pickupId, dto);
        return ResponseEntity.ok().build();
    }

    // [관리자] 픽업 게임 삭제
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{pickupId}")
    @Operation(summary = "픽업 게임 삭제", description = "관리자 전용 기능: 등록된 픽업 게임을 삭제합니다")
    public ResponseEntity<Void> deletePickup(@PathVariable Long pickupId) {
        pickupService.deletePickup(pickupId);
        return ResponseEntity.ok().build();
    }

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
