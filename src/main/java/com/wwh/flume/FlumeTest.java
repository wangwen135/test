package com.wwh.flume;

import org.apache.flume.api.NettyAvroRpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author wwh
 * @date 2015年9月11日 下午5:55:01
 *
 */
public class FlumeTest {

    private static final Logger logger = LoggerFactory
            .getLogger("abc");
//    private static final Logger log = LoggerFactory.getLogger("LOG_FLUME");

    public static void main(String[] args) {
        System.out.println("开始。。。。");
        for (int i = 0; i < 100; i++) {
            System.out.println(i+"  ####################");
            logger.info("记录日志：" + i);
        }

        System.out.println("结束。。。。");
    }

}
