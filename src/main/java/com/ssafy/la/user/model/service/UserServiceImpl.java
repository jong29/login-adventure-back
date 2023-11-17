package com.ssafy.la.user.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRegisterDelete userRegisterDelete;
	
	@Autowired
	UserLoginLogout userLoginLogout;
	
	@Autowired
	UserViewModify userViewModify;
	
	@Autowired
	UserFindIdPwd userFindIdPwd;

	

}
