package io.github.yoshirulz.jtysh.pipeline;

import io.github.yoshirulz.jtysh.pipeline.PipeArg.Pipeable.RawStringAArg;
import io.github.yoshirulz.jtysh.pipeline.PipeArg.Pipeable.RawStringArg;
import io.github.yoshirulz.jtysh.pipeline.PipeCMD.NoArgPipeCMD;
import io.github.yoshirulz.jtysh.pipeline.PipeCMD.ReqArgPipeCMD;
import io.github.yoshirulz.jtysh.pipeline.pipecmd.*;
import io.github.yoshirulz.jtysh.shell.History;

import static io.github.yoshirulz.jtysh.pipeline.pipecmd.TermEchoCMD.ECHO;
import static java.text.MessageFormat.format;

/**
 * tl:dr; the rightmost segment of a pipeline, possibly the only segment, possibly allowing additional segments.
 * Represents one of:
 * - the first segment of a pipeline (NoArgPipelineHead);
 * - the rightmost segment, with the rest of the pipeline contained... within? (PipelineHead);
 * - one of these rightmost segments, sealed to prevent more being added to the pipeline (PipelineVoid); or
 * - a single lonely segment that's both the head and final tail of its own pipeline (NoArgPipelineVoid).
 * The idea is one can both nest pipelines and construct them iteratively with method chaining, limited only by commands being marked as data sinks. This is to mimic the behaviour of pipelines in the Unix shell.
 * @author YoshiRulz
 * @version 2017-12-03/00
 */
public interface Pipeline {
	int length();

	default String getHistoryString() {
		return toString();
	}

	void compute();

	PipeArg<?> typedOutput();
	/**
	 * @deprecated Outside of the JTysh shell, you should directly call `typedOutput()` (this is shorthand).
	 */
	@Deprecated
	default PipeArg<?> t() {
		return typedOutput();
	}


	default void run() {
		typedOutput();
		History.write(getHistoryString());
	}
	/**
	 * @deprecated Outside of the JTysh shell, you should directly call `run()` (this is shorthand).
	 */
	@Deprecated
	default void r() {
		run();
	}

	default String asString() {
		return typedOutput().toString();
	}
	/**
	 * @deprecated Outside of the JTysh shell, you should directly call `asString()` (this is shorthand).
	 */
	@Deprecated
	default String s() {
		return asString();
	}

	default PipeArg<?> asPipeArg() {
		return PipeArg.rawString(asString());
	}
	/**
	 * @deprecated Outside of the JTysh shell, you should directly call `asPipeArg()` (this is shorthand).
	 */
	@Deprecated
	default PipeArg<?> a() {
		return asPipeArg();
	}

	static ChainablePipeline from(PipeArg<?> p) {
		return new NoArgPipelineHead(new DumpArgCMD(p));
	}
	static ChainablePipeline from(String... s) {
		return from(PipeArg.rawString(s));
	}

	static ChainablePipeline construct(NoArgPipeCMD firstCMD, ReqArgPipeCMD... cmds) {
		ChainablePipeline p = new NoArgPipelineHead(firstCMD);
		for (ReqArgPipeCMD cmd : cmds) p = p.pipeTo(cmd);
		return p;
	}

	interface ChainablePipeline extends Pipeline {
		default PipelineHead pipeTo(ReqArgPipeCMD cmd) { return new PipelineHead(cmd, this); }
		default PipelineHead echo() { return pipeTo(ECHO); }

		default ChainablePipeline awk(int field, String separator) { return pipeTo(new AwkFilterCMD(field, separator)); }
		default ChainablePipeline awk(int field) { return pipeTo(new AwkFilterCMD(field)); }

		default ChainablePipeline tailExact(int n) { return pipeTo(new TailFilterCMD(n, true)); }
		default ChainablePipeline tail(int n) { return pipeTo(new TailFilterCMD(n)); }
		default ChainablePipeline tail() { return pipeTo(new TailFilterCMD()); }
	}

	/**
	 * Used in `Pipeline.from(PipeArg&lt;?&gt; p)`.
	 */
	class DumpArgCMD implements NoArgPipeCMD {
		private static final String CMD_STRING = "echo {0}";
		private final PipeArg<?> arg;

		DumpArgCMD(PipeArg<?> p) {
			arg = p;
		}

		public PipeArg<?> execNoInput() {
			return arg;
		}

		public String toString() {
			return format(CMD_STRING, arg);
		}
	}
}
