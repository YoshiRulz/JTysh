package io.github.yoshirulz.jtysh.shell.pipecmd;

import io.github.yoshirulz.jtysh.pipeline.PipeArg;
import io.github.yoshirulz.jtysh.pipeline.PipeCMD.NoArgPipeCMD;
import io.github.yoshirulz.jtysh.shell.ShellExecWrapper;
import io.github.yoshirulz.jtysh.shell.WhichWrapper.ProgramNotFoundException;
import io.github.yoshirulz.jtysh.strtransform.StringSplit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.text.MessageFormat.format;

/**
 * Danger zone - mixing OOP Pipelines w/ the event-driven `Main.main()`-logging ShellExecWrapper. The exceptions thrown by the latter are all rethrown as new RuntimeExceptions.
 * @author YoshiRulz
 * @version 2017-12-04/00
 */
public final class ShellExecMetaWrapperCMD implements NoArgPipeCMD {
	private static final String CMD_STRING = "echo $({0})";

	private final String historyFormat;
	@SuppressWarnings("FieldNotUsedInToString")
	private final PipeArg<?> arg;

	public ShellExecMetaWrapperCMD(String s) {
		historyFormat = format(CMD_STRING, s);
		String[] fullCMD = StringSplit.onSpaces(s);
		String[] args = new String[fullCMD.length - 1];
		System.arraycopy(fullCMD, 1, args, 0, args.length);
		try {
			arg = PipeArg.rawString(ShellExecWrapper.withLookup(
				fullCMD[0], args, false, 1L, TimeUnit.MINUTES
				).getOutput());
		} catch (InterruptedException | IOException | ProgramNotFoundException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public PipeArg<?> execNoInput() {
		return arg;
	}

	public String toString() {
		return historyFormat;
	}
}
