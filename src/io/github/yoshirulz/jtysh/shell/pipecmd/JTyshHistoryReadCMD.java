package io.github.yoshirulz.jtysh.shell.pipecmd;

import io.github.yoshirulz.jtysh.pipeline.PipeArg;
import io.github.yoshirulz.jtysh.pipeline.PipeCMD.NoArgPipeCMD;
import io.github.yoshirulz.jtysh.shell.History;

/**
 * @author YoshiRulz
 * @version 2017-12-04/00
 */
public final class JTyshHistoryReadCMD implements NoArgPipeCMD {
	public static final JTyshHistoryReadCMD JTYSH_HISTORY = new JTyshHistoryReadCMD();

	private static final String CMD_STRING = "history";

	public PipeArg<?> execNoInput() {
		return PipeArg.rawString(History.getJTyshHistory());
	}

	public String toString() {
		return CMD_STRING;
	}
}
