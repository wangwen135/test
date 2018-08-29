package com.wwh.test.type;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeTest {

    private static final Logger logger = LoggerFactory.getLogger(TypeTest.class);

    Map<String, String> map;

    public static void main(String[] args) throws Exception {
        Field f = TypeTest.class.getDeclaredField("map");
        
        System.out.println(f.getGenericType()); // java.util.Map<java.lang.String, java.lang.String>
        
        System.out.println(f.getGenericType() instanceof ParameterizedType); // true
        
        ParameterizedType pType = (ParameterizedType) f.getGenericType();
        
        System.out.println(pType.getRawType()); // interface java.util.Map
        
        for (Type type : pType.getActualTypeArguments()) {
            System.out.println(type); // 打印两遍: class java.lang.String
        }
        
        System.out.println(pType.getOwnerType()); // null
    }

    public static <T> T getAny(Class<T> t) {
        //t.getcla

        // Class <T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        return null;
    }
}
