package io.github.yoshirulz.jtysh;

import io.github.yoshirulz.jtysh.shell.WhichWrapper;
import io.github.yoshirulz.jtysh.shell.WhichWrapper.ProgramNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static io.github.yoshirulz.jtysh.JTyshInternalError.CannotFindJavaParadox;
import static io.github.yoshirulz.jtysh.JTyshInternalError.InstanceInterrupted;
import static java.lang.ProcessBuilder.Redirect.INHERIT;

/**
 * @author YoshiRulz
 * @version 2017-12-11/00
 */
public class Main {
	private static final String STDOUT_HEADER = "====================";
	private static final String[] INST_ARGS = new String[]{
		"-classpath",
		Main.class.getProtectionDomain().getCodeSource().getLocation().getFile(), // This project's `.../src` dir
		Main.class.getName(),
		"x-rnasi8"
	};

	private static File tempFile;

	public static void main(String[] args) {
//		if (args.length > 0 && args[0].equals(INST_ARGS[3])) {
			new JTyshInstantiation().execute();
//		} else {
//			parse();
//			try {
//				compile();
//			} catch (Throwable t) {
//				t.printStackTrace();
//				return;
//			}
//			Debug.debug();
//			execute(args);
//		}
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
			ArrayList<String> temp = new ArrayList<>();
			temp.add(new WhichWrapper("java").path);
			temp.addAll(Arrays.asList(INST_ARGS));
			temp.addAll(Arrays.asList(args));
			ProcessBuilder pb = new ProcessBuilder(temp)
				.redirectOutput(INHERIT).redirectError(INHERIT);
			Process p = pb.start();
			p.waitFor();
		} catch (InterruptedException e) {
			error(InstanceInterrupted);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ProgramNotFoundException e) {
			error(CannotFindJavaParadox);
			e.printStackTrace();
		} finally {
			System.out.println();
		}
	}

	public static void error(JTyshInternalError e) {
		System.out.println(e.message + " Stacktrace:");
	}
}
