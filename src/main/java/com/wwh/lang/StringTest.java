/**
 * @Title: StringTest.java 
 * @Package com.ww.lang 
 * @Description: 
 * @author ww
 * @date 2015年6月5日 下午5:08:03 
 * @version V1.0  
 */
package com.wwh.lang;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author ww
 * @date 2015年6月5日 下午5:08:03
 *
 */
public class StringTest {
    
    public static void main(String[] args) {
    }
    
    public static void main8(String[] args) {
        String name = "adf{asdfadf}.java.vm";
        System.out.println(name.substring(0, name.lastIndexOf(".")));
    }
    
    public static void main7(String[] args) {
        String ss = "adsfasdf=  asdfadf 是的";
        
        String[] arrays = ss.split("[ ]*=[ ]*",2);
        
        System.out.println(arrays[0]);
        System.out.println(arrays[1]);
    }
    
    public static void main6(String[] args) {
        String[] s  = new String[]{"","asdf","","",null};
        List<String> list = new ArrayList<String>();
        for (String str : s) {
            if(str==null || "".equals(str.trim())){
                continue;
            }
            list.add(str);
        }
        if(list.isEmpty()){
            System.out.println("null");
        }else{
            System.out.println(list);
        }
        
    }
    
    public static void main5(String[] args) {
        String ss = "用户关系\n信(息（这里只存一些";
        System.out.println(ss.split("[（( \n]")[0]);
    }
    
    public static <T> List<T> getT(T... arg){
        return null;
    }
    
    public static void main4(String[] args) {
        tt("asdfasdf","asdfasd");
    }
    
    public static void tt(String... aa){
        System.out.println(Arrays.toString(aa));
    }
    
    public static void main3(String[] args) throws UnsupportedEncodingException {
        byte[] b = new byte[]{116, 101, 115, 116, 67, 97, 99, 104, 101, 58, 84, 101, 115, 116, 83, 101, 114, 118, 105, 99, 101, 73, 109, 112, 108, 46, 100, 101, 108, 101, 116, 101, 66, 121, 73, 100, 40, 54, 41};
      //  byte[] b = new byte[]{116, 101, 115, 116, 67, 97, 99, 104, 101, 58};
        
        Charset charset = Charset.forName("UTF8");
        
        String s = new String(b,charset);
        
        System.out.println(s);
    }
	public static void main2(String[] args) {
		String auth = "123123  asdf12kasdj12312jasdf";
		String[] auths = auth.split("  ",2);
		for (String s : auths) {
			System.out.println(s);
		}
	}
}
