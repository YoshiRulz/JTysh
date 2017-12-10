package io.github.yoshirulz.jtysh;

import io.github.yoshirulz.jtysh.git.pipecmd.GitCloneCMD;
import io.github.yoshirulz.jtysh.git.pipecmd.GitCloneCMD.CloneOptions;
import io.github.yoshirulz.jtysh.pipeline.*;
import io.github.yoshirulz.jtysh.pipeline.Pipeline.ChainablePipeline;
import io.github.yoshirulz.jtysh.shell.History;
import io.github.yoshirulz.jtysh.shell.pipecmd.ShellExecMetaWrapperCMD;
import io.github.yoshirulz.jtysh.uris.GitRepoURI;
import io.github.yoshirulz.jtysh.uris.GitRepoURI.GitHubRepoURI;
import io.github.yoshirulz.jtysh.uris.URI;
import io.github.yoshirulz.jtysh.uris.URIThrowables.InvalidURICastException;

import static io.github.yoshirulz.jtysh.pipeline.pipecmd.TermEchoCMD.ECHO;

/**
 * Just `import static io.github.yoshirulz.jtysh.JTyshNatLang.$` and you're awaaaaaay...
 * N.B. methods and subclasses (subenums?) are sorted alphabetically in `.java` form.
 * @author YoshiRulz
 * @version 2017-11-28/00
 */
public enum JTyshNatLang { ;
	/**
	 * @deprecated The `$.*()` shorthand functions should only be used in the shell, and never in code intended to be run more than once.
	 */
	@Deprecated
	@SuppressWarnings({"DollarSignInName", "EnumeratedClassNamingConvention"})
	public enum $ { ;
		@SuppressWarnings({"MethodNameSameAsClassName", "StaticMethodNamingConvention"})
		public static ChainablePipeline $(String s) {
			return new NoArgPipelineHead(new ShellExecMetaWrapperCMD(s));
		}
		public static PipelineVoid echo(Object o) {
			return Pipeline.from(o.toString()).pipeTo(ECHO);
		}
		public static ChainablePipeline history() {
			return History.get();
		}
		@SuppressWarnings("StaticMethodNamingConvention")
		public enum git { ;
			public static ChainablePipeline clone_(GitRepoURI uri, CloneOptions options) {
				return new NoArgPipelineHead(new GitCloneCMD(uri, options));
			}
			public static ChainablePipeline clone_(GitRepoURI uri) {
				return new NoArgPipelineHead(new GitCloneCMD(uri));
			}
			public static ChainablePipeline clone_(String uri, CloneOptions options) throws InvalidURICastException {
				return clone_(GitRepoURI.fromURI(new URI(uri)), options);
			}
			public static ChainablePipeline clone_(String uri) throws InvalidURICastException {
				return clone_(GitRepoURI.fromURI(new URI(uri)));
			}
		}
		@SuppressWarnings("StaticMethodNamingConvention")
		public enum github { ;
			public static ChainablePipeline clone_(String user, String repo, CloneOptions options) {
				return git.clone_(new GitHubRepoURI(user, repo), options);
			}
			public static ChainablePipeline clone_(String user, String repo) {
				return git.clone_(new GitHubRepoURI(user, repo));
			}
			/**
			 * @param s Of the form "user/repo"
			 */
			public static ChainablePipeline clone_(String s, CloneOptions options) {
				String[] a = s.split("/");
				return clone_(a[0], a[1], options);
			}
			/**
			 * @param s Of the form "user/repo"
			 */
			public static ChainablePipeline clone_(String s) {
				String[] a = s.split("/");
				return clone_(a[0], a[1]);
			}
		}
	}
}
