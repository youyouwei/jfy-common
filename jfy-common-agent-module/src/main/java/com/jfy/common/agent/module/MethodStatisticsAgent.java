package com.jfy.common.agent.module;

import java.lang.instrument.Instrumentation;

/**
 * @Description: 方法 时长 统计 探针
 * @Author: jiahuiyang
 * @Date: Created in 19:09 2021/4/21
 */
public class MethodStatisticsAgent {
    public static void premain(String agentOps, Instrumentation instrumentation) {
        System.out.println("======== pre main agent ========");
        System.out.println(agentOps);
    }


    public static void premain(String agentOps) {
        System.out.println("========== pre main other agent ===========");
        System.out.println(agentOps);
    }
}
