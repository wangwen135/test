package com.wwh.test.jmx.regist;

import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import com.wwh.test.jmx.mbean.Hello;

/**
 * <pre>
 * 通过程序启动jmx端口
 * 无需在启动参数中指定
 * 
 * 启动程序
 * 在JConsole中选择远程进程，输入 serviceURL
 * </pre>
 *
 * @author wangwh
 * @date 2018年9月13日下午4:02:11
 * 
 */
public class RegistTest {

	public static void main(String[] args) throws Exception {

		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		String domainName = "MyMBean";
		ObjectName helloName = new ObjectName(domainName + ":name=HelloWorld");
		// 将new Hello()这个对象注册到MBeanServer上去
		mbs.registerMBean(new Hello(), helloName);

		// 创建一个接受对特定端口调用的远程对象注册表
		// 注册一个端口，绑定url后，客户端就可以使用rmi通过url方式来连接JMXConnectorServer
		int rmiPort = 8888;
		LocateRegistry.createRegistry(rmiPort);

		// service:jmx:protocol:sap
		String serviceURL = "service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/" + domainName;

		// JMX API 连接器服务器的地址。此类的实例是不可变的。
		JMXServiceURL url = new JMXServiceURL(serviceURL);

		// 用于创建 JMX API 连接器服务器的工厂
		// 创建位于给定地址的连接器服务器
		JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);

		// 激活连接器服务器，即开始侦听客户端连接。在连接器服务器已激活时，调用此方法没有任何作用。在连接器服务器已停止时，调用此方法将生成 IOException。
		cs.start();

		System.out.println("....................rmi start.....");
		System.out.println("service URL:");
		System.out.println(serviceURL);
		System.in.read();
	}
}
