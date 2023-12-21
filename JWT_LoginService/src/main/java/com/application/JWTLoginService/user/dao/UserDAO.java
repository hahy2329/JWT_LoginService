package com.application.JWTLoginService.user.dao;

import java.util.Optional;

import com.application.JWTLoginService.user.dto.UserDTO;

public interface UserDAO {
	
	Optional<UserDTO> findByUserId(String userName);
}
