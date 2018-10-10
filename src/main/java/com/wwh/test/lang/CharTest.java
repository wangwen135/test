package com.wwh.test.lang;

import java.util.Random;

public class CharTest {
	public static void main(String[] args) {
		int start = 0x4E00;
		int end = 0x9FA5;
		System.out.println("start = " + start + "    end = " + end + "   range = " + (end - start));
		// start = 19968 end = 40869 range = 20901

		// for (int i = start; i < end; i++) {
		// System.out.println("i = " + i + " char = " + (char) i);
		// }

		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			System.out.println(getRandomChinese(r.nextInt(20)));
		}
	}

	public static String getRandomChinese(int length) {
		Random r = new Random();
		// int start = 0x4E00;
		// int end = 0x9FA5;

		char[] chars = new char[length];
		for (int i = 0; i < length; i++) {
			chars[i] = (char) (0x4E00 + r.nextInt(2000));
		}
		return new String(chars);
	}
}
