package com.ssafy.la.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.la.user.model.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;

}
