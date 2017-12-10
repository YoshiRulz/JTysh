#!/bin/sh
# v2017-12-10/01

src=src
pkg=$src/io/github/yoshirulz/jtysh
inst=$pkg/JTyshInstantiation.java
out=out/production/JTysh
mkdir -p $out
cat $pkg/header.java.txt >$inst

case "$1" in
	"-f")
		awk '{print"\t\t"$0}' "$2" >>$inst;;
	"-i")
		while read l; do
			if [ -n "$l" ]; then printf "\t\t%s\n" "$l" >>$inst; else break; fi
		done;;
	*)
		for l in "$@"; do
			if [ -n "$l" ]; then printf "\t\t%s\n" "$l" >>$inst; fi
		done;;
esac

cat $pkg/footer.java.txt >>$inst
javac -sourcepath $src -d $out $pkg/Main.java
java -classpath $out io.github.yoshirulz.jtysh.Main
