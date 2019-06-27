package com.wwh.test.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于读取CSV文件
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * wangwh 	1.0  		2019年4月9日 	Created
 * </pre>
 * 
 * @author wwh
 * @since 1.
 */
public class CSVFileReadUtil {

    /**
     * 分隔符号
     */
    public static final String SEPARATOR = ",";

    /**
     * 默认编码
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * <pre>
     * 读取CSV文件中的列
     * 如果某一行没有该列，会在list中添加一个null
     * 使用默认的分隔符
     * </pre>
     * 
     * @param csvFile CSV文件
     * @param charset 文件编码
     * @param column 要读取的列，从0开始
     * @param skipRow 要跳过的行，从1开始，如要跳过第一行，则为1，跳过前面两行，则为2
     * @return
     * @throws IOException
     */
    public static List<String> readCsvFileColumn(File csvFile, String charset, int column,
                                                 int skipRow) throws IOException {
        return readCsvFileColumn(csvFile, charset, column, skipRow, CSVFileReadUtil.SEPARATOR);
    }

    /**
     * <pre>
     * 读取CSV文件中的列
     * 如果某一行没有该列，会在list中添加一个null
     * </pre>
     * 
     * @param csvFile CSV文件
     * @param charset 文件编码
     * @param column 要读取的列，从0开始
     * @param skipRow 要跳过的行，从1开始，如要跳过第一行，则为1，跳过前面两行，则为2
     * @param separator 文件分隔符号
     * @return
     * @throws IOException
     */
    public static List<String> readCsvFileColumn(File csvFile, String charset, int column, int skipRow,
                                                 String separator) throws IOException {
        if (csvFile == null || !csvFile.exists()) {
            throw new IllegalArgumentException("CSV文件错误");
        }

        Charset charsetObj = Charset.forName(charset);

        if (separator == null) {
            throw new IllegalArgumentException("文件分隔符号错误");
        }
        List<String> list = new ArrayList<>();
        try (BufferedReader bufReader = new BufferedReader(
            new InputStreamReader(new FileInputStream(csvFile), charsetObj))) {

            String line;
            int lineIndex = 0;
            while ((line = bufReader.readLine()) != null) {
                lineIndex++;
                if (lineIndex <= skipRow) {
                    continue;
                }

                String[] colArray = line.split(separator);
                if (colArray.length < column) {
                    list.add(null);
                } else {
                    list.add(colArray[column]);
                }
            }
        }
        return list;
    }

}
