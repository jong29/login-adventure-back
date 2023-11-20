package com.ssafy.la.util.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.REQUEST_TIMEOUT;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

import com.ssafy.la.util.exception.exceptions.KeyTimeoutException;
import com.ssafy.la.util.exception.exceptions.LoginFailException;
import com.ssafy.la.util.exception.exceptions.MyException;
import com.ssafy.la.util.exception.exceptions.NoPermissionException;
import com.ssafy.la.util.exception.exceptions.NotFoundClassException;

import lombok.Getter;

@Getter
public enum ErrorCode {
	PERMISSION_ERROR(FORBIDDEN, 11, "접근이 안되는 기능입니다.", NoPermissionException.class),
    COMMON_ERROR(BAD_REQUEST, 12, "공통오류", MyException.class),
    KEY_TIMEOUT_ERROR(REQUEST_TIMEOUT, 13, "비대칭키 저장 시간 만료.", KeyTimeoutException.class),
    LOGIN_ERROR(BAD_REQUEST, 14, "로그인을 다시 시도해 주세요.", LoginFailException.class),
//    REGISTER_ERROR(BAD_REQUEST, "회원가입을 다시 시도해 주세요.", RegistFailException.class),
//    PLAN_REGIST_ERROR(BAD_REQUEST,"조회할 게시글을 다시 확인해주세요", PlanReigstException.class),
//    NOT_LOGINED_ERROR(BAD_REQUEST,"로그인을 다시 해주세요", NotLoginedException.class),
    ;
    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
    private final Class<? extends Exception> klass;

    ErrorCode(HttpStatus httpStatus, int code, String message, Class<? extends Exception> klass) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.klass = klass;
    }
    
    public static ErrorCode findByClass(Class<? extends Exception> klass){
        return Arrays.stream(ErrorCode.values())
                .filter(code -> code.klass.equals(klass))
                .findAny().orElseThrow(NotFoundClassException::new);
    }
}
