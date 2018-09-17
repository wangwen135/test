package com.wwh.test.queue;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * <pre>
 * 有长度限制的，自删队列
 * 先进入队列的元素先被删除
 * 
 * 线程安全
 * </pre>
 *
 * @author wangwh
 * @date 2018年9月17日上午10:02:30
 * 
 * @param <E>
 */
public class LimitQueue<E> extends AbstractQueue<E> implements Queue<E>, Cloneable {

	/**
	 * 队列长度
	 */
	private int limit;

	private LinkedList<E> queue = new LinkedList<E>();

	private Object lock = new Object();

	/**
	 * 构造一个限定大小的队列
	 * @param limit
	 */
	public LimitQueue(int limit) {
		this.limit = limit;
	}

	@Override
	public boolean offer(E e) {
		synchronized (lock) {
			// 需要判断大小
			if (queue.size() >= limit) {
				// 如果超出长度,入队时,先出队
				queue.poll();
			}
			return queue.offer(e);
		}
	}

	@Override
	public E poll() {
		synchronized (lock) {
			return queue.poll();
		}
	}

	@Override
	public E peek() {
		synchronized (lock) {
			return queue.peek();
		}
	}

	/**
	 * 通过浅克隆实现同步<br>
	 * 返回调用时的副本对象
	 */
	@Override
	public Iterator<E> iterator() {
		synchronized (lock) {
			@SuppressWarnings("unchecked")
			LinkedList<E> list = (LinkedList<E>) queue.clone();
			return list.iterator();
		}
	}

	@Override
	public int size() {
		return queue.size();
	}

	/** 
	 * 获取限制大小 
	 * @return 
	 */
	public int getLimit() {
		return limit;
	}

	public static void main(String[] args) throws Exception {
		LimitQueue<String> limitQueue = new LimitQueue<>(5);
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					limitQueue.add("1 - " + i);
				}
				Iterator<String> iterator1 = limitQueue.iterator();
				System.out.println("t1 over");

				while (iterator1.hasNext()) {
					System.out.println(iterator1.next());
				}
				System.out.println("----------------------------------------");

			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					limitQueue.add("2 - " + i);
				}
				Iterator<String> iterator2 = limitQueue.iterator();

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println("t2 over");

				while (iterator2.hasNext()) {
					System.out.println(iterator2.next());
				}
				System.out.println("----------------------------------------");

			}
		});

		t1.start();
		t2.start();

		Thread.sleep(1000);

		System.out.println(limitQueue.size());
		limitQueue.forEach(new Consumer<String>() {

			@Override
			public void accept(String t) {
				System.out.println(t);
			}
		});
		System.out.println();

		Iterator<String> iterator = limitQueue.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
}
