package com.ssafy.la.filter;

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

    private final List<String> whiteList = new ArrayList<>(Arrays.asList("login", "logout", "modify", "delete"));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String[] splitURI = requestURI.split("/");
        switch (splitURI[1]) {
            case "user":
            case "board":
                if (checkAuthorization(splitURI[2])) {
                    String salt = null;
                    if ("reissueAtk".equals(splitURI[2])) {
                        String rtk = (String) request.getAttribute("rtk");
                        if (rtk == null) {
                            throw new MyException();
                        }
                        if (!jwtProvider.validateToken(rtk, salt)) {
                            throw new MyException();
                        }
                    }
                    String atk = (String) request.getAttribute("atk");
                    if (atk == null) {
                        throw new MyException();
                    }
//                    salt = securityMapper.getSalt();
                    if (!jwtProvider.validateToken(atk, salt)) {
                        throw new MyException();
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
