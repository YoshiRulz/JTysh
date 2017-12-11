package io.github.yoshirulz.jtysh.shell;

/**
 * @author YoshiRulz
 * @version 2017-12-11/00
 */
public enum UnixShell {
	bash, csh, fish, ksh, zsh,
	Ammonite, JTysh
//
//	class CustomShell implements UnixShell {
//		private final String stdExecPath;
//		private final String execPath;
//
//		public CustomShell(String stdExecPath, String execPath) {
//			this.stdExecPath = stdExecPath;
//			this.execPath = execPath;
//		}
//
//		public String getStdExecPath() {
//			return stdExecPath;
//		}
//		public String getExecPath() {
//			return execPath;
//		}
//	}
//
//	@SuppressWarnings("HardCodedStringLiteral")
//	enum CommonShells implements UnixShell {
//		Bash("/bin/bash", "~/.bash_history"), Csh("/bin/csh"), Fish("/bin/fish"), Ksh("/bin/ksh"), Zsh("/bin/zsh");
////		Ammonite("/usr/local/bin/amm")
////		JTysh("/usr/local/bin/jtysh")
//
//		private final String stdExecPath;
//		private final String execPath;
//		private final File historyFile;
//
//		CommonShells(String stdExecPath, String historyPath) {
//			this.stdExecPath = stdExecPath;
//			execPath = stdExecPath;
//			historyFile = new File(historyPath);
//		}
//		CommonShells(String stdExecPath) {
//			this(stdExecPath, null);
//		}
//
//		public String getStdExecPath() {
//			return stdExecPath;
//		}
//		public String getExecPath() {
//			return execPath;
//		}
//		public File getHistoryFile() {
//			return historyFile;
//		}
//	}
//
//	String getStdExecPath();
//	String getExecPath();
}
