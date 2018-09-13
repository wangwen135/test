package com.wwh.test.jmx.client;

import java.util.Iterator;
import java.util.Set;

import javax.management.Attribute;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.wwh.test.jmx.mbean.HelloMBean;

/**
 * <pre>
 * 客户端代码远程连接JMXConnectorServer操作MBean
 * 
 * 先启动 RegistTest
 * </pre>
 *
 */
public class ClientTest {

	public static void main(String[] args) throws Exception {

		// connect JMX
		JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:8888/MyMBean");
		JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
		MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
		ObjectName mbeanName = new ObjectName("MyMBean:name=HelloWorld");

		// print domains
		System.out.println("Domains:---------------");
		String domains[] = mbsc.getDomains();
		for (int i = 0; i < domains.length; i++) {
			System.out.println("\tDomain[" + i + "] = " + domains[i]);
		}
		// MBean count
		System.out.println("MBean count = " + mbsc.getMBeanCount());

		// process attribute
		mbsc.setAttribute(mbeanName, new Attribute("Name", "new value"));
		System.out.println("get value : Name = " + mbsc.getAttribute(mbeanName, "Name"));

		// invoke via proxy
		HelloMBean proxy = (HelloMBean) MBeanServerInvocationHandler.newProxyInstance(mbsc, mbeanName, HelloMBean.class, false);
		proxy.printHello();
		proxy.printHello("I'll connect to JMX Server via client.");

		// invoke via rmi
		mbsc.invoke(mbeanName, "printHello", null, null);
		mbsc.invoke(mbeanName, "printHello", new Object[] { "I'll connect to JMX Server via client2." }, new String[] { String.class.getName() });

		// get mbean information
		MBeanInfo info = mbsc.getMBeanInfo(mbeanName);
		System.out.println("Hello Class:" + info.getClassName());
		System.out.println("Hello Attriber:" + info.getAttributes()[0].getName());
		System.out.println("Hello Operation:" + info.getOperations()[0].getName());

		// ObjectName of MBean
		System.out.println("all ObjectName:---------------");
		Set<?> set = mbsc.queryMBeans(null, null);
		for (Iterator<?> it = set.iterator(); it.hasNext();) {
			ObjectInstance oi = (ObjectInstance) it.next();
			System.out.println("\t" + oi.getObjectName());
		}
		jmxc.close();
	}

}
