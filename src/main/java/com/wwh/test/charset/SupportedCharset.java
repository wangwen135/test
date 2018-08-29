package com.wwh.test.charset;

import java.nio.charset.Charset;

public class SupportedCharset {

    public static void main(String[] args) {
        String charset = "UTF-8";
        System.out.println(Charset.isSupported(charset));
    }
}
