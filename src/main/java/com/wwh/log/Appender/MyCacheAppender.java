package com.wwh.log.Appender;

import org.apache.log4j.Layout;
import org.apache.log4j.WriterAppender;

/**
 * <pre>
 * 测试日志用
 * </pre>
 * 
 * @author wwh
 * @date 2017年10月28日 下午2:39:52
 */
public class MyCacheAppender extends WriterAppender {

    public static final int DEFAULT_LINE = 100;

    protected int maxLine = DEFAULT_LINE;

    private CacheQueueStream cqStream;

    public MyCacheAppender() {
        cqStream = CacheQueueStream.getInstance();
    }

    public MyCacheAppender(Layout layout) {
        this(layout, DEFAULT_LINE);
    }

    public MyCacheAppender(Layout layout, int maxLine) {

        cqStream = CacheQueueStream.getInstance();

        setLayout(layout);
        setMaxLine(maxLine);

        activateOptions();
    }

    public int getMaxLine() {

        return maxLine;
    }

    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
        cqStream.setMaxLine(maxLine);
    }

    public void activateOptions() {

        setWriter(createWriter(cqStream));

        super.activateOptions();
    }

}
