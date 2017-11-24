package io.github.yoshirulz.jtysh;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author YoshiRulz
 * @version 2017-11-21/00
 */
public class WhichWrapper extends ShellCommandWrapper {
	private static final int WHICH_TIMEOUT_MS = 1000;

	public final String path;

	public WhichWrapper(String program) throws IOException, InterruptedException, ProgramNotFoundException {
		super(
				new String[]{"/usr/bin/which", program}, //TODO port to Windows
				true, WHICH_TIMEOUT_MS, TimeUnit.MILLISECONDS
			);
		String[] o = getOutput();
		if (o.length > 0) {
			path = o[0];
		} else {
			throw new ProgramNotFoundException(program);
		}
	}

	public static class ProgramNotFoundException extends Exception { //TODO choose less general super
		public ProgramNotFoundException(String program) {
			super("Cannot find the program " + program + " in $PATH!");
		}
	}
}