package com.application.JWTLoginService.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.application.JWTLoginService.user.jwtToken.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

//GenericFilterBean이란, Spring에 들어가기 전 수행되는 것이 필터이다.
//그래서 스프링 컨테이너에 속해있는 Bean을 가지고 올 수 없다. 이것을 도와주는 것이 GenericFilterBean이다.

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
	
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//헤더에서 JWT를 받아옵니다.
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
		//유효한 토큰인지 확인.
		if(token != null && jwtTokenProvider.validateToken(token)) {
			
			//토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			//SecurityContext에 Authentication 객체를 저장합니다.
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request, response);
		//이 메서드를 통해서 요청(Request)와 응답(Response)쌍이 체인을 통과할 때마다 컨테이너에서 호출됩니다. 
		//체인을 따라서 계속 다음에 존재하는 필터로 이동하는 것.
	}
	
	
}
