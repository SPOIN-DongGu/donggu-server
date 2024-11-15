package com.donggu.server.domain.pickupuser.service;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.pickup.service.PickupService;
import com.donggu.server.domain.pickupuser.domain.PickupUser;
import com.donggu.server.domain.pickupuser.domain.Status;
import com.donggu.server.domain.pickupuser.dto.PickupUserResponseDto;
import com.donggu.server.domain.pickupuser.repository.PickupUserRepository;
import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PickupUserService {

    private final PickupUserRepository pickupUserRepository;
    private final PickupService pickupService;
    private final UserService userService;

    public void applyPickup(Long pickupId, Long userId) {
        Pickup pickup = pickupService.findPickupById(pickupId);
        User user = userService.findUserById(userId);

        if (pickupUserRepository.findByPickupAndUser(pickup, user) != null) {
            throw new IllegalArgumentException("이미 신청한 게임입니다");
        }

        PickupUser pickupUser = PickupUser.builder()
                .pickup(pickup)
                .user(user)
                .status(Status.PENDING)
                .build();

        pickupUserRepository.save(pickupUser);
    }

    public List<PickupUserResponseDto> getAppliedUsersByProgram(Long pickupId) {
        Pickup pickup = pickupService.findPickupById(pickupId);
        List<PickupUser> pickupUserList = pickupUserRepository.findAllByPickup(pickup);

        return pickupUserList.stream()
                .map(pickupUser -> {
                    User user = pickupUser.getUser();

                    return PickupUserResponseDto.of(
                            user.getFullName(),
                            user.getGender(),
                            user.getHeight(),
                            user.getWeight(),
                            user.getPosition(),
                            pickupUser.getStatus()
                    );
                })
                .toList();
    }

    public void handleUserApply(Long pickupUserId, Status status) {
        PickupUser pickupUser = pickupUserRepository.findById(pickupUserId).orElseThrow();

        if (pickupUser.getStatus() == Status.PENDING) {
            pickupUser.updateStatue(status);
        }

        throw new IllegalArgumentException("이미 처리된 신청입니다");
    }
}
