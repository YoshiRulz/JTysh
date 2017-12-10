package io.github.yoshirulz.jtysh.pacman;

/**
 * @author YoshiRulz
 * @version 2017-11-19/00
 */
public interface Package {
	String getName();

	enum PackageFlags {
		Metapackage
	}
}
