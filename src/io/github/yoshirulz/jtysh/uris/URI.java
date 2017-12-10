package io.github.yoshirulz.jtysh.uris;

import io.github.yoshirulz.jtysh.uris.URIHandler.URICustomHandler;

import java.util.StringJoiner;

import static io.github.yoshirulz.jtysh.uris.URIHandler.URIProtocol.UNKNOWN;
import static io.github.yoshirulz.jtysh.uris.URIHandler.URIProtocol.parseString;

/**
 * @author YoshiRulz
 * @version 2017-12-10/00
 */
@SuppressWarnings("HardCodedStringLiteral")
public class URI {
	public final URIHandler handler;
	private URIDomain fullDomain;
	public final String[] location;
	protected String filename;

	public URI(URIHandler handler, URIDomain domain, String[] location) {
		this.handler = handler;
		fullDomain = domain;
		this.location = location;
	}

	public URI(String uri) {
		String[] splitURI = uri.split("/");
		if ("".equals(splitURI[1]) && splitURI[0].endsWith(":")) {
			String temp = splitURI[0].substring(0, splitURI[0].length() - 1);
			URIHandler temp1 = parseString(temp);
			fullDomain = URIDomain.parseString(splitURI[2]);
			if (temp1 == UNKNOWN) handler = new URICustomHandler(temp, getFullDomain().port, true);
			else handler = temp1;
			location = new String[splitURI.length - 3];
			for (int i = 0; i < location.length; i++) location[i] = splitURI[i + 3];
		} else if (splitURI[0].contains(":")) {
			String[] temp = splitURI[0].split(":");
			fullDomain = new URIDomain("oops"); //TODO
			handler = new URICustomHandler(temp[0], getFullDomain().port, false);
			location = new String[]{"oops"}; //TODO
		} else {
			fullDomain = URIDomain.parseString(splitURI[0]);
			handler = null;
			location = new String[splitURI.length - 1];
			for (int i = 0; i < location.length; i++) location[i] = splitURI[i + 1];
		}
	}

	public String getFilename() {
		if (filename == null) filename = location[location.length - 1];
		return filename;
	}

	public String toString() {
		StringJoiner sj = new StringJoiner("/");
		for (String s : location) sj.add(s);
		return (handler == null ? "" : handler.getHandle() + "://") + (fullDomain.port != handler.getDefaultPort() ? fullDomain : fullDomain.toString().split(":")[0]) + "/" + sj;
	}

	public URIDomain getFullDomain() {
		return fullDomain;
	}

	public void auth(String user, String pass) {
		fullDomain = new URIDomain(user, pass, fullDomain.domain, fullDomain.port);
	}
	public void auth(String pass) {
		auth(fullDomain.user, pass);
	}
}
