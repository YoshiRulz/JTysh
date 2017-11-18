package io.github.yoshirulz.jtysh;

import java.io.*;
import java.util.List;

import static java.lang.ProcessBuilder.Redirect.*;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author YoshiRulz
 * @version 2017-11-18/00
 */
public class Main {
	private static final String INST_STRING = "x-rnasi8";
	private static final String STDOUT_HEADER = "====================";

	private static File tempFile;

	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals(INST_STRING)) {
			checkInstantiation(args);
		} else {
			parse();
			try {
				compile();
			} catch (Throwable t) {
				t.printStackTrace();
				return;
			}
			execute(args);
		}
	}

	private static void parse() {
		//TODO parse
	}

	private static void compile() {
		//TODO compile
	}

	private static void execute(String[] args) {
		try {
			// tl;dr: $(which java >$jtysh_temp)
			tempFile = File.createTempFile("jtysh-", null);
			ProcessBuilder p = new ProcessBuilder(
					new String[]{"/usr/bin/which", "java"} //TODO port to Windows, check install dir of `which` on macOS
				).redirectOutput(to(new File(tempFile.getPath()))).redirectError(DISCARD);
			Process p2 = p.start();
			p2.waitFor(1, SECONDS);

			// tl;dr: $($(cat $jtysh_temp | head -n 1) -classpath /path/to/jtysh/install io.github.yoshirulz.jtysh $jtysh_inst)
			// ...which, given that it requires nested expansions to write as a shell script, might be a bad idea.
			List<String> temp = List.of(
					new BufferedReader(new FileReader(tempFile)).readLine(), // First line of $(which java) output - Java path
					"-classpath",
					Main.class.getProtectionDomain().getCodeSource().getLocation().getFile(), // This project's `.../src` dir
					Main.class.getName(),
					INST_STRING
				);
			for (int i = 1; i < args.length; i++) temp.add(args[i]);
			p = new ProcessBuilder(temp).redirectOutput(INHERIT).redirectError(INHERIT);
			p2 = p.start();
			p2.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void checkInstantiation(String[] s) {
		System.out.println("Successfully instantiated.");
	}
}