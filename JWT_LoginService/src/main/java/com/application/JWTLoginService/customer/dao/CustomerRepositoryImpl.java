package com.application.JWTLoginService.customer.dao;

import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.application.JWTLoginService.customer.dto.CustomerDTO;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Optional<CustomerDTO> findByEmail(String email) {
		return sqlSession.selectOne("customer.getEmail", email);
	}

}
