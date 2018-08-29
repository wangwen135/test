package com.wwh.test.math;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * <pre>
 * 超大号次方
 * </pre>
 *
 * @author wwh
 * @date 2015年11月18日 下午4:33:01
 *
 */
public class Power {

    public static BigDecimal power(int a, int b) {
        BigDecimal result = BigDecimal.ONE;
        if (b <= 0) {
            return BigDecimal.ONE;
        } else {
            BigDecimal A = new BigDecimal(a);
            for (int i = 0; i < b; i++) {
                result = result.multiply(A);
            }
        }
        return result;
    }

    public static void main(String[] arguments) {
        System.out.println("输入数字：");
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();// 读取控制台输入的整数
        System.out.println("输入次方：");
        int b = sc.nextInt();
        sc.close();
        BigDecimal result = power(a, b);

        int precision = result.precision();
        System.out.println(a + " 的 " + b + " 次方");
        System.out.println("其结果精度是：" + precision);

        if (precision > 1000) {
            System.out.println("太大了...");
        } else {
            String str = result.toEngineeringString();
            System.out.println(str);
        }

    }
}
