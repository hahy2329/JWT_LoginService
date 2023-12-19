package com.application.JWTLoginService.customer.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.application.JWTLoginService.customer.dao.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
	
	private final CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUserName(String userName) throws UsernameNotFoundException {
		return (UserDetails) customerRepository.findByEmail(userName)
				.orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
	}
	
	
	
}
