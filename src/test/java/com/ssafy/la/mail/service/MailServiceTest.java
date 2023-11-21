package com.ssafy.la.mail.service;

import com.ssafy.la.mail.model.service.MailService;
import com.ssafy.la.user.model.dto.UserVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class MailServiceTest {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailService mailService;

    @Test
    void  sendVerficiationEmail() {

        UserVo user = new UserVo("jongool", "1234", "jongwoop97@gmail.com", "jongwoo", "admin");
        mailService.sendVerficiationEmail(user);
//        verify(mailSender, times(1)).send((MimeMessage) any());

    }
}