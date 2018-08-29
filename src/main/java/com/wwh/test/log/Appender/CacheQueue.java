package com.wwh.test.log.Appender;

import static com.wwh.test.log.Appender.MyCacheAppender.DEFAULT_LINE;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class CacheQueue {

    private final ReentrantLock lock = new ReentrantLock();

    private int maxLine;

    private List<String> list;

    public CacheQueue() {
        this(DEFAULT_LINE);
    }

    public CacheQueue(int maxLine) {
        this.maxLine = maxLine;
        list = new ArrayList<>(maxLine + 1);
    }

    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
    }

    /**
     * 
     * @param line
     */
    public void add(String line) {
        lock.lock();
        try {

            if (list.size() >= maxLine) {// 保证大小
                list.remove(0);
            }
            list.add(line);

        } finally {
            lock.unlock();
        }

    }

    /**
     * 可能会有并发问题<br>
     * 
     * @return
     */
    public List<String> getAll() {
        lock.lock();
        try {
            List<String> temp = new ArrayList<>();

            for (String line : list) {
                temp.add(line);
            }

            return temp;

        } finally {
            lock.unlock();
        }
    }

}
