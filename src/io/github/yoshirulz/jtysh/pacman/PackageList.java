package io.github.yoshirulz.jtysh.pacman;

import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * @author YoshiRulz
 * @version 2017-12-11/00
 */
public class PackageList {
	private ArrayList<Package> value;

	public PackageList(ArrayList<Package> p) {
		value = p;
	}
	public PackageList(String s) {
		for (String p : s.split(" ")) value.add(new BasicPackage(p));
	}

	public String toString() {
		StringJoiner j = new StringJoiner(" ");
		for (Package p : value) j.add(p.getName());
		return j.toString();
	}
}
