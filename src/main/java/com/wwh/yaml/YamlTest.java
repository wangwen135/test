package com.wwh.yaml;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;

public class YamlTest {
    
    public static void main(String[] args) {
        Person p1 = new Person();
        p1.setName("父亲");
        p1.setAge(60);
        p1.setSex("M");
        
        Person c1 = new Person();
        c1.setName("xx1");
        c1.setAge(30);
        c1.setSex("M");
        
        p1.addChidern(c1);
        
        Yaml yaml = new Yaml();
        String str = yaml.dump(p1);
        System.out.println(str);
        
        Person p2 = yaml.load(str);
        System.out.println(p2.toString());
    }

    public static void main1(String[] args) {
        InputStream is = YamlTest.class.getClassLoader().getResourceAsStream("test.yaml");

        Yaml yaml = new Yaml();
        // 读入文件
        Object result = yaml.load(is);

        System.out.println(result.getClass());

        System.out.println(result);
    }
}
