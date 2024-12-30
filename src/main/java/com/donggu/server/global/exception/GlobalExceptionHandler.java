package com.donggu.server.global.exception;

import com.donggu.server.global.base.BasicResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BasicResponse> handleCustomException(CustomException exception, HttpServletRequest request) {
        log.error("CustomException: {} {}", exception.getErrorCode(), request.getRequestURI());
        return BasicResponse.to(exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BasicResponse> handleException(Exception exception, HttpServletRequest request) {
        log.error("Exception: {} {}", exception.getMessage(), request.getRequestURI());

        CustomException customException = new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "");
        log.error("Exception: {} {}", customException.getErrorCode(), request.getRequestURI());
        return BasicResponse.to(customException);
    }
}
