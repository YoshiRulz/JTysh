package io.github.yoshirulz.jtysh.uris;

/**
 * @author YoshiRulz
 * @version 2017-11-19/00
 */
public class URICustomHandler implements URIHandler {
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
