package com.donggu.server.domain.pickup.domain;

import com.donggu.server.domain.user.domain.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity @Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pickup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게임 일시 및 시간
    @Column(nullable = false)
    private LocalDateTime dateTime;

    // 지역명
    @Column(length = 32)
    private String region;

    // 장소(농구장 상세 명칭)
    @Column(nullable = false, length = 32)
    private String location;

    // 성별
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    // 참가비
    private Long price;

    // 최대 참가 인원
    @Column(nullable = false)
    private int maxParticipant;

    // 현재 참가 인원
    private int currentParticipant;
}
