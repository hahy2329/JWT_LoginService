package com.application.JWTLoginService.user.dao;

import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.application.JWTLoginService.user.dto.UserDTO;

@Repository
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Optional<UserDTO> findByUserId(String userName) {
		return sqlSession.selectOne("user.getUserInfo", userName);
	}

}
