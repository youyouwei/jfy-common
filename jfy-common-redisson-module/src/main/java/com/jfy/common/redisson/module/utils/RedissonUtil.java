package com.jfy.common.redisson.module.utils;

import org.redisson.api.RedissonClient;

/**
 * @Description:
 * @Author: jiahuiyang
 * @Date: Created in 17:49 2021/4/1
 */
public final class RedissonUtil {

    private final static RedissonClient REDISSON_CLIENT = SpringContextHolder.getBean(RedissonClient.class);


    public static void delKeys(String... key) {
        REDISSON_CLIENT.getKeys().delete(key);
    }


}
