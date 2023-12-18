package com.application.JWTLoginService.customer.JWT;

import org.springframework.stereotype.Component;

import com.application.JWTLoginService.customer.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
	
	private String secretKey = "gsgsdfsdfoclffdkdikkflgkfkfkfkslsl";
	
	private long tokenValidTime = 30 * 60 * 1000L; //토큰 유효시간 30분으로 지정
	
	private final CustomerService customerService;
	
	//객체 초기화, secretKey를 Base64로 인코딩한다.
	
	
	
}
