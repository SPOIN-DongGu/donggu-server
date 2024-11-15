package com.donggu.server.domain.pickup.service;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.pickup.dto.PickupRequestDto;
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

    public void registerPickup(PickupRequestDto dto) {
        Pickup pickup = Pickup.builder()
                .date(dto.date())
                .startTime(dto.startTime())
                .endTime(dto.endTime())
                .region(dto.region())
                .location(dto.location())
                .gender(dto.gender())
                .price(dto.price())
                .gameType(dto.gameType())
                .maxParticipant(dto.maxParticipant())
                .currentParticipant(0)
                .build();

        pickupRepository.save(pickup);
    }

    public void updatePickup(Long pickupId, PickupRequestDto dto) {
        Pickup pickup = pickupRepository.findById(pickupId).orElseThrow();

        if (dto.date() != null) {
            pickup.updateDateTime(dto.date());
        }
        if (dto.startTime() != null) {
            pickup.updateStartTime(dto.startTime());
        }
        if (dto.endTime() != null) {
            pickup.updateEndTime(dto.endTime());
        }
        if (dto.region() != null) {
            pickup.updateRegion(dto.region());
        }
        if (dto.location() != null) {
            pickup.updateLocation(dto.location());
        }
        if (dto.gender() != null) {
            pickup.updateGender(dto.gender());
        }
        if (dto.price() != null) {
            pickup.updatePrice(dto.price());
        }
        if (dto.maxParticipant() != null) {
            pickup.updateMaxParticipant(dto.maxParticipant());
        }

        pickupRepository.save(pickup);
    }

    public void deletePickup(Long pickupId) {
        Pickup pickup = pickupRepository.findById(pickupId)
                .orElseThrow();
        pickupRepository.delete(pickup);
    }

    public List<PickupResponseDto> getAllPickup() {
        return pickupRepository.findAll().stream()
                .map(pickup -> PickupResponseDto.of(
                        pickup.getDate(),
                        pickup.getStartTime(),
                        pickup.getEndTime(),
                        pickup.getRegion(),
                        pickup.getLocation(),
                        pickup.getGender(),
                        pickup.getGameType(),
                        pickup.getPrice(),
                        pickup.getMaxParticipant(),
                        pickup.getCurrentParticipant()
                ))
                .collect(Collectors.toList());
    }

    public PickupResponseDto getPickup(Long pickupId) {
        return pickupRepository.findById(pickupId)
                .map(pickup -> PickupResponseDto.of(
                        pickup.getDate(),
                        pickup.getStartTime(),
                        pickup.getEndTime(),
                        pickup.getRegion(),
                        pickup.getLocation(),
                        pickup.getGender(),
                        pickup.getGameType(),
                        pickup.getPrice(),
                        pickup.getMaxParticipant(),
                        pickup.getCurrentParticipant()
                )).orElseThrow();
    }

    public Pickup findPickupById(Long id) {
        return pickupRepository.findById(id).orElseThrow();
    }
}
