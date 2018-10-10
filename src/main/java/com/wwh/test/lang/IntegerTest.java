package com.wwh.test.lang;

public class IntegerTest {
	private Integer tt;
	public Integer getTt() {
		return tt;
	}
	public void setTt(Integer tt) {
		this.tt = tt;
	}
	public static void main(String[] args) {

		IntegerTest it = new IntegerTest();
		System.out.println(it.getTt() == 1);
		
		System.out.println(Integer.valueOf(100) == 100);
		System.out.println(Integer.valueOf(200) == 200);
		System.out.println(Integer.valueOf(500) == 500);
		System.out.println(Integer.valueOf(1000) == 1000);

		System.out.println(Integer.valueOf(100) == Integer.valueOf(100));
		// 127 以内可以直接比
		System.out.println(Integer.valueOf(127) == Integer.valueOf(127));

		System.out.println(Integer.valueOf(200) == Integer.valueOf(200));
		System.out.println(Integer.valueOf(500) == Integer.valueOf(500));
		System.out.println(Integer.valueOf(1000) == Integer.valueOf(1000));
	}
}
