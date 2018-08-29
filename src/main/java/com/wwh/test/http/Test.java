package com.wwh.test.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class Test {

    public static void main(String[] args) throws IOException {
        
        Properties prop = System.getProperties();        

        // socks代理服务器的地址与端口        
        prop.setProperty("socksProxyHost", "118.193.225.166");        
        prop.setProperty("socksProxyPort", "9150");        
        
        URL url = new URL("http://foggeddriztrcar2.onion/");
        URLConnection urlc = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));

        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
    }
}
