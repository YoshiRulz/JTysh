package io.github.yoshirulz.jtysh;

/**
 * @author YoshiRulz
 * @version 2017-11-21/00
 */
public enum JTyshInternalError {
	CannotFinishTempfileRead("TODO message");

	public final String message;

	JTyshInternalError(String s) {
		message = s;
	}
}