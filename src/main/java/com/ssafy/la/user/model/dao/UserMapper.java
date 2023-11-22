package com.ssafy.la.user.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.la.user.model.dto.UserVo;

@Mapper
public interface UserMapper {

	UserVo login(@Param("userid")String userid, @Param("password") String password);
	
	UserVo userinfo(String userid);
	
	void signup(UserVo userVo);
	
	void goodbye(@Param("userid")String userid, @Param("password") String password);
	
	void modify(@Param("userid")String userid, @Param("password") String password);
	
	String checkId(String userId);

	String checkEmail(String email);

	void verifyUser(String email);
}
