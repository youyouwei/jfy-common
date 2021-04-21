package com.jfy.common.redisson.module.lock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: jiahuiyang
 * @Date: Created in 13:06 2021/3/17
 */
public interface Lock {
    /**
     * get lock
     * @param key
     * @return
     */
    RLock lock(String key);

    /**
     * get lock by release time
     * @param key
     * @param releaseTime
     * @return
     */
    RLock lock(String key, int releaseTime);

    /**
     * get lock by release time and unit
     *
     * @param key
     * @param releaseTime
     * @param unit
     * @return
     */
    RLock lock(String key, int releaseTime, TimeUnit unit);

    /**
     * try lock
     *
     * @param key
     * @param awaitTime
     * @param releaseTime
     * @param unit
     * @return
     */
    boolean tryLock(String key, int awaitTime, int releaseTime, TimeUnit unit);

    /**
     * release lock by key
     * @param key
     */
    void unLock(String key);

    /**
     * release lock by lock
     * @param lock
     */
    void unLock(RLock lock);

}
