package com.donggu.server.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends Exception{
    private final ErrorCode errorCode;
    private final String detail;

    public CustomException(ErrorCode errorCode, String detail) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = detail;
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = "";
    }
}
