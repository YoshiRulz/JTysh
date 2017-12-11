package io.github.yoshirulz.jtysh.pipeline;

import io.github.yoshirulz.jtysh.pipeline.PipeArg.Pipeable;
import io.github.yoshirulz.jtysh.pipeline.PipeArg.Pipeable.RawStringAArg;
import io.github.yoshirulz.jtysh.pipeline.PipeArg.Pipeable.RawStringArg;

import static io.github.yoshirulz.jtysh.strtransform.StringAConcat.withNewlines;
import static io.github.yoshirulz.jtysh.strtransform.StringSplit.onNewlines;

/**
 * @author YoshiRulz
 * @version 2017-12-06/00
 */
@SuppressWarnings("ClassWithTooManyDependents")
public class PipeArg<T extends Pipeable> {
	public static final PipeArg<?> NO_OUTPUT = new PipeArg<>(null);

	private final T a;

	public PipeArg(T arg) {
		a = arg;
	}

	public T get() {
		return a;
	}

	public String toString() {
		return a.getString();
	}

	public static PipeArg<?> rawString(String... s) {
		return new PipeArg<>(s.length > 1 ? new RawStringAArg(s) : new RawStringArg(s[0]));
	}

	@FunctionalInterface
	public interface Pipeable {
		/**
		 * @return Single-line command output or `Object.toString()`.
		 */
		String getString();

		/**
		 * @return An array of lines of program output (may be length 1).
		 */
		default String[] getStringA() {
			return onNewlines(getString());
		}

		/**
		 * Used in `Pipeline.fromRaw())`.
		 */
		class RawStringArg implements Pipeable {
			private final String s;
			RawStringArg(String s) { this.s = s; }
			@SuppressWarnings("SuspiciousGetterSetter")
			public String getString() { return s; }
			public String toString() { return s; }
		}

		/**
		 * Used in `Pipeline.fromRaw())`.
		 */
		class RawStringAArg implements Pipeable {
			private final String[] a;
			RawStringAArg(String[] a) { this.a = a.clone(); }
			public String getString() { return toString(); }
			public String[] getStringA() { return a.clone(); }
			public String toString() { return withNewlines(a); }
		}
	}
}
