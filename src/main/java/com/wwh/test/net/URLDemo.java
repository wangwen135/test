package com.wwh.test.net;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLDemo {
    public static void main(String[] args) {

        String url = "://www.runoob.com/index.html?language=cn#j2se";

        String spec2 = "ftp://xxd.runoob.com/index.html?language=cn#j2se";

         printURL(url);

        // System.out.println(getTopUrl(spec2));

        // getTopUrl2();
    }

    private static void printURL(String spec) {
        try {

            URL url = new URL(spec);
            System.out.println("URL 为：" + url.toString());
            System.out.println("协议为：" + url.getProtocol());
            System.out.println("验证信息：" + url.getAuthority());
            System.out.println("文件名及请求参数：" + url.getFile());
            System.out.println("主机名：" + url.getHost());
            System.out.println("路径：" + url.getPath());
            System.out.println("端口：" + url.getPort());
            System.out.println("默认端口：" + url.getDefaultPort());
            System.out.println("请求参数：" + url.getQuery());
            System.out.println("定位位置：" + url.getRef());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getTopUrl2() {
        String s = "Http://topic.csdn.net/u/20120604/22/2479ec15-887a-4a7f-9ca6-042d37214302.html";
        Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
        Matcher m = p.matcher(s);
        if (m.find()) {
            System.out.println(m.group());
        }
    }

    /**
     * 提取主域名
     */
    public static String getTopUrl(String url) {
        String regex = "(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)";
        System.out.println(regex);

        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(url);
        matcher.find();
        // System.out.println(matcher.group());
        return matcher.group();
    }

    public static boolean isURL2(String str) {
        String regex = "^((https|http|ftp|rtsp|mms)?://)" + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
                // + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,4})?" // 端口- :80
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        // return match(regex, str);
        return false;

    }
}
