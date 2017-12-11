package io.github.yoshirulz.jtysh.pacman;

/**
 * @author YoshiRulz
 * @version 2017-12-11/00
 */
public interface Package {
	String getName();

	enum PackageFlags {
		Metapackage
	}
}
