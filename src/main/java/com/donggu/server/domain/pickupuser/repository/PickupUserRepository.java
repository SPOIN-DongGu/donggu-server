package com.donggu.server.domain.pickupuser.repository;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.pickupuser.domain.PickupUser;
import com.donggu.server.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PickupUserRepository extends JpaRepository<PickupUser, Long> {
    List<PickupUser> findAllByPickup(Pickup pickup);

    PickupUser findByPickupAndUser(Pickup pickup, User user);
}
