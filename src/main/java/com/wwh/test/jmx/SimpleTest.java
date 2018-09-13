package com.wwh.test.jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.wwh.test.jmx.mbean.Hello;
import com.wwh.test.jmx.mbean.WwhBean;

/**
 * <pre>
 * 运行程序
 * 使用jconsole 连接，可以查看到注册的MBean
 * jconsole 会自动列出本地程序
 * 并设置其属性，调用方法
 * 
 * 要远程连接，在启动参数中加上:
 * -Dcom.sun.management.jmxremote.port=8888 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false
 * 在jconsole中输入IP和上面定义的端口 8888 连接
 *  
 * </pre>
 *
 * @author wangwh
 * @date 2018年9月13日下午3:12:28
 * 
 */
public class SimpleTest {

	public static void main(String[] args) throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, IOException {

		// 这种方式不会出现在Jconsole中
		// MBeanServer mbs = MBeanServerFactory.createMBeanServer();

		// 返回平台 MBeanServer
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		String domainName = "WWHMBean";

		// 创建ObjectName实例
		ObjectName helloName = new ObjectName(domainName + ":name=HelloWorld");

		// 将new Hello()这个对象注册到MBeanServer上去
		mbs.registerMBean(new Hello(), helloName);

		ObjectName wwhName = new ObjectName(domainName + ":name=wwh");

		mbs.registerMBean(new WwhBean(), wwhName);

		System.in.read();
	}
}
