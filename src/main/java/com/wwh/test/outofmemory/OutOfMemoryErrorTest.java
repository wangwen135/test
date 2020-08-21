package com.wwh.test.outofmemory;

import java.util.ArrayList;
import java.util.List;

public class OutOfMemoryErrorTest {

    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        while (true) {
            list.add(new Object());
        }
    }

}
