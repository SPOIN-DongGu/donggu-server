package com.donggu.server.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 서비스 관련 에러
    BAD_REQUEST(4001, "잘못된 요청입니다", HttpStatus.BAD_REQUEST),
    ALREADY_APPLY(4002, "이미 신청한 게임입니다", HttpStatus.BAD_REQUEST),
    ALREADY_PROCESSED(4003, "이미 처리된 신청입니다", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXIST(4004, "이미 존재하는 사용자입니다", HttpStatus.BAD_REQUEST),
    INVALID_JOIN_USER(4005, "참여자 수가 적절하지 않습니다", HttpStatus.BAD_REQUEST),
    // auth 관련 에러
    INVALID_TOKEN(4011, "올바르지 않은 토큰입니다", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN(4012, "토큰이 만료되었습니다", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(4013, "권한이 없습니다", HttpStatus.UNAUTHORIZED),
    // 리소스 관련 에러
    NOT_FOUND(4041, "값을 찾을 수 없습니다", HttpStatus.NOT_FOUND),
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
