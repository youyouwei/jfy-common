package com.jfy.common.redisson.module.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: jiahuiyang
 * @Date: Created in 13:14 2021/3/18
 */
@Component
@Slf4j
public class RedissonLock implements Lock {

    private RedissonClient redissonClient;

    public RedissonLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public RLock lock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.lock();
        return lock;
    }

    @Override
    public RLock lock(String key, int releaseTime) {
        RLock lock = redissonClient.getLock(key);
        lock.lock(releaseTime, TimeUnit.SECONDS);
        return lock;
    }

    @Override
    public RLock lock(String key, int releaseTime, TimeUnit unit) {
        RLock lock = redissonClient.getLock(key);
        lock.lock(releaseTime, unit);
        return null;
    }

    @Override
    public boolean tryLock(String key, int awaitTime, int releaseTime, TimeUnit unit) {
        RLock lock = redissonClient.getLock(key);
        try {
            return lock.tryLock(awaitTime, releaseTime, unit);
        } catch (InterruptedException e) {
            log.info("RedissonLock tryLock false info: {}", e);
            return false;
        }
    }

    @Override
    public void unLock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }

    @Override
    public void unLock(RLock lock) {
        lock.unlock();
    }
}
