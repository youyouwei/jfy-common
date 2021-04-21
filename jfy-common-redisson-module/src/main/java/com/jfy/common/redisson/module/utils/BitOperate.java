package com.jfy.common.redisson.module.utils;

/**
 * @Description: 位运算
 * @Author: jiahuiyang
 * @Date: Created in 14:21 2021/4/21
 */
public class BitOperate {

    public static void main(String[] args) {
        int first = 1;
        int second = 2;

        bitOr(first, second);


    }

    /**
     *
     * @param first
     * @param second
     */
    private static void bitOr(int first, int second) {
        System.out.println(first | second);
    }

}
