package com.ssafy.la.filter;

import com.ssafy.la.user.model.dao.UserRedisDao;
import com.ssafy.la.user.model.security.dao.SecurityMapper;
import com.ssafy.la.util.exception.exceptions.MyException;
import com.ssafy.la.util.security.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class GlobalFilter extends OncePerRequestFilter {

	@Autowired
	JWTProvider jwtProvider;

	@Autowired
	SecurityMapper securityMapper;

	@Autowired
	UserRedisDao userRedisDao;

	private final List<String> whiteList = new ArrayList<>(Arrays.asList("login", "logout", "modify", "delete"));

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String[] splitURI = requestURI.split("/");
		switch (splitURI[1]) {
		case "user":
		case "board":
			if (checkAuthorization(splitURI[2])) { // 권한이 필요한 상황
				String salt = null;
				String userid = (String) request.getAttribute("userid");

				if (userid == null) {
					throw new MyException();
				}

				if ("reissue".equals(splitURI[2])) { // 토큰 재발급 상황
					String rtk = (String) request.getAttribute("rtk");
					if (rtk == null) { // rtk가 없는 경우
						throw new MyException();
					}
					String token = userRedisDao.readFromRedis("rtk:" + userid);
					if (token == null) {
						throw new MyException();
					}
					if (rtk.equals(token)) {
//						salt = securityMapper.getSalt(userid);	// salt값 가져와서 한 번 더 토큰 검증
						if (!jwtProvider.validateToken(rtk, salt)) {
							throw new MyException();
						}
					}

				}
				String atk = (String) request.getAttribute("atk");
				if (atk == null) { // atk이 없는 경우
					throw new MyException();
				}
				String token = userRedisDao.readFromRedis("atk:" + userid);
				if (token == null) {
					throw new MyException();
				}
				if (atk.equals(token)) {
//					salt = securityMapper.getSalt(userid);
					if (!jwtProvider.validateToken(atk, salt)) { // atk 만료된 상황 -> rtk 들고 오라고 응답
						throw new MyException();
					}
				}
			}
			break;
		default:
			throw new MyException();
		}

		filterChain.doFilter(request, response);
	}

	private boolean checkAuthorization(String request) {
		return whiteList.contains(request);
	}
}
