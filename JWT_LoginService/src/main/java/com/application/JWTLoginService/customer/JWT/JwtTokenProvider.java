package com.application.JWTLoginService.customer.JWT;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.application.JWTLoginService.customer.service.CustomerService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
	
	private String secretKey = "gsgsdfsdfoclffdkdikkflgkfkfkfkslsl";
	
	private long tokenValidTime = 30 * 60 * 1000L; //토큰 유효시간 30분으로 지정
	
	private final CustomerService customerService;
	
	//객체 초기화, secretKey를 Base64로 인코딩한다.
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	//JWT 토큰 생성
	public String createToken(String userPk, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(userPk); // JWT Payload에 저장되는 정보단위
		claims.put("roles", roles); //정보는 key / value 쌍으로 저장된다.
		Date now = new Date();
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		Map<String, Object> headerMap = new HashMap<String, Object>();
		
		headerMap.put("typ", "JWT");
		headerMap.put("alg", "HS256");
		
		
		return Jwts.builder().setHeader(headerMap)//헤더에 담을 정보
				.setClaims(claims)//정보저장
				.setIssuedAt(now)//토큰 발행 시간 정보
				.setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
				.signWith(signatureAlgorithm, secretKey) // 사용할 암호화 알고리즘
														 // signature에 들어갈 secret값 세팅
				.compact();
		
	}
	
	
}
