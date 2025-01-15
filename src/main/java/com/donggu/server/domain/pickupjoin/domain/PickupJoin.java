package com.donggu.server.domain.pickupjoin.domain;

import com.donggu.server.domain.pickup.domain.Pickup;
import com.donggu.server.domain.user.domain.Gender;
import com.donggu.server.domain.user.domain.Position;
import com.donggu.server.domain.user.domain.User;
import com.donggu.server.global.base.BaseEntity;
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
@Table(name = "pickup_user")
public class PickupJoin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pickup_id", nullable = false)
    private Pickup pickup;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private double height;

    private double weight;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING)
    private Status status;

    public void updateStatue(Status status) {
        this.status = status;
    }
}
