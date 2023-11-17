package com.ssafy.la.user.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.la.user.model.dao.UserRedisDao;
import com.ssafy.la.util.security.JWTProvider;

@Service
public class UserViewModify {
	// 회원 정보 조회, 수정
	
	@Autowired
	JWTProvider jwtProvider;
	
	@Autowired
	UserRedisDao userRedisDao;

}
