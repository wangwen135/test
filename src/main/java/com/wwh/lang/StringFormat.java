package com.wwh.lang;

import java.util.Date;

public class StringFormat {
    /**
     * <pre>
     * 常规类型、字符类型和数值类型的格式说明符的语法如下：
     * %[argument_index$][flags][width][.precision]conversion
     * 
     * 可选的 argument_index 是一个十进制整数，用于表明参数在参数列表中的位置。第一个参数由 "1$" 引用，第二个参数由 "2$" 引用，依此类推。 
    
    可选 flags 是修改输出格式的字符集。有效标志集取决于转换类型。 
    
    可选 width 是一个非负十进制整数，表明要向输出中写入的最少字符数。 
    
    可选 precision 是一个非负十进制整数，通常用来限制字符数。特定行为取决于转换类型。 
    
    所需 conversion 是一个表明应该如何格式化参数的字符。给定参数的有效转换集取决于参数的数据类型。
     * 
     * </pre>
     * 
     * @param args
     */
    public static void main(String[] args) {
        formatBoolean();

        formatHex();

        formatString();

        formatUnicode();

        formatDate();

    }

    private static void formatDate() {
        System.out.println("格式化日期");
        System.out.println("这个有点多，要看api");
        System.out.println("例子：");
        System.out.println(String.format("%1$tY年%1$tm月%1$td日 %1$tH时%1$tM分%1$tS秒", new Date()));
        System.out.println(String.format("%1$tY年%1$tm月%1$td日 %1$tH时%1$tM分%1$tS秒", new Date().getTime()));
        System.out.println(String.format("%1$tR      %1$tT      %1$tr", new Date()));
        System.out.println(String.format("%1$tD      %1$tF      %1$tc", new Date()));
        // 格式化long
        System.out.println(String.format("%1$tY年%1$tm月%1$td日 %1$tH时%1$tM分%1$tS秒", 1232332223424l));

        System.out.println("\n--------------------------------------------------------------------\n");
    }

    private static void formatUnicode() {
        System.out.println("格式化Unicode");
        System.out.println("结果是一个 Unicode 字符 ");
        System.out.println("例子：");
        System.out.println("%1$c   %1$C  %2$10c  %3$10C  %4$10c");
        System.out.println(" 'a', 97, 66, 125");
        System.out.println("\n结果：");
        System.out.println(String.format("%1$c   %1$C  %2$10c  %3$10C  %4$10c", 'a', 97, 66, 125));

        System.out.println("\n--------------------------------------------------------------------\n");
    }

    private static void formatString() {
        System.out.println("格式化字符串");
        System.out.println("如果参数 arg 为 null，则结果为 \"null\"。如果 arg 实现 Formattable，则调用 arg.formatTo。否则，结果为调用 arg.toString() 得到的结果。");
        System.out.println("例子：");
        System.out.println("%1$s   %1$S  %2$10s  %3$10S  %4$10.3s");
        System.out.println("\"hello\", \"aaa\", 121, null");
        System.out.println("\n结果：");
        System.out.println(String.format("%1$s   %1$S  %2$10s  %3$10S  %4$10.3s", "hello", "aaa", 121, null));
        System.out.println("\n--------------------------------------------------------------------\n");
    }

    private static void formatHex() {
        System.out.println("格式化Hex值");
        System.out.println("如果参数 arg 为 null，则结果为 \"null\"。否则，结果为调用 Integer.toHexString(arg.hashCode()) 得到的结果。");
        System.out.println("例子：");
        System.out.println("%1$h   %1$H  %2$10h  %3$10h  %4$10H");
        System.out.println("10, \"aaa\", 121, null");
        System.out.println("\n结果：");
        System.out.println(String.format("%1$h   %1$H  %2$10h  %3$10h  %4$10H", 10, "aaa", 121, null));
        System.out.println("\n--------------------------------------------------------------------\n");
    }

    private static void formatBoolean() {
        System.out.println("格式化boolean值");
        System.out.println("如果参数 arg 为 null，则结果为 \"false\"。如果 arg 是一个 boolean 值或 Boolean，则结果为 String.valueOf() 返回的字符串。否则结果为 \"true\"。 ");
        System.out.println("例子：");
        System.out.println("%1$b  %1$B  %2$10B  %3$10b  %4$10.1B");
        System.out.println("true, \"aaa\", 1, null");
        System.out.println("\n结果：");
        System.out.println(String.format("%1$b  %1$B  %2$10B  %3$10b  %4$10.1B", true, "aaa", 1, null));
        System.out.println("\n--------------------------------------------------------------------\n");
    }
}
