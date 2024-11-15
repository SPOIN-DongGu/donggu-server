package com.donggu.server.domain.pickupuser.controller;

import com.donggu.server.domain.pickupuser.service.PickupUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pickup")
@RequiredArgsConstructor
public class PickupUserController {

    private final PickupUserService pickupUserService;

    // 픽업 게임 신청과 거절을 관리
    // 신청, 게임 별 신청 목록 조회, 거절
    // 픽업 게임 조회 시 신청 상태는 고민을 좀..

    @PostMapping("/{pickupId}")
    public ResponseEntity<Void> applyPickup(@PathVariable String pickupId, @RequestBody Long userId) {
        pickupUserService.applyPickup(Long.valueOf(pickupId), userId);
        return ResponseEntity.ok().build();
    }
}
