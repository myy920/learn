package com.myy.util.redis;

import java.util.concurrent.Callable;

public class RedisUtils {

    public static void lock(String key, Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T lock(String key, Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
