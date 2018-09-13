package com.wwh.test.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.alibaba.fastjson.JSON;

public class CalendarTest {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	
	public static void main(String[] args) {
		
		Date d = new Date();
		
		String s =JSON.toJSONString(d);
		System.out.println(s);
				
		Calendar cd = Calendar.getInstance();
		
		System.out.println(cd.get(Calendar.HOUR_OF_DAY));
		
	}
	
	public static void main2(String[] args) {
		Calendar cd = Calendar.getInstance();
		
		System.out.println(sdf.format(cd.getTime()));
		
		cd.set(Calendar.DAY_OF_MONTH, 1);
		cd.set(Calendar.HOUR_OF_DAY, 0);
		cd.set(Calendar.MINUTE, 0);
		cd.set(Calendar.SECOND, 0);
		cd.set(Calendar.MILLISECOND, 0);
		
		System.out.println(sdf.format(cd.getTime()));
		
		cd.add(Calendar.MONTH, -1);
		
		System.out.println(sdf.format(cd.getTime()));

		cd.add(Calendar.MONTH, 13);
		
		System.out.println(sdf.format(cd.getTime()));
	}
}
