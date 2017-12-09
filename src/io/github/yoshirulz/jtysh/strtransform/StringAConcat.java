package io.github.yoshirulz.jtysh.strtransform;

import java.util.StringJoiner;

/**
 * @author YoshiRulz
 * @version 2017-12-03/00
 */
public enum StringAConcat { ;
	public static String with(String separator, String[] a) {
		StringJoiner sj = new StringJoiner(separator);
		for (String s : a) sj.add(s);
		return sj.toString();
	}
	public static String withNewlines(String[] a) {
		return with("\n", a);
	}
	public static String withSpaces(String[] a) {
		return with(" ", a);
	}
}
