package com.wwh.test.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 * synchronized 可以重入
 * </pre>
 */
public class SynchronizedTest implements Runnable {

    public static int type = 2;

    public static Object objLock = new Object();
    private String msg;

    public SynchronizedTest(String msg){
        this.msg = msg;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 1; i++) {
            executorService.submit(new SynchronizedTest("线程编号：" + i));
        }
    }

    @Override
    public void run() {
        if (type == 1) {
            doWork(msg, 0);
        } else if (type == 2) {
            doWork2(msg, 0);
        }
    }

    private synchronized void doWork(String msg, int count) {
        System.out.println("开始执行任务：" + msg + "  count:" + count);
        try {
            Thread.sleep(1000);
            if (count < 10) {
                System.out.println("重复执行");
                doWork(msg, ++count);
            } else {
                System.out.println("不再重复执行了");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("任务执行完成：" + msg);
    }

    private void doWork2(String msg, int count) {

        synchronized (objLock) {
            try {
                Thread.sleep(1000);
                if (count < 10) {
                    System.out.println("重复执行");
                    doWork(msg, ++count);
                } else {
                    System.out.println("不再重复执行了");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
