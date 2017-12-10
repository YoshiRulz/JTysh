package io.github.yoshirulz.jtysh.uris;

import io.github.yoshirulz.jtysh.uris.URIHandler.URIProtocol;
import io.github.yoshirulz.jtysh.uris.URIThrowables.InvalidURICastException;
import io.github.yoshirulz.jtysh.uris.URIThrowables.WebInvalidProtocolException;

import java.util.*;

import static io.github.yoshirulz.jtysh.uris.URIHandler.URIProtocol.HTTP;
import static io.github.yoshirulz.jtysh.uris.URIHandler.URIProtocol.HTTPS;
import static java.text.MessageFormat.format;

/**
 * @author YoshiRulz
 * @version 2017-12-10/00
 */
@SuppressWarnings({"ClassHasNoToStringMethod", "ClassNamingConvention"})
public class WebURI extends URI {
	private static final String KV_PAIR_SEP = "&";
	private static final String QUERY_SEP = "?";
	private static final String QUERY_SEP_PAT = "\\?";
	private static final String TARGET_SEP = "#";
	private static final String VALUE_SEP = "=";

	private static final String QUERY_FORMAT = "{0}={1}";
	private static final String TARGET_FORMAT = "#{0}";

	private final String[] trueLocation;
	private final Map<String, String> query;
	private final String target;

	public WebURI(URIDomain domain, String[] location, Map<String, String> query, String target, @SuppressWarnings("MethodParameterNamingConvention") boolean isHTTPS) {
		super(isHTTPS ? HTTPS : HTTP, domain, concatLocation(location, query, target));
		trueLocation = location.clone();
		this.query = query;
		this.target = target;
	}

	public WebURI(String s) {
		// Parse in super
		super(s); //TODO be smart
		if (getHandler() != HTTP && getHandler() != HTTPS) throw new WebInvalidProtocolException();
		String[] loc = super.getLocation();
		if (loc[loc.length - 1].contains(QUERY_SEP)) {
			String[] temp = loc[loc.length - 1].split(QUERY_SEP_PAT);
			trueLocation = new String[loc.length];
			System.arraycopy(loc, 0, trueLocation, 0, loc.length);
			trueLocation[trueLocation.length - 1] = temp[0];
			StringJoiner sj = new StringJoiner(QUERY_SEP);
			for (int i = 1; i < temp.length; i++) sj.add(temp[i]);
			String temp1 = sj.toString();
			String[] temp2 = temp1.split(TARGET_SEP);
			StringJoiner sj1 = new StringJoiner(TARGET_SEP);
			for (int i = 0; i < temp2.length - 1; i++) sj1.add(temp2[i]);
			String[] temp3 = s.split(KV_PAIR_SEP);
			query = new HashMap<>(temp3.length);
			setQueryFromStringA(temp3);
			target = temp2[temp2.length - 1];
		} else {
			trueLocation = null; //TODO
			query = null; //TODO
			target = null; //TODO
		}
	}

	private void setQueryFromStringA(String[] s) {
		for (String kvPairStr : s) {
			String[] kvPairArr = kvPairStr.split(VALUE_SEP);
			@SuppressWarnings("ObjectAllocationInLoop") StringJoiner sj = new StringJoiner(VALUE_SEP);
			for (int i = 1; i < kvPairArr.length; i++) sj.add(kvPairArr[i]);
			query.put(kvPairArr[0], sj.toString());
		}
	}

	public static WebURI fromURI(URI uri) {
		if (!(uri.getHandler() instanceof URIProtocol) ||
				uri.getHandler() != HTTP && uri.getHandler() != HTTPS)
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

	public String[] getLocation() {
		return trueLocation.clone();
	}

	public String getFilename() {
		return trueLocation[trueLocation.length - 1];
	}

	public String getQueryOrDefault(String key, String defaultValue) {
		return query.getOrDefault(key, defaultValue);
	}
	public String getQueryOrDefault(String key) {
		return getQueryOrDefault(key, "");
	}

	public String getTarget() {
		return target;
	}
}
