package com.wwh.test.collection;

import java.util.ArrayList;
import java.util.LinkedList;

public class LinkedListTest {

    public static void main(String[] args) {
        LinkedList<Integer> ll = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            ll.addFirst(i);
        }

        System.out.println(ll);

        for (Integer integer : ll) {
            
        }
        

    }

}
