package com.wwh.test.math;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * <pre>
 * 阶乘
 * </pre>
 *
 * @author wwh
 * @date 2015年11月18日 下午4:12:43
 *
 */
public class Factorial {
    public static BigDecimal factorial(int n) {
        BigDecimal result = new BigDecimal(1);
        BigDecimal a;
        for (int i = 2; i <= n; i++) {
            a = new BigDecimal(i);// 将i转换为BigDecimal类型
            result = result.multiply(a);// 不用result*a，因为BigDecimal类型没有定义*操作</span><span>
        }
        return result;
    }

    public static void main(String[] arguments) {
        System.out.println("输入要计算阶乘的数字：");
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();// 读取控制台输入的整数
        sc.close();
        BigDecimal result = factorial(a);

        int precision = result.precision();
        System.out.println(a + " 的阶乘");
        System.out.println("其结果精度是：" + precision);

        if (precision > 1000) {
            System.out.println("太大了...");
        } else {
            String str = result.toEngineeringString();
            System.out.println(str);
        }

    }
}
