package com.ssafy.la.user.model.dto;

public class UserDeleteDto {
    String uuid, userid, password;

    public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public String toString() {
		return "UserDeleteDto{" +
				"uuid='" + uuid + '\'' +
				", userid='" + userid + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
