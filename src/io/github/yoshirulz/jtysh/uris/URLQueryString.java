package io.github.yoshirulz.jtysh.uris;

import java.util.StringJoiner;

/**
 * @author YoshiRulz
 * @version 2017-11-19/00
 */
public class URLQueryString {
	private static final String QUERY_SEP = "&";
	private static final String VALUE_SEP = "=";

	private final String[] keys;
	private final String[] values;

	public URLQueryString(String[] keys, String[] values) {
		this.keys = keys.clone();
		this.values = values.clone();
	}

	/**
	 * @param s Querystring w/o leading "?"
	 */
	public URLQueryString(String s) {
		String[] temp = s.split(QUERY_SEP);
		keys = new String[temp.length];
		values = new String[temp.length];
		for (int i = 0; i < temp.length; i++) {
			String[] temp1 = temp[i].split(VALUE_SEP);
			keys[i] = temp1[0];
			@SuppressWarnings("ObjectAllocationInLoop") StringJoiner sj = new StringJoiner(VALUE_SEP);
			for (int j = 1; j < temp1.length; j++) sj.add(temp1[j]);
			values[i] = sj.toString();
		}
	}

	public int length() {
		return keys.length;
	}

	@SuppressWarnings("StringConcatenation")
	public String toString() {
		StringJoiner sj = new StringJoiner(QUERY_SEP);
		for (int i = 0; i < keys.length; i++) sj.add(keys[i] + VALUE_SEP + values[i]);
		return sj.toString();
	}
}
