package com.wwh.test.utils;

import java.util.HashMap;
import java.util.Map;

public class MapTest {

	public static void main(String[] args) {
		Long l1 = Long.valueOf(100l);
		Long l2 = new Long("100");

		// 内存位置不同
		System.out.println(l1 == l2);

		//包含相同的long值
		System.out.println(l1.equals(l2));
		
		// hash相同
		System.out.println(l1.hashCode());
		System.out.println(l2.hashCode());

		Map<Long, String> map = new HashMap<>();
		map.put(l1, "hello");
		System.out.println(map.get(l2));
	}
}
