package com.wwh.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTest {
    

	public static void main1(String[] args) {
		long time = 1468216800000L;
		Date d = new Date(time);
		System.out.println(d.toLocaleString());
	}
	
	public static void main(String[] args) {
		Long l = 1278511200000l;
		Date d = new Date(l);

		System.out.println(d);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(d));
		
		try {
			Date d2 = sdf.parse("2016-07-11 14:00:00");
			System.out.println(d2.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
