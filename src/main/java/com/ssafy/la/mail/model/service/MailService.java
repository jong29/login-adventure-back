package com.ssafy.la.mail.model.service;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.ssafy.la.user.model.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.la.mail.model.dao.MailMapper;
import com.ssafy.la.mail.model.dto.MailTokenVo;
import com.ssafy.la.user.model.dto.UserVo;
import com.ssafy.la.util.exception.exceptions.MyException;


@Service
public class MailService {
	
	@Autowired
	MailMapper mailMapper;

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Value("${spring.server.url}")
	private String siteURL; 

	@Transactional
	public void sendVerficiationEmail(UserVo user) {
		String randomCode = UUID.randomUUID().toString();
		String toAddress = user.getEmail();
	    String fromAddress = "jongwooseob@gmail.com";
	    String senderName = "Login Adventure";
	    String subject = "회원가입을 인증해주세요";
	    String content = "[[name]]님 안녕하세요,<br>"
	    		+ "회원가입 인증 링크입니다:<br>"
	            + "<h3><a href=\"[[URL]]\" target=\"_self\">인증하기</a></h3>"
	            + "갑사합니다,<br>"
	            + "종우섭이가 (Login Adventure 일동).";
	     
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    try {
			helper.setFrom(fromAddress, senderName);
			helper.setTo(toAddress);
			helper.setSubject(subject);
			content = content.replace("[[name]]", user.getUsername());
			String verifyURL = siteURL + "/mail/verify?code=" + randomCode;
			content = content.replace("[[URL]]", verifyURL);
			helper.setText(content, true);
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
			System.out.println("메일 전송 오류");
			throw new MyException(); // 회원가입 오류
		}
	    mailSender.send(message);
		mailMapper.insertToken(new MailTokenVo(toAddress, randomCode));
	}

	@Transactional
	public boolean verifyToken(String verifytoken) {
		String usermail = mailMapper.verifyToken(verifytoken);
		if (usermail == null) {
			System.out.println("메일 인증 실패");
			return false;
		} else {
			mailMapper.deleteToken(verifytoken);
			userMapper.verifyUser(usermail);
		}
		return true;
	}

}
