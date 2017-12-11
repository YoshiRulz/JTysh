package io.github.yoshirulz.jtysh.pacman;

/**
 * @author YoshiRulz
 * @version 2017-12-11/00
 */
public class BasicPackage implements Package {
	private final String name;

	public BasicPackage(String s) {
		name = s;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return getName();
	}
}
