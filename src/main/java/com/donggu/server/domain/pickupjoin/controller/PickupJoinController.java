package com.donggu.server.domain.pickupjoin.controller;

import com.donggu.server.domain.pickup.dto.PickupDetailResponseDto;
import com.donggu.server.domain.pickupjoin.dto.PickupJoinRequestDto;
import com.donggu.server.domain.pickupjoin.service.PickupJoinService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pickup")
@RequiredArgsConstructor
public class PickupJoinController {

    private final PickupJoinService pickupJoinService;

    @GetMapping("/join/{userId}")
    @Operation(summary = "[유저] 신청한 픽업 게임 목록 조회", description = "자신이 신청한 픽업 게임의 목록을 조회합니다")
    public ResponseEntity<List<PickupDetailResponseDto>> getAllAppliedPickup(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(pickupJoinService.getAllAppliedPickup(userId));
    }

    @PostMapping("/{pickupId}/apply")
    @Operation(summary = "[유저] 픽업 게임 신청", description = "신청자 정보를 기재해 픽업 게임을 신청합니다")
    public ResponseEntity<Void> applyPickup(@PathVariable("pickupId") Long pickupId, @RequestBody PickupJoinRequestDto dto) {
        pickupJoinService.applyPickup(pickupId, dto);
        return ResponseEntity.ok().build();
    }
}
