package io.github.yoshirulz.jtysh.uris;

import static java.lang.Integer.parseInt;

/**
 * Now imagine how many constructors there'd be if this was all just part of `URI`.
 * @author YoshiRulz
 * @version 2017-11-19/00
 */
@SuppressWarnings({"ClassWithTooManyConstructors", "WeakerAccess"})
public class URIDomain {
	private static final String PASS_SEP = ":";
	private static final String PORT_SEP = ":";
	private static final int SB_CAP = 256;
	private static final String USER_SEP = "@";

	public final String user;
	public final String password;
	public final String domain;
	public final int port;

	public URIDomain(String user, String password, String domain, int port) {
		this.user = user;
		this.password = password;
		this.domain = domain;
		this.port = port;
	}

	public URIDomain(String user, String domain, int port) {
		this(user, null, domain, port);
	}
	public URIDomain(String user, String password, String domain) {
		this(user, password, domain, 0);
	}

	public URIDomain(String domain, int port) {
		this(null, null, domain, port);
	}
	public URIDomain(String user, String domain) {
		this(user, null, domain, 0);
	}

	/**
	 * Not to be confused with URIDomain.parseString().
	 */
	public URIDomain(String domain) {
		this(null, null, domain, 0);
	}

	public static URIDomain parseString(String s) {
		if (s.contains(USER_SEP)) {
			String[] fullString = s.split(USER_SEP);
			if (fullString[0].contains(PASS_SEP)) {
				String[] userPass = fullString[0].split(PASS_SEP);
				if (fullString[1].contains(PORT_SEP)) {
					String[] domainPort = fullString[1].split(PORT_SEP);
					return new URIDomain(userPass[0], userPass[1], domainPort[0], parseInt(domainPort[1]));
				}
				return new URIDomain(userPass[0], userPass[1], fullString[1]);
			}
			if (fullString[1].contains(PORT_SEP)) {
				String[] domainPort = fullString[1].split(PORT_SEP);
				return new URIDomain(fullString[0], domainPort[0], parseInt(domainPort[1]));
			}
			return new URIDomain(fullString[0], fullString[1]);
		}
		if (s.contains(PORT_SEP)) {
			String[] domainPort = s.split(PORT_SEP);
			return new URIDomain(domainPort[0], parseInt(domainPort[1]));
		}
		return new URIDomain(s);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(SB_CAP);
		if (user != null) {
			sb.append(user);
			if (password != null) sb.append(PASS_SEP).append(password);
			sb.append(USER_SEP);
		}
		sb.append(domain);
		if (port > 0) sb.append(PORT_SEP).append(port);
		return sb.toString();
	}
}
