package com.donggu.server.domain.pickup.service;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.pickup.dto.PickupRegisterRequestDto;
import com.donggu.server.domain.pickup.dto.PickupResponseDto;
import com.donggu.server.domain.pickup.repository.PickupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public void deletePickup(Long pickupId) {
        Pickup pickup = pickupRepository.findById(pickupId)
                .orElseThrow();
        pickupRepository.delete(pickup);
    }

    public PickupResponseDto getPickup(Long pickupId) {
        return pickupRepository.findById(pickupId)
                .map(pickup -> PickupResponseDto.of(
                        pickup.getDateTime(),
                        pickup.getRegion(),
                        pickup.getLocation(),
                        pickup.getGender(),
                        pickup.getPrice(),
                        pickup.getMaxParticipant(),
                        pickup.getCurrentParticipant()
                )).orElseThrow();
    }

    public List<PickupResponseDto> getAllPickup() {
        return pickupRepository.findAll().stream()
                .map(pickup -> PickupResponseDto.of(
                        pickup.getDateTime(),
                        pickup.getRegion(),
                        pickup.getLocation(),
                        pickup.getGender(),
                        pickup.getPrice(),
                        pickup.getMaxParticipant(),
                        pickup.getCurrentParticipant()
                ))
                .collect(Collectors.toList());
    }
}
