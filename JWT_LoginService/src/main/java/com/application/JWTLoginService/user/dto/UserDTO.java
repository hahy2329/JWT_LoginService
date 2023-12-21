package com.application.JWTLoginService.user.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
	
	private String userId;
	private String password;
	private String name;
	private String email;
	private Date birthDt;
	private Date enrollDt;
	
	
}
