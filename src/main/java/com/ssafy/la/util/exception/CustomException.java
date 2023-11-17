package com.ssafy.la.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode = ErrorCode.findByClass(this.getClass());

    private HttpStatus httpStatus;
    private String msg;

    public CustomException() {
        this.httpStatus = errorCode.getHttpStatus();
        this.msg = errorCode.getMessage();
    }
    public CustomException(String msg){
        this.httpStatus = errorCode.getHttpStatus();
        this.msg = msg;
    }
}
