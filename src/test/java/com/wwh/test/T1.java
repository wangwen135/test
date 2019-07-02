package com.wwh.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * </pre>
 *
 * @author wwh
 * @date 2015年8月4日 上午10:57:03
 */
public class T1 {

    private static final Logger log = LoggerFactory.getLogger(T1.class);

    public static void main(String[] args) throws InterruptedException {
        new T1().test1();
    }

    public void test1() throws InterruptedException {
        //
        log.debug("开始");
        System.out.println("hello world");

        while (true) {
            System.out.println("...");
            Thread.sleep(1000);
        }
    }
}
