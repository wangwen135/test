package com.wwh.test.concurrent.set;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMapTest {

    public static void main(String[] args) {

        final Map<Integer, String> map = new ConcurrentHashMap<>();
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

        Set<Integer> set = map.keySet();
        for (Integer key : set) {
            if (key % 2 == 1) {
                System.out.println("delete this: " + key + " = " + key);
                map.put(key + 10, "奇数"); // ConcurrentModificationException
                map.remove(key); // ConcurrentModificationException
            }
        }

        /*Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Integer, String> entry = it.next();
            int key = entry.getKey();
            if (key % 2 == 1) {
                System.out.println("delete this: " + key + " = " + key);
                map.put(key + 10, "奇数"); // ConcurrentModificationException
                // map.remove(key); //ConcurrentModificationException
                it.remove(); // OK
            }
        }*/

        // 遍历当前的map；这种新的for循环无法修改map内容，因为不通过迭代器。

        System.out.println("-------nt最终的map的元素遍历：");

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            int k = entry.getKey();
            String v = entry.getValue();
            System.out.println(k + " = " + v);
        }

    }

    public static void main2(String[] args) {
        final Map<Integer, String> map = new ConcurrentHashMap<>();
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

        Thread t = new Thread(new Runnable() {
            Random rd = new Random();
            int time = 0;

            @Override
            public void run() {
                while (time < 1000) {
                    time++;
                    int index = rd.nextInt(100);
                    map.put(index, "index = " + index);

                    System.out.println("add " + index);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        t.start();

        int time = 0;
        while (time < 1000) {
            time++;
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                int key = entry.getKey();
                if (key % 2 == 1) {
                    System.out.println("delete this: " + key + " = " + key);
                    map.remove(key); // ConcurrentModificationException
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        //
        // while (it.hasNext()) {
        // Map.Entry<Integer, String> entry = it.next();
        // int key = entry.getKey();
        // if (key % 2 == 1) {
        // System.out.println("delete this: " + key + " = " + key);
        // map.put(key+10, "奇数"); // ConcurrentModificationException
        // // map.remove(key); //ConcurrentModificationException
        // it.remove(); // OK
        // }
        // }

        // 遍历当前的map；这种新的for循环无法修改map内容，因为不通过迭代器。

        System.out.println("-------nt最终的map的元素遍历：");

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            int k = entry.getKey();
            String v = entry.getValue();
            System.out.println(k + " = " + v);
        }
    }

}
