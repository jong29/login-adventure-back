package com.ssafy.la.user.model.dto;

import com.ssafy.la.util.exception.exceptions.MyException;

public class LoginRequestDto {

    private String userid, userpassword;

    public String getUserid() {
        return userid;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserid(String userid) {
        if (userid == null || userid.isEmpty()) {
            throw new MyException();
        }
        this.userid = userid;
    }

    public void setUserpassword(String userpassword) {
        if (userid == null || userid.isEmpty()) {
            throw new MyException();
        }
        this.userpassword = userpassword;
    }
}
