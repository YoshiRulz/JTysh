package io.github.yoshirulz.jtysh.pipeline;

/**
 * @author YoshiRulz
 * @version 2017-12-11/00
 */
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface PipeCMD {
	default PipeArg<?> exec(PipeArg<?> pipeIn) {
		return execNoInput();
	}
	PipeArg<?> execNoInput();

	default String getCMDString() {
		return toString();
	}

	interface NoArgPipeCMD extends PipeCMD {
		PipeArgDiscardedException ARG_DISCARDED = new PipeArgDiscardedException();

		final class PipeArgDiscardedException extends RuntimeException {
			PipeArgDiscardedException() {
				super("OOPS");
			} //TODO
		}

		default PipeArg<?> exec(PipeArg<?> pipeIn) {
			throw ARG_DISCARDED;
		}
	}

	interface ReqArgPipeCMD extends PipeCMD {
		NoPipeArgGivenException NO_ARG = new NoPipeArgGivenException();

		final class NoPipeArgGivenException extends RuntimeException {
			NoPipeArgGivenException() {
				super("OOPS");
			} //TODO
		}

		default PipeArg<?> execNoInput() {
			throw NO_ARG;
		}
	}
}
