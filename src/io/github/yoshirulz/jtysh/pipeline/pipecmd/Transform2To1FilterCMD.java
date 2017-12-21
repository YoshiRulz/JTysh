package io.github.yoshirulz.jtysh.pipeline.pipecmd;

import io.github.yoshirulz.jtysh.pipeline.PipeArg;

import static io.github.yoshirulz.jtysh.pipeline.PipeArg.rawString;
import static io.github.yoshirulz.jtysh.pipeline.PipeCMD.ReqArgPipeCMD;
import static io.github.yoshirulz.jtysh.strtransform.StringTransforms.StringTransform2To1;
import static java.text.MessageFormat.format;

/**
 * @author YoshiRulz
 * @version 2017-12-21/00
 */
public class Transform2To1FilterCMD implements ReqArgPipeCMD {
	private static final String CMD_STRING = "str-transform.sh \"{0}\" \"{1}\"";

	private final StringTransform2To1 transformation;
	private final String arg;
	private final boolean isArg2;

	private Transform2To1FilterCMD(StringTransform2To1 tr, String s, boolean b) {
		transformation = tr;
		arg = s;
		isArg2 = b;
	}

	/**
	 * @param s the constant second parameter for the StringTransform
	 */
	public Transform2To1FilterCMD(StringTransform2To1 tr, String s) {
		this(tr, s, true);
	}

	/**
	 * @param s the constant first parameter for the StringTransform
	 */
	public Transform2To1FilterCMD(String s, StringTransform2To1 tr) {
		this(tr, s, false);
	}

	public PipeArg<?> exec(PipeArg<?> pipeIn) {
		return rawString(transformation.performOn(isArg2 ? pipeIn.toString() : arg, isArg2 ? arg : pipeIn.toString()));
	}

	public String toString() {
		return format(CMD_STRING, transformation, arg);
	}
}
