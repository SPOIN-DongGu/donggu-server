package com.donggu.server.domain.pickup.service;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.pickup.dto.PickupResponseDto;
import com.donggu.server.domain.pickup.repository.PickupRepository;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PickupService {

    private final PickupRepository pickupRepository;

    public List<PickupResponseDto> getAllPickup() {
        return pickupRepository.findAll().stream()
                .map(pickup -> PickupResponseDto.of(
                        pickup.getId(),
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
                        pickup.getId(),
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
                )).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    public Pickup findPickupById(Long id) {
        return pickupRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    public void savePickup(Pickup pickup) {
        pickupRepository.save(pickup);
    }
}
