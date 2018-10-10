package com.wwh.test.lang;

public class DoubleTest {

	public static void main(String[] args) {
		float f = 111.23f;
		double d = f;
		System.out.println(d);
		
		Float f2 = 111.23f;
		Double d2 = Double.valueOf(f2);
		System.out.println(d2);
		
		
		Double d3 = Double.valueOf(String.valueOf(f2));
		System.out.println(d3);
		
		System.out.println(Double.valueOf(f2.toString())); 
	}
}
