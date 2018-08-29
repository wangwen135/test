package com.wwh.test.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadANSIFile {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        File f = new File("D:\\temp\\testANSI.us");
        FileInputStream fs = new FileInputStream(f);
        BufferedReader br = new BufferedReader(new InputStreamReader(fs, "GB18030"));
        String line;

        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        br.close();
    }
}
