package com.donggu.server.domain.pickup.repository;

import com.donggu.server.domain.pickup.domain.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickupRepository extends JpaRepository<Pickup, Long> {
}
