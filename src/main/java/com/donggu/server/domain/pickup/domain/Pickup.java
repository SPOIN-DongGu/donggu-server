package com.donggu.server.domain.pickup.domain;

import com.donggu.server.domain.user.domain.Gender;
import com.donggu.server.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity @Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pickup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게임 일시
    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    private LocalTime endTime;

    // 지역명
    @Column(length = 32)
    private String region;

    // 장소(농구장 상세 명칭)
    @Column(nullable = false, length = 32)
    private String location;

    // 성별
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    // 5vs5, 3vs3의 5나 3 등을 저장
    private int gameType;

    // 참가비
    private Long price;

    // 최대 참가 인원
    @Column(nullable = false)
    private Integer maxParticipant;

    // 현재 참가 인원
    private Integer currentParticipant;

    public void updateDateTime(LocalDate date) {
        this.date = date;
    }

    public void updateStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void updateEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void updateRegion(String region) {
        this.region = region;
    }

    public void updateLocation(String location) {
        this.location = location;
    }

    public void updateGender(Gender gender) {
        this.gender = gender;
    }

    public void updatePrice(Long price) {
        this.price = price;
    }

    public void updateMaxParticipant(Integer maxParticipant) {
        this.maxParticipant = maxParticipant;
    }

    public void updateCurrentParticipant(Integer currentParticipant) {
        this.currentParticipant = currentParticipant;
    }
}
