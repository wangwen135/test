package com.wwh.regex;

public class RegexTest {

    public static void main(String[] args) {
        String url = "http://deepdot*35wvmeyd5.onion/questions/**/8?/*/favorite/form";

        //String[] sp = url.split("[\\W[^*?]]+");
        String[] sp = url.split("[^\\w*?]+");
        
        for (String string : sp) {
            System.out.println(string);
        }
    }
}
