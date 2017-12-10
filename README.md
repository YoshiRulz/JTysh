## JTysh

### Quick start
```
git clone https://github.com/YoshiRulz/JTysh.git && cd JTysh
./jty.sh "long time = System.nanoTime();" "System.out.println(time);"
./jty.sh '$.echo($.$("uname -a").t()).r();'
```
Mind your escapes! Under bash (and others?) $vars and $(subshells) aren't evaluated inside single quotes.

### .java instead of .sh? No way!
Java, despite its reputation, is capable of allowing programmers to express their program's goals succinctly.

The following example shows that, as well as being (arguably) easier to parse by eye, the (for lack of a better term) "natlang'd" Java can better capture the relationship between programs and their arguments (by adding syntax, esp. nesting in parameters).
These properties are not limited to just Java or to the JVM languages - they are true of many common languages.

For reference, all four of these examples simply take the URI that was the previous command's first argument (say, from `echo <uri>`) and pass it to `git clone` as a repo. The verbose-but-functional Java:
```
new NoArgPipelineHead(new GitCloneCMD(
		GitRepoURI.fromURI(new URI(
			History.get().tail().awk(2).asString()
		))
	)).run();
```
After rewriting that as "natlang'd" Java using overloaded methods:
```
$.git.clone_($.history().tail().awk(2).s()).r();          <== 48 chars
```
...by comparing that to the equivalent POSIX bash:
```
git clone "$(history | tail -n 1 | awk "{print \\$3}")"   <== 55 chars
```
...or, incorrectly but commonly in bash:
```
git clone `history|tail -n1|awk {print\\$3}`              <== 44 chars
```
...it becomes clear that very little brevity needs to be sacrificed for great gains in clarity.
The potential benefits only grow when you consider how often shell scripts need to invoke either:
1. (for basic arithmetic at least) `bc`, or constructs like the laughable 11-char `i=$(($i+1))` increment operator;
2. other languages' interpreters - Ruby, Python, or whatever the programmer is comfortable with (this is an obvious red flag to me);
3. chains of `printf`, `cat`, `head`, `tail`, `cut`, `fold`, `tr`, `grep`, `sed`, `awk`, and more, sometimes even nested, and don't forget output redirection!
All that, simply because the very language that forms the foundation of our beloved Unix-like systems is not only incapable of working with files and strings (i.e. its purpose), but also incapable of the most basic numerical operations.

JTysh is not the best idea. It may not even be a good idea. All it promises to be is a *better* idea, and I'd love to see what other better ideas the open-source community can come up with. Which leads me to...

### Better ideas
The two I've heard of are @lihaoyi's Ammonite-Shell (part of [Ammonite](https://github.com/lihaoyi/ammonite)), which is the Scala-based older brother of this project, and [@Icelandjack's TySh](https://github.com/Icelandjack/TySh), a similar idea relying on the Haskell environment rather than the JVM.
