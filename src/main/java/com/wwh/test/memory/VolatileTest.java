package com.wwh.test.memory;

/**
 * <pre>
 * volatile关键字的作用：保证了变量的可见性（visibility）。
 * 被volatile关键字修饰的变量，如果值发生了变更，其他线程立马可见，避免出现脏读的现象。
 * </pre>
 */
public class VolatileTest {

    // boolean isShutDown = false;

    // 加上volatile就能结束doWork 线程
    volatile boolean isShutDown = false;

    void shutdown() {
        isShutDown = true;
        System.out.println("shutdown!");
    }

    void doWork() {
        System.out.println("start work...");
        while (!isShutDown) {

        }
        System.out.println("doWork end");
    }

    public static void main(String[] args) throws Exception {
        VolatileTest vt = new VolatileTest();
        new Thread(vt::doWork).start();
        // 这里需要休眠一下，否则无法保证是哪一个线程先执行
        Thread.sleep(100);
        new Thread(vt::shutdown).start();
    }
}
