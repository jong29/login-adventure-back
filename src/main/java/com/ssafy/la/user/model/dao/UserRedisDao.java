package com.ssafy.la.user.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserRedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void saveToRedis(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String readFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
