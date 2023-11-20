package com.ssafy.la.user.model.dto;

import com.ssafy.la.util.exception.exceptions.MyException;

public class LoginRequestDto {

    private String uuid, userid, userpassword;

    public String getUuid() {
        return uuid;
    }

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

    public void setUuid(String uuid) {
        if (uuid == null || uuid.isEmpty()) {
            throw new MyException();
        }
        this.uuid = uuid;
    }
}
