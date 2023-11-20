package com.ssafy.la.user.model.dto;

public class UserDeleteDto {
    String uuid, userid, userpassword;

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

	public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

	@Override
	public String toString() {
		return "UserDeleteDto [uuid=" + uuid + ", userid=" + userid + ", userpassword=" + userpassword + "]";
	}
    
}
