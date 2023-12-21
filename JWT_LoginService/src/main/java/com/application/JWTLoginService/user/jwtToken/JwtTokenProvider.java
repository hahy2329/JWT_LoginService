package com.application.JWTLoginService.user.jwtToken;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
	
	@PostConstruct //객체 초기화(다른 리소스 호출을 해도 해당 객체는 자동 초기화), secretKey를 Base64로 인코딩한다.
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
				.compact(); //위의 설정대로 실행하겠다.
	}
	
	
	//JWT 토큰에서 인증 정보 조회 
	public Authentication getAuthentication(String token) { //요청한 회원의 토큰과 서버측의 토큰 비교
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
		//반환된 사용자의 아이디를 구현했던 loadUserByUsername에 넣고 UserDetails형태로 사용자의 정보가 정보가 반환된다.
		return new UsernamePasswordAuthenticationToken(userDetails,"" ,userDetails.getAuthorities());
		//그리고 UsernamePasswordAuthenticationToken() 안에 넣는것을 볼 수 있는데, UsernamePasswordAuthenticationToken은
		//인증된 사용자의 아이디, 비밀번호, 권한을 파라미터로 받는다.
		//비밀번호를 ""한 이유는 사용자의 아이디는 우리가 직접 userDetailsService를 만들어서 찾았지만, 비밀번호는 SpringSecurity에서 알아서 다 해준다고 한다. 
	}
	
	//토큰에서 회원 정보 추출
	public String getUserPk(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
		//토큰을 파라미터로 받고 getBody().getSubject()를 하면 사용자의 아이디가 반환된다.
		
	}
	
	//Request의 Header에서 token값을  가져옵니다. , "X-AUTH-TOKEN" : "TOKEN값"
	public String resolveToken(HttpServletRequest request) {
		
	}
	
	
}
