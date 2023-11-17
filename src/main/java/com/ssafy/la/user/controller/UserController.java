package com.ssafy.la.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ssafy.la.user.model.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;

}
