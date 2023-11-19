package com.ssafy.la.user.model.service;

import com.ssafy.la.user.model.dto.LoginRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.la.user.model.dao.UserRedisDao;
import com.ssafy.la.util.security.JWTProvider;
import com.ssafy.la.util.security.SecurityUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserLoginLogout {
	
	@Autowired
	SecurityUtil securityUtil;
	@Autowired
	UserRedisDao userRedisDao;
	@Autowired
	JWTProvider jwtProvider;


	public Map<String, Object> login(LoginRequestDto loginRequestDto) {

		Map<String, Object> username = new HashMap<>();

		username.put("username", "jong");


		return username;
	}

}
