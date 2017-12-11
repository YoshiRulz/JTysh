package io.github.yoshirulz.jtysh.pipeline;

import io.github.yoshirulz.jtysh.pipeline.PipeCMD.ReqArgPipeCMD;
import io.github.yoshirulz.jtysh.pipeline.Pipeline.ChainablePipeline;

import static java.text.MessageFormat.format;

/**
 * @author YoshiRulz
 * @see Pipeline
 * @version 2017-12-03/00
 */
@SuppressWarnings("ClassNamePrefixedWithPackageName")
public class PipelineHead extends PipelineVoid implements ChainablePipeline {
	static final String PIPE_FORMAT = "{0} | {1}";

	private final ChainablePipeline preExec;
	private final PipeCMD exec;
	private PipeArg<?> pipeOut;

	public PipelineHead(ReqArgPipeCMD exec, ChainablePipeline preExec) {
		super(exec, preExec);
		this.exec = exec;
		this.preExec = preExec;
	}

	public int length() {
		return preExec.length() + 1;
	}

	public void compute() {
		pipeOut = exec.exec(preExec.typedOutput());
	}

	public PipeArg<?> typedOutput() {
		if (pipeOut == null) compute();
		return pipeOut;
	}

	public String toString() {
		return format(PIPE_FORMAT, preExec.getHistoryString(), exec.getCMDString());
	}
}
