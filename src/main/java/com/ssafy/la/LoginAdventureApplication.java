package com.ssafy.la;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan(basePackages = "com.ssafy.la.*.model.dao", sqlSessionFactoryRef = "loginAdventureSqlSessionFactory")
@MapperScan(basePackages = "com.ssafy.la.user.model.security.dao", sqlSessionFactoryRef = "securitySqlSessionFactory")
public class LoginAdventureApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginAdventureApplication.class, args);
	}

}
