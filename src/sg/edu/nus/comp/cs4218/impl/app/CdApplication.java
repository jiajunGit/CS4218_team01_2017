package sg.edu.nus.comp.cs4218.impl.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.CdException;

/**
 * The cd command changes the current working directory to the specified working directory. 
 * 
 * <p>
 * <b>Command format:</b> <code>cd PATH </code>
 * <dl>
 * <dt>PATH</dt>
 * <dd>specifies a relative directory path</dd>
 * </p>
 */

public class CdApplication implements Application {
	
	private static final String ERROR_EXP_NULL_ARGS = "Null argument detected";
	private static final String ERROR_EXP_UNKNOWN_DIR = "Unknown current directory";
	private static final String ERROR_EXP_DIRECTORY_UNSPECIFIED = "No relative directory specified";
	private static final String ERROR_EXP_ONE_ARG = "Only 1 argument is expected";
	private static final String ERROR_EXP_DIRECTORY_DOES_NOT_EXIST = "Directory does not exist";
	
	/**
	 * Runs the cd application with the specified arguments.
	 * 
	 * @param args
	 *            Array of arguments for the application.
	 * @param stdin
	 *            An InputStream, not used.
	 * @param stdout
	 *            An OutputStream. Elements of args will be output to stdout,
	 *            separated by a space character.
	 * 
	 * @throws DirectoryNotFoundException
	 *             If an I/O exception occurs.
	 */
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws CdException {
		if (args == null){
			throw new CdException(ERROR_EXP_NULL_ARGS);
		}
		if( Environment.currentDirectory == null || !isCurrentDirectoryValid() ) {
			throw new CdException(ERROR_EXP_UNKNOWN_DIR);
		}
		if (args.length == 0){
			throw new CdException(ERROR_EXP_DIRECTORY_UNSPECIFIED);
		}
		if (args.length > 1){
			throw new CdException(ERROR_EXP_ONE_ARG);
		}
		
		changeDirectory(args[0]);
		
	}

	
	/**
	 * @param arg specifies the directory to change to
	 */
	private void changeDirectory(String arg) throws CdException{
		boolean isDir = false;
		File changeDirectory = new File(Environment.currentDirectory + File.separator +  arg);
		try {
			changeDirectory = changeDirectory.getCanonicalFile();
		} catch (IOException e) {
			isDir = false;
		}
		
		if (changeDirectory.isDirectory()){
			Environment.currentDirectory = changeDirectory.getAbsolutePath();
			isDir = true;
			return;
		}
		else{
			changeDirectory = new File(arg);
			try {
				changeDirectory = changeDirectory.getCanonicalFile();
			} catch (IOException e) {
				isDir = false;
			}
			if (changeDirectory.isDirectory()){
				Environment.currentDirectory = changeDirectory.getAbsolutePath();
				isDir = true;
				return;
			}
		}
		
		if (!isDir){
			throw new CdException(ERROR_EXP_DIRECTORY_DOES_NOT_EXIST);
		}
	}
	
	/**
	 * Returns true if Environment.currentDirectory is valid, false, otherwise 
	 */
	private boolean isCurrentDirectoryValid() {
		File dir = new File(Environment.currentDirectory);
		return dir.isDirectory();
	}
}
