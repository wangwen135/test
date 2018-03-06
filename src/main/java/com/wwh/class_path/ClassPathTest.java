package com.wwh.class_path;

import org.apache.hadoop.util.StringUtils;

public class ClassPathTest {

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));// 系统的classpaht路径
        System.out.println(System.getProperty("user.dir"));// 用户的当前路径

        System.out.println("测试hadoop的错误信息");

        System.out.println(StringUtils.toLowerCase("Hadoop Test"));
        
        //不同环境下jvm加载同一个目录中的jar文件顺序略有不同，需要通过启动脚本指定某些jar包先加载
        
    }
}
