package com.wwh.log.Appender;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Class1 {

    private final static Logger logger = LoggerFactory.getLogger(Class1.class);

    public void method1() {
        logger.info("++++++++++++++++++++++++++++++++++++++++++++++");

        // do something
        logger.info("class1 的日志");

        logger.debug("debug 级别");

        logger.error("错误级别的日志");
    }

    public static void main(String[] args) {
        Class1 c1 = new Class1();
        Class2 c2 = new Class2();

        c1.method1();

        logger.debug("调用c2的方法");

        c2.method1();

        System.err.println("打印出缓存的内容");

        while (true) {

            List<String> list = CacheQueueStream.getAllCacheString();

            for (String str : list) {
                System.err.print(str);
            }

        }
    }
}
