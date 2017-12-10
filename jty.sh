#!/bin/sh
# v2017-12-10/00

src=src
pkg=$src/io/github/yoshirulz/jtysh
inst=$pkg/JTyshInstantiation.java
out=out/production/JTysh

mkdir -p $out

cat $pkg/header.java.txt >$inst
for l in "$1" "$2" "$3" "$4" "$5" "$6" "$7" "$8" "$9"; do
	if [ "$l" != "" ]; then printf "\t\t%s\n" "$l" >>$inst; fi
done
cat $pkg/footer.java.txt >>$inst

javac -sourcepath $src -d $out $pkg/Main.java
java -classpath $out io.github.yoshirulz.jtysh.Main
