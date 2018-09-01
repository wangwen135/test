package com.wwh.test.memory;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MemoryTest2 {

	public static void main(String[] args) {
		System.out.println("方法1：内存会被回收");
		m2();
		System.out.println("方法2：内存不会被回收");
		m1();
	}

	/**
	 * 持有对象引用
	 */
	public static void m1() {
		List<byte[]> list = new ArrayList<>();
		byte[] b;
		for (int i = 0; i < 1000000; i++) {
			printMemory(i);
			b = new byte[1024];
			list.add(b);
		}
		pause();
	}

	/**
	 * 不停创建对象
	 */
	public static void m2() {
		@SuppressWarnings("unused")
		byte[] b;
		for (int i = 0; i < 1000000; i++) {
			printMemory(i);
			b = new byte[1024];
		}
		pause();
	}

	public static void printMemory(int i) {
		if (i % 20000 == 0) {
			System.out.println("已经添加：" + i);
			displayAvailableMemory();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void pause() {
		System.out.println("按回车继续...");
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
