package com.ssafy.la.user.model.dao;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRedisDao {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    /*
     * Duration.ofMillis(Long tokenLive)
     */
    public void saveToRedis(String key, String value, Duration duration) {
    	redisTemplate.opsForValue().set(key, value, duration);
    }
    
    public void saveToRedis(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String readFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    
    public void deleteFromRedis(String key) {
    	redisTemplate.delete(key);
    }
}
