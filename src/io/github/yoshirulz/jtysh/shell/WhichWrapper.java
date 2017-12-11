package io.github.yoshirulz.jtysh.shell;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author YoshiRulz
 * @version 2017-12-11/00
 */
public class WhichWrapper extends ShellExecWrapper {
	private static final long WHICH_TIMEOUT_MS = 500L;
	private static final String WHICH_PATH;

	static {
		//TODO port to Windows
		WHICH_PATH = "/usr/bin/which";
	}

	public final String path;

	public WhichWrapper(String program) throws IOException, InterruptedException, ProgramNotFoundException {
		super(
				new String[]{WHICH_PATH, program},
				true, WHICH_TIMEOUT_MS, TimeUnit.MILLISECONDS
			);
		String[] o = getOutput();
		if (o.length == 0) throw new ProgramNotFoundException(program);
		path = o[0];
	}

	public static class ProgramNotFoundException extends Exception { //TODO choose less general super
		public ProgramNotFoundException(String program) {
			super("Cannot find the program " + program + " in $PATH!");
		}
	}
}
