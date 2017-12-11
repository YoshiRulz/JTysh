package io.github.yoshirulz.jtysh.strtransform;

/**
 * @author YoshiRulz
 * @version 2017-12-11/00
 */
public enum StringSplit { ;
	public static String[] on(String separator, String s) {
		return s.split(separator);
	}
	public static String[] onNewlines(String s) {
		return on("\n", s);
	}
	public static String[] onSpaces(String s) {
		return on(" ", s);
	}
}
