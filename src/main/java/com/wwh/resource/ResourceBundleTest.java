package com.wwh.resource;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ResourceBundleTest {
	public static void main(String[] args) {

		ResourceBundle prb = PropertyResourceBundle.getBundle("tt", Locale.US);
		String s = prb.getString("s1");
		System.out.println(s);

	}

}
