package io.github.yoshirulz.jtysh.test;

import io.github.yoshirulz.jtysh.uris.URI;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author YoshiRulz
 * @version 2017-12-17/00
 */
public class URITest {
	private static final HashMap<String, String> TEST_URIS = new HashMap<>();
	static {
		TEST_URIS.put("xkcd.com", "unknown://xkcd.com");
		Stream.of(
				"https://google.com"
			).forEach(s -> TEST_URIS.put(s, s));
	}

	@Test
	public void testToString() throws Exception {
		for (Map.Entry<String, String> e : TEST_URIS.entrySet()) Assert.assertEquals(new URI(e.getKey()).toString(), e.getValue());
	}
}
