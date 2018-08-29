package com.wwh.test.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

    public static void main(String[] args) {
        // 校验URL是否正确
        String regex = "((https?|ftp|file)://)?[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";

        Pattern pattern = Pattern.compile(regex);

        String url = "http://kz77lt2wvpmgzedq.onion/register.php";
        Matcher mc = pattern.matcher(url);
        System.out.println( mc.matches());
    }

    public static void main2(String[] args) {
        String url = "http://deepdot*35wvmeyd5.onion/questions/**/8?/*/favorite/form";

        // String[] sp = url.split("[\\W[^*?]]+");
        String[] sp = url.split("[^\\w*?]+");

        for (String string : sp) {
            System.out.println(string);
        }
    }
}
