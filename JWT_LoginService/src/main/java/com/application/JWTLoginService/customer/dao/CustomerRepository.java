package com.application.JWTLoginService.customer.dao;

import java.util.Optional;

import com.application.JWTLoginService.customer.dto.CustomerDTO;

public interface CustomerRepository  {
	
	public Optional<CustomerDTO> findByEmail(String email);
}
