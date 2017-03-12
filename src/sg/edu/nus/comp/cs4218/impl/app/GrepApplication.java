package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Symbol;
import sg.edu.nus.comp.cs4218.Utility;
import sg.edu.nus.comp.cs4218.app.Grep;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.GrepException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

/**
 * The grep command searches for lines containing a match to a specified
 * pattern. The output of the command is the list of the lines. Each line is
 * printed followed by a newline.
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
public class GrepApplication implements Grep {

    public static final String EXP_USAGE = "Usage: grep PATTERN [FILE]...";

    public static final String ERROR_EXP_NULL_ARGS = "Null arguments";
    public static final String ERROR_EXP_INVALID_OUTSTREAM = "OutputStream not provided";
    public static final String ERROR_EXP_INVALID_INSTREAM = "InputStream not provided";
    public static final String ERROR_EXP_INVALID_PATTERN = "Invalid pattern specified";
    public static final String ERROR_EXP_INVALID_FILE = "Invalid file specified";
    public static final String ERROR_EXP_INVALID_ARGS_COUNT = "Invalid number of arguments specified";
    public static final String ERROR_EXP_INTERNAL = "Internal error occurred";

    private static final int NEW_LINE_LENGTH = Symbol.NEW_LINE_S.length();
    
    private static final int TEMP_BUF_SZ = 10000;
    private byte[] tempBuf = new byte[TEMP_BUF_SZ];

    /**
     * Runs the grep application with the specified arguments.
     * 
     * @param args
     *      Array of arguments for the application. First array element is
     *      a regular expression in JAVA format. Subsequent array
     *      element(s) is/are the path(s) to a file(s). If no files are
     *      specified stdin is used.
     * @param stdin
     *      An InputStream. The input for the command is read from this
     *      inputStream if no files are specified.
     * @param stdout
     *      An OutputStream. The output of the command is written to this
     *      OutputStream.
     * 
     * @throws GrepException
     *      If null arguments are specified, or the regular expression
     *      pattern specified is invalid, or the file(s) specified do not
     *      exist or are unreadable or the number of arguments specified
     *      is not supported.
     */
    @Override
    public void run(String[] args, InputStream stdin, OutputStream stdout) throws GrepException {

        if (args == null) {
            throw new GrepException(ERROR_EXP_NULL_ARGS);
        }
        if (stdout == null) {
            throw new GrepException(ERROR_EXP_INVALID_OUTSTREAM);
        }

        String outputStr;

        if (args.length == 1) {
            outputStr = grepFromStdin(args[0], stdin);
        } else if (args.length == 2) {
            outputStr = grepFromOneFile(args[0], args[1]);
        } else if (args.length > 2) {
            outputStr = grepFromMultipleFiles(args);
        } else {
            throw new GrepException(EXP_USAGE);
        }

        try {
            stdout.write(outputStr.getBytes());
            stdout.flush();
        } catch (IOException e) {
            throw new GrepException(ERROR_EXP_INVALID_OUTSTREAM);
        }
    }

    /**
     * Returns a StringBuilder object containing the contents read from the
     * stdin specified.
     * 
     * @param stdin
     *      An InputStream.
     * 
     * @throws GrepException
     *      If the specified input stream is invalid
     * 
     * @return 
     *      A StringBuilder object that holds the contents read from the
     *      stdin specified.
     */
    private StringBuilder readContentFromStdin(InputStream stdin) throws GrepException {

        int bytesRead = 0;
        StringBuilder content = new StringBuilder();
        while (true) {
            try {
                bytesRead = stdin.read(tempBuf);
                if (bytesRead <= -1) {
                    break;
                }
                content.append(new String(tempBuf, 0, bytesRead));
            } catch (IOException e) {
                throw new GrepException(ERROR_EXP_INVALID_INSTREAM);
            } catch (NullPointerException e) {
                throw new GrepException(ERROR_EXP_INTERNAL);
            }
        }
        return content;
    }
    
    private boolean endsWith( StringBuilder content, String suffix ) {
        
        if( content == null || suffix == null ){
            return false;
        }
        
        int suffixStart = content.length() - suffix.length();
        if(suffixStart < 0){
            return false;
        }
        
        for( int i = suffixStart, j = 0, length = content.length(); i < length; ++i ){
            if(content.charAt(i) != suffix.charAt(j++)){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns all lines within str that contains the regex string pattern
     * matched by regxMatcher.
     * 
     * @param str
     *      The StringBuilder object used to locate the regex string
     *      pattern.
     * @param regxMatcher
     *      The matcher object used to locate the regex string pattern
     *      within str.
     * @param prefixStr
     *      The string to prepend the output lines containing the regex
     *      string pattern.
     * @param output
     *      The StringBuilder object holding all the lines from str
     *      containing the regex string pattern matched by regxMatcher.
     */
    private void findAllRegex(StringBuilder str, Matcher regxMatcher, String prefixStr, StringBuilder output) {

        int prevStart = -NEW_LINE_LENGTH;
        int prevEnd = -NEW_LINE_LENGTH;
        int startOfLine;
        int endOfLine;
        int newLineIdx;

        while (regxMatcher.find()) {

            int start = regxMatcher.start();
            int end = regxMatcher.end();
            
            if (start < 0 || end <= start || (start >= prevStart && end <= prevEnd)) {
                continue;
            }

            newLineIdx = str.lastIndexOf(Symbol.NEW_LINE_S, start - 1);
            if (newLineIdx > -1) {
                startOfLine = newLineIdx + NEW_LINE_LENGTH;
            } else {
                startOfLine = prevEnd + NEW_LINE_LENGTH;
            }

            newLineIdx = str.indexOf(Symbol.NEW_LINE_S, end);
            if (newLineIdx > -1) {
                endOfLine = newLineIdx;
            } else {
                endOfLine = str.length();
            }

            output.append(prefixStr);
            output.append(str.substring(startOfLine, endOfLine));
            output.append(Symbol.NEW_LINE_S);

            prevStart = startOfLine;
            prevEnd = endOfLine;
        }
    }
    
    private void findAllLines(StringBuilder str, String prefixStr, StringBuilder output) {
        
        int findStart = 0;
        
        while( findStart < str.length() ){
            
            int newLineStart = str.indexOf(Symbol.NEW_LINE_S, findStart);
            
            if(newLineStart < 0){
                output.append(prefixStr);
                output.append(str.substring(findStart));
                output.append(Symbol.NEW_LINE_S);
                break;
            }
            
            output.append(prefixStr);
            output.append(str.substring(findStart, newLineStart));
            output.append(Symbol.NEW_LINE_S);
            
            findStart = newLineStart + Symbol.NEW_LINE_S.length();
        }
    }

    /**
     * Returns string containing lines which match the specified pattern in
     * Stdin.
     * 
     * @param pattern
     *      The specified regex pattern string.
     * @param stdin
     *      The input stream used to find the specified regex pattern.
     * 
     * @throws GrepException
     *      If the specified input stream is invalid or an invalid
     *      pattern is specified
     * 
     * @return 
     *      A String containing all lines within input stream that contains
     *      the specified regex pattern delimited by a newline character.
     */
    public String grepFromStdin(String pattern, InputStream stdin) throws GrepException {

        if (pattern == null) {
            throw new GrepException(ERROR_EXP_INVALID_PATTERN);
        }
        if (stdin == null) {
            throw new GrepException(ERROR_EXP_INVALID_INSTREAM);
        }

        StringBuilder content = readContentFromStdin(stdin);

        StringBuilder output = new StringBuilder();
        if(pattern.isEmpty()){
            findAllLines(content, Symbol.EMPTY_S, output);
            if( output.length() > 0 && endsWith(output, Symbol.NEW_LINE_S) ){
                output.setLength(output.length() - Symbol.NEW_LINE_S.length());
            }
            return output.toString();
        }
        
        Pattern regxPattern = null;
        try {
            regxPattern = Pattern.compile(pattern);
        } catch (PatternSyntaxException e) {
            throw new GrepException(ERROR_EXP_INVALID_PATTERN);
        }

        Matcher regxMatcher = regxPattern.matcher(content);
        
        findAllRegex(content, regxMatcher, Symbol.EMPTY_S, output);
        if( output.length() > 0 && endsWith(output, Symbol.NEW_LINE_S) ){
            output.setLength(output.length() - Symbol.NEW_LINE_S.length());
        }
        return output.toString();
    }

    /**
     * Returns string containing lines which match the specified pattern in the
     * given file.
     * 
     * @param pattern
     *      The specified regex pattern string.
     * @param fileName
     *      The name of the file whose contents are used to find the
     *      specified regex pattern.
     * 
     * @throws GrepException
     *      If the specified file name is/are invalid or an invalid
     *      pattern is specified
     * 
     * @return 
     *      A String containing all lines within the file that contains the
     *      specified regex pattern delimited by a newline character.
     */
    public String grepFromOneFile(String pattern, String fileName) throws GrepException {

        if (fileName == null || fileName.length() <= 0) {
            throw new GrepException(ERROR_EXP_INVALID_FILE);
        }

        BufferedInputStream bis = null;
        FileInputStream fs = null;
        String outputStr;
        try {

            fileName = Environment.getAbsPath(fileName);
            
            if(fileName.isEmpty() || !Environment.isFile(fileName)){
                throw new GrepException(ERROR_EXP_INVALID_FILE);
            }

            fs = new FileInputStream(fileName);
            bis = new BufferedInputStream(fs);

            outputStr = grepFromStdin(pattern, bis);

        } catch (IOException e) {
            throw new GrepException(ERROR_EXP_INVALID_FILE);
        } catch (SecurityException e) {
            throw new GrepException(ERROR_EXP_INVALID_FILE);
        } finally{
            Utility.closeStdin(fs);
            Utility.closeStdin(bis);
        }
        return outputStr;
    }

    /**
     * Returns string containing lines which match the specified pattern in the
     * given files
     * 
     * @param args
     *      Array of arguments for the application. First array element is
     *      a regular expression in JAVA format. Subsequent array
     *      element(s) is/are the path(s) to a file(s).
     * 
     * @throws GrepException
     *      If null arguments are specified, or the regular expression
     *      pattern specified is invalid, or the file(s) specified do not
     *      exist or are unreadable or the number of arguments specified
     *      is not supported.
     * 
     * @return 
     *      A String containing all lines within the file(s) that contain the
     *      specified regex pattern delimited by a newline character
     */
    public String grepFromMultipleFiles(String[] args) throws GrepException {

        if (args == null) {
            throw new GrepException(ERROR_EXP_NULL_ARGS);
        }
        if (args.length < 2) {
            throw new GrepException(ERROR_EXP_INVALID_ARGS_COUNT);
        }
        if( args.length == 2 ) {
            return grepFromOneFile( args[0], args[1] );
        }

        String pattern = args[0];

        if (pattern == null) {
            throw new GrepException(ERROR_EXP_INVALID_PATTERN);
        }

        BufferedInputStream bis = null;
        FileInputStream fs = null;
        String out;
        
        try {

            Pattern regxPattern = null;
            if(!pattern.isEmpty()){
                regxPattern = Pattern.compile(pattern);
            }

            StringBuilder output = new StringBuilder();

            for (int i = 1; i < args.length; ++i) {

                String fileName = args[i];

                if (fileName == null || fileName.length() <= 0) {
                    throw new GrepException(ERROR_EXP_INVALID_FILE);
                }

                fileName = Environment.getAbsPath(fileName);
                
                if(fileName.isEmpty() || !Environment.isFile(fileName)){
                    throw new GrepException(ERROR_EXP_INVALID_FILE);
                }
                
                fs = new FileInputStream(fileName);
                bis = new BufferedInputStream(fs);

                StringBuilder content = readContentFromStdin(bis);

                Utility.closeStdin(fs);
                fs = null;
                
                Utility.closeStdin(bis);
                bis = null;
                
                if(!pattern.isEmpty()){
                    Matcher regxMatcher = regxPattern.matcher(content);
                    findAllRegex(content, regxMatcher, fileName + ": ", output);
                } else {
                    findAllLines(content, fileName + ": ", output);
                } 
            }
            
            if( output.length() > 0 && endsWith(output, Symbol.NEW_LINE_S) ){
                output.setLength(output.length() - Symbol.NEW_LINE_S.length());
            }
            
            out = output.toString();
            
        } catch (IOException e) {
            throw new GrepException(ERROR_EXP_INVALID_FILE);
        } catch (SecurityException e) {
            throw new GrepException(ERROR_EXP_INVALID_FILE);
        } catch (PatternSyntaxException e) {
            throw new GrepException(ERROR_EXP_INVALID_PATTERN);
        } finally {
            Utility.closeStdin(fs);
            Utility.closeStdin(bis);
        }
        return out;
    }
    
    @Override
    public String grepFromStdin(String args) {
        ShellImpl shell = new ShellImpl();
        try {
            return shell.parseAndEvaluate(args);
        } catch (AbstractApplicationException | ShellException e) {
            return e.getMessage();
        }
    }

    @Override
    public String grepFromOneFile(String args) {
        ShellImpl shell = new ShellImpl();
        try {
            return shell.parseAndEvaluate(args);
        } catch (AbstractApplicationException | ShellException e) {
            return e.getMessage();
        }
    }

    @Override
    public String grepFromMultipleFiles(String args) {
        ShellImpl shell = new ShellImpl();
        try {
            return shell.parseAndEvaluate(args);
        } catch (AbstractApplicationException | ShellException e) {
            return e.getMessage();
        }
    }

    @Override
    public String grepInvalidPatternInStdin(String args) {
        ShellImpl shell = new ShellImpl();
        try {
            return shell.parseAndEvaluate(args);
        } catch (AbstractApplicationException | ShellException e) {
            return e.getMessage();
        }
    }

    @Override
    public String grepInvalidPatternInFile(String args) {
        ShellImpl shell = new ShellImpl();
        try {
            return shell.parseAndEvaluate(args);
        } catch (AbstractApplicationException | ShellException e) {
            return e.getMessage();
        }
    }
}