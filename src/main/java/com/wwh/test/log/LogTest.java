package com.wwh.test.log;

import java.io.IOException;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
	private static Logger logger = LoggerFactory.getLogger(LogTest.class);

	public static void main(String[] args) {
		logger.debug("aaaaaaaaaa");

		logger.info("aaaaaaaaaaaa");

		logger.error("asfdasfd");

		try {
			org.apache.log4j.Logger myTest = org.apache.log4j.Logger
					.getLogger("myTest");

			Layout layout = new PatternLayout("%m%n");

			Appender appender = new FileAppender(layout, "wwwww.log");
			myTest.addAppender(appender);
			myTest.setAdditivity(false);
			
			
			myTest.error("asfdasfdasfd");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
