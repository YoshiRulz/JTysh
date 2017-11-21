package io.github.yoshirulz.jtysh;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static io.github.yoshirulz.jtysh.JTyshInternalError.CannotFinishTempfileRead;
import static java.lang.ProcessBuilder.Redirect;
import static java.lang.ProcessBuilder.Redirect.DISCARD;

/**
 * @author YoshiRulz
 * @version 2017-11-21/00
 */
public class ShellCommandWrapper {
	public final String[] output;

	public ShellCommandWrapper(String[] cmdPath, boolean ignoreError, int timeout, TimeUnit timeoutUnits) throws IOException, InterruptedException {
		File tempFile = File.createTempFile("jtysh-", null);
		Redirect toTempFile = Redirect.to(tempFile);
		ProcessBuilder pb = new ProcessBuilder(cmdPath)
			.redirectOutput(toTempFile).redirectError(ignoreError ? DISCARD : toTempFile);
		Process p = pb.start();
		p.waitFor(timeout, timeoutUnits);

		Reader r = new FileReader(tempFile);
		ArrayList<String> temp = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(r)) {
			while (br.ready()) temp.add(br.readLine());
		} catch (IOException e) {
			Main.error(CannotFinishTempfileRead);
			e.printStackTrace();
		} finally {
			r.close();
			output = new String[temp.size()];
			for (int i = 0; i < output.length; i++) output[i] = temp.get(i);
		}
	}
}