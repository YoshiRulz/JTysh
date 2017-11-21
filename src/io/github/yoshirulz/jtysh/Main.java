package io.github.yoshirulz.jtysh;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.ProcessBuilder.Redirect.INHERIT;

/**
 * @author YoshiRulz
 * @version 2017-11-20/00
 */
public class Main {
	private static final String INST_ERR_INTERUPT = "The sub-process was interrupted! (Did you press ^C?)";
	private static final String ERR_SUFFIX = " Stacktrace:";

	private static final String INST_STRING = "x-rnasi8";
	private static final String STDOUT_HEADER = "====================";

	private static final String[] INST_ARGS = new String[]{
		"-classpath",
		Main.class.getProtectionDomain().getCodeSource().getLocation().getFile(), // This project's `.../src` dir
		Main.class.getName(),
		INST_STRING
	};

	private static File tempFile;

	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals(INST_STRING)) {
			new JTyshInstantiation().execute();
		} else {
			parse();
			try {
				compile();
			} catch (Throwable t) {
				t.printStackTrace();
				return;
			}
			execute(args);
			Debug.debug();
		}
	}

	private static void parse() {
		//TODO parse
	}

	private static void compile() {
		//TODO compile
	}

	/**
	 * tl;dr: runs the command:
	 * $($(which java) -classpath /path/to/jtysh/install io.github.yoshirulz.jtysh $jtysh_inst)
	 * ...which is very possibly a bad idea.
	 */
	private static void execute(String[] args) {
		try {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(new WhichWrapper("java").path);
			for (String s : INST_ARGS) temp.add(s);
			for (String s : args) temp.add(s);
			ProcessBuilder pb = new ProcessBuilder(temp).redirectOutput(INHERIT).redirectError(INHERIT);
			Process p = pb.start();
			p.waitFor();
		} catch (InterruptedException e) {
			System.out.println(INST_ERR_INTERUPT + ERR_SUFFIX);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WhichWrapper.ProgramNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void error(JTyshInternalError e) {
		System.out.println(e.message + ERR_SUFFIX);
	}
}