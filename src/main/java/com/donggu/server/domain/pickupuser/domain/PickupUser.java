package com.donggu.server.domain.pickupuser.domain;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity @Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PickupUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pickup_id", nullable = false)
    private Pickup pickup;

    private Status status;
}
