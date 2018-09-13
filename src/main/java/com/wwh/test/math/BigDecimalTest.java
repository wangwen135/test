package com.wwh.test.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <pre>
 * 方法描述：
 * add(BigDecimal)        BigDecimal对象中的值相加，然后返回这个对象。
 * subtract(BigDecimal)   BigDecimal对象中的值相减，然后返回这个对象。
 * multiply(BigDecimal)   BigDecimal对象中的值相乘，然后返回这个对象。
 * divide(BigDecimal)     BigDecimal对象中的值相除，然后返回这个对象。
 * toString()             将BigDecimal对象的数值转换成字符串。
 * doubleValue()          将BigDecimal对象中的值以双精度数返回。
 * floatValue()           将BigDecimal对象中的值以单精度数返回。
 * longValue()            将BigDecimal对象中的值以长整数返回。
 * intValue()             将BigDecimal对象中的值以整数返回。
 * 
 * 舍入模式：
 * BigDecimal.ROUND_DOWN        接近零的舍入模式。
 * BigDecimal.ROUND_FLOOR       接近负无穷大的舍入模式。
 * BigDecimal.ROUND_HALF_DOWN   向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为上舍入的舍入模式。
 * BigDecimal.ROUND_HALF_UP     向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。（四舍五入）
 * BigDecimal.ROUND_UP          舍入远离零的舍入模式。
 * </pre>
 *
 * @author wangwh
 * @date 2018年8月18日下午2:35:49
 * 
 */
public class BigDecimalTest {

	public static void main(String[] args) {
		Float ff = Float.valueOf("1900000.3");
		System.out.println(ff);
		
		BigDecimal totalAmount = new BigDecimal("0");
		for (int i = 0; i < 2998; i++) {
			totalAmount = totalAmount.add(calculate(10, 450.000f));
		}

		System.out.println(totalAmount);
		System.out.println(totalAmount.floatValue());
		System.out.println(totalAmount.toString());
		System.out.println(Float.valueOf(totalAmount.toString()));
		System.out.println(totalAmount.doubleValue());
	}

	private static BigDecimal calculate(int renewDuration, Float servicePrice) {
		// 续费时长
		BigDecimal renewDurationBD = new BigDecimal(renewDuration);
		// 服务费用
		BigDecimal servicePriceBD = new BigDecimal(servicePrice.toString());
		// 保留3位小数，四舍五入
		// 续费模式
		int renewType = 1;
		// 区分年和月的付费方式，计算单位价格
		if (renewType == 2) {
			return servicePriceBD.multiply(renewDurationBD).setScale(3, RoundingMode.HALF_UP);
		} else {
			// 为避免计算后的小数位误差，用年服务费 X 年数
			// 续费月份都是 12 的倍数
			BigDecimal year = renewDurationBD.divide(new BigDecimal(12), 3, RoundingMode.HALF_UP);
			return servicePriceBD.multiply(year).setScale(3, RoundingMode.HALF_UP);
		}
	}

	public static void main2(String[] args) {
		divide();

		multiply();
		// construction();

	}

	/**
	 * 使用BigDecimal的字符串构造函数<br>
	 * 否则会有精度问题
	 */
	public static void construction() {
		Float f = 32.8f;
		System.out.println(f);

		// 直接使用Float会有精度问题
		BigDecimal b = new BigDecimal(f);
		System.out.println(b.toString());

		// 用字符串构建不会
		BigDecimal b2 = new BigDecimal(f.toString());
		System.out.println(b2.toString());

		// #############################################
		Double d = 1.99d;
		System.out.println(d.toString());

		// 直接使用Double也会有精度问题
		b = new BigDecimal(d);
		System.out.println(b.toString());

		// 用字符串构建不会
		b2 = new BigDecimal(d.toString());
		System.out.println(b2.toString());
	}

	/**
	 * <pre>
	 * 舍入测试
	 * </pre>
	 */
	public static void scale() {
		BigDecimal decimal = new BigDecimal("1.12345");
		System.out.println(decimal);

		BigDecimal setScale = decimal.setScale(4, BigDecimal.ROUND_HALF_DOWN);
		System.out.println(setScale);

		// 四舍五入
		BigDecimal setScale1 = decimal.setScale(4, BigDecimal.ROUND_HALF_UP);
		System.out.println(setScale1);
	}

	/**
	 * 测试乘法
	 */
	public static void multiply() {
		BigDecimal b1 = new BigDecimal("32.8");
		BigDecimal b2 = new BigDecimal("3");
		BigDecimal b3 = b1.multiply(b2);
		System.out.println(b3);

		b1 = new BigDecimal("32.8323");
		b2 = new BigDecimal("3");
		b3 = b1.multiply(b2);
		System.out.println(b3.setScale(3, RoundingMode.HALF_UP));

		BigDecimal b4 = b1.multiply(b2);
		System.out.println(b4.setScale(3, RoundingMode.HALF_UP));
	}

	/**
	 * 测试除法
	 */
	public static void divide() {
		BigDecimal b1 = new BigDecimal("32.8");
		BigDecimal b2 = new BigDecimal("3");
		BigDecimal b3 = b1.divide(b2, 3, RoundingMode.HALF_UP);
		System.out.println(b3);

		BigDecimal b4 = new BigDecimal("10");
		System.out.println(b4.divide(b2, 3, RoundingMode.HALF_UP));
	}
}
