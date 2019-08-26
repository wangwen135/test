package com.wwh.test.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class TomcatTest {

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        // E:\git\test
        Tomcat tomcat = new Tomcat();
        // tomcat.getHost();
        tomcat.setPort(8090);
        // tomcat.setHostname("localhost");

        // tomcat.setBaseDir(basedir);
        // tomcat.addContext(contextPath, docBase)

        String baseDir = System.getProperty("user.dir");
        
        tomcat.setBaseDir(baseDir +"/target");
        
        String appPath = baseDir + "/src/main/webapp";
        tomcat.addWebapp("/test", appPath);

        try {
            tomcat.start();
            System.out.println("tomcat start over");
        } catch (LifecycleException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
