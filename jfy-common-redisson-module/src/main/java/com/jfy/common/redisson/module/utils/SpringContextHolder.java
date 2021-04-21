package com.jfy.common.redisson.module.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @Description:
 * @Author: jiahuiyang
 * @Date: Created in 11:47 2021/3/18
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext context;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        assertContext();
        return context;
    }

    public static <T> T getBean(String beanName) {
        assertContext();
        return (T) context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> tClass ) {
        assertContext();
        return context.getBean(tClass);
    }

    private static void assertContext() {
        Assert.notNull(context, "application context is null!");
    }
}
