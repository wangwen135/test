/**
 * @Title: StringTest.java 
 * @Package com.ww.lang 
 * @Description: 
 * @author ww
 * @date 2015年6月5日 下午5:08:03 
 * @version V1.0  
 */
package com.wwh.test.lang;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static void main13(String[] args) {
        String url = "http://www.example.com/index.html#print";
        int i = url.indexOf('h');
        System.out.println(i);
        if (i > 0) {
            System.out.println(url.substring(0, i));
        }
    }

    public static void main12(String[] args) {
        String url = "http://de­­­e­­pdot3­­­5wvmeyd5.onion";
        System.out.println(url);

        char c1 = 173;

        String sss = "­";

        String spc = String.valueOf(c1);

        String url2 = url.replace(spc, "");

        System.out.println(url2);

        char c = '­';
        System.out.println(((int) c));

    }

    public static void main11(String[] args) {
        String domain = "baidu.com";
        int code = domain.hashCode();
        code = Math.abs(code);
        System.out.println(code);
        number2String(code);
    }

    public static void main10(String[] args) {
        for (int i = 0; i < 678; i++) {
            number2String(i);
        }
    }

    public static void number2String(int hashCode1) {

        int maxValue = 676;// 26*26

        int remainder = hashCode1 % maxValue;

        System.out.print("余数：" + remainder + "        ");

        char c1 = (char) (65 + remainder / 26);
        char c2 = (char) (65 + remainder % 26);
        System.out.println(c1 + "" + c2);

    }

    public static void main9(String[] args) {

        // String a = "aa";
        // int hashCode1 = a.hashCode();

        int hashCode1 = 1;

        System.out.println(hashCode1);

        int maxValue = 676;

        int remainder = hashCode1 % maxValue;

        System.out.println("余数：" + remainder);

        if (remainder < 26) {
            char c2 = (char) (65 + remainder);
            System.out.println("0" + c2);

        } else {

            char c1 = (char) (64 + remainder / 26);
            char c2 = (char) (65 + remainder % 26);
            System.out.println(c1 + "" + c2);
        }

    }

    public static void main8(String[] args) {
        String name = "adf{asdfadf}.java.vm  xxxx|xxxx2|xxxx3|xxxx4";

        System.out.println(name.substring(0, name.lastIndexOf(".")));

        System.out.println(name.split("[|]")[2]);
    }

    public static void main7(String[] args) {
        String ss = "adsfasdf=  asdfadf 是的";

        String[] arrays = ss.split("[ ]*=[ ]*", 2);

        System.out.println(arrays[0]);
        System.out.println(arrays[1]);
    }

    public static void main6(String[] args) {
        String[] s = new String[] { "", "asdf", "", "", null };
        List<String> list = new ArrayList<String>();
        for (String str : s) {
            if (str == null || "".equals(str.trim())) {
                continue;
            }
            list.add(str);
        }
        if (list.isEmpty()) {
            System.out.println("null");
        } else {
            System.out.println(list);
        }

    }

    public static void main5(String[] args) {
        String ss = "用户关系\n信(息（这里只存一些";
        System.out.println(ss.split("[（( \n]")[0]);
    }

    public static <T> List<T> getT(T... arg) {
        return null;
    }

    public static void main4(String[] args) {
        tt("asdfasdf", "asdfasd");
    }

    public static void tt(String... aa) {
        System.out.println(Arrays.toString(aa));
    }

    public static void main3(String[] args) throws UnsupportedEncodingException {
        byte[] b = new byte[] { 116, 101, 115, 116, 67, 97, 99, 104, 101, 58, 84, 101, 115, 116, 83, 101, 114, 118, 105,
                99, 101, 73, 109, 112, 108, 46, 100, 101, 108, 101, 116, 101, 66, 121, 73, 100, 40, 54, 41 };
        // byte[] b = new byte[]{116, 101, 115, 116, 67, 97, 99, 104, 101, 58};

        Charset charset = Charset.forName("UTF8");

        String s = new String(b, charset);

        System.out.println(s);
    }

    public static void main2(String[] args) {
        String auth = "123123  asdf12kasdj12312jasdf";
        String[] auths = auth.split("  ", 2);
        for (String s : auths) {
            System.out.println(s);
        }
    }
}
