package io.github.yoshirulz.jtysh.test;

import io.github.yoshirulz.jtysh.pipeline.Pipeline;
import io.github.yoshirulz.jtysh.pipeline.pipecmd.Transform1To1FilterCMD;
import io.github.yoshirulz.jtysh.strtransform.MorseCodeConverter;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author YoshiRulz
 * @version 2017-12-21/00
 */
public class StrTransformTest {
	@Test
	public void testMorse() throws Exception {
		Assert.assertEquals("TEST",
			Pipeline.from("- . ... -")
				.pipeTo(new Transform1To1FilterCMD(
						new MorseCodeConverter(false, MorseCodeConverter.DEFAULT_OPTIONS)
					)).asString());
		Assert.assertEquals("-- --- .-. ... . / .. ... / ..-. ..- -.",
			Pipeline.from("MORSE IS FUN")
				.pipeTo(new Transform1To1FilterCMD(
						new MorseCodeConverter(true, MorseCodeConverter.DEFAULT_OPTIONS)
					)).asString());
	}
}
