package com.ssafy.la.user.model.dto;

public class UserModifyDto {
    String uuid, userid, curPw, newPw;

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

	public String getCurPw() {
		return curPw;
	}

	public void setCurPw(String curPw) {
		this.curPw = curPw;
	}

	public String getNewPw() {
		return newPw;
	}

	public void setNewPw(String newPw) {
		this.newPw = newPw;
	}

	@Override
	public String toString() {
		return "UserModifyDto [uuid=" + uuid + ", userid=" + userid + ", curPw=" + curPw + ", newPw=" + newPw + "]";
	}

}
