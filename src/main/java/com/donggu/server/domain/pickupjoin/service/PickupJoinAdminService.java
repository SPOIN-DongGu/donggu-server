package com.donggu.server.domain.pickupjoin.service;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.pickup.service.PickupService;
import com.donggu.server.domain.pickupjoin.domain.PickupJoin;
import com.donggu.server.domain.pickupjoin.domain.Status;
import com.donggu.server.domain.pickupjoin.dto.PickupJoinResponseDto;
import com.donggu.server.domain.pickupjoin.repository.PickupJoinRepository;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PickupJoinAdminService {

    private final PickupService pickupService;
    private final PickupJoinRepository pickupJoinRepository;

    public List<PickupJoinResponseDto> getAppliedUsersByPickup(Long pickupId) {
        log.info("[Pickup Join] Get all users applied to pickup game: "+pickupId);

        Pickup pickup = pickupService.findPickupById(pickupId);
        List<PickupJoin> pickupJoinList = pickupJoinRepository.findAllByPickup(pickup);

        return pickupJoinList.stream()
                .map(PickupJoinResponseDto::from)
                .toList();
    }

    @Transactional
    public void handleUserApply(Long pickupId, Long pickupJoinId, Status status) {
        log.info("[Pickup Join] pickup {} & pickup join {} to {}", pickupId, pickupJoinId, status);

        Pickup pickup = pickupService.findPickupById(pickupId);

        PickupJoin pickupJoin = pickupJoinRepository.findById(pickupJoinId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        if (pickupJoin.getStatus() != Status.PENDING) {
            throw new CustomException(ErrorCode.ALREADY_PROCESSED);
        }

        int maxParticipant = pickup.getMaxParticipant();
        int currentParticipant = pickup.getCurrentParticipant();

        if (status == Status.APPROVED && currentParticipant<maxParticipant) {
            pickup.updateCurrentParticipant(currentParticipant+1);
        } else if (status == Status.REJECTED) {
            pickup.updateCurrentParticipant(Math.max(currentParticipant-1, 0));
        } else throw new CustomException(ErrorCode.INVALID_JOIN_USER);

        pickupJoin.updateStatue(status);
        pickupService.savePickup(pickup);
        pickupJoinRepository.save(pickupJoin);
    }
}
