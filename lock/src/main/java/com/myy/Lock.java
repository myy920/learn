package com.myy;

import org.springframework.data.redis.core.RedisTemplate;

public class Lock {

    private RedisTemplate<String, String> redisTemplate;

    private final String lockName;

    private Integer tryTimes;

    private Integer tryInterval;

    private Integer renewLiveTime;

    private Integer renewInterval;

    public Lock(String lockName) {
        this.lockName = lockName;
    }


}
