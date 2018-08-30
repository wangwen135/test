package com.wwh.test.lang;

public class FloatTest {
	public static void main(String[] args) {

		lossAccuracy();

		//
		// subtraction();

	}

	/**
	 * Float 大于一定的值会丢失精度
	 */
	public static void lossAccuracy() {
		Float ff = Float.valueOf("1900000.3");
		System.out.println(ff);
	}

	/**
	 * 减法精度丢失问题
	 */
	public static void subtraction() {
		Float f1 = 11.9f;
		Float f2 = 0.8f;
		System.out.println(f1 - f2);
	}

	/**
	 * 乘法丢失精度
	 */
	public static void multiply() {
		Float f = 32.8f;
		Integer t = 3;
		Float f2 = f * t;
		System.out.println(f * t);
		System.out.println(f2);
	}

}
