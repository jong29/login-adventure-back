package com.ssafy.la.user.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.la.user.model.dao.UserRedisDao;
import com.ssafy.la.util.security.JWTProvider;
import com.ssafy.la.util.security.SecurityUtil;

@Service
public class UserLoginLogout {
	
	@Autowired
	SecurityUtil securityUtil;
	@Autowired
	UserRedisDao userRedisDao;
	@Autowired
	JWTProvider jwtProvider;
	
	

}
