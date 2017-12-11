package io.github.yoshirulz.jtysh.pipeline;

import io.github.yoshirulz.jtysh.pipeline.PipeCMD.NoArgPipeCMD;

/**
 * Takes no pipeline input, gives no pipeline output.
 * @author YoshiRulz
 * @see Pipeline
 * @version 2017-12-11/00
 */
public class NoArgPipelineVoid implements Pipeline {
	private static final int LENGTH = 1;

	private final PipeCMD exec;
	@SuppressWarnings({"FieldNotUsedInToString", "InstanceVariableMayNotBeInitialized"})
	private PipeArg<?> pipeOut;

	@SuppressWarnings("WeakerAccess")
	protected NoArgPipelineVoid(@SuppressWarnings("TypeMayBeWeakened") NoArgPipeCMD exec) {
		this.exec = exec;
	}

	public int length() {
		return LENGTH;
	}

	public void compute() {
		pipeOut = exec.execNoInput();
	}

	public PipeArg<?> typedOutput() {
		if (pipeOut == null) compute();
		return pipeOut;
	}

	public String toString() {
		return exec.getCMDString();
	}
}
