package com.donggu.server.domain.pickup.service;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.pickup.dto.PickupRegisterRequestDto;
import com.donggu.server.domain.pickup.repository.PickupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PickupService {

    private final PickupRepository pickupRepository;

    public void registerPickup(PickupRegisterRequestDto dto) {
        Pickup pickup = Pickup.builder()
                .dateTime(dto.dateTime())
                .region(dto.region())
                .location(dto.location())
                .gender(dto.gender())
                .price(dto.price())
                .maxParticipant(dto.maxParticipant())
                .currentParticipant(0)
                .build();

        pickupRepository.save(pickup);
    }
}
