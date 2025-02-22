package com.donggu.server.domain.pickup.service;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.pickup.dto.PickupRequestDto;
import com.donggu.server.domain.pickup.repository.PickupRepository;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PickupAdminService {

    private final PickupRepository pickupRepository;

    @Transactional
    public void registerPickup(PickupRequestDto dto) {
        Pickup pickup = Pickup.builder()
                .date(dto.date())
                .startTime(dto.startTime())
                .endTime(dto.endTime())
                .region(dto.region())
                .location(dto.location())
                .gender(dto.gender())
                .gameType(dto.gameType())
                .price(dto.price())
                .maxParticipant(dto.maxParticipant())
                .currentParticipant(0)
                .build();

        pickupRepository.save(pickup);
    }

    @Transactional
    public void updatePickup(Long pickupId, PickupRequestDto dto) {
        Pickup pickup = pickupRepository.findById(pickupId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        if (dto.date() != null) {
            pickup.updateDate(dto.date());
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

    @Transactional
    public void deletePickup(Long pickupId) {
        Pickup pickup = pickupRepository.findById(pickupId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        pickupRepository.delete(pickup);
    }
}
