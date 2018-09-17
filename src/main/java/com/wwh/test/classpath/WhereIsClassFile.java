package com.wwh.test.classpath;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Class 文件在哪里
 * </pre>
 *
 * @author wangwh
 * @date 2018年9月14日下午3:29:38
 * 
 */
public class WhereIsClassFile {

	private static final Logger logger = LoggerFactory.getLogger(WhereIsClassFile.class);

	public static final String SUFFIX = ".class";

	public static void main(String[] args) throws IOException {
		// String className = "org/slf4j/impl/StaticLoggerBinder.class";
		// String className = "org/apache/commons/lang/StringUtils.class";

		if (args.length == 0) {
			String message = "传入参数为空\n请传入要查找的类名全路径，如：" + "\n" + WhereIsClassFile.class.getName() + "\n";

			System.out.println(message);
			logger.info(message);
			printClassURL(WhereIsClassFile.class.getName());
		} else {
			for (String string : args) {
				System.out.println("----------------------------------");
				printClassURL(string);
			}
		}

	}

	public static void printClassURL(String className) throws IOException {
		if (className == null || className.equals("")) {
			return;
		}

		if (className.endsWith(SUFFIX)) {
			className = className.substring(0, className.length() - 6);
		}
		// 将. 替换为/
		className = className.replace('.', '/');

		// 需要以class结尾
		className = className + SUFFIX;

		System.out.println(className);
		logger.info(className);

		Enumeration<URL> paths = ClassLoader.getSystemResources(className);
		int count = 0;
		while (paths.hasMoreElements()) {
			count++;
			URL path = (URL) paths.nextElement();
			System.out.println(path);
			logger.info(path.toString());
		}
		System.out.println("total : " + count);
		logger.info("total : " + count);
	}
}
