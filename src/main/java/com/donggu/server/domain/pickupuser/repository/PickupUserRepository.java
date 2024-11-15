package com.donggu.server.domain.pickupuser.repository;

import com.donggu.server.domain.pickupuser.domain.PickupUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickupUserRepository extends JpaRepository<PickupUser, Long> {
}
