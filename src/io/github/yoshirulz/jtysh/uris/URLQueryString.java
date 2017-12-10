package io.github.yoshirulz.jtysh.uris;

import java.util.StringJoiner;

/**
 * @author YoshiRulz
 * @version 2017-11-19/00
 */
public class URLQueryString {
	private final String[] keys;
	private final String[] values;

	private String raw;

	@SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
	public URLQueryString(String[] keys, String[] values) {
		this.keys = keys;
		this.values = values;
	}

	/**
	 * @param s Querystring w/o leading "?"
	 */
	public URLQueryString(String s) {
		String[] temp = s.split("&");
		keys = new String[temp.length];
		values = new String[temp.length];
		for (int i = 0; i < temp.length; i++) {
			String[] temp1 = temp[i].split("=");
			keys[i] = temp1[0];
			StringJoiner sj = new StringJoiner("=");
			for (int j = 1; j < temp1.length; j++) sj.add(temp1[j]);
			values[i] = sj.toString();
		}
	}

	public int length() {
		return keys.length;
	}

	public String toString() {
		StringJoiner sj = new StringJoiner("&");
		for (int i = 0; i < keys.length; i++) sj.add(keys[i] + "=" + values[i]);
		return sj.toString();
	}
}
