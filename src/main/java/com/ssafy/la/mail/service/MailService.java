package com.ssafy.la.mail.service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.ssafy.la.mail.model.dto.MailTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ssafy.la.mail.model.dao.MailMapper;
import com.ssafy.la.user.model.dto.UserVo;
import com.ssafy.la.util.exception.exceptions.MyException;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MailService {
	
//	@Autowired
//	MailMapper mailMapper;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Value("${spring.server.url}")
	private String siteURL; 

	@Transactional
	public void sendVerficiationEmail(UserVo user) {
		String randomCode = generateRandomString(64);
		String toAddress = user.getEmail();
	    String fromAddress = "jongwooseob@gmail.com";
	    String senderName = "Login Adventure";
	    String subject = "회원가입을 인증해주세요";
	    String content = "[[name]]님 안녕하세요,<br>"
	    		+ "회원가입 인증 링크입니다 해주세요:<br>"
	            + "<h3><a href=\"[[URL]]\" target=\"_self\">인증하기</a></h3>"
	            + "갑사합니다,<br>"
	            + "Login Adventure.";
	     
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    try {
			helper.setFrom(fromAddress, senderName);
			helper.setTo(toAddress);
			helper.setSubject(subject);
			content = content.replace("[[name]]", user.getUsername());
			String verifyURL = siteURL + "/verify?code=" + randomCode;
			content = content.replace("[[URL]]", verifyURL);
			helper.setText(content, true);
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
			throw new MyException(); // 회원가입 오류
		}
	    mailSender.send(message);
		mailMapper.insertToken(new MailTokenVo(toAddress, randomCode));
	}
	
	private String generateRandomString(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[length];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, length);
    }

}
