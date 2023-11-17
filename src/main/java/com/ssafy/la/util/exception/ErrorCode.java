package com.ssafy.la.util.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

import com.ssafy.la.util.exception.exceptions.NoPermissionException;
import com.ssafy.la.util.exception.exceptions.NotFoundClassException;

import lombok.Getter;
@Getter
public enum ErrorCode {
    //400
//    COMMON_ERROR(BAD_REQUEST,"공통오류", MyException.class),
//    LOGIN_ERROR(BAD_REQUEST,"로그인을 다시 시도해 주세요.", LoginFailException.class),
//    REGISTER_ERROR(BAD_REQUEST, "회원가입을 다시 시도해 주세요.", RegistFailException.class),
//    PLAN_REGIST_ERROR(BAD_REQUEST,"조회할 게시글을 다시 확인해주세요", PlanReigstException.class),
//    NOT_LOGINED_ERROR(BAD_REQUEST,"로그인을 다시 해주세요", NotLoginedException.class),
//
//
//    // 403
    PERMISSION_ERROR(FORBIDDEN,"접근이 안되는 기능입니다.", NoPermissionException.class),
    ;
    private final HttpStatus httpStatus;
    private final String message;
    private final Class<? extends Exception> klass;

    ErrorCode(HttpStatus httpStatus, String message, Class<? extends Exception> klass) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.klass = klass;
    }
    
    public static ErrorCode findByClass(Class<? extends Exception> klass){
        return Arrays.stream(ErrorCode.values())
                .filter(code -> code.klass.equals(klass))
                .findAny().orElseThrow(NotFoundClassException::new);
    }
}
