package com.application.JWTLoginService.user.dao;

import java.util.Optional;

import com.application.JWTLoginService.user.dto.UserDTO;

public interface UserDAO {
	
	public void save(UserDTO userDTO) throws Exception;
	Optional<UserDTO> findByUserEmail(String email);
}
