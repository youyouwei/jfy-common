package com.jfy.common.redisson.module.utils;

import java.lang.instrument.Instrumentation;

/**
 * @Description: java agent 探针
 * @Author: jiahuiyang
 * @Date: Created in 18:40 2021/4/21
 */
public class JavaAgentOperate {

    /**
     * 在jvm启动时 premain方法在main方法之前运行 并且被同一个System ClassLoader装载
     *
     * @param agentOps
     * @param instrumentation
     */
    public static void premain(String agentOps, Instrumentation instrumentation) {
        System.out.println("========== pre main ");
        System.out.println(agentOps);
    }

    /**
     * 如果premain(String,Instrumentation)不存在 则会执行premain(String)
     * @param agentOps
     */
    public static void premain(String agentOps) {
        System.out.println("========== pre main ");
        System.out.println(agentOps);
    }

    public static void main(String[] args) {

    }




}
