package io.github.yoshirulz.jtysh.strtransform;

import io.github.yoshirulz.jtysh.strtransform.StringTransforms.StringTransform1To1;
import io.github.yoshirulz.jtysh.strtransform.StringTransforms.TransformationType;

import java.util.*;
import java.util.regex.Pattern;

import static io.github.yoshirulz.jtysh.strtransform.StringTransforms.LatinCharset.getUppercase;

/**
 * @author YoshiRulz
 * @version 2017-12-21/00
 */
@SuppressWarnings("WeakerAccess")
public class MorseCodeConverter implements StringTransform1To1 {
	public static final MorseCodeOptions DEFAULT_OPTIONS = new MorseCodeOptions();

	private static final int HM_SIZE = 35;
	private static final Pattern PLAINTEXT_WORD_BREAK = Pattern.compile(" ");

	private final boolean toMorse;
	private final Pattern charBreak;
	private final Pattern wordBreak;
	private final String unknownSequence;
	private final Map<String, String> sequenceMap;

	/** @param toMorse Pass true for plaintext -> Morse, false for Morse -> plaintext. */
	@SuppressWarnings({"StringConcatenation", "StringConcatenationMissingWhitespace"})
	public MorseCodeConverter(boolean toMorse, MorseCodeOptions options) {
		this.toMorse = toMorse;
		wordBreak = options.wordBreak;
		charBreak = options.charBreak;
		unknownSequence = options.unknownSequence;
		String i = options.dit;
		String a = options.dah;
		String[] morse = new String[]{
			i + a,          a + i + i + i,
			a + i + a + i,  a + i + i,
			i,              i + i + a + i,
			a + a + i,      i + i + i + i,
			i + i,          i + a + a + a,
			a + i + a,      i + a + i + i,
			a + a,          a + i,
			a + a + a,      i + a + a + i,
			a + a + i + a,  i + a + i,
			i + i + i,      a,
			i + i + a,      i + i + i + a,
			i + a + a,      a + i + i + a,
			a + i + a + a,  a + a + i + i
		};
		sequenceMap = toMorse ? genSequenceMap(getUppercase(), morse) : genSequenceMap(morse, getUppercase());
	}

	@SuppressWarnings("ArrayLengthInLoopCondition")
	private static Map<String, String> genSequenceMap(String[] keys, String[] values) {
		Map<String, String> toReturn = new HashMap<>(HM_SIZE);
		for (int i = 0; i < keys.length; i++) toReturn.put(keys[i], values[i]);
		return toReturn;
	}

	public final TransformationType getTransformType() {
		return TransformationType.SimpleDigital;
	}

	public final String performOn(String s) {
		StringJoiner sj = new StringJoiner(toMorse ? wordBreak.pattern() : PLAINTEXT_WORD_BREAK.pattern());
		Arrays.stream(toMorse ? PLAINTEXT_WORD_BREAK.split(s) : wordBreak.split(s)).forEachOrdered(w -> {
			StringJoiner sj1 = new StringJoiner(toMorse ? charBreak.pattern() : "");
			if (toMorse) for (char c : w.toCharArray()) sj1.add(sequenceMap.getOrDefault(String.valueOf(c), String.valueOf(c)));
			else for (String c : charBreak.split(w)) sj1.add(sequenceMap.getOrDefault(c, unknownSequence));
			sj.add(sj1.toString());
		});
		return sj.toString();
	}

	@SuppressWarnings({"FieldHasSetterButNoGetter", "PackageVisibleField"})
	public static class MorseCodeOptions {
		String dit = ".";
		String dah = "-";
		Pattern charBreak = Pattern.compile(" ");
		Pattern wordBreak = Pattern.compile(" / ");
		String unknownSequence = "?";

		public MorseCodeOptions setDit(String s) {
			dit = s; return this; }
		public MorseCodeOptions setDah(String s) {
			dah = s; return this; }
		public MorseCodeOptions setCharBreak(Pattern p) {
			charBreak = p; return this; }
		/** @param s RegEx, using anything but literal characters may impact performance. */
		public MorseCodeOptions setCharBreak(String s) { return setCharBreak(Pattern.compile(s)); }
		public MorseCodeOptions setWordBreak(Pattern p) {
			wordBreak = p; return this; }
		/** @param s RegEx, using anything but literal characters may impact performance. */
		public MorseCodeOptions setWordBreak(String s) { return setWordBreak(Pattern.compile(s)); }
		/** @param s To be inserted into the output when a morse sequence is unrecognized. */
		public MorseCodeOptions setUnknownSequence(String s) {
			unknownSequence = s; return this; }
	}
}
