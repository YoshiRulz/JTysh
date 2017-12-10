package io.github.yoshirulz.jtysh.git;

import io.github.yoshirulz.jtysh.git.pipecmd.GitCloneCMD;
import io.github.yoshirulz.jtysh.pipeline.PipeArg;
import io.github.yoshirulz.jtysh.uris.GitRepoURI;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YoshiRulz
 * @version 2017-12-06/00
 */
public class FakeGitRepo implements PipeArg.Pipeable {
	private static final String DEFAULT_REMOTE = "origin";

	private final Map<String, GitRepoURI> remotes = new HashMap<>(1);

	private FakeGitRepo(GitRepoURI uri, GitCloneCMD.CloneOptions options) {
		remotes.put(DEFAULT_REMOTE, uri);
		//TODO
	}

	public String getString() {
		return "DEBUG TEXT";
	}

	public String[] getStringA() {
		return new String[]{"ANOTHER DEBUG STRING"};
	}

	@SuppressWarnings("PublicMethodNotExposedInInterface")
	public GitRepoURI getRemoteByName(String s) {
		return remotes.get(s);
	}

	public static FakeGitRepo fakeClone(GitRepoURI uri, GitCloneCMD.CloneOptions options) {
		return new FakeGitRepo(uri, options);
	}
}
