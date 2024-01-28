package com.myy;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class Demo {

    public static void main(String[] args) {
        ValueOperations<Object, Object> ops = new RedisTemplate<>().opsForValue();


    }

}
