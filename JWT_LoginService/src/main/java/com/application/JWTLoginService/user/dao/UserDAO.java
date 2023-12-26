package com.application.JWTLoginService.user.dao;


import com.application.JWTLoginService.user.dto.UserDTO;

public interface UserDAO {
	
	public void save(UserDTO userDTO) throws Exception;
	public UserDTO findByUserEmail(String email);
}
