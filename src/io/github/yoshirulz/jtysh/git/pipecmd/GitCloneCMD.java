package io.github.yoshirulz.jtysh.git.pipecmd;

import io.github.yoshirulz.jtysh.git.FakeGitRepo;
import io.github.yoshirulz.jtysh.pipeline.PipeArg;
import io.github.yoshirulz.jtysh.pipeline.PipeArg.Pipeable;
import io.github.yoshirulz.jtysh.pipeline.PipeCMD.NoArgPipeCMD;
import io.github.yoshirulz.jtysh.uris.GitRepoURI;

import java.text.MessageFormat;
import java.util.StringJoiner;

import static io.github.yoshirulz.jtysh.git.pipecmd.GitCloneCMD.CloneOptions.DEFAULT_CLONE_OPTS;

/**
 * @author YoshiRulz
 * @version 2017-12-11/00
 */
public class GitCloneCMD implements NoArgPipeCMD {
	private static final String CMD_STRING = "git clone{1} {0}";

	private final GitRepoURI uri;
	private final CloneOptions options;

	public GitCloneCMD(GitRepoURI uri, CloneOptions options) {
		this.uri = uri;
		this.options = options;
	}
	public GitCloneCMD(GitRepoURI uri) {
		this(uri, DEFAULT_CLONE_OPTS);
	}

	public PipeArg<FakeGitRepo> execNoInput() {
		return new PipeArg<>(FakeGitRepo.fakeClone(uri, options));
	}

	public String toString() {
		return MessageFormat.format(CMD_STRING, uri, options.getString());
	}

	@SuppressWarnings({"InstanceVariableMayNotBeInitialized", "WeakerAccess"})
	public static class CloneOptions implements Pipeable {
		static final CloneOptions DEFAULT_CLONE_OPTS = new CloneOptions();

		private String branch;
		private boolean isSingleBranch;

		public CloneOptions branch(String s) {
			branch = s;
			return this;
		}

		public CloneOptions singleBranch(String s) {
			isSingleBranch = true;
			return branch(s);
		}

		public String getString() {
			return toString();
		}

		@SuppressWarnings({"HardCodedStringLiteral", "StringConcatenation"})
		public String toString() {
			StringJoiner sj = new StringJoiner(" --").add("");
			if (branch != null) sj.add("branch " + branch);
			if (isSingleBranch) sj.add("single-branch");
			return sj.toString();
		}
	}
}
