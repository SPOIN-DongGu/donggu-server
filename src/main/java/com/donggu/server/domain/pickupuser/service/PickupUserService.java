package com.donggu.server.domain.pickupuser.service;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.pickup.service.PickupService;
import com.donggu.server.domain.pickupuser.domain.PickupUser;
import com.donggu.server.domain.pickupuser.repository.PickupUserRepository;
import com.donggu.server.domain.user.domain.User;
import com.donggu.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PickupUserService {

    private final PickupUserRepository pickupUserRepository;
    private final PickupService pickupService;
    private final UserService userService;

    public void applyPickup(Long pickupId, Long userId) {
        Pickup pickup = pickupService.findPickupById(pickupId);
        User user = userService.findUserById(userId);

        PickupUser pickupUser = PickupUser.builder()
                .pickup(pickup)
                .user(user)
                .build();

        pickupUserRepository.save(pickupUser);
    }
}
