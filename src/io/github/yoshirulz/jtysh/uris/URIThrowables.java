package io.github.yoshirulz.jtysh.uris;

/**
 * @author YoshiRulz
 * @version 2017-12-03/00
 */
public enum URIThrowables { ;
	private static class URIException extends Exception {
		public URIException(String s) {
			super(s);
		}
	}

	public static final class GitHubDeprecatedProtocolException extends URIException {
		public GitHubDeprecatedProtocolException() {
			super("GitHub no longer allows HTTP connections.");
		}
	}

	public static final class InvalidURICastException extends URIException {
		public InvalidURICastException(URI uri, Class<? extends URI> uriClass) {
			super("Could not cast " + uri + " to " + uriClass);
		}
	}
}
