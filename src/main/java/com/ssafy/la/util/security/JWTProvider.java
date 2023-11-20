package com.ssafy.la.util.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.ssafy.la.user.model.dto.UserVo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTProvider {
	
	private final String Issuer = "JongWooSeob";

	public String createAccessToken(UserVo user, Long tokenLive, String salt) {
		String token = "";
		Date now = new Date();
		
		Claims claims = Jwts.claims();
		claims.put("userid", user.getUserid());
		claims.put("issuedAt", now.getTime());
		claims.put("role", user.getRole());
		claims.put("issuer", Issuer);
		
		token = Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setClaims(claims)
				.setIssuedAt(now)
				.setIssuer(Issuer)
				.setExpiration(new Date(now.getTime() + tokenLive))
				.signWith(SignatureAlgorithm.HS256, salt)
				.compact();
		
		return token;
	}

	public String createRefreshToken(String userid, Long tokenLive, String salt) {
		String token = "";
		Date now = new Date();
		
		Claims claims = Jwts.claims();
		claims.put("userid", userid);
		claims.put("issuer", Issuer);
		
		token = Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setClaims(claims)
				.setIssuedAt(now)
				.setIssuer(Issuer)
				.setExpiration(new Date(now.getTime() + tokenLive))
				.signWith(SignatureAlgorithm.HS256, salt)
				.compact();
		
		return token;
	}
	
	public boolean validateToken(String jwtToken, String salt) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(salt).parseClaimsJws(jwtToken);	// 위 변조 확인
			return !(claims.getBody().getExpiration().before(new Date()) && claims.getBody().getIssuer().equals(Issuer));
		} catch (Exception e) {
			return false;
		}	
	}
}
