package com.ssafy.la.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssafy.la.util.exception.exceptions.AtkTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.la.user.model.dao.UserRedisDao;
import com.ssafy.la.user.model.security.dao.SecurityMapper;
import com.ssafy.la.util.exception.exceptions.MyException;
import com.ssafy.la.util.security.JWTProvider;

//@Component
public class GlobalInterceptor implements HandlerInterceptor{

	@Autowired
	JWTProvider jwtProvider;

	@Autowired
	SecurityMapper securityMapper;

	@Autowired
	UserRedisDao userRedisDao;

	@Value("${spring.front.url}")
	private String ORIGIN;

	private final List<String> whiteList = new ArrayList<>(Arrays.asList("logout", "modify", "delete", "userinfo", "reissue"));

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestbody = (String) request.getAttribute("requestBody");

		if (!ORIGIN.equals(request.getHeader("Origin"))) {
			return false;
		}

		String requestURI = request.getRequestURI();
		String[] splitURI = requestURI.split("/");
		switch (splitURI[1]) {
			case "user":
			case "board":
				if (checkAuthorization(splitURI[2])) { // 권한이 필요한 상황
					String salt;
					ObjectMapper om = new ObjectMapper();
					if (!"OPTIONS".equals(request.getMethod())) {
						Map<String, String> map = om.readValue(requestbody, Map.class);
						String userid = map.get("userid");

						if (userid == null) {
							throw new MyException();
						}

						if ("reissue".equals(splitURI[2])) { // 토큰 재발급 상황
							String rtk = map.get("rtk");
							if (rtk == null) { // rtk가 없는 경우
								throw new MyException();
							}
							String token = userRedisDao.readFromRedis("rtk:" + userid);
							if (token == null) {
								throw new MyException(); // 로그아웃
							}
							if (rtk.equals(token)) {
								salt = userRedisDao.readFromRedis("salt:"+userid); // salt값 가져와서 한 번 더 토큰 검증
								if (!jwtProvider.validateToken(rtk, salt)) { // 토큰 변조
									throw new MyException();
								}
							}

						} else {
							String atk = map.get("atk");
							if (atk == null) { // atk이 없는 경우
								throw new MyException();
							}
							String token = userRedisDao.readFromRedis("atk:" + userid);
							if (token == null) {
								throw new AtkTimeoutException();
							}
							if (atk.equals(token)) {
								salt = userRedisDao.readFromRedis("salt:" + userid);
								if (!jwtProvider.validateToken(atk, salt)) { // atk 만료된 상황 -> rtk 들고 오라고 응답
									throw new MyException();
								}
							}
						}
					}
				}
				break;
			default:
				throw new MyException();
		}
		return true;
	}

	private boolean checkAuthorization(String request) {
		return whiteList.contains(request);
	}
}
