package com.ssafy.la.user.model.service;

import com.ssafy.la.user.model.dao.UserMapper;
import com.ssafy.la.user.model.dto.UserDeleteDto;
import com.ssafy.la.user.model.dto.UserRegisterDto;
import com.ssafy.la.user.model.dto.UserVo;
import com.ssafy.la.user.model.security.dao.SecurityMapper;
import com.ssafy.la.util.security.SHA_256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.la.mail.service.MailService;
import com.ssafy.la.user.model.dao.UserRedisDao;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegisterDelete {
	// 회원 가입, 탈퇴

	@Autowired
	UserRedisDao userRedisDao;

	@Autowired
	UserMapper userMapper;

	@Autowired
	SecurityMapper securityMapper;

	@Autowired
	MailService mailService;
	
	@Autowired
	SHA_256 sha_256;

	@Transactional
	public void register(UserRegisterDto userRegisterDto) {
		UserVo user = new UserVo(userRegisterDto.getUserid(), userRegisterDto.getPassword(),
				userRegisterDto.getEmail(), userRegisterDto.getUsername(), userRegisterDto.getRole());

		String salt = sha_256.getSalt();
		user.setPassword(sha_256.SHA256(user.getPassword(), salt));

		// securityMapper.registerSalt(user.getUserid(), salt); // salt 저장
		// userMapper.register(user); // 회원 저장
	}

	@Transactional
	public void delete(UserDeleteDto userDeleteDto) {
	}
}

