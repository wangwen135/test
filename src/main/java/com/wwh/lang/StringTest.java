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
import java.util.Arrays;

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
