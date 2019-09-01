package com.wwh.test.swing.monitor;

/**
 * <pre>
 * xy坐标
 * X轴 为 具体的值
 * Y轴 为 时间
 * </pre>
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

    public static void main(String[] args) {
        double d;
        long l;
        for (int i = 0; i < 720; i++) {
            d = Math.sin(Math.toRadians(i));
            l = (long) (d * 100);
            System.out.println(l);
        }
    }

}
