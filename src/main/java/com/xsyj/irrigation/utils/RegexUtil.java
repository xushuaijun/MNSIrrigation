package com.xsyj.irrigation.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	public static boolean check(String source, String regex) {

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(source);
		return m.matches();

	}

}
