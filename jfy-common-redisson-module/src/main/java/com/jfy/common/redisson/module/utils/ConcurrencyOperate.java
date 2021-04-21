package com.jfy.common.redisson.module.utils;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Description: 并发测试
 * @Author: jiahuiyang
 * @Date: Created in 11:02 2021/4/21
 */
public class ConcurrencyOperate {

    private static final List<Integer> LIST = Lists.newArrayList();

    private static final int COUNT = 300;

    static {
        for (int i = 0; i < COUNT; i++) {
            LIST.add(i);
        }
    }

    private static final int CORE_NUM = Runtime.getRuntime().availableProcessors() * 2;

    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(CORE_NUM, CORE_NUM, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque(500));


    public static void main(String[] args) {
//        serial();

        concurrency();
    }

    /**
     * 并行执行 平均耗时 550ms 将近优化了10倍左右
     */
    private static void concurrency() {
        List<Integer> newList = Lists.newArrayList();
        List<Future<Integer>> futures = Lists.newArrayList();
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (Integer i : LIST) {
            futures.add(EXECUTOR_SERVICE.submit(() -> operate(i)));
        }
        EXECUTOR_SERVICE.shutdown();
        for (Future<Integer> future : futures) {
            int result = 0;
            try {
                // 如果超时会报异常 不是返回null
                result = future.get(300, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            newList.add(result);
        }

        stopwatch.stop();
        System.out.println("耗时：" + stopwatch.elapsed(TimeUnit.MILLISECONDS));
//        newList.forEach(System.out::println);
    }

    /**
     * 串行处理 平均耗时 6500ms
     */
    private static void serial() {
        List<Integer> newList = Lists.newArrayList();
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (Integer i : LIST) {
            int temp = operate(i);
            newList.add(temp);
        }
        stopwatch.stop();
        System.out.println("耗时：" + stopwatch.elapsed(TimeUnit.MILLISECONDS));
//        newList.forEach(System.out::println);
    }

    private static int operate(Integer i) {
        int temp = i * 2;
        //模拟过程处理耗时
        try {
            if (i == 299) {
                TimeUnit.MILLISECONDS.sleep(500);
            } else {
                TimeUnit.MILLISECONDS.sleep(20);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return temp;
    }

}
