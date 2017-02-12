package sg.edu.nus.comp.cs4218.impl.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Symbol;
import sg.edu.nus.comp.cs4218.exception.PwdException;

/**
 * The pwd command reports the current working directory followed by a newline.
 * 
 * <p>
 * <b>Command format:</b> <code>pwd</code>
 * </p>
 */
public class PwdApplication implements Application {

	public static final String ERROR_EXP_INVALID_OUTSTREAM = "OutputStream not provided";
	public static final String ERROR_EXP_UNKNOWN_DIR = "Unknown current directory";
	
	/**
	 * Runs the pwd application with the specified arguments.
	 * 
	 * @param args
	 *            Array of arguments for this application will be ignored.
	 * @param stdin
	 *            An InputStream. The input stream for this application will be ignored.
	 * @param stdout
	 *            An OutputStream. The output of the command is written to this outputStream.
	 * 
	 * @throws PwdException
	 * 			  If current directory is invalid or the output stream specified is invalid.
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws PwdException {
		
		if( stdout == null ){
			throw new PwdException(ERROR_EXP_INVALID_OUTSTREAM);
		}
		
		if( Environment.currentDirectory == null || !isCurrentDirectoryValid() ) {
			throw new PwdException(ERROR_EXP_UNKNOWN_DIR);
		}
		
		try {
			stdout.write(Environment.currentDirectory.getBytes());
			stdout.write(Symbol.NEW_LINE_S.getBytes());
			stdout.flush();
		} 
		catch (IOException e) {
			throw new PwdException(ERROR_EXP_INVALID_OUTSTREAM);
		}
	}
	
	/**
	 * Checks if the current directory referenced by this application is valid or not.
	 * 
	 * @return
	 * 		A boolean value indicating if the current directory referenced by this application is valid or not.
	 */
	private boolean isCurrentDirectoryValid() {
		File dir = new File(Environment.currentDirectory);
		return dir.isDirectory();
	}
}
