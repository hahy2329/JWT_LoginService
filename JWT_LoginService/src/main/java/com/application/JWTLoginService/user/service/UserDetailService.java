package com.application.JWTLoginService.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.application.JWTLoginService.user.dao.UserDAO;
import com.application.JWTLoginService.user.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
	
	private final UserDAO userDAO;
	
	
	//가입 메서드
	public void save(UserDTO userDTO) throws Exception{
		
		userDAO.save(userDTO);
	}
	
	
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return (UserDetails) userDAO.findByUserEmail(userName);
				
	}
	

}
