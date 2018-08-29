package com.wwh.test.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestList {

    public static void main1(String[] args) {
        List<Integer> list = getList(100);
        // ConcurrentModificationException
        for (Integer integer : list) {
            if (integer == 2)
                list.remove(integer);
        }
    }

    public static void main(String[] args) {

        List<Integer> list = getList(10);

        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Integer integer = (Integer) iterator.next();
            if (integer == 2) {
                iterator.remove();
            }
        }

        for (Integer integer : list) {
            System.out.println(integer);
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
