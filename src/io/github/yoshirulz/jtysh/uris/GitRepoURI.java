package io.github.yoshirulz.jtysh.uris;

import io.github.yoshirulz.jtysh.uris.URIThrowables.GitHubDeprecatedProtocolException;
import io.github.yoshirulz.jtysh.uris.URIThrowables.InvalidURICastException;

import static io.github.yoshirulz.jtysh.uris.URIProtocol.*;

/**
 * @author YoshiRulz
 * @version 2017-11-19/00
 */
public class GitRepoURI extends URI {
	public GitRepoURI(URIHandler connType, URIDomain domain, String[] location) {
		super(connType, domain, location);
		if (!isValidGitProtocol(connType)) throw new IllegalArgumentException("Git can only use the Git, HTTP(S) and SSH protocols!"); //TODO move to URIThrowables
	}
	public GitRepoURI(URIDomain domain, String[] location) {
		this(Git, domain, location);
	}

	@SuppressWarnings("MethodWithMoreThanThreeNegations")
	public static GitRepoURI fromURI(URI uri) throws InvalidURICastException {
		if (!(uri.handler instanceof URIProtocol) ||
				!isValidGitProtocol(uri.handler) ||
				(uri.handler != Git && !uri.location[uri.location.length - 1].endsWith(".git"))) // Using HTTP(S) or SSH and path doesn't match *.git
			throw new InvalidURICastException(uri, GitRepoURI.class);
		return new GitRepoURI(uri.handler, uri.getFullDomain(), uri.location);
	}

	public static GitRepoURI parseGitSSHForm(String s) {
		throw new RuntimeException(""); //TODO make less general
		//TODO parse
	}

	public static class GitHubRepoURI extends GitRepoURI {
		private GitHubRepoURI(URIHandler connType, String user, String repo) {
			super(connType,
					new URIDomain(connType == SSH ? "git" : null, null, "github.com", connType.getDefaultPort()),
					new String[]{user, repo + ".git"}
				);
		}

		public GitHubRepoURI(String user, String repo, URIHandler connType) throws GitHubDeprecatedProtocolException {
			this(connType, user, repo);
			if (connType == HTTP) throw new GitHubDeprecatedProtocolException();
		}
		public GitHubRepoURI(String user, String repo) {
			this(HTTPS, user, repo);
		}

		public WebURI asWebURI() {
			if (handler != HTTP && handler != HTTPS) throw new RuntimeException("Using git:// or ssh:// - cannot cast to WebURI!"); //TODO make less general
			return new WebURI(getFullDomain(), location, null, null, handler == HTTPS);
		}
	}

	public static final boolean isValidGitProtocol(URIHandler handler) {
		return handler == Git || handler == HTTP || handler == HTTPS || handler == SSH;
	}
}
