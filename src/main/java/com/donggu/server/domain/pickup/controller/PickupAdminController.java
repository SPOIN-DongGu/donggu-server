package com.donggu.server.domain.pickup.controller;

import com.donggu.server.domain.pickup.dto.PickupRequestDto;
import com.donggu.server.domain.pickup.service.PickupAdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class PickupAdminController {

    private final PickupAdminService pickupAdminService;

    // [관리자] 픽업 게임 등록
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/pickup")
    @Operation(summary = "픽업 게임 생성", description = "관리자 전용 기능: 게임 정보를 기재해 픽업 게임을 생성합니다\n시작/종료 시각은 HH:mm:ss형식으로 작성")
    public ResponseEntity<Void> registerPickup(@RequestBody PickupRequestDto dto) {
        pickupAdminService.registerPickup(dto);
        return ResponseEntity.ok().build();
    }

    // [관리자] 픽업 게임 수정
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{pickupId}")
    @Operation(summary = "픽업 게임 수정", description = "관리자 전용 기능: 등록된 픽업 게임의 정보를 수정합니다")
    public ResponseEntity<Void> updatePickup(@PathVariable Long pickupId, @RequestBody PickupRequestDto dto) {
        pickupAdminService.updatePickup(pickupId, dto);
        return ResponseEntity.ok().build();
    }

    // [관리자] 픽업 게임 삭제
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{pickupId}")
    @Operation(summary = "픽업 게임 삭제", description = "관리자 전용 기능: 등록된 픽업 게임을 삭제합니다")
    public ResponseEntity<Void> deletePickup(@PathVariable Long pickupId) {
        pickupAdminService.deletePickup(pickupId);
        return ResponseEntity.ok().build();
    }
}
