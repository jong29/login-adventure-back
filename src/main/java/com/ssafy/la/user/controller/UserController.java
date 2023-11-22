package com.ssafy.la.user.controller;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.ssafy.la.user.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.la.user.model.dao.UserRedisDao;
import com.ssafy.la.user.model.service.UserCheckId;
import com.ssafy.la.user.model.service.UserLoginLogout;
import com.ssafy.la.user.model.service.UserService;
import com.ssafy.la.user.model.service.UserSignupGoodbye;
import com.ssafy.la.user.model.service.UserViewModify;
import com.ssafy.la.util.common.CommonResponse;
import com.ssafy.la.util.common.SuccessResponse;
import com.ssafy.la.util.exception.exceptions.MyException;
import com.ssafy.la.util.security.JWTProvider;
import com.ssafy.la.util.security.RSA_2048;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserLoginLogout userLoginLogout;

	@Autowired
	UserRedisDao userRedisDao;

	@Autowired
	RSA_2048 rsa_2048;

	@Autowired
	UserSignupGoodbye userSignupGoodbye;

	@Autowired
	JWTProvider jwtProvider;

	@Autowired
	UserViewModify userViewModify;

	@Autowired
	UserCheckId userCheckId;

	@Value("${spring.rsa.live}")
	private Long rsaLive;

	@GetMapping("/checkId")
	public ResponseEntity<CommonResponse> checkId(@RequestBody String userId) {
		String name = userCheckId.checkId(userId);
		if (name != null) {
			throw new MyException();
		}
		return SuccessResponse.toResponseEntity(200, "사용가능한 아이디입니다.", null);
	}

	@GetMapping("/height")
	public ResponseEntity<CommonResponse> height() {
		System.out.println("hello");
		String uuid = UUID.randomUUID().toString();    // uuid 생성
		KeyPair keyPair = rsa_2048.createKey();    // key 생성

		userRedisDao.saveToRedis("rsa:" + uuid, rsa_2048.keyToString(keyPair.getPrivate()), Duration.ofMillis(rsaLive)); // redis에 user key 저장

		String modulus = rsa_2048.getPublicKeyModulus((RSAPublicKey) keyPair.getPublic(), uuid);
		String exponent = rsa_2048.getPublicKeyExponent((RSAPublicKey) keyPair.getPublic(), uuid);

		Map<String, Object> map = new HashMap<>();
		map.put("uuid", uuid);
		map.put("modulus", modulus);
		map.put("exponent", exponent);

		return SuccessResponse.toResponseEntity(200, "키 발급 성공", map);
	}

	@PostMapping("/reissue")
	public ResponseEntity<CommonResponse> reissue(@RequestBody Map<String, String> body) {

		String userid = body.get("userid");

		Map<String, Object> data = userLoginLogout.reissue(userid);

		return SuccessResponse.toResponseEntity(200, "atk 재발급 성공", data);
	}

	@PostMapping("/login")
	public ResponseEntity<CommonResponse> login(@RequestBody LoginRequestDto loginRequestDto) {

		Map<String, Object> username = userLoginLogout.login(loginRequestDto);

		return SuccessResponse.toResponseEntity(200, "로그인 성공", username);

	}

	@PostMapping("/signup")
	public ResponseEntity<CommonResponse> signup(@RequestBody UserSignupDto userSignupDto) {
		String uuid = userSignupDto.getUuid();
		String privateKey = userRedisDao.readFromRedis("rsa:" + uuid);
		if (privateKey == null) {
			throw new MyException();
		}
		/**
		 * 복호화
		 */
		userSignupDto.setPassword(rsa_2048.decrypt(userSignupDto.getPassword(), privateKey));
		userSignupDto.setEmail(rsa_2048.decrypt(userSignupDto.getEmail(), privateKey));
		userSignupDto.setUsername(rsa_2048.decrypt(userSignupDto.getUsername(), privateKey));

		userSignupGoodbye.signup(userSignupDto);

		return SuccessResponse.toResponseEntity(201, "회원가입 성공", null);
	}

	@PostMapping("/modify")
	public ResponseEntity<CommonResponse> modify(@RequestBody UserModifyDto userModifyDto) {
		String uuid = userModifyDto.getUuid();
		String privateKey = userRedisDao.readFromRedis("rsa:" + uuid);
		if (privateKey == null) {
			throw new MyException();
		}

		userModifyDto.setCurPw(rsa_2048.decrypt(userModifyDto.getCurPw(), privateKey));
		userModifyDto.setNewPw(rsa_2048.decrypt(userModifyDto.getNewPw(), privateKey));

		userViewModify.modify(userModifyDto);

		return SuccessResponse.toResponseEntity(200, "회원정보 수정 성공", null);
	}

	@PostMapping("/userinfo")
	public ResponseEntity<CommonResponse> userinfo(@RequestBody Map<String, String> request) {

		UserInfoResponseDto userInfoResponseDto = userViewModify.userinfo(request.get("userid"));
		Map<String, Object> data = new HashMap<>();
		data.put("username", userInfoResponseDto.getUsername());
		data.put("email", userInfoResponseDto.getEmail());

		return SuccessResponse.toResponseEntity(200, "회원정보 조회 성공", data);
	}

	@PostMapping("/logout")
	public ResponseEntity<CommonResponse> logout(@RequestBody Map<String, String> req) {
		String userid = req.get("userid");

		userLoginLogout.logout(userid);

		return SuccessResponse.toResponseEntity(200, "로그아웃 성공", null);
	}
	
	@PostMapping("/goodbye")
	public ResponseEntity<CommonResponse> goodbye(@RequestBody Map<String, String> request) {
		String uuid = request.get("uuid");
		String privateKey = userRedisDao.readFromRedis("rsa:" + uuid);
		if (privateKey == null) {
			throw new MyException();
		}
		String userid = request.get("userid");
		String password = request.get("password");
		
		password = rsa_2048.decrypt(password, privateKey);
		
		userSignupGoodbye.goodbye(userid, password);
		
		return SuccessResponse.toResponseEntity(200, "Bye", null);
	}
}
