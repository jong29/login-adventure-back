package com.ssafy.la.user.model.dto;

import com.ssafy.la.util.exception.exceptions.MyException;

public class LoginRequestDto {

    private String uuid, userid, password;

    public String getUuid() {
        return uuid;
    }

    public String getUserid() {
        return userid;
    }

    public String getPassword() {
        return password;
    }

    public void setUserid(String userid) {
        if (userid == null || userid.isEmpty()) {
            throw new MyException();
        }
        this.userid = userid;
    }

    public void setPassword(String password) {
        if (userid == null || userid.isEmpty()) {
            throw new MyException();
        }
        this.password = password;
    }

    public void setUuid(String uuid) {
        if (uuid == null || uuid.isEmpty()) {
            throw new MyException();
        }
        this.uuid = uuid;
    }
}
