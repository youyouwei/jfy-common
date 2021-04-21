package com.jfy.common.redisson.module.annotation;


import com.jfy.common.redisson.module.config.RedissonConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author jiahuiyang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RedissonConfig.class)
public @interface EnableRedisson {
}
