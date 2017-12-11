package io.github.yoshirulz.jtysh.uris;

/**
 * @author YoshiRulz
 * @version 2017-12-10/00
 */
public interface URIHandler {
	int getDefaultPort();
	String getHandle();

	enum URIProtocol implements URIHandler {
		FTP(21), SFTP(22), SSH(22), HTTP(80),
		HTTPS(443),
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

		@SuppressWarnings("TypeMayBeWeakened")
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

	class URICustomHandler implements URIHandler {
		public final int defaultPort;
		public final String handle;
		public final boolean useProtocolForm;

		public URICustomHandler(String handle, int defaultPort, boolean useProtocolForm) {
			this.defaultPort = defaultPort;
			this.handle = handle;
			this.useProtocolForm = useProtocolForm;
		}

		public int getDefaultPort() {
			return defaultPort;
		}

		public String getHandle() {
			return handle;
		}
	}
}
