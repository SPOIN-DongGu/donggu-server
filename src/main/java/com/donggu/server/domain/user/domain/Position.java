package com.donggu.server.domain.user.domain;

import lombok.Getter;

@Getter
public enum Position {
    PG("포인트 가드"),
    SG("슈팅 가드"),
    SF("스몰 포워드"),
    PF("파워 포워드"),
    C("센터");

    private final String position;

    Position(String position) {
        this.position = position;
    }
}
