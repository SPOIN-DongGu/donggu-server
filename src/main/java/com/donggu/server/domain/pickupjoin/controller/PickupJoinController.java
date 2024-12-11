package com.donggu.server.domain.pickupjoin.controller;

import com.donggu.server.domain.pickupjoin.dto.PickupJoinRequestDto;
import com.donggu.server.domain.pickupjoin.service.PickupJoinService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pickup/{pickupId}/apply")
@RequiredArgsConstructor
public class PickupJoinController {

    private final PickupJoinService pickupJoinService;

    @PostMapping("/")
    @Operation(summary = "[유저] 픽업 게임 신청", description = "신청자 정보를 기재해 픽업 게임을 신청합니다")
    public ResponseEntity<Void> applyPickup(@PathVariable Long pickupId, @RequestBody PickupJoinRequestDto dto) {
        pickupJoinService.applyPickup(pickupId, dto);
        return ResponseEntity.ok().build();
    }
}
