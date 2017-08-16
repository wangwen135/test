package com.wwh.map;

import java.util.Map;
import java.util.TreeMap;

public class TreeMapTest {
    public static void main(String[] args) {
        Map<Integer, String> map = new TreeMap<>();
        map.put(2, "这是第二个");
        map.put(1, "这是第一个");
        map.put(3, "这是第三个");

        for (String string : map.values()) {
            System.out.println(string);
        }

    }
}
