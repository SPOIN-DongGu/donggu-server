package com.donggu.server.domain.pickupjoin.controller;

import com.donggu.server.domain.pickupjoin.domain.Status;
import com.donggu.server.domain.pickupjoin.dto.PickupJoinRequestDto;
import com.donggu.server.domain.pickupjoin.dto.PickupJoinResponseDto;
import com.donggu.server.domain.pickupjoin.service.PickupJoinService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pickup/{pickupId}/apply")
@RequiredArgsConstructor
public class PickupJoinController {

    private final PickupJoinService pickupJoinService;

    // 픽업 게임 참여 신청
    @PostMapping("/")
    @Operation(summary = "픽업 게임 신청", description = "회원 전용 기능: 신청자 정보를 기재해 픽업 게임을 신청합니다")
    public ResponseEntity<Void> applyPickup(@PathVariable Long pickupId, @RequestBody Long userId, @RequestBody PickupJoinRequestDto dto) {
        pickupJoinService.applyPickup(pickupId, userId, dto);
        return ResponseEntity.ok().build();
    }

    // [관리자] 픽업 게임 참여 신청 현황 게임별 조회
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    @Operation(summary = "픽업 게임 신청자 조회", description = "관리자 전용 기능: 픽업 게임별 신청자 목록을 조회합니다")
    public ResponseEntity<List<PickupJoinResponseDto>> getAppliedUsersByPickup(@PathVariable Long pickupId) {
        return ResponseEntity.ok(pickupJoinService.getAppliedUsersByPickup(pickupId));
    }

    // [관리자] 픽업 게임 참여 신청 승인/거절
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{pickupUserId}")
    @Operation(summary = "픽업 게임 신청 처리", description = "관리자 전용 기능: 참여 신청에 대해 승인 혹은 거절합니다")
    public ResponseEntity<Void> handleUserApply(@PathVariable Long pickupId, @PathVariable Long pickupUserId, @RequestBody Status status) {
        pickupJoinService.handleUserApply(pickupUserId, status);
        return ResponseEntity.ok().build();
    }
}
