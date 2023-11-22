package com.ssafy.la.user.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.la.user.model.dao.UserMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserCheckId {

	@Autowired
	UserMapper userMapper;
	
	public Map<String, Object> checkId(String userId) {
		Map<String, Object> data = new HashMap<>();
		String user = userMapper.checkId(userId);
		if (user == null) {
			data.put("available", true);
		} else {
			data.put("available", false);
		}
		return data;
	}

	public Map<String, Object> checkEmail(String email) {
		Map<String, Object> data = new HashMap<>();
		String mail = userMapper.checkEmail(email);
		if (mail == null) {
			data.put("available", true);
		} else {
			data.put("available", false);
		}
		return data;
	}
}
