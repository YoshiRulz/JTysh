package io.github.yoshirulz.jtysh.strtransform;

import io.github.yoshirulz.jtysh.strtransform.StringTransforms.StringTransform1To1;
import io.github.yoshirulz.jtysh.strtransform.StringTransforms.TransformationType;

/**
 * @author YoshiRulz
 * @version 2017-11-23/00
 */
public class SimpleCiphers {
	public static class CaesarCipher implements StringTransform1To1 {
		private final int shift;

		public CaesarCipher(int shift) {
			this.shift = shift % 26;
		}

		public TransformationType getTransformType() {
			return TransformationType.Cipher;
		}

		public String performOn(String s) {
			StringBuilder toReturn = new StringBuilder();
			int temp;
			for (char c : s.toCharArray()) {
				temp = c;
				if (64 < temp && temp < 91) {
					toReturn.append((char) (65 + (temp - 65 + shift) % 26));
				} else if (96 < temp && temp < 123) {
					toReturn.append((char) (97 + (temp - 97 + shift) % 26));
				} else {
					toReturn.append(c);
				}
			}
			return toReturn.toString();
		}
	}
}
