package com.wwh.test.pool.connection;

import java.util.LinkedList;

public class Connection {

	void close() {
		System.out.println("关闭连接");
	}
	
	public static void main(String[] args) {
		int i = 1;
		++i;
		System.out.println(i);
	}
	
	public static void main1(String[] args) {
		LinkedList<Integer> deque = new LinkedList<>();
		deque.add(1);
		deque.add(2);
		deque.add(3);
		deque.add(4);
		deque.add(5);
		
		System.out.println(deque.pollLast());
		System.out.println(deque.getFirst());
		System.out.println(deque.removeFirst());
		
	}
}
