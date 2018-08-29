package com.wwh.test.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoolException implements Runnable {

    private int idx;

    private int count;

    public PoolException(int idx) {
        this.idx = idx;
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {

            executorService.execute(new PoolException(1));

            executorService.execute(new PoolException(2));

            executorService.execute(new PoolException(3));

            executorService.execute(new PoolException(4));

        } catch (Exception e) {
            System.out.println(" == ==  出来了。。== == ==");
            e.printStackTrace();
        }
        System.out.println("这里立马执行。。。");

    }

    @Override
    public void run() {

        for (; count < 20; count++) {

            System.out.println("IDX: " + idx + "   working 。。。");

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 测试一下
            if (count % 5 == 0) {
                // throw new RuntimeException("测试异常状态");
                throw new Error("xxxx");
            }
        }

        System.out.println("over");
    }
}
