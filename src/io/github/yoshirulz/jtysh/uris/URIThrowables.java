package io.github.yoshirulz.jtysh.uris;

import java.text.MessageFormat;

/**
 * @author YoshiRulz
 * @version 2017-12-11/00
 */
public enum URIThrowables { ;
	private static class URIException extends RuntimeException {
		URIException(String s) {
			super(s);
		}
	}

	public static final class GitHubDeprecatedProtocolException extends URIException {
		GitHubDeprecatedProtocolException() {
			super("GitHub no longer allows HTTP connections.");
		}
	}

	public static final class GitInvalidProtocolException extends URIException {
		GitInvalidProtocolException() {
			super("Git can only use the Git, HTTP(S) and SSH protocols.");
		}
	}

	public static final class InvalidURICastException extends URIException {
		InvalidURICastException(URI uri, Class<? extends URI> uriClass) {
			super(MessageFormat.format("Could not cast {0} to {1}.", uri, uriClass));
		}
	}

	public static final class WebInvalidProtocolException extends URIException {
		WebInvalidProtocolException() {
			super("WebURIs can only use HTTP(S).");
		}
	}
}
