package com.wwh.test.cpu;

/**
 * 让CPU使用率100%
 */
public class CpuUsage {

    void loop() {
        // for (long i = 0; i < Long.MAX_VALUE; i++) {
        // }
        while (true) {

        }
    }

    public static void main(String[] args) {
        CpuUsage u = new CpuUsage();

        // 几个核心的CPU就用几

        // 不能单独让一个cpu核心100%，因为会进行调度
        for (int i = 0; i < 2; i++) {
            new Thread(u::loop).start();
        }
    }
}
