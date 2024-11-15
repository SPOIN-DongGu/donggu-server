package com.donggu.server.domain.pickupjoin.controller;

import com.donggu.server.domain.pickupjoin.domain.Status;
import com.donggu.server.domain.pickupjoin.dto.PickupJoinRequestDto;
import com.donggu.server.domain.pickupjoin.dto.PickupJoinResponseDto;
import com.donggu.server.domain.pickupjoin.service.PickupJoinService;
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

    // 픽업 게임 신청과 거절을 관리
    // 신청, (관리자)게임 별 신청 목록 조회, (관리자)거절

    // 픽업 게임 참여 신청
    @PostMapping("/")
    public ResponseEntity<Void> applyPickup(@PathVariable Long pickupId, @RequestBody Long userId, @RequestBody PickupJoinRequestDto dto) {
        pickupJoinService.applyPickup(pickupId, userId, dto);
        return ResponseEntity.ok().build();
    }

    // 픽업 게임 참여 신청 현황 게임별 조회
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<PickupJoinResponseDto>> getAppliedUsersByPickup(@PathVariable Long pickupId) {
        return ResponseEntity.ok(pickupJoinService.getAppliedUsersByPickup(pickupId));
    }

    // 픽업 게임 참여 신청 승인/거절
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{pickupUserId}")
    public ResponseEntity<Void> handleUserApply(@PathVariable Long pickupId, @PathVariable Long pickupUserId, @RequestBody Status status) {
        pickupJoinService.handleUserApply(pickupUserId, status);
        return ResponseEntity.ok().build();
    }
}
