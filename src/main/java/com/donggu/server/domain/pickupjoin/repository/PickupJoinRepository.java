package com.donggu.server.domain.pickupjoin.repository;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.pickupjoin.domain.PickupJoin;
import com.donggu.server.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PickupJoinRepository extends JpaRepository<PickupJoin, Long> {
    List<PickupJoin> findAllByPickup(Pickup pickup);

    PickupJoin findByPickupAndUser(Pickup pickup, User user);
}
