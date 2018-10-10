package com.wwh.test.common.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

/**
 * <pre>
 * 这个是无法复制
 * </pre>
 *
 * @author wangwh
 * @date 2018年10月8日上午10:23:06
 * 
 */
public class CopyPropertiesTest {
	public static void main(String[] args) {
		Person p = new Person();
		p.setName("aaa");
		p.setAge(11);
		p.setBirth(new Date());

		System.out.println(p.toString());

		Person p2 = new Person();

		try {
			BeanUtils.copyProperties(p2, p);

			System.out.println(p2.toString());

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}

class Person {
	private String name;
	private int age;
	private Date birth;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [name=");
		builder.append(name);
		builder.append(", age=");
		builder.append(age);
		builder.append(", birth=");
		builder.append(birth);
		builder.append("]");
		return builder.toString();
	}

}