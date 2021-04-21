package com.jfy.common.redisson.module.config;


import com.jfy.common.redisson.module.constants.RedissonConstant;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: jiahuiyang
 * @Date: Created in 16:05 2021/3/22
 */
@Configuration
@ConditionalOnClass({Redisson.class})
@EnableConfigurationProperties({RedissonProperties.class})
@Slf4j
public class RedissonConfig {

    @Resource
    private RedissonProperties redissonProperties;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public RedissonClient redissonClient() {
        Config config = new Config();
        try {
            config.useSingleServer().setAddress(RedissonConstant.REDIS_URL_PREFIX + redissonProperties.getAddress())
                    .setPassword(redissonProperties.getPassword())
                    .setDatabase(redissonProperties.getDatabase())
                    .setTimeout(redissonProperties.getTimeout())
                    .setConnectTimeout(redissonProperties.getConnectTimeout());
        } catch (Exception e) {
            log.warn("RedissonConfig config error: {}", e);
        }
        return Redisson.create(config);
    }

}
