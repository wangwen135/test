package com.wwh.test.jmx.adaptor;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;
import com.wwh.test.jmx.mbean.Hello;

/**
 * <pre>
 * 提供http视图管理MBean
 * 需要引入：jmxtools.jar
 * 
 * 启动程序，在浏览器中访问：
 * http://127.0.0.1:8082
 * 
 * </pre>
 *
 * @author wangwh
 * @date 2018年9月13日下午3:24:32
 * 
 */
public class HtmlAdaptorTest {

	public static void main(String[] args) throws JMException, IOException {

		// 返回平台 MBeanServer
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		String domainName = "WWHMBean";

		// 创建ObjectName实例
		ObjectName helloName = new ObjectName(domainName + ":name=HelloWorld");

		// 将new Hello()这个对象注册到MBeanServer上去
		mbs.registerMBean(new Hello(), helloName);

		// Distributed Layer, 提供了一个HtmlAdaptor。
		// 支持Http访问协议，并且有一个不错的HTML界面，这里的Hello就是用这个作为远端管理的界面
		// 事实上HtmlAdaptor是一个简单的HttpServer，它将Http请求转换为JMX Agent的请求
		ObjectName adapterName = new ObjectName(domainName + ":name=htmladapter,port=8082");
		HtmlAdaptorServer adapter = new HtmlAdaptorServer();
		adapter.start();
		mbs.registerMBean(adapter, adapterName);

		System.in.read();
	}
}
