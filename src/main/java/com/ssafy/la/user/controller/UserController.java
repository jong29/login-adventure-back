package com.ssafy.la.user.controller;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.ssafy.la.user.model.dao.UserRedisDao;
import com.ssafy.la.user.model.dto.UserDeleteDto;
import com.ssafy.la.user.model.dto.UserSignupDto;
import com.ssafy.la.user.model.service.UserSignupDelete;
import com.ssafy.la.util.exception.exceptions.MyException;
import com.ssafy.la.util.security.RSA_2048;
import com.ssafy.la.user.model.dto.LoginRequestDto;
import com.ssafy.la.user.model.service.UserLoginLogout;
import com.ssafy.la.util.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	UserSignupDelete userSignupDelete;
	
	@Value("${spring.rsa.live}")
	private Long rsaLive; 
	
	@GetMapping("/height")
	public ResponseEntity<CommonResponse> height() {
		String uuid = UUID.randomUUID().toString();	// uuid 생성
		KeyPair keyPair = rsa_2048.createKey();	// key 생성
		
		userRedisDao.saveToRedis("rsa:" + uuid, rsa_2048.keyToString(keyPair.getPrivate()), Duration.ofMillis(rsaLive)); // redis에 user key 저장
		
		String modulus = rsa_2048.getPublicKeyModulus((RSAPublicKey) keyPair.getPublic(), uuid);
		String exponent = rsa_2048.getPublicKeyExponent((RSAPublicKey) keyPair.getPublic(), uuid);
		
		Map<String, Object> map = new HashMap<>();
		map.put("uuid", uuid);
		map.put("modulus", modulus);
		map.put("exponent", exponent);
		
		return SuccessResponse.toResponseEntity(200, "키 발급 성공", map);
	}

	@PostMapping("/login")
	public ResponseEntity<CommonResponse> login(@RequestBody LoginRequestDto loginRequestDto) {

		Map<String, Object> username = ull.login(loginRequestDto);
		
		// redis 로그인 시도 횟수 확인
		

		return SuccessResponse.toResponseEntity(200, "로그인 성공", username);

	}

	@PostMapping("/regist")
	public ResponseEntity<CommonResponse> register(@RequestBody UserSignupDto userRegisterDto) {
		String uuid = userRegisterDto.getUuid();
		String privateKey = userRedisDao.readFromRedis("rsa:" + uuid);
		if (privateKey == null) {
			throw new MyException();
		}
		/**
		 * 복호화
		 */
		userRegisterDto.setPassword(rsa_2048.decrypt(userRegisterDto.getPassword(), privateKey));
		userRegisterDto.setEmail(rsa_2048.decrypt(userRegisterDto.getEmail(), privateKey));
		userRegisterDto.setUsername(rsa_2048.decrypt(userRegisterDto.getUsername(), privateKey));
		userRegisterDto.setRole(rsa_2048.decrypt(userRegisterDto.getRole(), privateKey));

		userSignupDelete.signup(userRegisterDto);

		return SuccessResponse.toResponseEntity(201, "회원가입 성공", null);
	}

	@PostMapping("/delete")
	public ResponseEntity<CommonResponse> delete(@RequestBody UserDeleteDto userDeleteDto) {
		String uuid = userDeleteDto.getUserpassword();
		String privateKey = userRedisDao.readFromRedis("rsa:" + uuid);
		if (privateKey == null) {
			throw new MyException();
		}

		userDeleteDto.setUserpassword(rsa_2048.decrypt(userDeleteDto.getUserpassword(), privateKey));

		userSignupDelete.delete(userDeleteDto);

		return SuccessResponse.toResponseEntity(200, "회원탈퇴 성공", null);
	}
}
