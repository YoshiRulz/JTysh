package io.github.yoshirulz.jtysh;

/**
 * @author YoshiRulz
 * @version 2017-11-21/00
 */
public enum JTyshInternalError {
	CannotFinishTempfileRead("TODO message"),
	InstanceInterrupted("The sub-process was interrupted (you might have pressed ^C)!");

	public final String message;

	JTyshInternalError(String s) {
		message = s;
	}
}