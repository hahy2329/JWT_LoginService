package com.application.JWTLoginService.customer.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CustomerDTO {
	
	private long customerId;
	private String password;
	private String name;
	private String email;
	private Date birthDt;
	private Date enrollDt;
}
