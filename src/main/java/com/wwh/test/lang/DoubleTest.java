package com.wwh.test.lang;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import sun.misc.Unsafe;
import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * <pre>
 * IEEE 754浮点数规范
 * 
 * +- d.ddd...d * βe   (0 <= di < β)
 * 其中d.dd...d 为有效数字,β为基数，e 为指数
 * 
 * 有效数字中数字的个数称为精度，我们可以用 p 来表示，即可称为 p 位有效数字精度。
 * 每个数字 d 介于 0 和基数 β 之间，包括 0。更精确地说，±d0.d1d2…d(p-1)×βe 表示以下数：
 * 
 * 其中，对十进制的浮点数，即基数 β 等于 10 的浮点数而言，上面的表达式非常容易理解。
 * 如 12.34，我们可以根据上面的表达式表达为：1×10e1 + 2×10e0 + 3×10e-1 + 4×10e-2，其规范浮点数表达为1.234×10e1。
 * 
 * 但对二进制来说，上面的表达式同样可以简单地表达。
 * 唯一不同之处在于：二进制的 β 等于 2，而每个数字 d 只能在 0 和 1 之间取值。
 * 如二进制数 1001.101，我们可以根据上面的表达式表达为：1×2e3 + 0×2e2+ 0×2e1 + 1×2e0 + 1×2e-1 + 0×2e-2 + 1×2e-3，其规范浮点数表达为 1.001101×2e3。
 * 
 * 把二进制转换为十进制，二进制数 1001.101 
 *  = 1×2e3 + 0×2e2+ 0×2e1 + 1×2e0 + 1×2e-1 + 0×2e-2 + 1×2e-3
 *  = 8 + 0 + 0 + 1 + 1/2 + 0 + 1/8
 *  = 9又1/8 (9又8分之1)
 *  = 9.625
 * 
 * 由上面的等式，我们可以得出：向左移动二进制小数点一位相当于这个数除以 2，而向右移动二进制小数点一位相当于这个数乘以 2。
 * 如 101.11=5又3/4  (5.75)，而 10.111=2又7/8 （2.875）。
 * 除此之外，我们还可以得到这样一个基本规律：一个十进制小数要能用浮点数精确地表示，最后一位必须是 5（当然这是必要条件，并非充分条件）。
 * 规律推演如下面的示例所示：
 * </pre>
 */
public class DoubleTest {

    public static Unsafe getUnsafe() throws Exception {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        return unsafe;
    }

    public static void mainx(String[] args) throws Exception {
        // setDouble();
        bit2Double("0011111111110001100110011001100110011001100110011001100110011010");
    }

    public static void main(String[] args) {
        double d = 1801439850948198345000d;
        System.out.println(d);
        System.out.println(Long.toBinaryString(Double.doubleToLongBits(d)));

    }

    public static void mainy(String[] args) {
        // 4607632778762754458
        // String s =
        // "0011111111110001100110011001100110011001100110011001100110011010";

        String s = "0100000100001111111111111111111111111111111111111111111111111111";
        System.out.println("二进制字符串 = " + s);
        long l = Long.parseLong(s, 2);
        System.out.println("转成Long = " + l);
        double d = Double.longBitsToDouble(l);
        System.out.println("再将Long转成Double = " + d);
    }

    /**
     * bit字符串转Double
     * 
     * @param bitStr 64位的01字符串
     * @throws Exception
     */
    public static void bit2Double(String bitStr) throws Exception {
        String[] array = splitString(bitStr, 8);
        System.out.println(Arrays.toString(array));

        Unsafe unsafe = getUnsafe();
        long address = unsafe.allocateMemory(8L);

        for (int i = 0; i < array.length; i++) {
            String bits = array[i];
            byte bt = (byte) Integer.parseInt(bits, 2);
            // 因为实际上是小端模式存储的，所以这里从后面开始写入
            unsafe.putByte(address + (7 - i), (byte) bt);
        }

        long lVal = unsafe.getLong(address);
        System.out.println("对应的long值是：" + lVal);
        System.out.println(Long.toBinaryString(lVal));

        double dVal = unsafe.getDouble(address);
        System.out.println("转成Double 类型是：" + dVal);
        System.out.println(Long.toBinaryString(Double.doubleToLongBits(dVal)));

    }

    public static String[] splitString(String source, int length) {
        String[] array = new String[length];
        for (int i = 0; i < length; i++) {
            array[i] = source.substring(i * length, (i + 1) * length);
        }
        return array;
    }

    public static void setDouble() throws Exception {
        Unsafe unsafe = getUnsafe();

        System.out.println("分配一个8byte的内存 64bit");
        long address = unsafe.allocateMemory(8L);
        System.out.println("address1 = " + address);

        System.out.println("64个bit全部填充为1");
        unsafe.setMemory(address, 8, (byte) 0xff);
        long lVal = unsafe.getLong(address);
        System.out.println(Long.toBinaryString(lVal));
        System.out.println("后面8个bit填充为1011 1011");
        unsafe.putByte(address, (byte) 0xaa);
        lVal = unsafe.getLong(address);
        System.out.println(Long.toBinaryString(lVal));

        System.out.println("移动8位 填充为1011 1011");
        unsafe.putByte(address + 1, (byte) 0xbb);
        lVal = unsafe.getLong(address);
        System.out.println(Long.toBinaryString(lVal));

        System.out.println("再移动8位 填充为0000 0000");
        unsafe.putByte(address + 2, (byte) 0x00);
        lVal = unsafe.getLong(address);
        System.out.println(Long.toBinaryString(lVal));

        System.out.println("再移动8位 填充为0000 0000");
        unsafe.putByte(address + 3, (byte) 0x00);
        lVal = unsafe.getLong(address);
        System.out.println(Long.toBinaryString(lVal));

        System.out.println("再移动8位 填充为1101 1101");
        unsafe.putByte(address + 4, (byte) 0xdd);
        lVal = unsafe.getLong(address);
        System.out.println(Long.toBinaryString(lVal));

        System.out.println("再移动8位 填充为1110 1110");
        unsafe.putByte(address + 5, (byte) 0xee);
        lVal = unsafe.getLong(address);
        System.out.println(Long.toBinaryString(lVal));

        System.out.println("再移动8位 填充为1111 0001");
        unsafe.putByte(address + 6, (byte) 0b11110001);
        lVal = unsafe.getLong(address);
        System.out.println(Long.toBinaryString(lVal));

        System.out.println("再移动8位 填充为1011 1111");
        unsafe.putByte(address + 7, (byte) 0b10111111);
        lVal = unsafe.getLong(address);
        System.out.println(Long.toBinaryString(lVal));

        System.out.println("================================================================");
        double dVal = unsafe.getDouble(address);
        System.out.println("转成Double 类型是：" + dVal);
        System.out.println(Long.toBinaryString(Double.doubleToLongBits(dVal)));
        System.out.println();

    }

    public static void main1(String[] args) throws Exception {

        try {
            Unsafe UNSAFE = getUnsafe();

            long a = UNSAFE.allocateMemory(8);

            UNSAFE.putLong(a, 0x0102030405060708L);
            // 存放此long类型数据，实际存放占8个字节，01,02,03,04,05,06,07,08
            byte b = UNSAFE.getByte(a);
            // 通过getByte方法获取刚才存放的long，取第一个字节
            // 如果是大端，long类型顺序存放—》01,02,03,04,05,06,07,08 ，取第一位便是0x01
            // 如果是小端，long类型顺序存放—》08,07,06,05,04,03,02,01 ，取第一位便是0x08
            ByteOrder byteOrder;
            switch (b) {
                case 0x01:
                    byteOrder = ByteOrder.BIG_ENDIAN;
                    break;
                case 0x08:
                    byteOrder = ByteOrder.LITTLE_ENDIAN;
                    break;
                default:
                    byteOrder = null;
            }

            System.out.println(byteOrder);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main4(String[] args) {
        double dx = 1.099999999999999999999999999999d;
        System.out.println(dx);// 1.1

        double dy = 1.1000000000000000000000000000001d;
        System.out.println(dy);// 1.1

        double d = 1.1f;
        System.out.println(d);

        System.out.println(Double.toHexString(1.1d));
        // 0x1.199999999999ap0

        System.out.println(Long.toBinaryString(Double.doubleToLongBits(-0.1)));
        // 1011111110111001100110011001100110011001100110011001100110011010

        System.out.println(Long.toBinaryString(Double.doubleToLongBits(-3.5)));
        // 1100000000001100000000000000000000000000000000000000000000000000

        System.out.println(Long.toBinaryString(Double.doubleToLongBits(1.1)));
        // 11111111110001100110011001100110011001100110011001100110011010

        System.out.println(Long.toBinaryString(Double.doubleToLongBits(-1.1)));
        // 1011111111110001100110011001100110011001100110011001100110011010

        System.out.println(Long.toBinaryString(Double.doubleToLongBits(-2.1)));
        // 1100000000000000110011001100110011001100110011001100110011001101

        System.out.println(Integer.toBinaryString(Float.floatToIntBits(1.1f)));
        // 00111111100011001100110011001101

        System.out.println(Integer.toBinaryString(Float.floatToIntBits(-1.1f)));
        // 10111111100011001100110011001101

        System.out.println(Integer.toBinaryString(Float.floatToIntBits(-2.1f)));
        // 11000000000001100110011001100110

        // 第 63 位（掩码 0x8000000000000000L 选定的位）表示浮点数的符号。
        // 第 62-52 位（掩码 0x7ff0000000000000L 选定的位）表示指数。
        // 第 51-0 位（掩码 0x000fffffffffffffL 选定的位）表示浮点数的有效数字（有时也称为尾数）。

    }

    public static void main6(String[] args) {
        long doubleToLongBits = Double.doubleToLongBits(-0.2);
        System.out.println(doubleToLongBits);

        // 以二进制无符号整数形式返回 long
        System.out.println(Long.toBinaryString(doubleToLongBits));

        System.out.println(Long.toString(doubleToLongBits, 2));

        long l = Long.parseLong("-100000000110110011001100110011001100110011001100110011001100110", 2);
        System.out.println(l);
        // double dd = Double.longBitsToDouble();
    }

    public static void main3(String[] args) {
        System.out.println(Double.toHexString(1d));
        System.out.println(Double.toHexString(-1d));
        System.out.println(Double.toHexString(2d));
        System.out.println(Double.toHexString(-2d));
        System.out.println(Double.toHexString(3d));
        System.out.println(Double.toHexString(-3d));

        System.out.println("-----------------------------");

        System.out.println(Double.doubleToLongBits(1));

        System.out.println("-----------------------------");

        System.out.println(Double.toHexString(0.03d));
    }

    public static void main5(String[] args) {
        System.out.println(-2.1);
        System.out.println(new BigDecimal(-2.1));

        System.out.println(Long.toBinaryString(Double.doubleToLongBits(-2.1)));

        System.out.println("减法测试：");
        double a = 0.03;
        double b = 0.01;
        double c = a - b;
        System.out.println(a + " - " + b + " = " + c);
        // 0.019999999999999997
        System.out.println("######################################");
        System.out.println(new BigDecimal(a));
        System.out.println(new BigDecimal(b));
        System.out.println(new BigDecimal(c));
        System.out.println("######################################");

        System.out.println("");

        System.out.println("加法测试：");
        double x = 10.2;
        double y = 10.03;
        double z = x + y;
        // 20.229999999999997
        System.out.println(x + " + " + y + " = " + z);
        System.out.println("######################################");
        System.out.println(new BigDecimal(x));
        System.out.println(new BigDecimal(y));
        System.out.println(new BigDecimal(z));
        System.out.println("######################################");

        System.out.println("位数不够的情况：");
        float f = 2.33445566f;
        System.out.println(f);
        // 2.3344557

    }

    public static void main8(String[] args) {
        // 有效位数不够
        float f = 1.23456789f;
        System.out.println(f);
        // 1.2345679

        System.out.println("=====================");
        float f1 = 10000f;
        System.out.println(f1);
        // 10000.0
        float f2 = 1.123456f;
        System.out.println(f2);
        // 1.123456

        // 相加之后有效位数不够
        float f3 = f1 + f2;
        System.out.println(f3);
        // 10001.123

    }

    public static void main7(String[] args) {
        double d1 = 0.1;
        double d2 = 0.2;
        double d3 = d1 + d2;
        System.out.println(d3);
        System.out.println("######################################");
        System.out.println(new BigDecimal(d1));
        System.out.println(new BigDecimal(d2));
        System.out.println(new BigDecimal(d3));

        System.out.println("######################################");
        System.out.println(Long.toBinaryString(Double.doubleToLongBits(d1)));
        System.out.println(Long.toBinaryString(Double.doubleToLongBits(d2)));
        System.out.println(Long.toBinaryString(Double.doubleToLongBits(d3)));
    }

    public static void main11(String[] args) {

        // 1.09999999999999994
        // 1.0999999999999999
        double dx = 10.09999999999998765d;
        System.out.println(dx);

        double dy = 1.100000000000001234d;
        System.out.println(dy);

        System.out.println(dx + dy);

        System.out.println("-----------------------------");

        float f = 111.23f;
        System.out.println(f);
        double d = f;
        System.out.println(d);

        System.out.println("-----------------------------");

        Float f2 = 111.23f;
        Double d2 = Double.valueOf(f2);
        System.out.println(d2);

        Double d3 = Double.valueOf(String.valueOf(f2));
        System.out.println(d3);

        System.out.println(Double.valueOf(f2.toString()));
    }
}
