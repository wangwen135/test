package com.wwh.test.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;

/**
 * 按照最简单的方法来，不使用CSV相关的工具包
 * 
 * @version
 * 
 * <pre>
 * Author	Version		Date		Changes
 * wangwh 	1.0  		2019年3月26日 	Created
 * </pre>
 * 
 * @author wwh
 * @since 1.
 */
public class CSVFileOutputUtil {

    /**
     * 分隔符号
     */
    public static final String SEPARATOR = ",";

    /**
     * 默认编码
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    private File outFile;

    private boolean isOpen = false;

    private PrintWriter out;

    private Charset charset;

    /**
     * 是否追加文件
     */
    private boolean append = false;

    /**
     * 构造方法
     * 
     * @param dir 文件目录
     * @param fileName 文件名
     * @param charset 文件编码
     * @param append 是否追加文件
     */
    public CSVFileOutputUtil(File dir, String fileName, String charset, boolean append){

        this.append = append;

        if (dir == null || !dir.isDirectory()) {
            throw new IllegalArgumentException("生成文件的目录错误");
        }
        dir.mkdirs();
        if (!StringUtils.endsWithIgnoreCase(fileName, ".csv")) {
            fileName = fileName + ".CSV";
        }

        this.charset = Charset.forName(charset);

        // 重复文件不覆盖
        outFile = new File(dir, fileName);
        if (outFile.exists() && !append) {
            // 文件存在并且非追加模式
            fileName = fileName.substring(0, fileName.length() - 4);
            fileName = fileName + "-" + System.currentTimeMillis() + ".CSV";
            outFile = new File(dir, fileName);
        }
    }

    /**
     * 构造方法<br>
     * 如果文件存在会生成一个新的文件
     * 
     * @param dir 文件目录
     * @param fileName 文件名
     * @param charset 文件编码
     */
    public CSVFileOutputUtil(File dir, String fileName, String charset){
        this(dir, fileName, charset, false);
    }

    public CSVFileOutputUtil(File dir, String fileName){
        this(dir, fileName, DEFAULT_CHARSET);
    }

    public void open() throws IOException {
        if (isOpen) {
            throw new RuntimeException("流已经开启了");
        }
        if (!outFile.exists()) {
            outFile.createNewFile();
        }
        out = new PrintWriter(
            new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile, append), charset)));
        isOpen = true;
    }

    public void writeContentLine(String contentLine) {
        if (!isOpen) {
            throw new RuntimeException("写入内容前需要先开启流");
        }
        out.println(contentLine);
    }

    /**
     * 写入一行内容，并立即刷新
     * 
     * @param contentLine
     */
    public void writeContentLineAndFlush(String contentLine) {
        if (!isOpen) {
            throw new RuntimeException("写入内容前需要先开启流");
        }
        out.println(contentLine);
        out.flush();
    }

    public void close() {
        if (!isOpen) {
            throw new RuntimeException("并没有开启");
        }
        out.flush();
        out.close();
    }

    /**
     * 转义
     * 
     * @param str
     * @return
     */
    public static String escape(String str) {

        if (StringUtils.isEmpty(str)) {
            return str;
        }

        if (str.contains(",")) {

            // 有双引号的替换成两个
            if (str.contains("\"")) {
                str = str.replaceAll("\"", "\"\"");
            }

            // 有逗号的加上双引号
            return "\"" + str + "\"";
        }

        return str;
    }

    public static void main(String[] args) {
        System.out.println(escape("123123"));
        System.out.println(escape("123,123"));
        System.out.println(escape("123\"123"));
        System.out.println(escape("123\"1,23"));
    }

}
