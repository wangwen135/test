package com.wwh.test.threadpool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5,
            10,
            Integer.MAX_VALUE,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10));

        CountDownLatch cdLatch = new CountDownLatch(10);

        System.out.println("问题1：" + threadPool.getCorePoolSize());

        for (int i = 0; i < 10; i++) {
            threadPool.submit(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                cdLatch.countDown();
            });
        }
        System.out.println("问题2：" + cdLatch.getCount());

        System.out.println("问题3：" + threadPool.getCorePoolSize());

        System.out.println("问题4：" + threadPool.getQueue().size());

        cdLatch.await();
        System.out.println("问题5：" + cdLatch.getCount());

        threadPool.shutdown();
    }

}
