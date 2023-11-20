package com.ssafy.la.user.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.la.user.model.dto.UserVo;

import com.ssafy.la.user.model.dto.UserVo;

@Mapper
public interface UserMapper {

	UserVo login(@Param("userid")String userid, @Param("userpassword") String userpassword);
	
	void signup(UserVo userVo);
	
	void delete(String userid);
}
