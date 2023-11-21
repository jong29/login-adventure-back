package com.ssafy.la.user.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.la.mail.model.service.MailService;
import com.ssafy.la.user.model.dao.UserRedisDao;

@Service
public class UserFindIdPwd {
	
	@Autowired
	MailService mailService;
	
	@Autowired
	UserRedisDao userRedisDao;


}
