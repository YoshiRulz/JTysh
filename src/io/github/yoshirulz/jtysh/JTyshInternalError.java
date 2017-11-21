package io.github.yoshirulz.jtysh;

/**
 * @author YoshiRulz
 * @version 2017-11-21/00
 */
public enum JTyshInternalError {
	CannotFindJavaParadox("Paradoxically, could not find the Java executable"),
	CannotFinishTempfileRead("TODO message"),
	InstanceInterrupted("The sub-process was interrupted (you might have pressed ^C)");

	public final String message;

	JTyshInternalError(String s) {
		message = s + "!";
	}
}