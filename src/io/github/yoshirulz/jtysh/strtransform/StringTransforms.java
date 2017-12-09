package io.github.yoshirulz.jtysh.strtransform;

/**
 * @author YoshiRulz
 * @version 2017-11-23/00
 */
public class StringTransforms {
	public static final String[] aToZLower = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
	public static final String[] aToZUpper = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

	enum TransformationType {
		Cipher, SimpleDigital,
		RegEx,
		Relex,
		Unknown
	}

	public interface StringTransformation {
		TransformationType getTransformType();
	}

	public interface StringTransform1To1 extends StringTransformation {
		String performOn(String s);
	}

	public interface StringTransform2To1 extends StringTransformation {
		String performOn(String s, String s1);
	}

	public interface StringTransform2To2 extends StringTransformation {
		String[] performOn(String s, String s1);
	}
}
