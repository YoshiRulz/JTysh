package io.github.yoshirulz.jtysh.uris;

import io.github.yoshirulz.jtysh.strtransform.StringAConcat;
import io.github.yoshirulz.jtysh.uris.URIHandler.URICustomHandler;

import static io.github.yoshirulz.jtysh.uris.URIHandler.URIProtocol.UNKNOWN;
import static io.github.yoshirulz.jtysh.uris.URIHandler.URIProtocol.parseString;

/**
 * @author YoshiRulz
 * @version 2017-12-10/00
 */
@SuppressWarnings({"ClassNamingConvention", "HardCodedStringLiteral", "WeakerAccess", "unused"})
public class URI {
	private static final String HANDLE_SEP = "://";
	private static final String LOC_SEP = "/";
	private static final int SB_CAP = 6;

	private final URIHandler handler;
	private URIDomain fullDomain;
	private final String[] location;

	@SuppressWarnings("MethodCanBeVariableArityMethod")
	public URI(URIHandler handler, URIDomain domain, String[] location) {
		this.handler = handler;
		fullDomain = domain;
		this.location = location.clone();
	}

	public URI(String uri) {
		String[] splitURI = uri.split(LOC_SEP);
		if (splitURI[1].isEmpty() && splitURI[0].endsWith(":")) {
			String temp = splitURI[0].substring(0, splitURI[0].length() - 1);
			URIHandler temp1 = parseString(temp);
			fullDomain = URIDomain.parseString(splitURI[2]);
			handler = temp1 == UNKNOWN ? new URICustomHandler(temp, fullDomain.port, true) : temp1;
			location = new String[splitURI.length - 3];
			System.arraycopy(splitURI, 3, location, 0, location.length);
		} else if (splitURI[0].contains(":")) {
			String[] temp = splitURI[0].split(":");
			fullDomain = new URIDomain("oops"); //TODO
			handler = new URICustomHandler(temp[0], fullDomain.port, false);
			location = new String[]{"oops"}; //TODO
		} else {
			fullDomain = URIDomain.parseString(splitURI[0]);
			handler = UNKNOWN;
			location = new String[splitURI.length - 1];
			System.arraycopy(splitURI, 1, location, 0, location.length);
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(SB_CAP);
		if (handler != null) sb.append(handler.getHandle()).append(HANDLE_SEP);
		if (fullDomain.port != handler.getDefaultPort()) sb.append(fullDomain);
		else sb.append(fullDomain.toString().split(":")[0]); //TODO simplify?
		sb.append(LOC_SEP).append(StringAConcat.with(LOC_SEP, location));
		return sb.toString();
	}

	public void reauth(String user, String pass) {
		fullDomain = new URIDomain(user, pass, fullDomain.domain, fullDomain.port);
	}
	public void reauth(String pass) {
		reauth(fullDomain.user, pass);
	}

	public URIHandler getHandler() {
		return handler;
	}

	public URIDomain getFullDomain() {
		return fullDomain;
	}

	public String[] getLocation() {
		return location.clone();
	}

	public String getFilename() {
		return location[location.length - 1];
	}
}
