package com.wwh.concurrent.set;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {
	public static void main(String[] args) {

		Map<Long, String> map = new ConcurrentHashMap<Long, String>();

		// 一个线程遍历
		Thread th1 = new Thread(new tm1(map));
		// 一个线程添加
		Thread th2 = new Thread(new tm2(map));

		th1.start();
		th2.start();
	}

}

class tm1 implements Runnable {
	Map<Long, String> map;

	public tm1(Map<Long, String> map) {
		this.map = map;
	}

	public void run() {
		while (true) {

			for (Map.Entry<Long, String> m : map.entrySet()) {
				System.out.println(m.getKey() + "  ===  " + m.getValue());
			}
			try {
				Thread.sleep(1l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("...");

		}
	}
}

class tm2 implements Runnable {
	Map<Long, String> map;

	public tm2(Map<Long, String> map) {
		this.map = map;
	}

	public void run() {
		while (true) {
			map.put(System.currentTimeMillis(), "test");

			try {
				Thread.sleep(1l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("add...");
		}
	}
}