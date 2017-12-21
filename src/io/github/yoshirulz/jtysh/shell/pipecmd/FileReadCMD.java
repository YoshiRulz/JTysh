package io.github.yoshirulz.jtysh.shell.pipecmd;

import io.github.yoshirulz.jtysh.pipeline.PipeArg;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static io.github.yoshirulz.jtysh.pipeline.PipeCMD.NoArgPipeCMD;

/**
 * @author YoshiRulz
 * @version 2017-12-21/00
 */
public class FileReadCMD implements NoArgPipeCMD {
	private static final int AL_SIZE = 128;
	private final File file;

	public FileReadCMD(File f) {
		file = f;
	}

	@SuppressWarnings({"ImplicitDefaultCharsetUsage", "MethodCallInLoopCondition"})
	public PipeArg<?> execNoInput() {
		List<String> temp = new ArrayList<>(AL_SIZE);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			while (br.ready()) temp.add(br.readLine());
		} catch (IOException e) {
			throw new RuntimeException(e); //TODO
		}
		return PipeArg.rawString(temp.toArray(new String[temp.size()]));
	}
}
