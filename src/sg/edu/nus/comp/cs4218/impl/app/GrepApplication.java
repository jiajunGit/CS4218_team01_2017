package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.GrepException;

/**
 * The grep command searches for lines containing a match to a specified pattern. The
 * output of the command is the list of the lines. Each line is printed followed by a newline.
 * 
 * <p>
 * <b>Command format:</b> <code>grep PATTERN [FILE]...</code>
 * <dl>
 * <dt>PATTERN</dt>
 * <dd>specifies a regular expression in JAVA format.</dd>
 * <dt>FILE</dt>
 * <dd>the name of the file(s). If no files are specified, use stdin.</dd>
 * </dl>
 * </p>
 */
public class GrepApplication implements Application {

	public static final String EXP_USAGE = "Usage: grep PATTERN [FILE]...";
	
	public static final String ERROR_EXP_NULL_ARGS = "Null arguments";
	public static final String ERROR_EXP_INVALID_OUTSTREAM = "OutputStream not provided";
	public static final String ERROR_EXP_INVALID_INSTREAM = "InputStream not provided";
	public static final String ERROR_EXP_INVALID_PATTERN = "Invalid pattern specified";
	public static final String ERROR_EXP_INVALID_FILE = "Invalid file specified";
	public static final String ERROR_EXP_INVALID_ARGS_COUNT = "Invalid number of arguments specified";
	public static final String ERROR_EXP_INTERNAL = "Internal error occurred";
	
	private static final int TEMP_BUF_SZ = 10000;
	private byte[] tempBuf = new byte[TEMP_BUF_SZ]; 
	
	/**
	 * Runs the grep application with the specified arguments.
	 * 
	 * @param args
	 *            Array of arguments for the application. First array element is a 
	 *            regular expression in JAVA format. Subsequent array element(s) is/are
	 *            the path(s) to a file(s). If no files are specified stdin is used.
	 * @param stdin
	 *            An InputStream. The input for the command is read from this
	 *            InputStream if no files are specified.
	 * @param stdout
	 *            An OutputStream. The output of the command is written to this
	 *            OutputStream.
	 * 
	 * @throws GrepException
	 * 			  If null arguments are specified, or the regular expression pattern specified is 
	 * 			  invalid, or the file(s) specified do not exist or are unreadable or the number of 
	 * 			  arguments specified is not supported.
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws GrepException {
		
		if( args == null ){
			throw new GrepException(ERROR_EXP_NULL_ARGS);
		}
		if( stdout == null ){
			throw new GrepException(ERROR_EXP_INVALID_OUTSTREAM);
		}
		
		String outputStr;
		
		if( args.length == 1 ){
			outputStr = grepFromStdin(args[0], stdin);
		}
		else if( args.length == 2 ) {
			outputStr = grepFromOneFile(args[0], args[1]);
		}
		else if( args.length > 2 ) {
			outputStr = grepFromMultipleFiles(args);
		}
		else{
			throw new GrepException(EXP_USAGE);
		}
		
		try {
			stdout.write(outputStr.getBytes());
			if(outputStr.isEmpty()){
				stdout.write("\n".getBytes());
			}
			stdout.flush();
		} 
		catch (IOException e) {
			throw new GrepException(ERROR_EXP_INVALID_OUTSTREAM);
		}
	}
	
	/**
	 * Returns a StringBuilder object containing the contents read from the stdin specified.
	 * 
	 * @param stdin
	 *      An InputStream.
	 * 
	 * @throws GrepException
	 *      If the specified input stream is invalid
	 * 
	 * @return 
	 * 		A StringBuilder object that holds the contents read from the stdin specified.
	 */
	private StringBuilder readContentFromStdin(InputStream stdin) throws GrepException {
		
		int bytesRead = 0;
		StringBuilder content = new StringBuilder();
		while(true){
			try {
				bytesRead = stdin.read(tempBuf);
				if(bytesRead <= -1) {
					break;
				}
				content.append(new String( tempBuf, 0, bytesRead ));
			}
			catch (IOException e) {
				throw new GrepException(ERROR_EXP_INVALID_INSTREAM);
			} 
			catch (NullPointerException e){
				throw new GrepException(ERROR_EXP_INTERNAL);
			}
		}
		return content;
	}
	
	/**
	 * Returns a boolean value indicating if the character is a newline character or not.
	 * 
	 * @param character
	 *            The character to be tested if it is a newline character or not..
	 * 
	 * @return a boolean value indicating if the character is a newline character or not.
	 */
	private boolean isNewLine( char character ) {
		return (character == '\n' || character == '\r');
	}
	
	/**
	 * Returns the index of the first occurrence of a newline character within str from start to end
	 * not inclusive of end.
	 * 
	 * @param str
	 *      The StringBuilder object used to find the first occurrence of a newline character.
	 * @param start
	 * 		The index to begin the search for the first occurrence of a newline character within str.
	 * @param end
	 * 		The index to end the search for the first occurrence of a newline character within str. Search
	 * 		include character at this index within str.
	 * 
	 * @return 
	 * 		The index where the first occurrence of a newline character within str is found.
	 */
	private int findFirstNewLine( StringBuilder str, int start, int end ) {
		if( start >= 0 && end < str.length() ){
			for( int i = start; i <= end; ++i ){
				if( isNewLine(str.charAt(i)) ){
					return i;
				}
			}
		}
		return -1;
	}
	
	/**
	 * Returns the index of the last occurrence of a newline character within str from start to end
	 * not inclusive of start.
	 * 
	 * @param str
	 *      The StringBuilder object used to find the last occurrence of a newline character.
	 * @param start
	 * 		The index to end the search for the last occurrence of a newline character within str. Search
	 * 		include character at this index within str.
	 * @param end
	 * 		The index to begin the search for the last occurrence of a newline character within str.
	 * 
	 * @return 
	 * 		The index where the last occurrence of a newline character within str is found.
	 */
	private int findLastNewLine( StringBuilder str, int start, int end ) {
		if( start >= 0 && end < str.length() ){
			for( int i = end; i >= start; --i ){
				if( isNewLine(str.charAt(i)) ){
					return i;
				}
			}
		}
		return -1;
	}
	
	/**
	 * Returns all lines within str that contains the regex string pattern matched by regxMatcher.
	 * 
	 * @param str
	 *          The StringBuilder object used to locate the regex string pattern.
	 * @param regxMatcher
	 * 			The matcher object used to locate the regex string pattern within str.
	 * @param prefixStr
	 * 			The string to prepend the output lines containing the regex string pattern.
	 * @param output
	 * 			The StringBuilder object holding all the lines from str containing the regex string pattern 
	 * 			matched by regxMatcher.
	 */
	private void findAllRegex( StringBuilder str, Matcher regxMatcher, String prefixStr, StringBuilder output ) {
		
		int prevStart = -1;
		int prevEnd = -1;
		int newLineIdx;
		
		while( regxMatcher.find() ){
			
			int start = regxMatcher.start();
			int end = regxMatcher.end();
			
			if( start < 0 || end <= start || (start >= prevStart && end <= prevEnd) ){
				continue;
			}

			int startOfLine = start;
			newLineIdx = findLastNewLine( str, prevEnd+1, start-1 );
			if( newLineIdx > -1 ){
				startOfLine = newLineIdx + 1;
			}
			else{
				startOfLine = prevEnd + 1;
			}
			
			int endOfLine = end;
			newLineIdx = findFirstNewLine( str, end, str.length()-1 );
			if( newLineIdx > -1 ){
				endOfLine = newLineIdx;
			}
			else{
				endOfLine = str.length();
			}
			
			output.append(prefixStr);
			output.append(str.substring(startOfLine, endOfLine));
			output.append('\n');
			
			prevStart = startOfLine;
			prevEnd = endOfLine;
		}
	}
	
	/**
	 * Returns string containing lines which match the specified pattern in Stdin.
	 * 
	 * @param pattern
	 * 		The specified regex pattern string.
	 * @param stdin
	 * 		The input stream used to find the specified regex pattern.
	 * 
	 * @throws GrepException
	 *      If the specified input stream is invalid or an invalid pattern is specified
	 * 
	 * @return
	 * 		A String containing all lines within input stream that contains the specified regex pattern
	 * 		delimited by a newline character.
	 */
	public String grepFromStdin(String pattern, InputStream stdin) throws GrepException {
		
		if( pattern == null || pattern.length() <= 0 ){
			throw new GrepException(ERROR_EXP_INVALID_PATTERN);
		}
		if( stdin == null ){
			throw new GrepException(ERROR_EXP_INVALID_INSTREAM);
		}
		
		StringBuilder content = readContentFromStdin(stdin);
		
		Pattern regxPattern = null;
		try{
			regxPattern = Pattern.compile(pattern);
		} 
		catch( PatternSyntaxException e ){
			throw new GrepException(ERROR_EXP_INVALID_PATTERN);
		}
		
		Matcher regxMatcher = regxPattern.matcher(content);
		StringBuilder output = new StringBuilder();
		findAllRegex( content, regxMatcher, "", output );
		
		return output.toString();
	}

	/**
	 * Returns string containing lines which match the specified pattern in the given file.
	 * 
	 * @param pattern
	 * 		The specified regex pattern string.
	 * @param fileName
	 * 		The name of the file whose contents are used to find the specified regex pattern.
	 * 
	 * @throws GrepException
	 *      If the specified file name is/are invalid or an invalid pattern is specified
	 * 
	 * @return
	 * 		A String containing all lines within the file that contains the specified regex pattern
	 * 		delimited by a newline character.
	 */
	public String grepFromOneFile(String pattern, String fileName) throws GrepException {
		
		if( pattern == null || pattern.length() <= 0 ){
			throw new GrepException(ERROR_EXP_INVALID_PATTERN);
		}
		if( fileName == null || fileName.length() <= 0 ){
			throw new GrepException(ERROR_EXP_INVALID_FILE);
		}
		
		BufferedInputStream bis = null;
		try {
			
			FileInputStream fs = new FileInputStream(fileName);
			bis = new BufferedInputStream(fs);
			
			String outputStr = grepFromStdin( pattern, bis );
			
			bis.close();
			fs.close();
			
			return outputStr;
		}
		catch (IOException e) {
			throw new GrepException(ERROR_EXP_INVALID_FILE);
		}
		catch ( SecurityException e ) {
			throw new GrepException(ERROR_EXP_INVALID_FILE);
		}
	}

	/**
	 * Returns string containing lines which match the specified pattern in the given files
	 * 
	 * @param args
	 *      Array of arguments for the application. First array element is a 
	 *      regular expression in JAVA format. Subsequent array element(s) is/are
	 *      the path(s) to a file(s).
	 *        
	 * @throws GrepException
	 *      If null arguments are specified, or the regular expression pattern specified is 
	 * 		invalid, or the file(s) specified do not exist or are unreadable or the number of 
	 * 		arguments specified is not supported.
	 *        
	 * @return
	 * 		A String containing all lines within the file(s) that contain the specified regex pattern
	 * 		delimited by a newline character
	 */
	public String grepFromMultipleFiles(String[] args) throws GrepException {
		
		if( args == null ){
			throw new GrepException(ERROR_EXP_NULL_ARGS);
		}
		if( args.length < 2 ){
			throw new GrepException(ERROR_EXP_INVALID_ARGS_COUNT);
		}
		
		String pattern = args[0];
		
		if( pattern == null || pattern.length() <= 0 ){
		    throw new GrepException(ERROR_EXP_INVALID_PATTERN);
		}
		
		BufferedInputStream bis = null;
		
		try {
			
			Pattern regxPattern = Pattern.compile(pattern);
			
			StringBuilder output = new StringBuilder();
			
			for( int i = 1; i < args.length; ++i ){
				
				String fileName = args[i];
				
				if( fileName == null || fileName.length() <= 0 ){
					throw new GrepException(ERROR_EXP_INVALID_FILE);
				}
				
				FileInputStream fs = new FileInputStream(fileName);
				bis = new BufferedInputStream(fs);
				
				StringBuilder content = readContentFromStdin(bis);
				
				bis.close();
				fs.close();
				
				Matcher regxMatcher = regxPattern.matcher(content);
				findAllRegex( content, regxMatcher, fileName + ": ", output );
			}
			
			return output.toString();
		}
		catch (IOException e) {
			throw new GrepException(ERROR_EXP_INVALID_FILE);
		}
		catch ( SecurityException e ) {
			throw new GrepException(ERROR_EXP_INVALID_FILE);
		}
		catch( PatternSyntaxException e ){
			throw new GrepException(ERROR_EXP_INVALID_PATTERN);
		}
	}
}