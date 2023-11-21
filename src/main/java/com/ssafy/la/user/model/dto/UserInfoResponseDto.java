package com.ssafy.la.user.model.dto;

public class UserInfoResponseDto {
    private String username, email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserInfoResponseDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
