package com.donggu.server.domain.pickupjoin.controller;

import com.donggu.server.domain.pickupjoin.domain.Status;
import com.donggu.server.domain.pickupjoin.dto.PickupJoinResponseDto;
import com.donggu.server.domain.pickupjoin.service.PickupJoinAdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/{pickupId}/apply")
@RequiredArgsConstructor
public class PickupJoinAdminController {

    private final PickupJoinAdminService pickupJoinAdminService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    @Operation(summary = "[관리자] 픽업 게임 신청자 조회", description = "픽업 게임별 신청자 목록을 조회합니다")
    public ResponseEntity<List<PickupJoinResponseDto>> getAppliedUsersByPickup(@PathVariable Long pickupId) {
        return ResponseEntity.ok(pickupJoinAdminService.getAppliedUsersByPickup(pickupId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{pickupJoinId}")
    @Operation(summary = "[관리자] 픽업 게임 신청 처리", description = "참여 신청에 대해 승인 혹은 거절합니다")
    public ResponseEntity<Void> handleUserApply(@PathVariable Long pickupId, @PathVariable Long pickupJoinId, @RequestBody Status status) {
        pickupJoinAdminService.handleUserApply(pickupId, pickupJoinId, status);
        return ResponseEntity.ok().build();
    }
}
