package com.wwh.test.monitor;

public class Coordinate {

	private long xValue;
	private long yValue;

	public Coordinate(long x, long y) {
		this.xValue = x;
		this.yValue = y;
	}

	public Coordinate() {
	}

	public long getxValue() {
		return xValue;
	}

	public void setxValue(long xValue) {
		this.xValue = xValue;
	}

	public long getyValue() {
		return yValue;
	}

	public void setyValue(long yValue) {
		this.yValue = yValue;
	}

}
