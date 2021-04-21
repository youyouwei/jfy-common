package com.jfy.common.redisson.module.config;

import com.jfy.common.redisson.module.constants.RedissonConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description:
 * @Author: jiahuiyang
 * @Date: Created in 16:14 2021/3/22
 */
@ConfigurationProperties(prefix = RedissonConstant.REDISSON_CONFIG_PREFIX)
@Getter
@Setter
public class RedissonProperties {

    private String address;

    private String password;

    private Integer database;

    private Integer timeout;

    private Integer connectTimeout;

    private Integer connectionPoolSize;


}
