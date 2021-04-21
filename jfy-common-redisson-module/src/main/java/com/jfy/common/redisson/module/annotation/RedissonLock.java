package com.jfy.common.redisson.module.annotation;

import java.lang.annotation.*;

/**
 * @Description:
 * @Author: jiahuiyang
 * @Date: Created in 19:35 2021/3/29
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedissonLock {

}
