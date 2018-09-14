package com.wwh.test.log;

import java.io.IOException;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 日志测试
 * 测试配置文件定义的日志和自定义的日志
 * </pre>
 *
 * @author wangwh
 * 
 */
public class LogTest {
	/**
	 * 会去找这个类，确定具体的日志实现
	 * org/slf4j/impl/StaticLoggerBinder.class
	 * 
	 * 从StaticLoggerBinder中获取 getLoggerFactory
	 */
	private static Logger logger = LoggerFactory.getLogger(LogTest.class);

	public static void main(String[] args) {
		logger.debug("debugggggggggggggggggggggggg");

		logger.info("infooooooooooooooooooooooooo");

		logger.warn("warnnnnnnnnnnnnnnnnnnnnnnnnn");

		logger.error("errorrrrrrrrrrrrrrrrrrrrrrrr");

		try {
			org.apache.log4j.Logger myLog4j = org.apache.log4j.Logger.getLogger("myLog4j");

			Layout layout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p %c %M(%F:%L) - %m%n");

			Appender appender = new FileAppender(layout, "./logs/customerLog.log");
			myLog4j.addAppender(appender);

			Appender consoleAppender = new ConsoleAppender(layout);
			myLog4j.addAppender(consoleAppender);

			/**
			 * 它是 子Logger 是否继承 父Logger 的 输出源（appender） 的标志位。
			 * 具体说，默认情况下子Logger会继承父Logger的appender，也就是说子Logger会在父Logger的appender里输出。
			 * 若是additivity设为false，则子Logger只会在自己的appender里输出，而不会在父Logger的appender里输出。
			 */
			myLog4j.setAdditivity(false);

			// 重新设置单个log的级别
			myLog4j.setLevel(Level.DEBUG);

			myLog4j.debug("customerLog --- debug");
			myLog4j.info("customerLog --- info");
			myLog4j.warn("customerLog --- warn");
			myLog4j.error("customerLog --- error");
			myLog4j.fatal("customerLog --- fatal");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
