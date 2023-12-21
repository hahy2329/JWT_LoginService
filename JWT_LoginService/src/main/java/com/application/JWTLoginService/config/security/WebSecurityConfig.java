package com.application.JWTLoginService.config.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.httpBasic() //Http basic Auth 기반으로 로그인 인증창 뜸. 기본 인증 로그인을 이용하지 않으면 disable
		.and() 
		.authorizeRequests() //요청에 대한 권한을 지정하겠다.
		.antMatchers("/admin/**").hasRole("ADMIN")
		//리소스 admin으로 시작하는 모든 url은 인증 후 ADMIN 레벨의 권한을 가진 사용자만 접근 허용
		.antMatchers("/user/**").hasRole("USER")
		//리소스 user로 시작하는 모든 url은 인증 후 USER 레벨의 권한을 가진 사용자만 접근 허용
		.antMatchers("/**").permitAll(); //위에 설정한 admin, user경로 외엔 모든 사람이 접근 가능
		
		
	}
}
