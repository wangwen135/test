package com.wwh.test.classpath;

import java.util.Enumeration;
import java.util.Properties;

/**
 * <pre>
 * 打印ClassPath
 * 列出所有加载的jar包
 * 不同平台的分隔符不同
 *  - windows ;
 *  - linux   :
 * </pre>
 *
 * @author wangwh
 * @date 2018年9月14日下午2:50:57
 * 
 */
public class PrintClassPathJar {

	public static void main(String[] args) {

		Properties properties = System.getProperties();

		System.out.println("所有的系统属性：");

		@SuppressWarnings("unchecked")
		Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			System.out.println(key);
			System.out.println(properties.getProperty(key));
			System.out.println();
		}

		System.out.println("\nBootClassLoader 加载：");

		String classPath = System.getProperty("sun.boot.class.path");
		printPathArray(classPath);

		System.out.println("\nExtClassLoader 加载：");

		classPath = System.getProperty("java.ext.dirs");
		printPathArray(classPath);

		System.out.println("\nAppClassLoader 加载：");

		classPath = System.getProperty("java.class.path");
		printPathArray(classPath);

	}

	public static void printPathArray(String classPath) {
		if (classPath == null || classPath.length() == 0) {
			return;
		}
		String[] classpathArray = classPath.split(System.getProperty("path.separator"));
		for (String str : classpathArray) {
			System.out.println(str);
		}
	}
}
