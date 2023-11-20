package com.ssafy.la.user.model.security.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SecurityMapper {
	
	void insertSecurity(@Param("userid") String userid, @Param("salt") String salt);
	
	String readSalt(String userid);
	
	void deleteSalt(String userid);
}
