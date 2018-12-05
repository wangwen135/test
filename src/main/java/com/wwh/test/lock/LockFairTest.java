package com.wwh.test.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockFairTest implements Runnable {

    /**
     * 此锁应该使用公平的排序策略，先请求先获取锁
     */
    private static Lock lock = new ReentrantLock(true);

    private int taskNumber;

    public LockFairTest(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 20; i++) {
            executorService.submit(new LockFairTest(i + 1));
            // Thread.sleep(1);
            Thread.yield();
        }

    }

    @Override
    public void run() {
        System.out.println("任务" + taskNumber + " 开始执行...");
        lock.lock();
        try {
            System.out.println("任务" + taskNumber + " 获取到锁...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            lock.unlock();
        }
    }
}
