package com.wwh.concurrent.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TestList implements Runnable {

	// private List<Integer> list = new ArrayList<Integer>();

	private List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());

	private boolean runFlag = true;

	public List<Integer> getList() {
		return list;
	}

	public void setList(List<Integer> list) {
		this.list = list;
	}

	public boolean isRunFlag() {
		return runFlag;
	}

	public void setRunFlag(boolean runFlag) {
		this.runFlag = runFlag;
	}

	public static void main(String[] args) {
		TestList tl = new TestList();
		Thread t1 = new Thread(tl);
		t1.start();

		Putter p = new Putter(tl);
		Thread t2 = new Thread(p);
		t2.start();

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tl.setRunFlag(false);
		System.out.println("主线程停止了 ");
	}

	@Override
	public void run() {
		while (runFlag) {

			// iterator 不是线程同步的

			/*
			 * Iterator<Integer> itr = list.iterator(); while (itr.hasNext()) {
			 * Integer i = itr.next(); if (i % 2 == 0 || i > 100) {
			 * System.out.println(i); itr.remove(); } }
			 */

			synchronized (list) {
				Iterator<Integer> i = list.iterator();

				while (i.hasNext()) {
					Integer itg = i.next();
					if (itg % 2 == 0 || itg > 100) {
						System.out.println(itg);
						list.remove(i);
					}

				}
			}

//			for (int i = 0; i < list.size(); i++) {
//				Integer itg = list.get(i);
//				if (itg % 2 == 0 || itg > 100) {
//					System.out.println(itg);
//					list.remove(i);
//				}
//			}

			// try {
			// Thread.sleep(1);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
		}
		System.out.println("线程1停止了");
	}
}

class Putter implements Runnable {

	private TestList testList;

	public Putter(TestList tl) {
		this.testList = tl;
	}

	@Override
	public void run() {
		int index = 0;
		while (testList.isRunFlag()) {
			System.out.println("++");
			testList.getList().add(index++);

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("线程2停止了");
	}

}
