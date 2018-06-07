package com.wwh.log.Appender;

import static com.wwh.log.Appender.MyCacheAppender.DEFAULT_LINE;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 单例输出流实现
 * 会缓存指定行数的数据
 * 通过静态方法 getAllCacheString 获取缓存的数据
 * </pre>
 * 
 * @author wwh
 * @date 2017年10月28日 下午3:02:45
 */
public class CacheQueueStream extends OutputStream {

    private static CacheQueueStream instance;

    private CacheQueue cacheQueue;

    private CacheQueueStream() {
        cacheQueue = new CacheQueue(DEFAULT_LINE);
    }

    /**
     * 获取实例
     * 
     * @return
     */
    public synchronized static CacheQueueStream getInstance() {
        if (instance == null) {
            instance = new CacheQueueStream();
        }
        return instance;
    }

    /**
     * 获取缓存的数据
     * 
     * @return
     */
    public static List<String> getAllCacheString() {
        if (instance == null) {
            return new ArrayList<>();
        } else {
            return instance.getCacheQueue().getAll();
        }
    }

    /**
     * 设置缓存的数据行数
     * 
     * @param maxLine
     */
    public void setMaxLine(int maxLine) {
        cacheQueue.setMaxLine(maxLine);
    }

    public CacheQueue getCacheQueue() {
        return cacheQueue;
    }

    public void close() {// 这个是无需关闭的
    }

    public void flush() {// flush也不需要

    }

    @Override
    public void write(final byte[] b) throws IOException {
        cacheQueue.add(new String(b));
    }

    public void write(final byte[] b, final int off, final int len) throws IOException {
        cacheQueue.add(new String(b, off, len));
    }

    public void write(final int b) throws IOException {
        cacheQueue.add(b + "");
    }

}
