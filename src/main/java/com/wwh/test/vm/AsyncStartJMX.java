package com.wwh.test.vm;

import java.io.IOException;
import java.util.Properties;

import com.sun.tools.attach.VirtualMachine;

/**
 * <pre>
 * 动态启动JMX
 * 需要连接独立启动的java程序
 * Eclipse中的程序不行
 * </pre>
 * 
 * @author wwh
 * @since 1.
 */
public class AsyncStartJMX {

    // 用ListJavaVM 找PID
    public static String PID = "9320";
    public static String jmxPort = "9001";

    public static void main(String[] args) throws Exception, IOException {

        // attach to target VM

        VirtualMachine vm = VirtualMachine.attach(PID);

        // start management agent

        // 启动目标虚拟机中的JMX管理代理
        // 配置属性与启动JMX管理代理时在命令行中指定的属性相同
        // 与在命令行中一样，少需要指定com.sun.management.jmxremote.port 属性
        Properties props = new Properties();

        props.put("com.sun.management.jmxremote.port", jmxPort);

        props.put("com.sun.management.jmxremote.ssl", "false");
        props.put("com.sun.management.jmxremote.authenticate", "false");
        vm.startManagementAgent(props);

        // vm.startLocalManagementAgent();

        // detach
        vm.detach();

        // JMX 默认密码文件
        // 位于 $JAVA_HOME/jre/lib/management
        // jre1.8.0_162\lib\management\jmxremote.password

        System.out.println("成功在进程：" + PID + " 启动JMX，port=" + jmxPort);
    }
}
