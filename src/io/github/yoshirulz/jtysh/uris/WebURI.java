package io.github.yoshirulz.jtysh.uris;

import io.github.yoshirulz.jtysh.uris.URIHandler.URIProtocol;
import io.github.yoshirulz.jtysh.uris.URIThrowables.InvalidURICastException;

import java.util.StringJoiner;

import static io.github.yoshirulz.jtysh.uris.URIHandler.URIProtocol.HTTP;
import static io.github.yoshirulz.jtysh.uris.URIHandler.URIProtocol.HTTPS;

/**
 * @author YoshiRulz
 * @version 2017-12-10/00
 */
public class WebURI extends URI {
	public final String[] trueLocation;
	public final URLQueryString query;
	public final String target;

	public WebURI(URIDomain domain, String[] location, URLQueryString query, String target, boolean isHTTPS) {
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
			temp = temp1.split("#");
			sj = new StringJoiner("#");
			for (int i = 0; i < temp.length - 1; i++) sj.add(temp[i]);
			query = new URLQueryString(sj.toString());
			target = temp[temp.length - 1];
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

	private static String[] concatLocation(String[] location, URLQueryString query, String target) {
		String[] toReturn = location;
		if (query != null) toReturn[toReturn.length - 1] += query.toString();
		if (target != null) toReturn[toReturn.length - 1] += "#" + target;
		return toReturn;
	}

	public String getFilename() {
		filename = trueLocation[trueLocation.length - 1];
		return filename;
	}

	public WebURI asWebURI() {
		return this;
	}
}
