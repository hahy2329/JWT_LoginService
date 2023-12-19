package com.application.JWTLoginService.customer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.application.JWTLoginService.customer.JWT.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final JwtTokenProvider jwtTokenProvider;
	
	//암호화에 필요한 PasswordEncoder를 Bean등록합니다.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	//authenticationManager를 Bean등록합니다.
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.httpBasic().and().authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/user/**").hasRole("USER")
		.antMatchers("/**").permitAll();
		
	}
	
}
