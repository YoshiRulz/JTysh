package io.github.yoshirulz.jtysh.pipeline.pipecmd;

import io.github.yoshirulz.jtysh.pipeline.PipeArg;

import static io.github.yoshirulz.jtysh.pipeline.PipeArg.rawString;
import static io.github.yoshirulz.jtysh.pipeline.PipeCMD.ReqArgPipeCMD;
import static io.github.yoshirulz.jtysh.strtransform.StringTransforms.StringTransform1To1;
import static java.text.MessageFormat.format;

/**
 * @author YoshiRulz
 * @version 2017-12-21/00
 */
public class Transform1To1FilterCMD implements ReqArgPipeCMD {
	private static final String CMD_STRING = "str-transform.sh \"{0}\"";

	private final StringTransform1To1 transformation;

	public Transform1To1FilterCMD(StringTransform1To1 tr) {
		transformation = tr;
	}

	public PipeArg<?> exec(PipeArg<?> pipeIn) {
		return rawString(transformation.performOn(pipeIn.toString()));
	}

	public String toString() {
		return format(CMD_STRING, transformation);
	}
}
