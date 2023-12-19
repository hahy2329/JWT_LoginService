package com.application.JWTLoginService.customer.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomerService {
	
	public UserDetails loadUserByUserName(String userName) throws UsernameNotFoundException;
}
