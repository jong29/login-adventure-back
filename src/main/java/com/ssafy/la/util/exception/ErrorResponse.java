package com.ssafy.la.util.exception;

import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private final int status;
    private final String msg;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorcode){
        return ResponseEntity.status(errorcode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorcode.getHttpStatus().value())
                        .msg(errorcode.getMessage()).build()
                );
    }
}
