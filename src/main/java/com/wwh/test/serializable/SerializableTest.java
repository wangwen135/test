package com.wwh.test.serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <pre>
 * 用java原始的序列化与反序列化
 * 少字段 和 多字段 都不会有问题
 * </pre>
 */
public class SerializableTest {

    /**
     * 先将对象写入到文件中
     */
    public static void main1(String[] args) throws Exception, IOException {

        ObjXX x1 = new ObjXX();
        x1.setId(100);
        x1.setName("name1");
        // x1.setAge(100);

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("\\opt\\serializable\\x3.obj")));

        oos.writeObject(x1);

        oos.flush();
        oos.close();
    }

    // 修改对象

    /**
     * 再从文件反序列化为对象
     */
    public static void main(String[] args) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("\\opt\\serializable\\x3.obj")));

        Object o1 = ois.readObject();

        System.out.println(o1);
        ois.close();
    }
}
