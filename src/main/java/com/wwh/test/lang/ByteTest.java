package com.wwh.test.lang;

public class ByteTest {

    public static void main(String[] args) {
        System.out.println("Byte类型最大值：" + Byte.MAX_VALUE);// 127
        System.out.println("Byte类型最小值：" + Byte.MIN_VALUE);// -128

        // 转成无符号的int
        int unsignedByte = Byte.toUnsignedInt(Byte.MIN_VALUE);
        System.out.println(unsignedByte); // 128

        int b1 = 0b10001000;
        System.out.println(b1); // 136

        int i1 = 0xFF;
        System.out.println(i1);// 255

        int i2 = 0xFFFF; // 65535
        System.out.println(i2);
        
        byte b = 12;
        System.out.println(Byte.toString(b));

    }
}
