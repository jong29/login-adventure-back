package com.ssafy.la.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.la.user.model.service.UserService;
import com.ssafy.la.util.common.SuccessResponse;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/test")
	public ResponseEntity<SuccessResponse> test() {
		Map<String, String> var = new HashMap<>();
		var.put("key", "val");
		
		return SuccessResponse.toResponseEntity(200, "success test", null);
	}

}
