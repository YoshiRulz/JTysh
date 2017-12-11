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

import static io.github.yoshirulz.jtysh.pipeline.pipecmd.TermEchoCMD.ECHO;

/**
 * Just `import static io.github.yoshirulz.jtysh.JTyshNatLang.$` and you're awaaaaaay...
 * N.B. methods and subclasses (subenums?) are sorted alphabetically in `.java` form.
 * @author YoshiRulz
 * @version 2017-11-28/00
 */
@SuppressWarnings({"unused", "ClassNamePrefixedWithPackageName"})
public enum JTyshNatLang { ;
	/**
	 * @deprecated The `$.*()` shorthand functions should only be used in the shell, and never in code intended to be run more than once.
	 */
	@Deprecated
	@SuppressWarnings({"DollarSignInName", "EnumeratedClassNamingConvention", "StaticMethodNamingConvention"})
	public enum $ { ;
		@SuppressWarnings("MethodNameSameAsClassName")
		public static ChainablePipeline $(String s) {
			return new NoArgPipelineHead(new ShellExecMetaWrapperCMD(s));
		}
		public static void $$(String s) { $(s).r(); }
		public static PipelineVoid echo(Object o) {
			return Pipeline.from(o.toString()).pipeTo(ECHO);
		}
		public static void echo$(Object o) { echo(o).r(); }
		public static ChainablePipeline history() {
			return History.get();
		}
		public enum git { ;
			public static ChainablePipeline clone_(GitRepoURI uri, CloneOptions options) {
				return new NoArgPipelineHead(new GitCloneCMD(uri, options));
			}
			public static ChainablePipeline clone_(GitRepoURI uri) {
				return clone_(uri, null);
			}
			public static ChainablePipeline clone_(String uri, CloneOptions options) {
				return clone_(GitRepoURI.fromURI(new URI(uri)), options);
			}
			public static ChainablePipeline clone_(String uri) {
				return clone_(uri, null);
			}
		}
		public enum github { ;
			public static ChainablePipeline clone_(String user, String repo, CloneOptions options) {
				return git.clone_(new GitHubRepoURI(user, repo), options);
			}
			public static ChainablePipeline clone_(String user, String repo) {
				return clone_(user, repo, null);
			}
		}
	}
}
