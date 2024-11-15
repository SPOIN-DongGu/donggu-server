package com.donggu.server.domain.pickup.controller;

import com.donggu.server.domain.pickup.dto.PickupRequestDto;
import com.donggu.server.domain.pickup.dto.PickupResponseDto;
import com.donggu.server.domain.pickup.service.PickupService;
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
    public ResponseEntity<Void> registerPickup(@RequestBody PickupRequestDto dto) {
        pickupService.registerPickup(dto);
        return ResponseEntity.ok().build();
    }

    // [관리자] 픽업 게임 수정
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{pickupId}")
    public ResponseEntity<Void> updatePickup(@PathVariable Long pickupId, @RequestBody PickupRequestDto dto) {
        pickupService.updatePickup(pickupId, dto);
        return ResponseEntity.ok().build();
    }

    // [관리자] 픽업 게임 삭제
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{pickupId}")
    public ResponseEntity<Void> deletePickup(@PathVariable Long pickupId) {
        pickupService.deletePickup(pickupId);
        return ResponseEntity.ok().build();
    }

    // [일반/관리자] 픽업 게임 전체 조회
    @GetMapping("/")
    public ResponseEntity<List<PickupResponseDto>> getAllPickup() {
        return ResponseEntity.ok(pickupService.getAllPickup());
    }

    // [일반/관리자] 픽업 게임 게임별 조회
    @GetMapping("/{pickupId}")
    public ResponseEntity<PickupResponseDto> getPickup(@PathVariable Long pickupId) {
        return ResponseEntity.ok(pickupService.getPickup(pickupId));
    }
}
