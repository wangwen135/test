package com.wwh.test.management;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author wwh
 * @date 2015年7月25日 下午4:09:49
 *
 */
public class GetPid {

    /**
     * @param args
     */
    public static void main(final String[] args) {
        final RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        final String info = runtime.getName();
        final int index = info.indexOf("@");
        if (index != -1) {
            final int pid = Integer.parseInt(info.substring(0, index));
            System.out.println(info);
            System.out.println(pid);
        }
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
