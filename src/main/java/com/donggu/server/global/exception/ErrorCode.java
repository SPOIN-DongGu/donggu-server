package com.donggu.server.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // auth 관련 오류
    INVALID_TOKEN(4011, "올바르지 않은 토큰입니다", HttpStatus.UNAUTHORIZED),

    // 서버 내부 에러
    INTERNAL_SERVER_ERROR(5001, "서버 내부에 에러가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
