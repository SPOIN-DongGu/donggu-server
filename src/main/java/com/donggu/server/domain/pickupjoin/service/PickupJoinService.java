package com.donggu.server.domain.pickupjoin.service;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.pickup.dto.PickupDetailResponseDto;
import com.donggu.server.domain.pickup.service.PickupService;
import com.donggu.server.domain.pickupjoin.domain.PickupJoin;
import com.donggu.server.domain.pickupjoin.domain.Status;
import com.donggu.server.domain.pickupjoin.dto.PickupJoinRequestDto;
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

    public List<PickupDetailResponseDto> getAllAppliedPickup(Long userId) {
        User user = userService.findById(userId);
        List<PickupJoin> pickupJoinList = pickupJoinRepository.findAllByUser(user);

        return pickupJoinList.stream()
                .map(pickupJoin -> {
                    Pickup pickup = pickupJoin.getPickup();
                    return PickupDetailResponseDto.of(
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
                            pickup.getCurrentParticipant(),
                            pickupJoin.getStatus()
                    );
                })
                .toList();
    }

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
}
