package com.wwh.memory;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MemoryTest {

    public static void main(String[] args) {

        Set<String> set = new HashSet<>();

        for (int i = 0; i < 1000000; i++) {
            if (i % 1000 == 0) {
                System.out.println("已经添加：" + i);
                displayAvailableMemory();
            }

            set.add(UUID.randomUUID().toString());
        }

        System.out.println("输入任意字符退出");
        try {
            System.in.read();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void displayAvailableMemory() {
        DecimalFormat df = new DecimalFormat("0.00");

        // 显示JVM总内存
        long totalMem = Runtime.getRuntime().totalMemory();
        System.out.println("虚拟机中的内存总量：" + df.format(totalMem / 1048576F) + "MB");

        // 显示JVM尝试使用的最大内存
        long maxMem = Runtime.getRuntime().maxMemory();
        System.out.println("虚拟机试图使用的最大内存量: " + df.format(maxMem / 1048576F) + "MB");

        // 空闲内存
        long freeMem = Runtime.getRuntime().freeMemory();
        System.out.println("虚拟机中的空闲内存量: " + df.format(freeMem / 1048576F) + "MB");
    }
}
