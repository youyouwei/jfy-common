package com.jfy.common.redisson.module.utils;


import com.jfy.common.redisson.module.lock.Lock;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: jiahuiyang
 * @Date: Created in 18:53 2021/3/16
 */
@Slf4j
public final class LockUtil {

    private final static Lock lock = SpringContextHolder.getBean(Lock.class);

    /**
     * lock
     *
     * @param key
     * @return
     */
    public static RLock lock(String key) {
        log.info("LockUtil lock by key: {}", key);
        return lock.lock(key);
    }

    /**
     * lock by release time
     *
     * @param key
     * @param releaseTime
     * @return
     */
    public static RLock lock(String key, int releaseTime) {
        log.info("LockUtil lock by key: {}, release time: {}", key, releaseTime);
        return lock.lock(key, releaseTime);
    }

    /**
     * lock by release time
     *
     * @param key
     * @param releaseTime
     * @param unit
     * @return
     */
    public static RLock lock(String key, int releaseTime, TimeUnit unit) {
        log.info("LockUtil lock by key: {}, release time: {}, unit: {}", key, releaseTime, unit);
        return lock.lock(key, releaseTime, unit);
    }

    /**
     * lock by release time and await time
     *
     * @param key
     * @param awaitTime
     * @param releaseTime
     * @param unit
     * @return
     */
    public static boolean tryLock(String key, int awaitTime, int releaseTime, TimeUnit unit) {
        log.info("LockUtil tryLock by key: {}, awaitTime: {}, release time: {}, unit: {}", key, awaitTime, releaseTime, unit);
        return lock.tryLock(key, awaitTime, releaseTime, unit);
    }


    /**
     * lock by release time 30s and await time 0s
     *
     * @param key
     * @return
     */
    public static boolean tryLock(String key) {
        log.info("LockUtil tryLock by key: {}, awaitTime: {}, release time: {}, unit: {}", key);
        return lock.tryLock(key, 0, 30, TimeUnit.SECONDS);
    }


    /**
     * unlock by key
     *
     * @param key
     */
    public static void unlock(String key) throws Exception {
        log.info("LockUtil unlock by key: {}", key);
        lock.unLock(key);
    }

    /**
     * unlock by key
     *
     * @param key
     */
    public static void unlockByNoCheck(String key) {
        log.info("LockUtil unlock by key: {}", key);
        try {
            lock.unLock(key);
        } catch (Exception e) {
            log.info("LockUtil unlock fail!");
        }
    }

    /**
     * unlock by lock
     *
     * @param lock
     */
    public static void unlock(RLock lock) throws Exception {
        log.info("LockUtil unlock by lock: {}", lock.getName());
        lock.unlock();
    }


}
