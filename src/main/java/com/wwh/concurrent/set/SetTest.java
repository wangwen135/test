package com.wwh.concurrent.set;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SetTest {
    
    public static void main(String[] args) {
        Set<Integer> s1 = new HashSet<>();
        s1.add(200);
        s1.add(201);
        System.out.println(s1.contains(200));
    }

	public static void main2(String[] args) {

		Set<Long> set = Collections.synchronizedSet(new HashSet<Long>());

		// 一个线程遍历
		Thread th1 = new Thread(new t1(set));
		// 一个线程添加
		Thread th2 = new Thread(new t2(set));

		th1.start();
		th2.start();
	}

}

class t1 implements Runnable {
	Set<Long> set;

	public t1(Set<Long> set) {
		this.set = set;
	}

	public void run() {
		while (true) {

			// synchronized (set) {

			// 避免同步时的低效率，重新复制一份
			Set<Long> _set;
			synchronized (set) {
				_set = new HashSet<>(set);
			}

			Iterator<Long> itr = _set.iterator();
			while (itr.hasNext()) {
				System.out.println(itr.next());
			}

			// }

			System.out.println("...");

		}
	}
}

class t2 implements Runnable {
	Set<Long> set;

	public t2(Set<Long> set) {
		this.set = set;
	}

	public void run() {
		while (true) {
			set.add(System.currentTimeMillis());

			System.out.println("add...");
		}
	}
}
