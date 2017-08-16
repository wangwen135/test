package com.wwh.map;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            map.put(i, i);
        }

        System.out.println(System.currentTimeMillis() - time1);
    }

}
