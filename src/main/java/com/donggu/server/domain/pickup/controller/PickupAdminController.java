package com.donggu.server.domain.pickup.controller;

import com.donggu.server.domain.pickup.dto.PickupRequestDto;
import com.donggu.server.domain.pickup.service.PickupAdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/pickup")
@RequiredArgsConstructor
public class PickupAdminController {

    private final PickupAdminService pickupAdminService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    @Operation(summary = "[관리자] 픽업 게임 생성", description = "게임 정보를 기재해 픽업 게임을 생성합니다(시간: HH:MM:SS)")
    public ResponseEntity<Void> registerPickup(@RequestBody PickupRequestDto dto) {
        pickupAdminService.registerPickup(dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{pickupId}")
    @Operation(summary = "[관리자] 픽업 게임 수정", description = "등록된 픽업 게임의 정보를 수정합니다")
    public ResponseEntity<Void> updatePickup(@PathVariable Long pickupId, @RequestBody PickupRequestDto dto) {
        pickupAdminService.updatePickup(pickupId, dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{pickupId}")
    @Operation(summary = "[관리자] 픽업 게임 삭제", description = "등록된 픽업 게임을 삭제합니다")
    public ResponseEntity<Void> deletePickup(@PathVariable Long pickupId) {
        pickupAdminService.deletePickup(pickupId);
        return ResponseEntity.ok().build();
    }
}
