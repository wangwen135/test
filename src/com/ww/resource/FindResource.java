package com.ww.resource;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindResource {
	
	public static void main(String[] args) {
		//找到类路径下所有的xml文件
		
		List<String> list = loadResource(Pattern.compile("[\\s\\S]*.xml"));
		for (String string : list) {
			System.out.println(string);
		}
	}

	public static List<String> loadResource(Pattern pattern) {
		ArrayList<String> arraylist = new ArrayList<String>();
		getClassInClassPath(arraylist, pattern);
		return arraylist;
	}

	private static void getClassInClassPath(Collection<String> collection,
			Pattern pattern) {
		String s = System.getProperty("java.class.path");
		s = s.replace(':', ';');
		String as[] = s.split(";");
		String as1[] = as;
		int i = as1.length;
		for (int j = 0; j < i; j++) {
			String s1 = as1[j];
			if (isJar(s1))
				getClassInJarFile(collection, pattern, s1);
			else
				getClassInDirectory(collection, pattern, s1);
		}

	}

	private static boolean isJar(String s) {
		int i = s != null ? s.length() : 0;
		if (i > 4)
			return Character.toLowerCase(s.charAt(--i)) == 'r'
					&& Character.toLowerCase(s.charAt(--i)) == 'a'
					&& Character.toLowerCase(s.charAt(--i)) == 'j'
					&& Character.toLowerCase(s.charAt(--i)) == '.';
		else
			return false;
	}

	private static void getClassInDirectory(Collection<String> collection,
			Pattern pattern, String s) {
		File file = null;
		if (s != null && (file = new File(s)).exists()) {
			int i = file.toString().length() + 1;
			getClassInDirectory(i, file, collection, pattern);
		}
	}

	private static void getClassInDirectory(int i, File file,
			Collection<String> collection, Pattern pattern) {
		File afile[] = file.listFiles();
		File afile1[] = afile;
		int j = afile1.length;
		for (int k = 0; k < j; k++) {
			File file1 = afile1[k];
			if (file1.isDirectory()) {
				getClassInDirectory(i, file1, collection, pattern);
				continue;
			}
			String s = file1.getPath();
			s = s.substring(i).replace('\\', '/');
			Matcher matcher = pattern.matcher(s);
			if (matcher.matches())
				collection.add(s);
		}

	}

	private static void getClassInJarFile(Collection<String> collection,
			Pattern pattern, String s) {
		try {
			JarFile jarfile = new JarFile(s);
			Enumeration<JarEntry> enumeration = jarfile.entries();
			do {
				if (!enumeration.hasMoreElements())
					break;
				String s1 = ((JarEntry) enumeration.nextElement()).getName()
						.replace('\\', '/');
				Matcher matcher = pattern.matcher(s1);
				if (matcher.matches())
					collection.add(s1);
			} while (true);
		} catch (Exception exception) {
		}
	}
}
