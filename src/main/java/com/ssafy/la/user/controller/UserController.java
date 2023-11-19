package com.ssafy.la.user.controller;

import java.util.HashMap;
import java.util.Map;

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

	@PostMapping("/login")
	public ResponseEntity<CommonResponse> login(@RequestBody LoginRequestDto loginRequestDto) {

		Map<String, Object> username = ull.login(loginRequestDto);
		return SuccessResponse.toResponseEntity(200, "로그인 성공", username);

	}

}
