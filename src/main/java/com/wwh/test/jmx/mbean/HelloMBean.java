package com.wwh.test.jmx.mbean;

public interface HelloMBean {

	public String getName();

	public void setName(String name);

	public void printHello();

	public void printHello(String whoName);
}
