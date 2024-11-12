package com.donggu.server.domain.pickup.controller;

import com.donggu.server.domain.pickup.dto.PickupRegisterRequestDto;
import com.donggu.server.domain.pickup.service.PickupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pickup")
@RequiredArgsConstructor
public class PickupController {

    private final PickupService pickupService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<Void> registerPickup(@RequestBody PickupRegisterRequestDto dto) {
        pickupService.registerPickup(dto);
        return ResponseEntity.ok().build();
    }
}
