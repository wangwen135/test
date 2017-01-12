package com.wwh.collection;

import java.util.ArrayList;
import java.util.List;

public class TestList {

	public static void main(String[] args) {
		List<Integer> list = getList(100);
		// ConcurrentModificationException
		for (Integer integer : list) {
			if (integer == 2)
				list.remove(integer);
		}
	}

	public static List<Integer> getList(int size) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			list.add(i);
		}
		return list;
	}
}
