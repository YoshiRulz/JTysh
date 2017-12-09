package io.github.yoshirulz.jtysh.pipeline.pipecmd;

import io.github.yoshirulz.jtysh.pipeline.PipeArg;
import io.github.yoshirulz.jtysh.pipeline.PipeCMD.ReqArgPipeCMD;

import static io.github.yoshirulz.jtysh.pipeline.PipeArg.NO_OUTPUT;

/**
 * @author YoshiRulz
 * @version 2017-12-07/00
 */
public enum TermEchoCMD implements ReqArgPipeCMD {
	ECHO;

	private static final String CMD_STRING = "echo";

	@SuppressWarnings("UseOfSystemOutOrSystemErr")
	public PipeArg<?> exec(PipeArg<?> pipeIn) {
		System.out.println(pipeIn);
		return NO_OUTPUT;
	}

	public String toString() {
		return CMD_STRING;
	}
}
