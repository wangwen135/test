package com.wwh.test.lang;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author wangwh
 * 
 */
public class BooleanTest {

	public static void main(String[] args) {
		// false
		System.out.println(Boolean.valueOf(null));
		System.out.println(Boolean.valueOf(""));
		System.out.println(Boolean.valueOf("  "));
		System.out.println(Boolean.valueOf("1"));
		System.out.println(Boolean.valueOf("0"));
		System.out.println(Boolean.valueOf("yes"));
		// true
		System.out.println(Boolean.valueOf("true"));
		System.out.println(Boolean.valueOf("TRUE"));
	}
}
