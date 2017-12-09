package io.github.yoshirulz.jtysh.pipeline.pipecmd;

import io.github.yoshirulz.jtysh.pipeline.PipeArg;
import io.github.yoshirulz.jtysh.pipeline.PipeCMD.ReqArgPipeCMD;
import io.github.yoshirulz.jtysh.pipeline.Pipeline;

import static io.github.yoshirulz.jtysh.pipeline.PipeArg.rawString;
import static java.text.MessageFormat.format;

/**
 * @author YoshiRulz
 * @version 2017-12-04/00
 */
public final class TailFilterCMD implements ReqArgPipeCMD {
	private static final String CMD_STRING = "tail -n{0}";

	private final int n;
	@SuppressWarnings("FieldNotUsedInToString") //TODO
	private final boolean exact;

	public TailFilterCMD(int n, boolean exact) {
		this.n = n;
		this.exact = exact;
	}
	public TailFilterCMD(int n) {
		this(n, false);
	}
	public TailFilterCMD() {
		this(1);
	}

	public PipeArg<?> exec(PipeArg<?> pipeIn) {
		String[] lines = pipeIn.get().getStringA();
		if (exact) return rawString(lines[lines.length - n]);
		int l = Math.min(lines.length, n);
		String[] a = new String[l];
		System.arraycopy(lines, lines.length - l, a, 0, l);
		return rawString(a);
	}

	public String toString() {
		return format(CMD_STRING, n);
	}
}
