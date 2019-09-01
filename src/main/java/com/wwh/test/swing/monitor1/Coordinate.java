package com.wwh.test.swing.monitor1;

/**
 * xy坐标
 * 
 * @author wwh
 */
public class Coordinate {

    private long xValue;
    private long yValue;

    public Coordinate(long x, long y) {
        this.xValue = x;
        this.yValue = y;
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
