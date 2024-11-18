package com.donggu.server.global.base;

import com.donggu.server.global.exception.CustomException;
import org.springframework.http.ResponseEntity;

public record BasicResponse(
        String response,
        String error,
        String detail
) {
    public static BasicResponse of(String response) {
        return new BasicResponse(
                response,
                "",
                ""
        );
    }

    public static BasicResponse of(CustomException exception) {
        return new BasicResponse(
                exception.getErrorCode().getMessage(),
                String.valueOf(exception.getErrorCode().getCode()),
                exception.getDetail()
        );
    }

    public static ResponseEntity<BasicResponse> to(CustomException exception) {
        return ResponseEntity
                .status(exception.getErrorCode().getHttpStatus())
                .body(BasicResponse.of(exception));
    }
}
