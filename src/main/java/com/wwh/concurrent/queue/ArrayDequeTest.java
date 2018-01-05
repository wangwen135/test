package com.wwh.concurrent.queue;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayDequeTest {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();

        // 测试多线程并发操作
        ArrayDeque<Integer> deque = new ArrayDeque<>(55);

        Thread th1 = new Thread(new t1(deque, lock));
        Thread th2 = new Thread(new t1(deque, lock));
        Thread th3 = new Thread(new t1(deque, lock));

        th1.setDaemon(true);
        th1.start();

        th2.setDaemon(true);
        th2.start();

        th3.setDaemon(true);
        th3.start();

        while (true) {

            System.out.println("Size: " + deque.size());

            lock.lock(); // block until condition holds
            try {
                for (Iterator iterator = deque.iterator(); iterator.hasNext();) {
                    Integer integer = (Integer) iterator.next();
                    System.out.print("  " + integer);
                }
            } finally {
                lock.unlock();
            }

            System.out.println("");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

class t1 implements Runnable {
    ArrayDeque<Integer> deque;
    ReentrantLock lock;

    public t1(ArrayDeque<Integer> deque, ReentrantLock lock) {
        this.deque = deque;
        this.lock = lock;
    }

    public void run() {
        while (true) {
            Random r = new Random();

            int ri = r.nextInt(100);

            lock.lock();
            try {
                if (!deque.contains(ri)) {

                    if (deque.size() > 50) {

                        deque.pollFirst();
                        deque.pollFirst();
                    }

                    deque.offer(ri);

                }
            } finally {
                lock.unlock();
            }

        }
    }
}
