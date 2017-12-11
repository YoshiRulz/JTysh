package io.github.yoshirulz.jtysh.pipeline;

import io.github.yoshirulz.jtysh.pipeline.PipeCMD.ReqArgPipeCMD;

import static io.github.yoshirulz.jtysh.pipeline.PipelineHead.PIPE_FORMAT;
import static java.text.MessageFormat.format;

/**
 * Gives no pipeline output.
 * @author YoshiRulz
 * @see Pipeline
 * @version 2017-12-11/00
 */
@SuppressWarnings({"ClassNamePrefixedWithPackageName", "FieldNotUsedInToString"})
public class PipelineVoid implements Pipeline {
	private static final String CMD_STRING_ARG_FORM = "echo {0}";

	private final int preExecLength;
	private final String pipeInHistoryString;
	private final PipeArg<?> pipeIn;
	private final ReqArgPipeCMD exec;

	@SuppressWarnings("InstanceVariableMayNotBeInitialized")
	private PipeArg<?> pipeOut;

	public PipelineVoid(ReqArgPipeCMD exec, @SuppressWarnings("TypeMayBeWeakened") ChainablePipeline preExec) {
		preExecLength = preExec.length();
		pipeInHistoryString = preExec.getHistoryString();
		pipeIn = preExec.typedOutput();
		this.exec = exec;
	}

	public PipelineVoid(ReqArgPipeCMD exec, PipeArg<?> pipeIn) {
		preExecLength = 0;
		pipeInHistoryString = format(CMD_STRING_ARG_FORM, pipeIn);
		this.pipeIn = pipeIn;
		this.exec = exec;
	}

	public int length() {
		return preExecLength + 1;
	}

	public void compute() {
		pipeOut = exec.exec(pipeIn);
	}

	public PipeArg<?> typedOutput() {
		if (pipeOut == null) compute();
		return pipeOut;
	}

	public String toString() {
		return format(PIPE_FORMAT, pipeInHistoryString, exec.getCMDString());
	}
}
