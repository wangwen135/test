package com.wwh.test.common.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

public class PublicPerson {

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
		builder.append("PublicPerson [name=");
		builder.append(name);
		builder.append(", age=");
		builder.append(age);
		builder.append(", birth=");
		builder.append(birth);
		builder.append("]");
		return builder.toString();
	}

	public static void main(String[] args) {

		PublicPerson p = new PublicPerson();
		p.setName("aaa");
		p.setAge(11);
		// p.setBirth(new Date());

		System.out.println(p.toString());

		PublicPerson p2 = new PublicPerson();

		try {
			// 处理date为null的问题
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);

			BeanUtils.copyProperties(p2, p);

			System.out.println(p2.toString());

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}
}
