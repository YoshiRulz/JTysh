package io.github.yoshirulz.jtysh.uris;

import io.github.yoshirulz.jtysh.uris.URIThrowables.*;

import static io.github.yoshirulz.jtysh.uris.URIHandler.URIProtocol.*;

/**
 * @author YoshiRulz
 * @version 2017-12-11/00
 */
public class GitRepoURI extends URI {
	private static final String GIT_EXT = ".git";

	public GitRepoURI(URIHandler connType, URIDomain domain, String[] location) {
		super(connType, domain, location);
		if (isInvalidGitProtocol(connType)) throw new GitInvalidProtocolException();
	}
	public GitRepoURI(URIDomain domain, String[] location) {
		this(Git, domain, location);
	}

	@SuppressWarnings("MethodWithMoreThanThreeNegations")
	public static GitRepoURI fromURI(URI uri) {
		if (!(uri.getHandler() instanceof URIProtocol) ||
			isInvalidGitProtocol(uri.getHandler()) ||
				(uri.getHandler() != Git && !uri.getLocation()[uri.getLocation().length - 1].endsWith(GIT_EXT))) // Using HTTP(S) or SSH and path doesn't match *.git
			throw new InvalidURICastException(uri, GitRepoURI.class);
		return new GitRepoURI(uri.getHandler(), uri.getFullDomain(), uri.getLocation());
	}

	public static GitRepoURI parseGitSSHForm(String s) {
		throw new RuntimeException(""); //TODO make less general
		//TODO parse
	}

	public static class GitHubRepoURI extends GitRepoURI {
		@SuppressWarnings("HardCodedStringLiteral")
		private GitHubRepoURI(URIHandler connType, String user, String repo) {
			//noinspection StringConcatenationMissingWhitespace
			super(connType,
					new URIDomain(connType == SSH ? "git" : null, null, "github.com", connType.getDefaultPort()),
					new String[]{user, repo + GIT_EXT}
				);
		}

		public GitHubRepoURI(String user, String repo, URIHandler connType) {
			this(connType, user, repo);
			if (connType == HTTP) throw new GitHubDeprecatedProtocolException();
		}
		public GitHubRepoURI(String user, String repo) {
			this(HTTPS, user, repo);
		}

		public WebURI asWebURI() {
			if (getHandler() != HTTP && getHandler() != HTTPS) throw new InvalidURICastException(this, URI.class);
			return new WebURI(getFullDomain(), getLocation(), null, null, getHandler() == HTTPS);
		}
	}

	private static boolean isInvalidGitProtocol(URIHandler handler) {
		return handler != Git && handler != HTTP && handler != HTTPS && handler != SSH;
	}
}
