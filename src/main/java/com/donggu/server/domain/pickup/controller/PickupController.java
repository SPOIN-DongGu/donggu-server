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
@RequestMapping("/pickup/admin")
@RequiredArgsConstructor
public class PickupController {

    private final PickupService pickupService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<Void> registerPickup(@RequestBody PickupRequestDto dto) {
        pickupService.registerPickup(dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{pickupId}")
    public ResponseEntity<Void> deletePickup(@PathVariable String pickupId) {
        pickupService.deletePickup(Long.valueOf(pickupId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{pickupId}")
    public ResponseEntity<PickupResponseDto> getPickup(@PathVariable String pickupId) {
        return ResponseEntity.ok(pickupService.getPickup(Long.valueOf(pickupId)));
    }

    @GetMapping("/")
    public ResponseEntity<List<PickupResponseDto>> getAllPickup() {
        return ResponseEntity.ok(pickupService.getAllPickup());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{pickupId}")
    public ResponseEntity<Void> updatePickup(@PathVariable String pickupId, @RequestBody PickupRequestDto dto) {
        pickupService.updatePickup(Long.valueOf(pickupId), dto);
        return ResponseEntity.ok().build();
    }
}
