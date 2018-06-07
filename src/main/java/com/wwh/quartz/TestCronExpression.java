package com.wwh.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.CronExpression;

public class TestCronExpression {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {

        // String cronExpression = "*/2 * * * * ? *";

        // String cronExpression = "1 0 12 L * ? *";
        
        // 0 0 10,14,16 * * ?
        // 0 0/30 9-17 * * ?
        
        String cronExpression = "0 0/30 9-17 * * ?";

        // String cronExpression = "00 00 00 26,27,28,29,31,L * *";

        CronExpression ce = null;
        try {
            ce = new CronExpression(cronExpression);
            System.out.println("表达式： " + ce.getCronExpression());

            System.out.println("最近10次执行时间：");

            Date d = new Date();
            for (int i = 0; i < 10; i++) {
                d = printNext(ce, d);

            }

        } catch (Exception e) {

            System.out.println("表达式错误");
            e.printStackTrace();
        }

    }

    private static Date printNext(CronExpression ce, Date lastDate) {

        Date d = ce.getNextValidTimeAfter(lastDate);

        System.out.println(sdf.format(d));

        return d;
    }
}
