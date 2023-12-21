package com.application.JWTLoginService.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.application.JWTLoginService.user.jwtToken.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final JwtTokenProvider jwtTokenProvider;
	
	//암호화에 필요한 PasswordEncoder를 Bean 등록합니다.
	@Bean //PasswordEncoder는 스프링 시큐리티 모듈에서 제공하는 인터페이스로, 비밀번호를 안전하게 암호화하고, 일치 여부를 검증하는 기능
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	
	//authenticationManager를 Bean 등록합니다.
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http
		.httpBasic() //Http basic Auth 기반으로 로그인 인증창 뜸. 기본 인증 로그인을 이용하지 않으면 disable
		.disable()//rest api만을 고려하여 기본 설정은 해제하겠습니다.
		.csrf().disable() // csrf 보안 토큰 disable처리.
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
		.and() 
		.authorizeRequests() //요청에 대한 권한을 지정하겠다.
		.antMatchers("/admin/**").hasRole("ADMIN")
		//리소스 admin으로 시작하는 모든 url은 인증 후 ADMIN 레벨의 권한을 가진 사용자만 접근 허용
		.antMatchers("/user/**").hasRole("USER")
		//리소스 user로 시작하는 모든 url은 인증 후 USER 레벨의 권한을 가진 사용자만 접근 허용
		.antMatchers("/**").permitAll()//위에 설정한 admin, user경로 외엔 모든 사람이 접근 가능
		.and()
		.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
		//JWT 인증을 위하여 직접 구현한 필터(JwtAuthenticationFilter)를 UsernamePasswordAuthenticationFilter 전에 실행하겠다는 설정이다.
		
		
	}
}
