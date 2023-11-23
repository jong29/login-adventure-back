package com.ssafy.la.user.model.service;

import com.ssafy.la.mail.model.service.MailService;
import com.ssafy.la.user.model.dao.UserMapper;
import com.ssafy.la.user.model.dto.UserSignupDto;
import com.ssafy.la.user.model.dto.UserVo;
import com.ssafy.la.user.model.security.dao.SecurityMapper;
import com.ssafy.la.util.exception.exceptions.MyException;
import com.ssafy.la.util.security.RSA_2048;
import com.ssafy.la.util.security.SHA_256;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.la.user.model.dao.UserRedisDao;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserSignupGoodbye {
    // 회원 가입, 탈퇴

    @Autowired
    UserRedisDao userRedisDao;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SecurityMapper securityMapper;

    @Autowired
    MailService mailService;

    @Autowired
    SHA_256 sha_256;

    @Autowired
    RSA_2048 rsa_2048;

    @Transactional(rollbackFor = Exception.class)
    public void signup(UserSignupDto userSignupDto) {

        String uuid = userSignupDto.getUuid();
        String privateKey = userRedisDao.readFromRedis("rsa:" + uuid);
        if (privateKey == null) {
            throw new MyException();
        }
        userSignupDto.setPassword(rsa_2048.decrypt(userSignupDto.getPassword(), privateKey));
        userSignupDto.setEmail(rsa_2048.decrypt(userSignupDto.getEmail(), privateKey));
        userSignupDto.setUsername(rsa_2048.decrypt(userSignupDto.getUsername(), privateKey));

        System.out.println("signupService");
        UserVo user = new UserVo();
        user.setUserid(userSignupDto.getUserid());
        user.setUsername(userSignupDto.getUsername());
        user.setRole("unCertified");
        user.setEmail(userSignupDto.getEmail());

        String pw = userSignupDto.getPassword();
		if (!isValidPassword(pw)) {
			throw new MyException();
		}

        String email = userSignupDto.getEmail();
		if (!isValidEmail(email)) {
			throw new MyException();
		}

        // 일단 보류
//		mailService.sendVerficiationEmail(user);

        String salt = sha_256.getSalt();
        user.setPassword(sha_256.SHA256(pw, salt));

        userMapper.signup(user); // 회원 저장
        securityMapper.insertSecurity(user.getUserid(), salt); // salt 저장
    }

    @Transactional
    public void goodbye(String userid, String password) {
        String salt = securityMapper.readSalt(userid);
        password = sha_256.SHA256(password, salt);

        userRedisDao.deleteFromRedis("atk:" + userid);
        userRedisDao.deleteFromRedis("rtk:" + userid);
        userRedisDao.deleteFromRedis("loginAttempt:" + userid);
        userRedisDao.deleteFromRedis("rsa:" + userid);
        userMapper.goodbye(userid, password);
        securityMapper.deleteSalt(userid);
    }

    public boolean isValidPassword(String password) {
        // 비밀번호 길이 검증 (8자 이상, 16자 이하)
        if (password.length() < 8 || password.length() > 16) {
            return false;
        }

        // 비밀번호에 띄어쓰기가 포함되어 있는지 검증
        if (password.contains(" ") || password.contains("<") || password.contains(">")) {
            return false;
        }

        // 최소한 하나 이상의 문자, 숫자, 특수문자를 포함하는지 검증
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        // 이메일 주소의 정규표현식 패턴
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
