package io.github.yoshirulz.jtysh;

import java.io.*;
import java.util.List;

import static java.lang.ProcessBuilder.Redirect.*;

/**
 * @author YoshiRulz
 * @version 2017-11-20/00
 */
public class Main {
	private static final String INST_ERR_INTERUPT = "The sub-process was interrupted! (Did you press ^C?)";
	private static final String INST_ERR_SUFFIX = " Stacktrace:";

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
			Debug.debug();
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
			ProcessBuilder pb = new ProcessBuilder("/usr/bin/which", "java") //TODO port to Windows, check install dir of `which` on macOS
				.redirectOutput(to(tempFile)).redirectError(DISCARD);
			Process p = pb.start();
			p.waitFor(1, java.util.concurrent.TimeUnit.SECONDS);

			// tl;dr: $($(cat $jtysh_temp | head -n 1) -classpath /path/to/jtysh/install io.github.yoshirulz.jtysh $jtysh_inst)
			// ...which, given that it requires nested expansions to write as a shell script, might be a bad idea.
			String javaPath = "/usr/bin/printf"; //TODO port to Windows, check install dir of `printf` on macOS
			Reader r = new FileReader(tempFile);
			try (BufferedReader br = new BufferedReader(r)) {
				javaPath = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				r.close();
			}
			List<String> temp = List.of(
				javaPath, // First line of $(which java) output - Java path
				"-classpath",
				Main.class.getProtectionDomain().getCodeSource().getLocation().getFile(), // This project's `.../src` dir
				Main.class.getName(),
				INST_STRING
			);
			for (String s : args) temp.add(s);
			pb = new ProcessBuilder(temp).redirectOutput(INHERIT).redirectError(INHERIT);
			p = pb.start();
			p.waitFor();
		} catch (InterruptedException e) {
			System.out.println(INST_ERR_INTERUPT + INST_ERR_SUFFIX);
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