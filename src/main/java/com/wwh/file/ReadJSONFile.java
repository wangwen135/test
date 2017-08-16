package com.wwh.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ReadJSONFile {

    public static void main(String[] args) throws IOException {

        File worldFile = new File("D:\\temp\\world_605kb.json");

        File countryFile = new File("D:\\temp\\country_sort_key.json");

        countryFile.createNewFile();

        FileInputStream fs = new FileInputStream(worldFile);

        FileOutputStream out = new FileOutputStream(countryFile);

        BufferedReader bufReader = new BufferedReader(new InputStreamReader(fs));

        BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(out));

        // 去掉第一行
        String line = bufReader.readLine();
        JSONObject json, properties;

        int i = 0;

        Map<String, String> map = new TreeMap<>();

        Map<String, String> postalMap = new TreeMap<>();

        while ((line = bufReader.readLine()) != null) {
            // System.out.println(line);

            i++;

            if (i == 253) {
                line = line.substring(0, line.length() - 2);
            } else {
                line = line.substring(0, line.length() - 1);
            }

            json = JSON.parseObject(line);

            properties = json.getJSONObject("properties");

            System.out.println(properties.getString("SOVEREIGNT") + "    " + properties.getString("SOV_A3"));

            map.put(properties.getString("SOVEREIGNT"), properties.getString("SOV_A3"));

            postalMap.put(properties.getString("SOV_A3"), properties.getString("SOVEREIGNT"));
        }

        bufReader.close();

        System.out.println(map.size());
        System.out.println(map);

        System.out.println(postalMap.size());
        System.out.println(postalMap);

        for (Map.Entry<String, String> entry : map.entrySet()) {

            bufWriter.write(entry.getValue());

            bufWriter.newLine();

            bufWriter.flush();

        }

        bufWriter.close();
    }
}
