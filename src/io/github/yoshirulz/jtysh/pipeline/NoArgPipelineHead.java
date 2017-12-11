package io.github.yoshirulz.jtysh.pipeline;

import io.github.yoshirulz.jtysh.pipeline.PipeCMD.NoArgPipeCMD;
import io.github.yoshirulz.jtysh.pipeline.Pipeline.ChainablePipeline;

/**
 * Takes no pipeline input.
 * @author YoshiRulz
 * @see Pipeline
 * @version 2017-12-04/00
 */
public class NoArgPipelineHead extends NoArgPipelineVoid implements ChainablePipeline {
	public NoArgPipelineHead(NoArgPipeCMD exec) {
		super(exec);
	}
}
