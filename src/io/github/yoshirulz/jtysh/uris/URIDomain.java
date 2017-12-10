package io.github.yoshirulz.jtysh.uris;

/**
 * @author YoshiRulz
 * @version 2017-11-19/00
 */
public class URIDomain {
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
		if (s.contains("@")) {
			String[] fullString = s.split("@");
			if (fullString[0].contains(":")) {
				String[] userPass = fullString[0].split(":");
				if (fullString[1].contains(":")) {
					String[] domainPort = fullString[1].split(":");
					return new URIDomain(userPass[0], userPass[1], domainPort[0], Integer.parseInt(domainPort[1]));
				}
				return new URIDomain(userPass[0], userPass[1], fullString[1]);
			}
			if (fullString[1].contains(":")) {
				String[] domainPort = fullString[1].split(":");
				return new URIDomain(fullString[0], domainPort[0], Integer.parseInt(domainPort[1]));
			}
			return new URIDomain(fullString[0], fullString[1]);
		}
		if (s.contains(":")) {
			String[] domainPort = s.split(":");
			return new URIDomain(domainPort[0], Integer.parseInt(domainPort[1]));
		}
		return new URIDomain(s);
	}

	public String toString() {
		return (user == null ? "" : user + (password == null ? "" : ":" + password) + "@") + domain + (port > 0 ? ":" + port : "");
	}
}
