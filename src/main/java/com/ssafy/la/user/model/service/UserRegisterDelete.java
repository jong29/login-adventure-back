package com.ssafy.la.user.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.la.mail.service.MailService;
import com.ssafy.la.user.model.dao.UserRedisDao;
import com.ssafy.la.util.security.SecurityUtil;

@Service
public class UserRegisterDelete {
	// 회원 가입, 탈퇴
	
	@Autowired
	UserRedisDao userRedisDao;
	
	@Autowired
	SecurityUtil securityUtil;
	
	@Autowired
	MailService mailService;

}
