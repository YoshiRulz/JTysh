package io.github.yoshirulz.jtysh.shell.pipecmd;

import io.github.yoshirulz.jtysh.pipeline.PipeArg;
import io.github.yoshirulz.jtysh.pipeline.PipeCMD.NoArgPipeCMD;
import io.github.yoshirulz.jtysh.shell.ShellExecWrapper;
import io.github.yoshirulz.jtysh.shell.WhichWrapper.ProgramNotFoundException;
import io.github.yoshirulz.jtysh.strtransform.StringSplit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static io.github.yoshirulz.jtysh.strtransform.StringAConcat.withSpaces;
import static java.text.MessageFormat.format;

/**
 * Danger zone - mixing OOP Pipelines w/ the event-driven `Main.main()`-logging ShellExecWrapper. The exceptions thrown by the latter are all rethrown as new RuntimeExceptions.
 * @author YoshiRulz
 * @version 2017-12-11/00
 */
public final class ShellExecMetaWrapperCMD implements NoArgPipeCMD {
	private static final String CMD_STRING = "echo $({0}{1})";
	private static final int SB_CAP = 128;

	private final String cmd;
	private String[] args;
	@SuppressWarnings({"FieldNotUsedInToString", "InstanceVariableMayNotBeInitialized"})
	private PipeArg<?> pipeOut;

	public ShellExecMetaWrapperCMD(String... cmdWithArgs) {
		cmd = cmdWithArgs[0];
		args = new String[cmdWithArgs.length - 1];
		System.arraycopy(cmdWithArgs, 1, args, 0, args.length);
	}

	private PipeArg<?> compute() {
		try {
			return PipeArg.rawString(ShellExecWrapper.withLookup(
				cmd, args, false, 1L, TimeUnit.MINUTES
			).getOutput());
		} catch (InterruptedException | IOException | ProgramNotFoundException e) {
			throw new RuntimeException(e); //TODO
		}
	}

	public PipeArg<?> exec(PipeArg<?> pipeIn) {
		if (args != null) throw new RuntimeException("OOPS " + withSpaces(args)); //TODO
		args = StringSplit.onSpaces(pipeIn.toString());
		return execNoInput();
	}

	public PipeArg<?> execNoInput() {
		if (pipeOut == null) pipeOut = compute();
		return pipeOut;
	}

	@SuppressWarnings("MagicCharacter")
	public String toString() {
		StringBuilder sb = new StringBuilder(SB_CAP);
		for (String s : args) sb.append(' ').append(s);
		return format(CMD_STRING, cmd, sb);
	}
}
