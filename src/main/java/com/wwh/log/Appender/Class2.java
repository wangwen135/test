package com.wwh.log.Appender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Class2 {
    private final static Logger logger = LoggerFactory.getLogger(Class2.class);

    public void method1() {
        // do something
        logger.error("开启一个线程不停的输入日志，测试并发性");

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {

                while (true) {
                    logger.debug("当前的时间是：" + System.currentTimeMillis());

                }
            }
        });

        t.setDaemon(true);
        t.start();
    }
}
