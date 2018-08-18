package com.wwh.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		System.out.println(formatDate(clearTime(new Date())));

		Date d2 = parseDate("2018-5-16 10:20:10");

		System.out.println(formatDate(clearTime(d2)));
	}

	public static Date parseDate(String dateStr) {
		try {
			return dateFormat2.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String formatDate(Date date) {
		return dateFormat2.format(date);
	}

	/**
	 * @param date
	 * @return
	 */
	public static Date clearTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.clear(Calendar.MINUTE);
		c.clear(Calendar.SECOND);
		c.clear(Calendar.MILLISECOND);

		// 清空月份中的日期
		// c.clear(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, 1);

		return c.getTime();
	}
}
