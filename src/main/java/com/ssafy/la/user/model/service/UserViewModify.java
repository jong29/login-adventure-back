package com.ssafy.la.user.model.service;

import com.ssafy.la.user.model.dto.UserInfoResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.la.user.model.dao.UserMapper;
import com.ssafy.la.user.model.dto.UserModifyDto;
import com.ssafy.la.user.model.dto.UserVo;
import com.ssafy.la.user.model.security.dao.SecurityMapper;
import com.ssafy.la.util.exception.exceptions.MyException;
import com.ssafy.la.util.security.JWTProvider;
import com.ssafy.la.util.security.SHA_256;

@Service
public class UserViewModify {
	// 회원 정보 조회, 수정
	
	@Autowired
	JWTProvider jwtProvider;

	@Autowired
	UserMapper userMapper;

	@Autowired
	SecurityMapper securityMapper;
	
	@Autowired
	SHA_256 sha_256;

	public void modify(UserModifyDto userModifyDto) {
		String userid = userModifyDto.getUserid();
		String curPw = userModifyDto.getCurPw();
		String newPw = userModifyDto.getNewPw();
		
		String salt = securityMapper.readSalt(userid);
		UserVo user = userMapper.userinfo(userid);
		
		curPw = sha_256.SHA256(curPw, salt);
		newPw = sha_256.SHA256(newPw, salt);
		if (!user.getPassword().equals(curPw)) {
			throw new MyException();
		}
		user.setPassword(newPw);
		userMapper.modify(user);
	}

	public UserInfoResponseDto userinfo(String userid) {
		UserVo user = userMapper.userinfo(userid);

		UserInfoResponseDto res = new UserInfoResponseDto();
		String[] split = user.getEmail().split("@");
		StringBuffer sb = new StringBuffer();

		sb.append(split[0].charAt(0));
		for (int i = 1; i < split[0].length(); i++) {
			sb.append("*");
		}
		sb.append("@").append(split[1]);
		String coveredEmail = sb.toString();
		res.setUsername(user.getUsername());
		res.setEmail(coveredEmail);
		return res;
	}
}
