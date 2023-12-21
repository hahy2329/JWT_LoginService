package com.application.JWTLoginService.user.jwtToken;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
	
	private String secretKey = "aaaabbbbccccddddeeeeffffgggghhhhiiii";
	
	private long tokenVaildTime = 30 * 60 * 1000L; // 토큰 유효시간 30분
	
	private final UserDetailsService userDetailsService;
	
	@PostConstruct //객체 초기화, secretKey를 Base64로 인코딩한다.
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	//JWT 토큰 생성
	public String createToken(String userPk, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(userPk); // JWT Payload에 저장되는 정보단위
		claims.put("roles", roles); //정보는 key / value 쌍으로 저장된다.
		Date now = new Date();
		return Jwts.builder()
				.setClaims(claims) //정보 저장
				.setIssuedAt(now) // 토큰 발행 시간 정보
				.setExpiration(new Date(now.getTime() + tokenVaildTime)) //JWT 만료 시간
				.signWith(SignatureAlgorithm.HS256, secretKey) //사용할 암호화 알고리즘과 signature에
				//들어갈 secret값 세팅
				.compact();
	}
}
