package com.wwh.release.swing.glasspanel;

import javax.swing.SwingWorker;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author wwh
 * @date 2015年9月6日 上午10:49:35
 *
 * @param <T>
 */
public abstract class MySwingWorker<T> extends SwingWorker<T, String> implements MessagePublisher {

    @Override
    public void publishMsg(java.lang.String... msg) {
        publish(msg);
    }
}
