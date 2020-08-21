package com.wwh.test.outofmemory;

public class StackOverflowErrorTest {

    public static void main(String[] args) {
        recursion(0);
    }

    public static void recursion(int i) {
        System.out.println(i);
        i++;
        recursion(i);
    }
}
