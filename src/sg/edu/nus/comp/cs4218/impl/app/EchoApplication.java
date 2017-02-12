package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.EchoException;

/**
 * The echo command writes its arguments separated by spaces and terminates by a
 * newline on the standard output.
 * 
 * <p>
 * <b>Command format:</b> <code>echo [ARG]...</code>
 * </p>
 */
public class EchoApplication implements Application {
	private final static String LINE_SEPARATOR = System.getProperty("line.separator");
	/**
	 * Runs the echo application with the specified arguments.
	 * 
	 * @param args
	 *            Array of arguments for the application.
	 * @param stdin
	 *            An InputStream, not used.
	 * @param stdout
	 *            An OutputStream. Elements of args will be output to stdout,
	 *            separated by a space character.
	 * 
	 * @throws EchoException
	 *             If an I/O exception occurs.
	 */
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws EchoException {
		if (args == null) {
			throw new EchoException("Null arguments");
		}
		if (stdout == null) {
			throw new EchoException("OutputStream not provided");
		}
		try {
			if (args.length == 0) {
				stdout.write(LINE_SEPARATOR.getBytes());
			} else {
				for (int i = 0; i < args.length; i++) {
					stdout.write(args[i].getBytes());
				    if(i!=args.length-1){
				    	stdout.write(" ".getBytes());//elements gets separated by a spacing
				    }
				}
				stdout.write(LINE_SEPARATOR.getBytes());
			}
		} catch (IOException e) {
			throw new EchoException("IOException");
		}
	}

}
