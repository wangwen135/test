package com.wwh.test.jmx.mbean;

/**
 * <pre>
 * 需要遵循MBean约定
 * </pre>
 *
 * @author wangwh
 * @date 2018年9月13日下午3:40:03
 * 
 */
public class WwhBean implements WwhMXBean {

	private String msg;

	@Override
	public String getThreadName() {
		String tName = Thread.currentThread().getName();
		System.out.println(tName);
		return tName;
	}

	@Override
	public String printMsg() {
		System.out.println(msg);
		return msg;
	}

	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String getMsg() {
		return msg;
	}
}
