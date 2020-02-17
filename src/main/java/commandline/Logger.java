package commandline;

import java.io.PrintStream;

public class Logger {
	PrintStream out;
	String separator;
	boolean logMode; // disable logging if false
	
	// if fileName exists truncate to size 0, otherwise create new file with fileName
	Logger(PrintStream out, boolean logMode) {
		if(logMode && out == null) throw new NullPointerException("Log destination cannot be null.");
		this.out = out;
		this.separator = "----------"; // Do not change, part of specification
		this.logMode = logMode;
	}
		
	// add characters to buffer without a newline
	public void append(String source) {
		if(!logMode) return;
		if(source == null) throw new NullPointerException("Attempted to add Null String to Logger.");
		out.print(source);
	}
	
	// add characters to buffer with a newline
	public void appendln(String source) {
		this.append(source);
		this.append("\n");
	}
	
	// Add line separator to buffer and flush characters to stream destination
	public void flush() {
		if(!logMode) return;
		this.appendln(this.separator);
		out.flush();
	}
}
