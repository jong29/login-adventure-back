package com.ssafy.la.user.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.la.user.model.dao.UserMapper;

@Service
public class UserCheckId {

	@Autowired
	UserMapper userMapper;
	
	public String checkId(String userId) {
		return userMapper.checkId(userId);
	}
}
