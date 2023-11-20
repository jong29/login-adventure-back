package com.ssafy.la.user.model.service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.la.user.model.dao.UserMapper;
import com.ssafy.la.user.model.dao.UserRedisDao;
import com.ssafy.la.user.model.dto.LoginRequestDto;
import com.ssafy.la.user.model.dto.UserVo;
import com.ssafy.la.user.model.security.dao.SecurityMapper;
import com.ssafy.la.util.exception.exceptions.KeyTimeoutException;
import com.ssafy.la.util.exception.exceptions.LoginFailException;
import com.ssafy.la.util.exception.exceptions.MyException;
import com.ssafy.la.util.security.JWTProvider;
import com.ssafy.la.util.security.RSA_2048;
import com.ssafy.la.util.security.SHA_256;

@Service
public class UserLoginLogout {

	@Autowired
	SecurityMapper securityMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	UserRedisDao userRedisDao;
	@Autowired
	JWTProvider jwtProvider;
	@Autowired
	RSA_2048 rsa;
	@Autowired
	SHA_256 sha;
	
	private static final long atkExp = 900000L;
	private static final long rtkExp = 604800000L;
	private static final int loginLimit = 5;
	

	public Map<String, Object> login(LoginRequestDto loginRequestDto) {
		// get uuid
		String uuid = loginRequestDto.getUuid();

		// search for uuid in redis
		String privateKey = userRedisDao.readFromRedis(uuid);

		// if not in redis throw exception
		if (privateKey == null) {
			throw new KeyTimeoutException();
		}

		// decrypt id
		String userid = rsa.decrypt(loginRequestDto.getUserid(), privateKey);
		
		// check login limit
		if (!loginLimitCheck(userid)) {
			throw new MyException();
		}
		
		// decrypt password
		String userpassword = rsa.decrypt(loginRequestDto.getUserpassword(), privateKey);

		loginRequestDto.setUserid(userid);
		loginRequestDto.setUserpassword(userpassword);

		String salt = securityMapper.readSalt(userid);
		String hashedPassword = sha.SHA256(userpassword, salt);

		UserVo user = userMapper.login(userid, hashedPassword);
		if (user == null) {
			throw new LoginFailException();
		}
		
		//create and return jwt
		String atk = jwtProvider.createAccessToken(user, atkExp, salt);
		String rtk = jwtProvider.createRefreshToken(userid, rtkExp, salt);
		
		// put jwt in redis
		userRedisDao.saveToRedis("atk:" + userid, atk, Duration.ofMillis(atkExp));
		userRedisDao.saveToRedis("rtk:" + userid, rtk, Duration.ofMillis(rtkExp));
		
		Map<String, Object> res = new HashMap<>();
		res.put("userid", userid);
		res.put("atk", atk);
		res.put("rtk", rtk);
		return res;
	}
	
	public Map<String, Object> reissue(String userid) {
		String salt = securityMapper.readSalt(userid);
		UserVo user = userMapper.userinfo(userid);
		
		//create and return jwt
		String atk = jwtProvider.createAccessToken(user, atkExp, salt);
		
		// put jwt in redis
		userRedisDao.saveToRedis("atk:" + userid, atk, Duration.ofMillis(atkExp));
		
		Map<String, Object> res = new HashMap<>();
		res.put("userid", userid);
		res.put("atk", atk);
		return res;
	}
	
	public boolean loginLimitCheck(String userid) {
		Long LoginRequestCount = userRedisDao.incrementAttempt(userid);
		if (LoginRequestCount > loginLimit) return false;
		return true;
	}
	
//	public boolean loginLimitCheck(String userid, String ) {
//	}

}
