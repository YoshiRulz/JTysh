package io.github.yoshirulz.jtysh.shell;

import io.github.yoshirulz.jtysh.pipeline.NoArgPipelineHead;
import io.github.yoshirulz.jtysh.pipeline.Pipeline.ChainablePipeline;
import io.github.yoshirulz.jtysh.pipeline.PipelineHead;
import io.github.yoshirulz.jtysh.shell.pipecmd.JTyshHistoryReadCMD;

import java.util.ArrayList;
import java.util.List;

import static io.github.yoshirulz.jtysh.shell.UnixShell.JTysh;

/**
 * @author YoshiRulz
 * @version 2017-12-11/00
 */
public class History {
	private static final String[] NO_OUTPUT = new String[0];
	private static final List<String> JTyshHistory = new ArrayList<>(0);

	private static UnixShell shell = JTysh;

//	@SuppressWarnings({"CollectionWithoutInitialCapacity", "MethodCallInLoopCondition"})
//	public static String[] getRawHistory(CommonShells sh) {
//		try (BufferedReader br = new BufferedReader(new FileReader(sh.getHistoryFile()))) {
//			List<String> temp = new ArrayList<>();
//			while (br.ready()) temp.add(br.readLine());
//			return temp.toArray(new String[temp.size()]);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return NO_OUTPUT;
//	}

	public static ChainablePipeline get() {
		return shell == JTysh ? new NoArgPipelineHead(new JTyshHistoryReadCMD()) : new NoArgPipelineHead(null); //TODO
	}

	public static PipelineHead getOf(UnixShell sh) {
		return new PipelineHead(null, null); //TODO
	}

	public static UnixShell getShell() {
		return shell;
	}

	public static void setShell(UnixShell sh) {
		shell = sh;
	}

	public static String[] getJTyshHistory() {
		return JTyshHistory.toArray(new String[JTyshHistory.size()]);
	}

	public static void write(String s) {
		switch (shell) {
			case JTysh:
				JTyshHistory.add(s);
				break;
			default:
				//noinspection UseOfSystemOutOrSystemErr
				System.out.println("OOPS: " + s); //TODO log to ~/.bash_history etc.
		}
	}

	public static void writeRawJTysh(String s) {
		JTyshHistory.add(s);
	}
}
