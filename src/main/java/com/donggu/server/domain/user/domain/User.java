package com.donggu.server.domain.user.domain;

import com.donggu.server.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    // pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 아이디
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // 본명
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private String region;

    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    private double height;

    private double weight;

    @Enumerated(EnumType.STRING)
    private Position position;

    public void updateRole(Role role) {
        this.role = role;
    }
}
