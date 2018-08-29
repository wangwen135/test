package com.wwh.test.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TimeTest {

    public static void main3(String[] args) {
        long ttl = 1500;
        long l2 = TimeUnit.MILLISECONDS.toSeconds(ttl);
        System.out.println(l2);
    }

    public static void main(String[] args) {

        Random r = new Random();

        for (int i = 0; i < 15; i++) {

            // 返回以毫秒为单位的当前时间。
            long time = System.currentTimeMillis();

            System.out.println(time);

            System.out.println(time / 1000);

            System.out.println(time / 3000);

            try {
                Thread.sleep(r.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main1(String[] args) {
        long time = 1468216800000L;
        Date d = new Date(time);
        System.out.println(d.toLocaleString());
    }

    public static void main2(String[] args) {
        Long l = 1278511200000l;
        Date d = new Date(l);

        System.out.println(d);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(d));

        try {
            Date d2 = sdf.parse("2016-07-11 14:00:00");
            System.out.println(d2.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
