package com.wwh.test.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 命令行工具
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * wangwh 	1.0  		2019年3月28日 	Created
 * </pre>
 * 
 * @author wwh
 * @since 1.
 */
public class CommondLineUtil {

    public static String formatDate(Date d) {
        if (d == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }

    public static Date getDate(BufferedReader br, String message, Date defaultValue) throws IOException {

        System.out.println(message);
        System.out.println("日期格式：yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss");
        System.out.println("默认值：" + formatDate(defaultValue));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < 3; i++) {
            String dateStr = br.readLine();
            if ("".equals(dateStr)) {
                return defaultValue;
            }
            try {
                if (dateStr.contains(":")) {
                    return sdf.parse(dateStr);
                } else {
                    return sdf2.parse(dateStr);
                }
            } catch (ParseException e) {
                System.out.println("日期格式错误，请重新输入");
            }
        }

        return null;
    }

    public static int[] getIntegerArray(BufferedReader br, String message) throws IOException {

        System.out.println(message);

        for (int i = 0; i < 3; i++) {

            String deviceIdsStr = br.readLine();

            if (StringUtils.isEmpty(deviceIdsStr)) {
                System.out.println("输入错误，请重新输入");
                continue;
            }

            String[] idArrayStr = deviceIdsStr.split(",");
            int[] idArray = new int[idArrayStr.length];

            for (int k = 0; k < idArrayStr.length; k++) {
                String idStr = idArrayStr[k];

                if (StringUtils.isNumeric(idStr)) {
                    idArray[k] = Integer.parseInt(idStr);
                } else {
                    System.out.println("输入错误，请重新输入");
                    continue;
                }
            }

            return idArray;
        }
        return null;
    }

    public static Integer getInteger(BufferedReader br, String message) throws IOException {

        System.out.println(message);

        for (int i = 0; i < 3; i++) {
            String deviceIdStr = br.readLine();

            if (StringUtils.isNumeric(deviceIdStr)) {
                return Integer.parseInt(deviceIdStr);
            } else {
                System.out.println("输入错误，请重新输入");
            }
        }
        return null;
    }

    public static File getFile(BufferedReader br, String message) throws IOException {
        System.out.println(message);

        for (int i = 0; i < 3; i++) {
            String filePath = br.readLine();

            if (StringUtils.isBlank(filePath)) {
                System.out.println("输入错误，请重新输入");
            } else {
                // 判断文件是否存在
                File f = new File(filePath);
                if (f.exists()) {
                    return f;
                } else {
                    System.out.println("文件不存在，请重新输入");
                }
            }

        }
        return null;

    }
}
