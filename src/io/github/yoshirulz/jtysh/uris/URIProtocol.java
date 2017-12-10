package io.github.yoshirulz.jtysh.uris;

/**
 * @author YoshiRulz
 * @version 2017-11-19/00
 */
public enum URIProtocol implements URIHandler {
	FTP(21), SFTP(22), SSH(22), HTTP(80), HTTPS(443),
	Git(9418),
	File,
	UNKNOWN;

	private final int defaultPort;

	URIProtocol(int i) {
		defaultPort = i;
	}
	URIProtocol() {
		this(0);
	}

	public int getDefaultPort() {
		return defaultPort;
	}

	public String getHandle() {
		return name().toLowerCase();
	}

	public static URIProtocol parseString(String s) {
		switch (s) {
			case "ftp": return FTP;
			case "http": return HTTP;
			case "https": return HTTPS;
			case "sftp": return SFTP;
		}
		return UNKNOWN;
	}
}
