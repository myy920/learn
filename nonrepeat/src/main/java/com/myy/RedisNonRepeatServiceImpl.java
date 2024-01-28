package com.myy;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RedisNonRepeatServiceImpl implements NonRepeatService {

    public static final String NON_REPEAT_PREFIX = "nonRepeat:";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String createToken() {
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(NON_REPEAT_PREFIX + token, "", 30, TimeUnit.MINUTES);
        return token;
    }

    @Override
    public boolean deleteToken(String token) {
        return Boolean.TRUE.equals(redisTemplate.delete(NON_REPEAT_PREFIX + token));
    }
}
