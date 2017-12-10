package io.github.yoshirulz.jtysh.uris;

import io.github.yoshirulz.jtysh.uris.URIHandler.URIProtocol;
import io.github.yoshirulz.jtysh.uris.URIThrowables.InvalidURICastException;

import java.util.*;

import static io.github.yoshirulz.jtysh.uris.URIHandler.URIProtocol.HTTP;
import static io.github.yoshirulz.jtysh.uris.URIHandler.URIProtocol.HTTPS;
import static java.text.MessageFormat.format;

/**
 * @author YoshiRulz
 * @version 2017-12-10/00
 */
public class WebURI extends URI {
	private static final String KV_PAIR_SEP = "&";
	private static final String VALUE_SEP = "=";
	private static final String QUERY_FORMAT = "{0}={1}";
	private static final String TARGET_FORMAT = "#{0}";

	public final String[] trueLocation;
	public final Map<String, String> query;
	public final String target;

	public WebURI(URIDomain domain, String[] location, Map<String, String> query, String target, boolean isHTTPS) {
		super(isHTTPS ? HTTPS : HTTP, domain, concatLocation(location, query, target));
		trueLocation = location;
		this.query = query;
		this.target = target;
	}

	public WebURI(String s) {
		// Parse in super
		super(s); //TODO be smart
		if (handler != HTTP && handler != HTTPS) throw new IllegalArgumentException("WebURIs must use http:// or https://!");
		if (location[location.length - 1].contains("?")) {
			String[] temp = location[location.length - 1].split("\\?");
			trueLocation = new String[location.length];
			System.arraycopy(location, 0, trueLocation, 0, location.length);
			trueLocation[trueLocation.length - 1] = temp[0];
			StringJoiner sj = new StringJoiner("?");
			for (int i = 1; i < temp.length; i++) sj.add(temp[i]);
			String temp1 = sj.toString();
			String[] temp2 = temp1.split("#");
			StringJoiner sj1 = new StringJoiner("#");
			for (int i = 0; i < temp2.length - 1; i++) sj1.add(temp2[i]);
			String[] temp3 = s.split(KV_PAIR_SEP);
			query = new HashMap<>(temp3.length);
			for (String kvPairStr : temp3) {
				String[] kvPairArr = kvPairStr.split(VALUE_SEP);
				@SuppressWarnings("ObjectAllocationInLoop") StringJoiner sj2 = new StringJoiner(VALUE_SEP);
				for (int i = 1; i < kvPairArr.length; i++) sj2.add(kvPairArr[i]);
				query.put(kvPairArr[0], sj2.toString());
			}
			target = temp2[temp2.length - 1];
		} else {
			trueLocation = null; //TODO
			query = null;
			target = null; //TODO
		}
	}

	public static WebURI fromURI(URI uri) throws InvalidURICastException {
		if (!(uri.handler instanceof URIProtocol) ||
				uri.handler != HTTP && uri.handler != HTTPS)
			throw new InvalidURICastException(uri, WebURI.class);
		return new WebURI(uri.toString()); //TODO be smart
	}

	private static String[] concatLocation(String[] location, Map<String, String> query, String target) {
		if (query != null) location[location.length - 1] += concatQuery(query);
		if (target != null) location[location.length - 1] += format(TARGET_FORMAT, target);
		return location;
	}

	private static String concatQuery(Map<String, String> query) {
		StringJoiner sj = new StringJoiner(KV_PAIR_SEP);
		query.forEach((key, value) -> sj.add(format(QUERY_FORMAT, key, value)));
		return sj.toString();
	}

	public String getFilename() {
		filename = trueLocation[trueLocation.length - 1];
		return filename;
	}

	public WebURI asWebURI() {
		return this;
	}

	public String getQueryOrDefault(String key, String defaultValue) {
		return query.getOrDefault(key, defaultValue);
	}
	public String getQueryOrDefault(String key) {
		return getQueryOrDefault(key, "");
	}
}
