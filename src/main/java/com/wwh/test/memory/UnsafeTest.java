package com.wwh.test.memory;

import java.lang.reflect.Field;
import java.nio.ByteOrder;
import sun.misc.Unsafe;

public class UnsafeTest {

    public static void printSystemInfo() {
        System.out.println("##########################################");
        System.out.println("系统名称：" + System.getProperty("os.name"));
        System.out.println("系统架构：" + System.getProperty("os.arch"));
        System.out.println("系统版本：" + System.getProperty("os.version"));
        System.out.println("##########################################");
        System.out.println("");
    }

    public static Unsafe getUnsafe() throws Exception {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        return unsafe;
    }

    public static void main(String[] args) throws Exception {
        printSystemInfo();
        try {
            Unsafe UNSAFE = getUnsafe();

            int intVal = 0x1A2B3C4D;
            System.out.println("原始值：十六进制：" + Integer.toHexString(intVal) + "  对应十进制：" + intVal);

            // 分配4个字节的内存
            long address = UNSAFE.allocateMemory(4);
            // 存放int类型的数据，占4个字节
            UNSAFE.putInt(address, intVal);
            byte b = UNSAFE.getByte(address);
            // 通过getByte方法获取刚才存放的int，取第一个字节
            // 如果是大端，存放顺序 —> 1A,2B,3C,4D，取第一位便是0x1A
            // 如果是小端，存放顺序 —> 4D,3C,2B,1A ，取第一位便是0x4D
            System.out.println("取到的第一个字节：" + Integer.toHexString(b));
            ByteOrder byteOrder;
            switch (b) {
                case 0x1A:
                    System.out.println("当前使用：大端序");
                    byteOrder = ByteOrder.BIG_ENDIAN;
                    break;
                case 0x4D:
                    System.out.println("当前使用：小端序");
                    byteOrder = ByteOrder.LITTLE_ENDIAN;
                    break;
                default:
                    byteOrder = null;
            }
            System.out.println(byteOrder);
            // 这里在X86架构的windows机器上跑，输出结果为：
            // LITTLE_ENDIAN

            // 然后我们重新从内存中读取int
            int val2 = UNSAFE.getInt(address);
            System.out.println("重新从内存中读取的值：" + val2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void putMemory() throws Exception {
        Unsafe UNSAFE = getUnsafe();

        int intVal = 0x1A2B3C4D;
        // 分配4个字节的内存
        long address = UNSAFE.allocateMemory(4);
        // 存放int类型的数据，占4个字节
        UNSAFE.putInt(address, intVal);
        System.out.println("******************************");
        // 然后我们之间读取long
        int val2 = UNSAFE.getInt(address);
        System.out.println("重新从内存中读取的值：" + val2);
    }
}
