package com.ssafy.la.mail.model.dto;

import com.ssafy.la.util.exception.exceptions.MyException;

public class MailTokenVo {

    private String email;
    private String verifytoken;

    public MailTokenVo() {}

    public MailTokenVo(String email, String verifytoken) {
        setEmail(email);
        setVerifytoken(verifytoken);
    }

    public String getEmail() {
        return email;
    }

    public String getVerifytoken() {
        return verifytoken;
    }

    public void setEmail(String email) {
        if (email == null || email.isEmpty()) throw new MyException();
        this.email = email;
    }

    public void setVerifytoken(String verifytoken) {
        if (verifytoken == null || verifytoken.isEmpty()) throw new MyException();
        this.verifytoken = verifytoken;
    }
}
