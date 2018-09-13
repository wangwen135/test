package com.wwh.test.jmx.mbean;

/**
 * <pre>
 * 需要遵循MBean约定
 * 
 * Standard MBean 或者 MXBean 
 * </pre>
 *
 * @author wangwh
 * @date 2018年9月13日下午3:40:03
 * 
 */
public interface WwhMXBean {

	public String getThreadName();

	void setMsg(String msg);

	String getMsg();

	public String printMsg();
}
