package com.wwh.concurrent.set;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MapRemove {
    public static void main1(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");
        map.put(6, "six");
        map.put(7, "seven");
        map.put(8, "eight");
        map.put(9, "nine");
        map.put(10, "ten");

        Set<Integer> keys = map.keySet();
        for (Integer integer : keys) {
            if (integer == 5) {
                map.remove(integer);// ConcurrentModificationException
            }
        }
    }

    public static void main(String[] args) {
        Map<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");
        map.put(6, "six");
        map.put(7, "seven");
        map.put(8, "eight");
        map.put(9, "nine");
        map.put(10, "ten");

        Collection<String> values = map.values();
        for (String string : values) {
            System.out.println(string);
            if ("five".equals(string)) {
                map.remove(5);// ConcurrentModificationException
            }
        }
    }

    public static void main2(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");
        map.put(6, "six");
        map.put(7, "seven");
        map.put(8, "eight");
        map.put(9, "nine");
        map.put(10, "ten");

        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Integer, String> entry = it.next();
            int key = entry.getKey();
            if (key % 2 == 1) {
                System.out.println("delete this: " + key + " = " + key);
                // map.put(key+10, "奇数"); // ConcurrentModificationException
                // map.remove(key); //ConcurrentModificationException
                it.remove(); // OK
            }
        }

        // 遍历当前的map；这种新的for循环无法修改map内容，因为不通过迭代器。

        System.out.println("-------nt最终的map的元素遍历：");

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            int k = entry.getKey();
            String v = entry.getValue();
            System.out.println(k + " = " + v);
        }
    }
}
