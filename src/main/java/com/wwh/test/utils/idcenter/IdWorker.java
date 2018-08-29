
package com.wwh.test.utils.idcenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * from
 * https://github.com/twitter/snowflake/blob/master/src/main/scala/com/twitter/
 * service/snowflake/IdWorker.scala
 * 
 * <pre>
 * 时间戳减去一个基准时间
 * 左移22位
 * datacenterId 占5位
 * workerId 占5位
 * 序列占12位，最大0xFFF，一毫秒最多可以产生4095个ID
 * </pre>
 *
 * @author adyliu (imxylz@gmail.com)
 * @author wwh
 * @since 1.0
 */
public class IdWorker {

    private final long workerId;
    private final long datacenterId;
    private final long idepoch;

    private static final long workerIdBits = 5L;
    private static final long datacenterIdBits = 5L;
    private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    private static final long sequenceBits = 12L;
    private static final long workerIdShift = sequenceBits;
    private static final long datacenterIdShift = sequenceBits + workerIdBits;
    private static final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    public static final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;
    private long sequence;
    private static final Random r = new Random();

    /**
     * 固定的基准时间戳：2016-07-11 14:00:00
     */
    private static final long baseTime = 1468216800000L;

    public IdWorker() {
        this(baseTime);
    }

    public IdWorker(long idepoch) {
        this(r.nextInt((int) maxWorkerId), r.nextInt((int) maxDatacenterId), 0, idepoch);
    }

    public IdWorker(long workerId, long datacenterId, long sequence) {
        this(workerId, datacenterId, sequence, baseTime);
    }

    //
    public IdWorker(long workerId, long datacenterId, long sequence, long idepoch) {
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
        this.idepoch = idepoch;
        if (workerId < 0 || workerId > maxWorkerId) {
            throw new IllegalArgumentException("workerId is illegal: " + workerId);
        }
        if (datacenterId < 0 || datacenterId > maxDatacenterId) {
            throw new IllegalArgumentException("datacenterId is illegal: " + workerId);
        }
        if (idepoch >= System.currentTimeMillis()) {
            throw new IllegalArgumentException("idepoch is illegal: " + idepoch);
        }
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    public long getWorkerId() {
        return workerId;
    }

    public long getTime() {
        return System.currentTimeMillis();
    }

    public long getId() {
        long id = nextId();
        return id;
    }

    private synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new IllegalStateException("Clock moved backwards.");
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        long id = ((timestamp - idepoch) << timestampLeftShift)//
                | (datacenterId << datacenterIdShift)//
                | (workerId << workerIdShift)//
                | sequence;
        return id;
    }

    /**
     * get the timestamp (millis second) of id
     * 
     * @param id
     *            the nextId
     * @return the timestamp of id
     */
    public long getIdTimestamp(long id) {
        return idepoch + (id >> timestampLeftShift);
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IdWorker{");
        sb.append("workerId=").append(workerId);
        sb.append(", datacenterId=").append(datacenterId);
        sb.append(", idepoch=").append(idepoch);
        sb.append(", lastTimestamp=").append(lastTimestamp);
        sb.append(", sequence=").append(sequence);
        sb.append('}');
        return sb.toString();
    }

    public static void main(String[] args) {
        Date nowTime = new Date();
        System.out.println(nowTime.getTime());
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        // 1468216800000
        // 1403125941024
        // 5471342741024
        System.out.println(timeFormat.format(2871342741024l));
        
        //大概到 2031-12-31 23:40:49:999 不会超过15位数字
        System.out.println(timeFormat.format(1468216800000l + 488281249999L));

        try {
            System.out.println("只要处理掉这个就行了：" + timeFormat.parse("2026-12-27 11:12:21:024").getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
