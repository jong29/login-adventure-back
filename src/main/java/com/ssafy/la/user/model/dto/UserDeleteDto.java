package com.ssafy.la.user.model.dto;

public class UserDeleteDto {
    String uuid, atk, rtk, userpassword;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAtk() {
        return atk;
    }

    public void setAtk(String atk) {
        this.atk = atk;
    }

    public String getRtk() {
        return rtk;
    }

    public void setRtk(String rtk) {
        this.rtk = rtk;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    @Override
    public String toString() {
        return "UserDeleteDto{" +
                "atk='" + atk + '\'' +
                ", rtk='" + rtk + '\'' +
                ", userpassword='" + userpassword + '\'' +
                '}';
    }
}
