package com.ssafy.la.mail.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.la.mail.model.service.MailService;
import com.ssafy.la.util.common.CommonResponse;
import com.ssafy.la.util.common.SuccessResponse;
import com.ssafy.la.util.exception.exceptions.MyException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mail")
public class MailController {

	@Autowired
	MailService mailService;
	
	@GetMapping("/verify")
	public ModelAndView verify(@RequestParam String code) {
		ModelAndView model = new ModelAndView();
		
		if (!mailService.verifyToken(code)) {
			model.setViewName("MailVerificationFail");
		} else {
			model.setViewName("MailVerificationSuccess");;
		}
		return model;
	}
	
}
