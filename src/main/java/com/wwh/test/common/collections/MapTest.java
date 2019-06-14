package com.wwh.test.common.collections;

import org.apache.commons.collections.IterableMap;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.HashedMap;

public class MapTest {

	public static void main(String[] args) {
		IterableMap map = new HashedMap();
		map.put("111", "aaaa");
		map.put("222", "bbbb");
		MapIterator it = map.mapIterator();

		while (it.hasNext()) {
			Object key = it.next();
			Object value = it.getValue();
			System.out.println("key:" + key + "  value:" + value);
			it.setValue("new value");
		}
	}
}
