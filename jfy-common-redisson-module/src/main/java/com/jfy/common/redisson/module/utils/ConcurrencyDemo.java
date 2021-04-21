package com.jfy.common.redisson.module.utils;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 并发测试
 * @Author: jiahuiyang
 * @Date: Created in 11:02 2021/4/21
 */
public class ConcurrencyDemo {

    private static final List<Integer> LIST = Lists.newArrayList();

    private static final int COUNT = 300;

    static {
        for (int i = 0; i < COUNT; i++) {
            LIST.add(i);
        }
    }

    public static void main(String[] args) {

        serial();


    }

    /**
     * 串行处理
     */
    private static void serial() {
        List<Integer> newList = Lists.newArrayList();
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (Integer i : LIST) {
            int temp = operate(i);
            newList.add(temp);
        }
        stopwatch.stop();
        System.out.println("consumer");
    }

    private static int operate(Integer i) {
        int temp = i * 2;
        //模拟过程处理耗时
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return temp;
    }

}
