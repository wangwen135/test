package com.wwh.charset;

import java.nio.charset.Charset;
import java.util.Map.Entry;
import java.util.SortedMap;

public class AllCharset {

    public static void main(String[] args) {
        System.out.println("default charset: ");
        System.out.println(Charset.defaultCharset());

        System.out.println("availableCharsets:");
        SortedMap<String, Charset> map = Charset.availableCharsets();
        for (Entry<String, Charset> entry : map.entrySet()) {
            System.out.println(entry.getKey());

        }
    }
}
