package io.github.yoshirulz.jtysh.pipeline.pipecmd;

import io.github.yoshirulz.jtysh.pipeline.PipeArg;
import io.github.yoshirulz.jtysh.pipeline.PipeCMD.ReqArgPipeCMD;
import io.github.yoshirulz.jtysh.strtransform.StringSplit;

import java.util.StringJoiner;

import static java.text.MessageFormat.format;

/**
 * Not a wrapper for (g)awk, nor an implementation of any awk features outside of field iteration.
 * @author YoshiRulz
 * @version 2017-12-04/00
 */
public final class AwkFilterCMD implements ReqArgPipeCMD {
	private static final String[] CMD_STRING = new String[]{"awk", "\"'{'print \\${0}'}'\""};
	private static final String DEFAULT_SEPARATOR = " ";

	private final int field;
	private final String separator;

	public AwkFilterCMD(int field, String separator) {
		this.field = field;
		this.separator = separator;
	}
	public AwkFilterCMD(int field) {
		this(field, DEFAULT_SEPARATOR);
	}

	public PipeArg<?> exec(PipeArg<?> pipeIn) {
		return PipeArg.rawString(StringSplit.on(separator, pipeIn.toString())[field - 1]);
	}

	public String toString() {
		StringJoiner sj = new StringJoiner(" ")
			.add(CMD_STRING[0]);
		if (!separator.equals(DEFAULT_SEPARATOR)) sj.add("-F" + separator);
		sj.add(CMD_STRING[1]);
		return format(sj.toString(), field);
	}
}
