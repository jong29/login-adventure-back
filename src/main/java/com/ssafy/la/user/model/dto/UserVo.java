package com.ssafy.la.user.model.dto;

import com.ssafy.la.util.exception.exceptions.MyException;

public class UserVo {

	private String userid, password, email, username, role;

	public UserVo(String userid, String password, String email, String username, String role) {
		super();
		setUserid(userid);
		setPassword(password);
		setEmail(email);
		setUsername(username);
		setRole(role);
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		if(userid == null) {
			throw new MyException();
		}
		this.userid = userid;	
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password == null) {
			throw new MyException();
		}
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email == null) {
			throw new MyException();
		}
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (username == null) {
			throw new MyException();
		}
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		if (role == null) {
			throw new MyException();
		}
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", password=" + password + ", email=" + email + ", username=" + username
				+ ", role=" + role + "]";
	}
	
}
