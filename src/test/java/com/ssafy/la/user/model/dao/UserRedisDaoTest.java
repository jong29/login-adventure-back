package com.ssafy.la.user.model.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRedisDaoTest {

	@Autowired
	private UserRedisDao redisService;

	@Test
	public void saveAndReadFromRedis() {
		String key = "exampleKey";
		String value = "exampleValue";

		// 저장
		redisService.saveToRedis(key, value);

		// 읽기
		String retrievedValue = redisService.readFromRedis(key);

		// 확인
		Assertions.assertThat(retrievedValue).isEqualTo(value);
	}

}
