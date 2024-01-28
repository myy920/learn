package com.myy;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @NonRepeat
    @GetMapping("/aaa")
    String aaa() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("a1",1);
        map.put("a2",2);
        map.put("a3",3);

        redisTemplate.opsForValue().set("aaa", map, 10, TimeUnit.SECONDS);
        return "aaa";
    }

}
