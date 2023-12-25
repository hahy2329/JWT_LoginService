package com.application.JWTLoginService.user.controller;

import java.util.Map;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.application.JWTLoginService.user.service.UserDetailService;
import com.application.JWTLoginService.user.dao.UserDAO;
import com.application.JWTLoginService.user.dto.UserDTO;
import com.application.JWTLoginService.user.jwtToken.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	

	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailService userDetailService;
	private final UserDAO userDAO;
	
	@PostMapping("/join") //회원가입
	public String join(@RequestBody Map<String, String> user) throws Exception {
		
		userDetailService.save(UserDTO.builder().userId(user.get("userId"))
				.password(passwordEncoder.encode(user.get("password")))
				.name(user.get("name"))
				.email(user.get("email")).build());
		
		return "가입 완료";
		
	}
	//로그인
	@PostMapping("/login")
	public String login(@RequestBody Map<String, String> loginUser) throws Exception{
		
		UserDTO user = userDAO.findByUserEmail(loginUser.get("email"))
				.orElseThrow(() -> new IllegalAccessException("가입되지 않은 E-MAIL입니다."));
		if(!passwordEncoder.matches(loginUser.get("password"), user.getPassword())) {
			throw new IllegalAccessException("잘못된 비밀번호입니다.");
		}
		
		return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
		
		
	}
	
	
	
}
