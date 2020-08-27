package com.wwh.test.lang;

public class BaseConversion {

    public static void main(String[] args) {
        // 表示
        System.out.println(0b101);// 二进制:5 （0b开头的）
        System.out.println(0e1011);// 0.0
        System.out.println(0120);// 八进制:80 (0开头的，后面跟随0-7)
        System.out.println(15);// 十进制:15
        System.out.println(0x11C);// 十六进制:284 （0x开头的，后面跟随0-9或a-f(A-F)来表示）

        // 打印
        System.out.printf("%x\n", 27);// 1b 按16进制输出
        System.out.printf("%o\n", 13);// 15 按8进制输出
        System.out.printf("%010x\n", 27);// 000000001b 按10位十六进制输出，向右靠齐，左边用0补齐
        System.out.printf("%010o\n", 13);// 0000000015 按10位八进制输出，向右靠齐，左边用0补齐

        // 输出二进制
        System.out.println(Integer.toBinaryString(11));// 1011 二进制

        // 字符 转换
        // 一个char类型占2个字节
        // u 表示unicode编码，后面两个字节，用16进制表示
        char c1 = '\u0042'; // 表示大写的B
        // 这里是直接用一个16进制的数字转成字符
        char c2 = 0x42; // 16进制 表示大写的B
        char c3 = 66; // 10进制 表示大写的B
        char c4 = 0102; // 8进制 表示大写的B

        System.out.println("c1=" + c1 + "  c2=" + c2 + "  c3=" + c3 + "  c4=" + c4);
        // 这些都表示字符B： c1=B c2=B c3=B c4=B

        
    }
}
