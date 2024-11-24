package com.donggu.server.domain.pickupjoin.service;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.pickup.service.PickupService;
import com.donggu.server.domain.pickupjoin.domain.PickupJoin;
import com.donggu.server.domain.pickupjoin.domain.Status;
import com.donggu.server.domain.pickupjoin.dto.PickupJoinRequestDto;
import com.donggu.server.domain.pickupjoin.dto.PickupJoinResponseDto;
import com.donggu.server.domain.pickupjoin.repository.PickupJoinRepository;
import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.service.UserService;
import com.donggu.server.global.exception.CustomException;
import com.donggu.server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PickupJoinService {

    private final PickupJoinRepository pickupJoinRepository;
    private final PickupService pickupService;
    private final UserService userService;

    public void applyPickup(Long pickupId, PickupJoinRequestDto dto) {
        Pickup pickup = pickupService.findPickupById(pickupId);
        User user = userService.findById(dto.userId());

        if (pickupJoinRepository.findByPickupAndUser(pickup, user) != null) {
            throw new CustomException(ErrorCode.ALREADY_APPLY);
        }

        PickupJoin pickupJoin = PickupJoin.builder()
                .pickup(pickup)
                .user(user)
                .name(dto.name())
                .gender(dto.gender())
                .height(dto.height())
                .weight(dto.weight())
                .position(dto.position())
                .status(Status.PENDING)
                .build();

        pickupJoinRepository.save(pickupJoin);
    }

    public List<PickupJoinResponseDto> getAppliedUsersByPickup(Long pickupId) {
        Pickup pickup = pickupService.findPickupById(pickupId);
        List<PickupJoin> pickupJoinList = pickupJoinRepository.findAllByPickup(pickup);

        return pickupJoinList.stream()
                .map(pickupJoin -> PickupJoinResponseDto.of(
                        pickupJoin.getId(),
                        pickupJoin.getName(),
                        pickupJoin.getGender(),
                        pickupJoin.getHeight(),
                        pickupJoin.getWeight(),
                        pickupJoin.getPosition(),
                        pickupJoin.getStatus()
                ))
                .toList();
    }

    public void handleUserApply(Long pickupId, Long pickupJoinId, Status status) {
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
