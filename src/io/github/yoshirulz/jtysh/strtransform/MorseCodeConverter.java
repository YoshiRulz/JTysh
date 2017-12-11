package io.github.yoshirulz.jtysh.strtransform;

import io.github.yoshirulz.jtysh.strtransform.StringTransforms.StringTransform1To1;
import io.github.yoshirulz.jtysh.strtransform.StringTransforms.TransformationType;

import java.util.*;
import java.util.regex.Pattern;

import static io.github.yoshirulz.jtysh.strtransform.StringTransforms.LatinCharset.getUppercase;

/**
 * @author YoshiRulz
 * @version 2017-11-23/00
 */
@SuppressWarnings("WeakerAccess")
public class MorseCodeConverter implements StringTransform1To1 {
	private static final String DEFAULT_DIT = ".";
	private static final String DEFAULT_DAH = "-";
	private static final String DEFAULT_WORD_BREAK = " / ";
	private static final String DEFAULT_CHAR_BREAK = " ";
	private static final String DEFAULT_UNKNOWN = "?";

	private static final Pattern PLAINTEXT_WORD_BREAK = Pattern.compile(" ");

	public static final MorseCodeConverter DEFAULT_FROM_MORSE = new MorseCodeConverter(false, DEFAULT_DIT, DEFAULT_DAH, DEFAULT_WORD_BREAK, DEFAULT_CHAR_BREAK);
	public static final MorseCodeConverter DEFAULT_TO_MORSE = new MorseCodeConverter(true, DEFAULT_DIT, DEFAULT_DAH, DEFAULT_WORD_BREAK, DEFAULT_CHAR_BREAK);

	private final boolean toMorse;
	private final Pattern wordBreak;
	private final Pattern charBreak;
	private final String unknownSequence;
	private final Map<String, String> sequenceMap;

	/**
	 * @param toMorse Pass true for plaintext -> Morse, false for Morse -> plaintext.
	 * @param unknownSequence To be inserted into the output when a morse sequence is unrecognized.
	 */
	@SuppressWarnings({"StringConcatenation", "StringConcatenationMissingWhitespace"})
	public MorseCodeConverter(boolean toMorse, String dit, String dah, Pattern wordBreak, Pattern charBreak, String unknownSequence) {
		this.toMorse = toMorse;
		this.wordBreak = wordBreak;
		this.charBreak = charBreak;
		this.unknownSequence = unknownSequence;
		String[] morse = new String[]{
			dit + dah,
			dah + dit + dit + dit,
			dah + dit + dah + dit,
			dah + dit + dit,
			dit,
			dit + dit + dah + dit,
			dah + dah + dit,
			dit + dit + dit + dit,
			dit + dit,
			dit + dah + dah + dah,
			dah + dit + dah,
			dit + dah + dit + dit,
			dah + dah,
			dah + dit,
			dah + dah + dah,
			dit + dah + dah + dit,
			dah + dah + dit + dah,
			dit + dah + dit,
			dit + dit + dit,
			dah,
			dit + dit + dah,
			dit + dit + dit + dah,
			dit + dah + dah,
			dah + dit + dit + dah,
			dah + dit + dah + dah,
			dah + dah + dit + dit
		};
		sequenceMap = toMorse ? genSequenceMap(getUppercase(), morse) : genSequenceMap(morse, getUppercase());
	}
	/**
	 * @param toMorse Pass true for English -> Morse, false for Morse -> English.
	 */
	public MorseCodeConverter(boolean toMorse, String dit, String dah, Pattern wordBreak, Pattern charBreak) {
		this(toMorse, dit, dah, wordBreak, charBreak, toMorse ? null : DEFAULT_UNKNOWN);
	}
	/**
	 * @param toMorse Pass true for English -> Morse, false for Morse -> English.
	 * @param wordBreak RegEx, using anything but literal characters may impact performance.
	 * @param charBreak RegEx, using anything but literal characters may impact performance.
	 * @param unknownSequence To be inserted into the output when a morse sequence is unrecognized.
	 */
	public MorseCodeConverter(boolean toMorse, String dit, String dah, String wordBreak, String charBreak, String unknownSequence) {
		this(toMorse, dit, dah, Pattern.compile(wordBreak), Pattern.compile(charBreak), unknownSequence);
	}
	/**
	 * @param toMorse Pass true for English -> Morse, false for Morse -> English.
	 * @param wordBreak RegEx, using anything but literal characters may impact performance.
	 * @param charBreak RegEx, using anything but literal characters may impact performance.
	 */
	public MorseCodeConverter(boolean toMorse, String dit, String dah, String wordBreak, String charBreak) {
		this(toMorse, dit, dah, Pattern.compile(wordBreak), Pattern.compile(charBreak));
	}

	@SuppressWarnings({"ArrayLengthInLoopCondition", "MagicNumber"})
	private static Map<String, String> genSequenceMap(String[] keys, String[] values) {
		Map<String, String> toReturn = new HashMap<>(35);
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
}
