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
import sg.edu.nus.comp.cs4218.app.Sed;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

/**
 * The sed command Copies input file (or input stream) to stdout performing string replacement. 
 * For each line containing a match to a specified pattern (in JAVA format), replaces the matched 
 * substring with the specified string.
 * 
 * <p>
 * <b>Command format:</b> <code>sed REPLACEMENT [FILE]</code>
 * <dl>
 * <dt>REPLACEMENT</dt>
 * <dd>specifies replacement rule, as follows: (1) s/regexp/replacement/ – replace the first (in each 
 * line) substring matched by regexp with the string replacement. (2) s/regexp/replacement/g – replace 
 * all the substrings matched by regexp with the string replacement. Note that the symbols “/” used to 
 * separate regexp and replacement string can be substituted by any other symbols. For example, “s/a/b/” 
 * and “s|a|b|” are the same replacement rules. However, this separation symbol should not be used inside 
 * the regexp and the replacement string.</dd>
 * <dt>FILE</dt>
 * <dd>the name of the file(s). If no files are specified, use stdin.</dd>
 * </dl>
 * </p>
 */
public class SedApplication implements Sed {

    public static final String EXP_USAGE = "Usage: sed REPLACEMENT [FILE]";

    public static final String ERROR_EXP_NULL_ARGS = "Null arguments";
    public static final String ERROR_EXP_INVALID_OUTSTREAM = "OutputStream not provided";
    public static final String ERROR_EXP_INVALID_INSTREAM = "InputStream not provided";
    public static final String ERROR_EXP_INVALID_REPLACEMENT = "Invalid replacement specified";
    public static final String ERROR_EXP_INVALID_REGEX = "Invalid regex specified";
    public static final String ERROR_EXP_INVALID_FILE = "Invalid file specified";
    public static final String ERROR_EXP_INVALID_ARGS_COUNT = "Invalid number of arguments specified";
    public static final String ERROR_EXP_INTERNAL = "Internal error occurred";
    
    public static final String REPLACEMENT_PREFIX = "s";
    public static final String REPLACEMENT_ALL_SUBSTRING_SUFFIX = "g";
    
    private static final int TEMP_BUF_SZ = 10000;
    private byte[] tempBuf = new byte[TEMP_BUF_SZ];
    
    /**
     * Runs the sed application with the specified arguments.
     * 
     * @param args
     *      Array of arguments for the application. First array element is
     *      the replacement expression. Second array element is the path to a file. 
     *      If no files are specified stdin is used.
     * @param stdin
     *      An InputStream. The input for the command is read from this
     *      inputStream if no file is specified.
     * @param stdout
     *      An OutputStream. The output of the command is written to this
     *      OutputStream.
     * 
     * @throws SedException
     *      If null arguments are specified, or the replacement expression specified is 
     *      invalid, or the file specified do not exist or is unreadable or the number 
     *      of arguments specified is not supported.
     */
    @Override
    public void run(String[] args, InputStream stdin, OutputStream stdout) throws SedException {

        if (args == null) {
            throw new SedException(ERROR_EXP_NULL_ARGS);
        }
        if (stdout == null) {
            throw new SedException(ERROR_EXP_INVALID_OUTSTREAM);
        }
        
        String outputStr;
        
        if (args.length == 1) {
            outputStr = sedFromStdin(args[0], stdin);
        } else if (args.length == 2) {
            outputStr = sedFromOneFile(args[0], args[1]);
        } else {
            throw new SedException(EXP_USAGE);
        }
        
        try {
            stdout.write(outputStr.getBytes());
            stdout.write(Symbol.NEW_LINE_S.getBytes());
            stdout.flush();
        } catch (IOException e) {
            throw new SedException(ERROR_EXP_INVALID_OUTSTREAM);
        }
    }

    private StringBuilder readContentFromStdin(InputStream stdin) throws SedException {

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
                throw new SedException(ERROR_EXP_INVALID_INSTREAM);
            } catch (NullPointerException e) {
                throw new SedException(ERROR_EXP_INTERNAL);
            }
        }
        return content;
    }
    
    private String replaceFirstSubString( String regex, String replacement, StringBuilder content ) 
            throws SedException {
        
        if( regex.isEmpty() ){
            return content.toString();
        }
        
        try {
            
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);
            int findStart = 0;
            
            while( findStart < content.length() && matcher.find(findStart) ){
                
                int start = matcher.start();
                int end = matcher.end();
                
                content.replace(start, end, replacement);
                
                findStart = start + replacement.length();
                
                findStart = content.indexOf(Symbol.NEW_LINE_S, findStart);
                
                if( findStart < 0 ){
                    break;
                }
                
                findStart += Symbol.NEW_LINE_S.length();
            }
            
        } catch( PatternSyntaxException e ) {
            throw new SedException(ERROR_EXP_INVALID_REGEX);
        }
        return content.toString();
    }
    
    private String replaceAllSubString( String regex, String replacement, StringBuilder content ) 
            throws SedException {
        
        if( regex.isEmpty() ){
            return content.toString();
        }
        
        String out;
        
        try {
            
            Pattern.compile(regex);
            
            out = content.toString();
            out = out.replaceAll(regex, replacement);
            
        } catch( PatternSyntaxException e ) {
            throw new SedException(ERROR_EXP_INVALID_REGEX);
        }
        return out;
    }
    
    /**
     * Returns a string which has first substring matched by the regex specified
     * replaced by the replacement string specified if no g suffix is specified
     * in the given replacement expression or a string which has all substrings matched 
     * by the regex specified replaced by the replacement string specified if the g 
     * suffix is specified in the given replacement expression.
     * 
     * @param replacement
     *      The specified replacement expression string. The replacement rule is as follows: 
     *      (1) s/regexp/replacement/ – replace the first (in each line) substring matched by 
     *      regexp with the string replacement. (2) s/regexp/replacement/g – replace all the 
     *      substrings matched by regexp with the string replacement. Note that the symbols “/” 
     *      used to separate regexp and replacement string can be substituted by any other symbols. 
     *      For example, “s/a/b/” and “s|a|b|” are the same replacement rules. However, this separation 
     *      symbol should not be used inside the regexp and the replacement string.
     * @param stdin
     *      The input stream used to retrieve the content to work on.
     * 
     * @throws SedException
     *      If the specified input stream is invalid or an invalid replacement expression string is 
     *      specified
     * 
     * @return 
     *      A string which has first substring matched by the regex specified
     *      replaced by the replacement string specified if no g suffix is specified
     *      in the given replacement expression or a string which has all substrings matched 
     *      by the regex specified replaced by the replacement string specified if the g 
     *      suffix is specified in the given replacement expression.
     */
    public String sedFromStdin( String replacement, InputStream stdin ) throws SedException {
        
        if (replacement == null || replacement.length() <= 1 || !replacement.startsWith(REPLACEMENT_PREFIX) ) {
            throw new SedException(ERROR_EXP_INVALID_REPLACEMENT);
        }
        if (stdin == null) {
            throw new SedException(ERROR_EXP_INVALID_INSTREAM);
        }
        
        String originalSeparator = Character.toString(replacement.charAt(1));
        String separator = Pattern.quote(originalSeparator);
        
        String modifiedReplacement;
        if( replacement.endsWith(REPLACEMENT_ALL_SUBSTRING_SUFFIX) ){
            modifiedReplacement = replacement.substring(2, replacement.length() - 1);
        } else {
            modifiedReplacement = replacement.substring(2, replacement.length());
        }
        
        String[] args = modifiedReplacement.split(separator);
        
        String output;
        
        if( args.length == 2 && replacement.endsWith(originalSeparator) ){
            StringBuilder content = readContentFromStdin(stdin);
            output = replaceFirstSubString( args[0], args[1], content );
        } else if( args.length == 2 && replacement.endsWith(originalSeparator+REPLACEMENT_ALL_SUBSTRING_SUFFIX) ) {
            StringBuilder content = readContentFromStdin(stdin);
            output = replaceAllSubString( args[0], args[1], content );
        } else {
            throw new SedException(ERROR_EXP_INVALID_REPLACEMENT);
        }
        return output;
    }
    
    /**
     * Returns a string which has first substring matched by the regex specified
     * replaced by the replacement string specified if no g suffix is specified
     * in the given replacement expression or a string which has all substrings matched 
     * by the regex specified replaced by the replacement string specified if the g 
     * suffix is specified in the given replacement expression in the given file.
     * 
     * @param replacement
     *      The specified replacement expression string. The replacement rule is as follows: 
     *      (1) s/regexp/replacement/ – replace the first (in each line) substring matched by 
     *      regexp with the string replacement. (2) s/regexp/replacement/g – replace all the 
     *      substrings matched by regexp with the string replacement. Note that the symbols “/” 
     *      used to separate regexp and replacement string can be substituted by any other symbols. 
     *      For example, “s/a/b/” and “s|a|b|” are the same replacement rules. However, this separation 
     *      symbol should not be used inside the regexp and the replacement string.
     * @param fileName
     *      The name of the file whose contents are worked on.
     * 
     * @throws SedException
     *      If the specified file name is/are invalid or an invalid replacement expression string is 
     *      specified
     * 
     * @return 
     *      A string which has first substring matched by the regex specified
     *      replaced by the replacement string specified if no g suffix is specified
     *      in the given replacement expression or a string which has all substrings matched 
     *      by the regex specified replaced by the replacement string specified if the g 
     *      suffix is specified in the given replacement expression.
     */
    public String sedFromOneFile( String replacement, String fileName ) throws SedException {
        
        if (fileName == null || fileName.length() <= 0) {
            throw new SedException(ERROR_EXP_INVALID_FILE);
        }
        
        BufferedInputStream bis = null;
        String outputStr;
        try {

            fileName = Environment.getAbsPath(fileName);
            
            if(fileName.isEmpty() || !Environment.isFile(fileName)){
                throw new SedException(ERROR_EXP_INVALID_FILE);
            }

            FileInputStream fs = new FileInputStream(fileName);
            bis = new BufferedInputStream(fs);

            outputStr = sedFromStdin(replacement, bis);

            bis.close();
            fs.close();

        } catch (IOException e) {
            throw new SedException(ERROR_EXP_INVALID_FILE);
        } catch (SecurityException e) {
            throw new SedException(ERROR_EXP_INVALID_FILE);
        }
        return outputStr;
    }
    
    @Override
    public String replaceFirstSubStringInFile(String args) {
        ShellImpl shell = new ShellImpl();
        try {
            return shell.parseAndEvaluate(args);
        } catch (AbstractApplicationException | ShellException e) {
            return e.getMessage();
        }
    }

    @Override
    public String replaceAllSubstringsInFile(String args) {
        ShellImpl shell = new ShellImpl();
        try {
            return shell.parseAndEvaluate(args);
        } catch (AbstractApplicationException | ShellException e) {
            return e.getMessage();
        }
    }

    @Override
    public String replaceFirstSubStringFromStdin(String args) {
        ShellImpl shell = new ShellImpl();
        try {
            return shell.parseAndEvaluate(args);
        } catch (AbstractApplicationException | ShellException e) {
            return e.getMessage();
        }
    }

    @Override
    public String replaceAllSubstringsInStdin(String args) {
        ShellImpl shell = new ShellImpl();
        try {
            return shell.parseAndEvaluate(args);
        } catch (AbstractApplicationException | ShellException e) {
            return e.getMessage();
        }
    }

    @Override
    public String replaceSubstringWithInvalidReplacement(String args) {
        ShellImpl shell = new ShellImpl();
        try {
            return shell.parseAndEvaluate(args);
        } catch (AbstractApplicationException | ShellException e) {
            return e.getMessage();
        }
    }

    @Override
    public String replaceSubstringWithInvalidRegex(String args) {
        ShellImpl shell = new ShellImpl();
        try {
            return shell.parseAndEvaluate(args);
        } catch (AbstractApplicationException | ShellException e) {
            return e.getMessage();
        }
    }
}
