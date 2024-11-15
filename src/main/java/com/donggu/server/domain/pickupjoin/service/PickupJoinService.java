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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PickupJoinService {

    private final PickupJoinRepository pickupJoinRepository;
    private final PickupService pickupService;
    private final UserService userService;

    public void applyPickup(Long pickupId, Long userId, PickupJoinRequestDto dto) {
        Pickup pickup = pickupService.findPickupById(pickupId);
        User user = userService.findUserById(userId);

        if (pickupJoinRepository.findByPickupAndUser(pickup, user) != null) {
            throw new IllegalArgumentException("이미 신청한 게임입니다");
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
        List<PickupJoin> pickupUserList = pickupJoinRepository.findAllByPickup(pickup);

        return pickupUserList.stream()
                .map(pickupJoin -> PickupJoinResponseDto.of(
                        pickupJoin.getName(),
                        pickupJoin.getGender(),
                        pickupJoin.getHeight(),
                        pickupJoin.getWeight(),
                        pickupJoin.getPosition(),
                        pickupJoin.getStatus()
                ))
                .toList();
    }

    public void handleUserApply(Long pickupUserId, Status status) {
        PickupJoin pickupJoin = pickupJoinRepository.findById(pickupUserId).orElseThrow();

        if (pickupJoin.getStatus() == Status.PENDING) {
            pickupJoin.updateStatue(status);
        }

        throw new IllegalArgumentException("이미 처리된 신청입니다");
    }
}
