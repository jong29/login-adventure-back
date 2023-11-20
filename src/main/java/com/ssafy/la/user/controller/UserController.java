package com.ssafy.la.user.controller;

import java.util.HashMap;
import java.util.Map;

import com.ssafy.la.user.model.dao.UserRedisDao;
import com.ssafy.la.user.model.dto.UserDeleteDto;
import com.ssafy.la.user.model.dto.UserRegisterDto;
import com.ssafy.la.user.model.service.UserRegisterDelete;
import com.ssafy.la.util.exception.exceptions.MyException;
import com.ssafy.la.util.security.RSA_2048;
import com.ssafy.la.user.model.dto.LoginRequestDto;
import com.ssafy.la.user.model.service.UserLoginLogout;
import com.ssafy.la.util.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.la.user.model.service.UserService;
import com.ssafy.la.util.common.SuccessResponse;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserLoginLogout ull;
	
	@Autowired
	UserRedisDao userRedisDao;
	
	@Autowired
	RSA_2048 rsa_2048;
	
	@Autowired
	UserRegisterDelete userRegisterDelete;

	@PostMapping("/login")
	public ResponseEntity<CommonResponse> login(@RequestBody LoginRequestDto loginRequestDto) {

		Map<String, Object> username = ull.login(loginRequestDto);
		return SuccessResponse.toResponseEntity(200, "로그인 성공", username);

	}

	@PostMapping("/regist")
	public ResponseEntity<CommonResponse> register(@RequestBody UserRegisterDto userRegisterDto) {
		String uuid = userRegisterDto.getUuid();
		String privateKey = userRedisDao.readFromRedis("rsa:" + uuid);
		if (privateKey == null) {
			throw new MyException();
		}
		/**
		 * 복호화
		 */
		userRegisterDto.setUserid(rsa_2048.decrypt(userRegisterDto.getUserid(), privateKey));
		userRegisterDto.setPassword(rsa_2048.decrypt(userRegisterDto.getPassword(), privateKey));
		userRegisterDto.setEmail(rsa_2048.decrypt(userRegisterDto.getEmail(), privateKey));
		userRegisterDto.setUsername(rsa_2048.decrypt(userRegisterDto.getUsername(), privateKey));
		userRegisterDto.setRole(rsa_2048.decrypt(userRegisterDto.getRole(), privateKey));

		userRegisterDelete.register(userRegisterDto);

		return SuccessResponse.toResponseEntity(201, "회원가입 성공", null);
	}

	@PostMapping("/delete")
	public ResponseEntity<CommonResponse> delete(@RequestBody UserDeleteDto userDeleteDto) {
		String uuid = userDeleteDto.getUuid();
		String privateKey = userRedisDao.readFromRedis("rsa:" + uuid);
		if (privateKey == null) {
			throw new MyException();
		}

		userDeleteDto.setUserpassword(rsa_2048.decrypt(userDeleteDto.getUserpassword(), privateKey));

		userRegisterDelete.delete(userDeleteDto);

		return SuccessResponse.toResponseEntity(200, "회원탈퇴 성공", null);
	}
}
