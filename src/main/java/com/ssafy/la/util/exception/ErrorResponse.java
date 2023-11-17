package com.ssafy.la.util.exception;

import org.springframework.http.ResponseEntity;

import com.ssafy.la.util.common.CommonResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse implements CommonResponse {
    private final int code;
    private final String msg;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorcode){
        return ResponseEntity.status(errorcode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .code(errorcode.getCode())
                        .msg(errorcode.getMessage()).build()
                );
    }
}
