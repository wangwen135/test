package com.wwh.test.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author wwh
 * @date 2015年6月25日 上午10:00:58
 *
 */
public class ClassTest {

    private Logger logger = LoggerFactory.getLogger(ClassTest.class);

    public static void main(String[] args) {
        int v =1;
        output(v);
        
    }

    /**
     * @param t
     */
    private static void output(Object t) {
        Class<? extends Object> class1 = t.getClass();

        if (t instanceof String) {

        } else if (class1.isPrimitive()) {
            System.out.println("是基本类型");
            if (Boolean.class == class1) {
                Boolean bv = (Boolean) t;
                System.out.println(bv.toString());
            }

        } else {
            System.out.println("不是基本类型");
            System.out.println(t.getClass());
        }
    }

}
