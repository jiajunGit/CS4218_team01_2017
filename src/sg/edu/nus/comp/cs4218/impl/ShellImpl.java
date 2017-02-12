package sg.edu.nus.comp.cs4218.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.Symbol;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;

import sg.edu.nus.comp.cs4218.impl.app.CalApplication;
import sg.edu.nus.comp.cs4218.impl.app.CatApplication;
import sg.edu.nus.comp.cs4218.impl.app.CdApplication;
import sg.edu.nus.comp.cs4218.impl.app.EchoApplication;
import sg.edu.nus.comp.cs4218.impl.app.GrepApplication;
import sg.edu.nus.comp.cs4218.impl.app.HeadApplication;
import sg.edu.nus.comp.cs4218.impl.app.PwdApplication;
import sg.edu.nus.comp.cs4218.impl.app.SortApplication;
import sg.edu.nus.comp.cs4218.impl.app.TailApplication;

/**
 * A Shell is a command interpreter and forms the backbone of the entire
 * program. Its responsibility is to interpret commands that the user type and
 * to run programs that the user specify in her command lines.
 * 
 * <p>
 * <b>Command format:</b>
 * <code>&lt;Pipe&gt; | &lt;Sequence&gt; | &lt;Call&gt;</code>
 * </p>
 */

public class ShellImpl implements Shell {
	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	
    @Override
    public String pipeTwoCommands(String args) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String pipeMultipleCommands(String args) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String pipeWithException(String args) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String globNoPaths(String args) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String globOneFile(String args) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String globFilesDirectories(String args) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String globWithException(String args) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String redirectInput(String args) {
		System.setOut(new PrintStream(out));
		try {
			this.parseAndEvaluate(args, System.out);
		} catch (AbstractApplicationException | ShellException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return out.toString(); 
    }

    @Override
    public String redirectOutput(String args) {
		System.setOut(new PrintStream(out));
		try {
			this.parseAndEvaluate(args, System.out);
		} catch (AbstractApplicationException | ShellException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return out.toString(); 
    }

    @Override
    public String redirectInputWithNoFile(String args) {
		System.setOut(new PrintStream(out));
		try {
			this.parseAndEvaluate(args, System.out);
		} catch (AbstractApplicationException | ShellException e) {
			return e.getClass().getName();
		}
		
		return out.toString(); 
    }

    @Override
    public String redirectOutputWithNoFile(String args) {
		System.setOut(new PrintStream(out));
		try {
			this.parseAndEvaluate(args, System.out);
		} catch (AbstractApplicationException | ShellException e) {
			return e.getClass().getName();
		}
		
		return out.toString(); 
    }

    @Override
    public String redirectInputWithException(String args) {
		System.setOut(new PrintStream(out));
		try {
			this.parseAndEvaluate(args, System.out);
		} catch (AbstractApplicationException | ShellException e) {
			return e.getClass().getName();
		}
		
		return out.toString(); 
    }

    @Override
    public String redirectOutputWithException(String args) {
		System.setOut(new PrintStream(out));
		try {
			this.parseAndEvaluate(args, System.out);
		} catch (AbstractApplicationException | ShellException e) {
			return e.getClass().getName();
		}
		
		return out.toString(); 
    }

    @Override
    public String performCommandSubstitution(String args) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String performCommandSubstitutionWithException(String args) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Main method for the Shell Interpreter program.
     * 
     * @param args
     *            List of strings arguments, unused.
     */
    public static void main(String... args) {
        
        System.out.println("Use Shell.java");
        
        /*
        try {
            
            ShellImp shell = new ShellImp();
            String readLine = " echo 'hi'";
            shell.parseAndEvaluate(readLine, System.out);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Terminated");
        }
        */
        
        /*
        BufferedReader bReader = new BufferedReader(new InputStreamReader(
                System.in));
        String readLine = null;
        String currentDir;

        while (true) {
            try {
                currentDir = Environment.currentDirectory;
                System.out.print(currentDir + ">");
                readLine = bReader.readLine();
                if (readLine == null) {
                    break;
                }
                if (("").equals(readLine)) {
                    continue;
                }
                //shell.parseAndEvaluate(readLine, System.out);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        */
    }
    
    /**
     * Searches for and processes the commands enclosed by back quotes for
     * command substitution.If no back quotes are found, the argsArray from the
     * input is returned unchanged. If back quotes are found, the back quotes
     * and its enclosed commands substituted with the output from processing the
     * commands enclosed in the back quotes.
     * 
     * @param argsArray
     *            String array of the individual commands.
     * 
     * @return String array with the back quotes command processed.
     * 
     * @throws AbstractApplicationException
     *             If an exception happens while processing the content in the
     *             back quotes.
     * @throws ShellException
     *             If an exception happens while processing the content in the
     *             back quotes.
     */
    public static String[] processBQ(String... argsArray)
            throws AbstractApplicationException, ShellException {
        // echo "this is space `echo "nbsp"`"
        // echo "this is space `echo "nbsp"` and `echo "2nd space"`"
        // Back quoted: any char except \n,`
        
        String[] resultArr = new String[argsArray.length];
        
        ShellImpl shell = new ShellImpl();
        
        for( int i = 0, size = argsArray.length; i < size; ++i ){
            String output = shell.processBQ(argsArray[i]);
            if(output != null){
                resultArr[i] = output;
            }
        }
        return resultArr;
    }

    /**
     * Static method to run the application as specified by the application
     * command keyword and arguments.
     * 
     * @param app
     *            String containing the keyword that specifies what application
     *            to run.
     * @param args
     *            String array containing the arguments to pass to the
     *            applications for running.
     * @param inputStream
     *            InputputStream for the application to get arguments from, if
     *            needed.
     * @param outputStream
     *            OutputStream for the application to print its output to.
     * 
     * @throws AbstractApplicationException
     *             If an exception happens while running any of the
     *             application(s).
     * @throws ShellException
     *             If an unsupported or invalid application command is detected.
     */
    public static void runApp(String app, String[] argsArray,
            InputStream inputStream, OutputStream outputStream)
            throws AbstractApplicationException, ShellException {
        Application absApp = null;
        if (("cat").equals(app)) {// cat [FILE]...
            absApp = new CatApplication();
        } else if (("echo").equals(app)) {// echo [args]...
            absApp = new EchoApplication();
        } else if (("cal").equals(app)) {
            absApp = new CalApplication();
        } else if (("cd").equals(app)) {
            absApp = new CdApplication();
        } else if (("grep").equals(app)) {
            absApp = new GrepApplication();
        } else if (("head").equals(app)) {
            absApp = new HeadApplication();
        } else if (("pwd").equals(app)) {
            absApp = new PwdApplication();
        } else if (("sort").equals(app)) {
            absApp = new SortApplication();
        } else if (("tail").equals(app)) {
            absApp = new TailApplication();
        } else if (("wc").equals(app)) {
            throw new ShellException(app + ": " + EXP_NOT_SUPPORTED);
        } else if (("sed").equals(app)) {
            throw new ShellException(app + ": " + EXP_NOT_SUPPORTED);
        } else if (("date").equals(app)) {
            throw new ShellException(app + ": " + EXP_NOT_SUPPORTED);
        } else { // invalid command
            throw new ShellException(app + ": " + EXP_INVALID_APP);
        }
        absApp.run(argsArray, inputStream, outputStream);
    }

    /**
     * Static method to creates an inputStream based on the file name or file
     * path.
     * 
     * @param inputStreamS
     *            String of file name or file path
     * 
     * @return InputStream of file opened
     * 
     * @throws ShellException
     *             If file is not found.
     */
    public static InputStream openInputRedir(String inputStreamS)
            throws ShellException {
        File inputFile = new File(inputStreamS);
        FileInputStream fInputStream = null;
        try {
            fInputStream = new FileInputStream(inputFile);
        } catch (FileNotFoundException e) {
            throw new ShellException(e.getMessage());
        }
        return fInputStream;
    }

    /**
     * Static method to creates an outputStream based on the file name or file
     * path.
     * 
     * @param onputStreamS
     *            String of file name or file path.
     * 
     * @return OutputStream of file opened.
     * 
     * @throws ShellException
     *             If file destination cannot be opened or inaccessible.
     */
    public static OutputStream openOutputRedir(String outputStreamS)
            throws ShellException {
        File outputFile = new File(outputStreamS);
        FileOutputStream fOutputStream = null;
        try {
            fOutputStream = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            throw new ShellException(e.getMessage());
        }
        return fOutputStream;
    }

    /**
     * Static method to close an inputStream.
     * 
     * @param inputStream
     *            InputStream to be closed.
     * 
     * @throws ShellException
     *             If inputStream cannot be closed successfully.
     */
    public static void closeInputStream(InputStream inputStream)
            throws ShellException {
        if (inputStream != null && inputStream != System.in) {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new ShellException(e.getMessage());
            }
        }
    }

    /**
     * Static method to close an outputStream. If outputStream provided is
     * System.out, it will be ignored.
     * 
     * @param outputStream
     *            OutputStream to be closed.
     * 
     * @throws ShellException
     *             If outputStream cannot be closed successfully.
     */
    public static void closeOutputStream(OutputStream outputStream)
            throws ShellException {
        if (outputStream != null && outputStream != System.out) {
            try {
                outputStream.close();
            } catch (IOException e) {
                throw new ShellException(e.getMessage());
            }
        }
    }

    /**
     * Static method to write output of an outputStream to another outputStream,
     * usually System.out.
     * 
     * @param outputStream
     *            Source outputStream to get stream from.
     * @param stdout
     *            Destination outputStream to write stream to.
     * @throws ShellException
     *             If exception is thrown during writing.
     */
    public static void writeToStdout(OutputStream outputStream,
            OutputStream stdout) throws ShellException {
        if (stdout == null || outputStream instanceof FileOutputStream) {
            return;
        }
        try {
            stdout.write(((ByteArrayOutputStream) outputStream).toByteArray());
        } catch (IOException e) {
            throw new ShellException(EXP_STDOUT);
        }
    }

    /**
     * Static method to pipe data from an outputStream to an inputStream, for
     * the evaluation of the Pipe Commands.
     * 
     * @param outputStream
     *            Source outputStream to get stream from.
     * 
     * @return InputStream with data piped from the outputStream.
     * 
     * @throws ShellException
     *             If exception is thrown during piping.
     */
    public static InputStream outputStreamToInputStream(
            OutputStream outputStream) throws ShellException {
        return (outputStream != null ? new ByteArrayInputStream(
                ((ByteArrayOutputStream) outputStream).toByteArray()) : new ByteArrayInputStream(new byte[0]));
    }

    /*
     *   Shell methods starts here ----------------------------------------------->
     */
    
    private Globber globber = new Globber();
    
    private static StringBuilder generateSymbols( String cmdline ) throws ShellException {
        
        StringBuilder symbols = new StringBuilder(cmdline.length());
        
        Stack<Character> quoteStack = new Stack<Character>();
        for( int i = 0, length = cmdline.length(); i < length; ++i ){
            
            char currentChar = cmdline.charAt(i);
            
            switch(currentChar) {
                    
                case Symbol.SINGLE_QUOTE:
                    if( quoteStack.isEmpty() ){
                        quoteStack.push(Symbol.SINGLE_QUOTE);
                        symbols.append(Symbol.SINGLE_QUOTE);
                    } else if( quoteStack.peek() == Symbol.SINGLE_QUOTE ){
                        quoteStack.pop();
                        if( quoteStack.isEmpty() ){
                            symbols.append(Symbol.SINGLE_QUOTE);
                        } else{
                            symbols.append(Symbol.UNRELATED);
                        }
                    } else{
                        quoteStack.push(Symbol.SINGLE_QUOTE);
                        symbols.append(Symbol.UNRELATED);
                    }
                    break;
                   
                case Symbol.DOUBLE_QUOTE:
                case Symbol.BACK_QUOTE:
                    if( quoteStack.isEmpty() ) {
                        quoteStack.push(currentChar);
                        symbols.append(currentChar);
                    } else if( quoteStack.peek() == currentChar ){
                        quoteStack.pop();
                        if(quoteStack.isEmpty()){
                            symbols.append(currentChar);
                        } else{
                            symbols.append(Symbol.UNRELATED);
                        }
                    } else if( quoteStack.peek() != Symbol.SINGLE_QUOTE ) {
                        quoteStack.push(currentChar);
                        symbols.append(Symbol.UNRELATED);
                    }
                    else{
                        symbols.append(Symbol.UNRELATED);
                    }
                    break;
                    
                case Symbol.SPACE:
                case Symbol.TAB:
                case Symbol.SEQ_OP:
                case Symbol.PIPE_OP:
                case Symbol.INPUT_OP:
                case Symbol.OUTPUT_OP:
                case Symbol.GLOB_OP:
                    if(quoteStack.isEmpty()){
                        symbols.append(currentChar);
                    } else{
                        symbols.append(Symbol.UNRELATED);
                    }
                    break;
                    
                default:
                    symbols.append(Symbol.UNRELATED);
                    break;
            }
        }
        
        if( !quoteStack.isEmpty() ){
            throw new ShellException(EXP_SYNTAX);
        }
        return symbols;
    }

    private StringBuilder generateSymbolsForBQ( String cmdline ) throws ShellException {
        
        StringBuilder symbols = new StringBuilder(cmdline.length());
        
        Stack<Character> quoteStack = new Stack<Character>();
        for( int i = 0, length = cmdline.length(); i < length; ++i ){
            
            char currentChar = cmdline.charAt(i);
            
            switch(currentChar) {
                    
                case Symbol.SINGLE_QUOTE:
                    if( quoteStack.isEmpty() ){
                        quoteStack.push(Symbol.SINGLE_QUOTE);
                        symbols.append(Symbol.SINGLE_QUOTE);
                    } else if( quoteStack.peek() == Symbol.SINGLE_QUOTE ){
                        quoteStack.pop();
                        if( quoteStack.isEmpty() ){
                            symbols.append(Symbol.SINGLE_QUOTE);
                        } else{
                            symbols.append(Symbol.UNRELATED);
                        }
                    } else{
                        quoteStack.push(Symbol.SINGLE_QUOTE);
                        symbols.append(Symbol.UNRELATED);
                    }
                    break;
                   
                case Symbol.DOUBLE_QUOTE:
                    if( quoteStack.isEmpty() ) {
                        quoteStack.push(currentChar);
                        symbols.append(currentChar);
                    } else if( quoteStack.peek() == currentChar ){
                        quoteStack.pop();
                        if(quoteStack.isEmpty()){
                            symbols.append(currentChar);
                        } else{
                            symbols.append(Symbol.UNRELATED);
                        }
                    } else if( quoteStack.peek() != Symbol.SINGLE_QUOTE ) {
                        quoteStack.push(currentChar);
                        symbols.append(Symbol.UNRELATED);
                    }
                    else{
                        symbols.append(Symbol.UNRELATED);
                    }
                    break;
                    
                case Symbol.BACK_QUOTE:
                    throw new ShellException(EXP_SYNTAX);
                    
                case Symbol.SPACE:
                case Symbol.TAB:
                case Symbol.SEQ_OP:
                case Symbol.PIPE_OP:
                case Symbol.INPUT_OP:
                case Symbol.OUTPUT_OP:
                case Symbol.GLOB_OP:
                    if(quoteStack.isEmpty()){
                        symbols.append(currentChar);
                    } else{
                        symbols.append(Symbol.UNRELATED);
                    }
                    break;
                    
                default:
                    symbols.append(Symbol.UNRELATED);
                    break;
            }
        }
        
        if( !quoteStack.isEmpty() ){
            throw new ShellException(EXP_SYNTAX);
        }
        return symbols;
    }
    
    private String parseAndEvaluateBQ( String input ) throws ShellException, AbstractApplicationException {

        StringBuilder cmdSymbols = generateSymbolsForBQ(input);
        
        StringBuilder cmd = new StringBuilder(input);
        
        processSQ(cmd, cmdSymbols);
        processDQ(cmd, cmdSymbols);
        
        List<Segment> segments = split(cmd, cmdSymbols);
        
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        evaluate(segments, outStream);
        return outStream.toString();
    }
    
    private String processBQOut( String output ) {
        return output.replace(Symbol.NEW_LINE_S, Symbol.SPACE_S);
    }
    
    private String generateSymbolsForBQOut( String output ) {
        StringBuilder outputSymbolsBuilder = new StringBuilder(output.length());
        for( int i = 0, length = output.length(); i < length; ++i ){
            if( isWhiteSpace(output.charAt(i)) ){
                outputSymbolsBuilder.append(Symbol.SPACE);
            } else{
                outputSymbolsBuilder.append(Symbol.UNRELATED);
            }
        }
        return outputSymbolsBuilder.toString();
    }
    
    private void processBQ( StringBuilder cmd, StringBuilder cmdSymbols ) 
            throws ShellException, AbstractApplicationException { 
        
        int fromIndex = 0;
        
        while(true){
            
            int start = cmdSymbols.indexOf(Symbol.BACK_QUOTE_S, fromIndex);
            if(start < 0){
                break;
            }
            
            int end = cmdSymbols.indexOf(Symbol.BACK_QUOTE_S, start+1);
            if(end < 0){
                throw new ShellException(EXP_SYNTAX);
            }
            
            String output = parseAndEvaluateBQ(cmd.substring(start+1, end));
            output = processBQOut(output);
            String outputSymbols = generateSymbolsForBQOut(output);
            
            // Replace
            cmd.replace(start, end+1, output);
            cmdSymbols.replace(start, end+1, outputSymbols);
            
            fromIndex = start + outputSymbols.length();
        }
    }
    
    private String parseAndEvaluateSQ( String input ) throws ShellException {
        if(input.indexOf(Symbol.SINGLE_QUOTE) >= 0){
            throw new ShellException(EXP_SYNTAX);
        }
        return input;
    }
    
    private String generateSymbolsForSQOut( String output ) {
        StringBuilder sb = new StringBuilder(output.length());
        for( int i = 0, size = output.length(); i < size; ++i ){
            sb.append(Symbol.UNRELATED);
        }
        return sb.toString();
    }
    
    private void processSQ( StringBuilder cmd, StringBuilder cmdSymbols ) 
            throws ShellException { 
        
        int fromIndex = 0;
        
        while(true){
            
            int start = cmdSymbols.indexOf(Symbol.SINGLE_QUOTE_S, fromIndex);
            if(start < 0){
                break;
            }
            
            int end = cmdSymbols.indexOf(Symbol.SINGLE_QUOTE_S, start+1);
            if(end < 0){
                throw new ShellException(EXP_SYNTAX);
            }
            
            String output = parseAndEvaluateSQ(cmd.substring(start+1, end));
            String outputSymbol = generateSymbolsForSQOut(output);
            
            // Replace
            cmd.replace(start, end+1, output);
            cmdSymbols.replace(start, end+1, outputSymbol);
            
            fromIndex = start + outputSymbol.length();
        }
    }
    
    private StringBuilder generateSymbolsForDQ( String input ) throws ShellException {
        
        StringBuilder symbols = new StringBuilder(input.length());
        
        Stack<Character> quoteStack = new Stack<Character>();
        for( int i = 0, length = input.length(); i < length; ++i ){
            
            char currentChar = input.charAt(i);
            
            switch(currentChar) {
                    
                case Symbol.SINGLE_QUOTE:
                    if( quoteStack.isEmpty() ){
                        quoteStack.push(Symbol.SINGLE_QUOTE);
                        symbols.append(Symbol.SINGLE_QUOTE);
                    } else if( quoteStack.peek() == Symbol.SINGLE_QUOTE ){
                        quoteStack.pop();
                        if( quoteStack.isEmpty() ){
                            symbols.append(Symbol.SINGLE_QUOTE);
                        } else{
                            symbols.append(Symbol.UNRELATED);
                        }
                    } else{
                        quoteStack.push(Symbol.SINGLE_QUOTE);
                        symbols.append(Symbol.UNRELATED);
                    }
                    break;
                   
                case Symbol.BACK_QUOTE:
                    if( quoteStack.isEmpty() ) {
                        quoteStack.push(currentChar);
                        symbols.append(currentChar);
                    } else if( quoteStack.peek() == currentChar ){
                        quoteStack.pop();
                        if(quoteStack.isEmpty()){
                            symbols.append(currentChar);
                        } else{
                            symbols.append(Symbol.UNRELATED);
                        }
                    } else if( quoteStack.peek() != Symbol.SINGLE_QUOTE ) {
                        quoteStack.push(currentChar);
                        symbols.append(Symbol.UNRELATED);
                    }
                    else{
                        symbols.append(Symbol.UNRELATED);
                    }
                    break;
                    
                case Symbol.DOUBLE_QUOTE:
                    throw new ShellException(EXP_SYNTAX);
                    
                case Symbol.SPACE:
                case Symbol.TAB:
                case Symbol.SEQ_OP:
                case Symbol.PIPE_OP:
                case Symbol.INPUT_OP:
                case Symbol.OUTPUT_OP:
                    if(quoteStack.isEmpty()){
                        symbols.append(currentChar);
                    } else{
                        symbols.append(Symbol.UNRELATED);
                    }
                    break;
                    
                default:
                    symbols.append(Symbol.UNRELATED);
                    break;
            }
        }
        
        if( !quoteStack.isEmpty() ){
            throw new ShellException(EXP_SYNTAX);
        }
        return symbols;
    }
    
    private String parseAndEvaluateDQ( String input ) throws ShellException, AbstractApplicationException {

        StringBuilder cmdSymbols = generateSymbolsForDQ(input);
        
        StringBuilder cmd = new StringBuilder(input);
        
        processSQ(cmd, cmdSymbols);
        processBQ(cmd, cmdSymbols);
        
        return cmd.toString();
    }
    
    private String generateSymbolsForDQOut( String output ) {
        StringBuilder sb = new StringBuilder(output.length());
        for( int i = 0, size = output.length(); i < size; ++i ){
            sb.append(Symbol.UNRELATED);
        }
        return sb.toString();
    }
    
    private void processDQ( StringBuilder cmd, StringBuilder cmdSymbols ) 
            throws ShellException, AbstractApplicationException { 
        
        int fromIndex = 0;
        
        while(true){
            
            int start = cmdSymbols.indexOf(Symbol.DOUBLE_QUOTE_S, fromIndex);
            if(start < 0){
                break;
            }
            
            int end = cmdSymbols.indexOf(Symbol.DOUBLE_QUOTE_S, start+1);
            if(end < 0){
                throw new ShellException(EXP_SYNTAX);
            }
            
            String output = parseAndEvaluateDQ(cmd.substring(start+1, end));
            String outputSymbol = generateSymbolsForDQOut(output);
            
            // Replace
            cmd.replace(start, end+1, output);
            cmdSymbols.replace(start, end+1, outputSymbol);
            
            fromIndex = start + outputSymbol.length();
        }
    }
    
    private boolean isWhiteSpace( char character ) {
        return (character == Symbol.SPACE || character == Symbol.TAB);
    }
    
    private List<Segment> split( StringBuilder cmd, StringBuilder cmdSymbols ) throws ShellException {
        
        StringBuilder segment = new StringBuilder();
        StringBuilder segmentSymbol = new StringBuilder();
        
        LinkedList<Segment> segments = new LinkedList<Segment>();
        
        boolean hasGlob = false;
        
        for( int i = 0, length = cmdSymbols.length(); i < length; ++i ){
            
            switch(cmdSymbols.charAt(i)){
            
                case Symbol.SPACE:
                case Symbol.TAB:
                    
                    if(segment.length() > 0){
                        segments.add( new Segment( segment.toString(), segmentSymbol.toString(), hasGlob ) );
                        segment.setLength(0);
                        segmentSymbol.setLength(0);
                    }
                    hasGlob = false;
                    break;
                    
                case Symbol.SEQ_OP:
                case Symbol.PIPE_OP:
                case Symbol.INPUT_OP:
                case Symbol.OUTPUT_OP:
                    
                    if(segment.length() > 0){
                        segments.add( new Segment( segment.toString(), segmentSymbol.toString(), hasGlob ) );
                        segment.setLength(0);
                        segmentSymbol.setLength(0);
                    }
                    segments.add(new Segment( cmd.charAt(i), cmdSymbols.charAt(i), false ));
                    hasGlob = false;
                    break;
                    
                case Symbol.GLOB_OP:
                    
                    hasGlob = true;
                    break;
                    
                default:
                  
                    segment.append(cmd.charAt(i));
                    segmentSymbol.append(cmdSymbols.charAt(i));
                    break;
            }
        }
        
        if(segment.length() > 0){
            segments.add( new Segment( segment.toString(), segmentSymbol.toString(), hasGlob ) );
        }
        
        return segments;
    }
    
    private List<Command> generateCommands( List<Segment> segments ) throws ShellException {
        CommandGenerator cmdGen = new CommandGenerator(globber, segments);
        return cmdGen.generateCommands();
    }
    
    private OutputStream generateOutputStream( Command cmd, OutputStream defaultStream ) throws ShellException {
        
        OutputStream outStream = defaultStream;
        
        if( cmd.hasStdout() ){
            
            try {
                
                String stdoutName = cmd.getStdoutName();
                
                String absPath = Environment.getAbsPath(stdoutName);
                if(absPath.isEmpty()){
                    throw new ShellException(EXP_STDOUT);
                }
                
                if( Environment.isDirectory(absPath) ){
                    throw new ShellException(EXP_STDOUT);
                }
                
                if( !Environment.isExists(absPath) && !Environment.createNewFile(absPath) ){
                    throw new ShellException(EXP_STDOUT);
                }
                
                outStream = new FileOutputStream(absPath);
                
            } catch (SecurityException | IOException e) {
                throw new ShellException(EXP_STDOUT);
            }
        }
        
        return outStream;
    }
    
    private InputStream generateInputStream( Command cmd, InputStream defaultStream ) throws ShellException {
        
        InputStream inStream = defaultStream;
        
        if( cmd.hasStdin() ){
            
            inStream = cmd.getStdin();
            
            if(inStream != null){
                return inStream;
            }
            
            inStream = defaultStream;
            try {
                
                String stdinName = cmd.getStdinName();
                
                String absPath = Environment.getAbsPath(stdinName);
                if(absPath.isEmpty()){
                    throw new ShellException(EXP_STDIN);
                }
                
                if( !Environment.isFile(absPath) ){
                    throw new ShellException(EXP_STDIN);
                }
                
                inStream = new FileInputStream(absPath);
                
            } catch (SecurityException | IOException e) {
                throw new ShellException(EXP_STDIN);
            }
        }
        
        return inStream;
    }
    
    private void executeCommandToPipe( Command cmd ) throws ShellException, AbstractApplicationException {
        
        String appName = cmd.getAppName();
        String []args = cmd.getArguments();
        InputStream inStream = generateInputStream(cmd, null);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        runApp(appName, args, inStream, outStream);
        
        if(inStream != null){
            closeInputStream(inStream);
        }
        
        Command nextCmd = cmd.getNextCommand();
        if( nextCmd != null ){
            if(nextCmd.hasStdin()){
                throw new ShellException(EXP_MULTI_STDIN);
            }
            ByteArrayInputStream newInStream = new ByteArrayInputStream(outStream.toByteArray());
            nextCmd.setStdin(newInStream);
        } else{
            throw new ShellException(EXP_SYNTAX);
        }
    }
    
    private void executeCommand( Command cmd, OutputStream stdout ) throws ShellException, AbstractApplicationException {
        
        if( cmd.getLinkTypeToNextCommand() != Link.PIPE ){
            
            String appName = cmd.getAppName();
            String []args = cmd.getArguments();
            InputStream inStream = generateInputStream(cmd, null);
            OutputStream outStream = generateOutputStream(cmd, stdout);
            runApp(appName, args, inStream, outStream);
            
            if(inStream != null){
                closeInputStream(inStream);
            }
            if(outStream != stdout){
                closeOutputStream(outStream);
            }
        } else{
            executeCommandToPipe(cmd);
        }
    }
    
    private void executeCommands( List<Command> commands, OutputStream stdout ) 
            throws ShellException, AbstractApplicationException {
        if(commands != null){
            for( Command command : commands ){
                executeCommand(command, stdout);
            }
        }
    }
    
    private void evaluate( List<Segment> segments, OutputStream stdout ) 
            throws ShellException, AbstractApplicationException {
       List<Command> commands = generateCommands(segments);
       executeCommands(commands, stdout);
    }
    
    // TODO Need to check with prof about the validity of a command that ends with a consecutive sequence of new lines at the end
    private boolean hasNewLineViolation(String input) {
        
        int fromIndex = 0;
        int newLineLength = Symbol.NEW_LINE_S.length();
        int lastNewLineIndex = -Symbol.NEW_LINE_S.length();
        
        while(true) {
            
            int currentIndex = input.indexOf(Symbol.NEW_LINE_S, fromIndex);
            
            if(currentIndex < 0){
                break;
            }
            
            if( lastNewLineIndex < 0 || (currentIndex - newLineLength) == lastNewLineIndex ){
                lastNewLineIndex = currentIndex;
            } else {
                return true;
            }
            
            fromIndex = currentIndex + newLineLength;
        }
        return (lastNewLineIndex >= 0 && ((input.length() - newLineLength) != lastNewLineIndex));
    }
    
    private void writeToOut(OutputStream stdout, String content ) throws ShellException {
        if( stdout != null && content != null ){
            try {
                stdout.write(content.getBytes());
                stdout.flush();
            } catch (IOException e) {
                throw new ShellException(e.getMessage());
            }
        }
    }
    
    // This method is in the testing of globbing. It is NOT used internally.
    public static String generateSymbolString( String input ) throws ShellException {
        if(input == null){
            return new String();
        }
        StringBuilder out = generateSymbols(input);
        return out.toString();
    }
    
    // This method is for the static method processBQ(). It is NOT used internally.
    public String processBQ( String input ) throws ShellException, AbstractApplicationException{
        
        if(input == null){
            return null;
        }
        StringBuilder cmdSymbols = generateSymbols(input);
        StringBuilder cmd = new StringBuilder(input);
        processBQ(cmd, cmdSymbols);
        
        return cmd.toString();
    }
    
    @Override
    public void parseAndEvaluate(String cmdline, OutputStream stdout)
            throws AbstractApplicationException, ShellException {
        
        if(cmdline == null){
            throw new ShellException(EXP_SYNTAX);
        }
        if(stdout == null){
            throw new ShellException(EXP_STDOUT);
        }
        
        cmdline = cmdline.trim();
        
        if( cmdline.length() <= 0 || hasNewLineViolation(cmdline) ) {
            throw new ShellException(EXP_SYNTAX);
        }
        
        cmdline = cmdline.replace(Symbol.NEW_LINE_S, Symbol.EMPTY_S);
        
        if( cmdline.length() <= 0) {
            throw new ShellException(EXP_SYNTAX);
        }
        
        StringBuilder cmdSymbols = generateSymbols(cmdline);
        StringBuilder cmd = new StringBuilder(cmdline);
        
        processBQ(cmd, cmdSymbols);
        processSQ(cmd, cmdSymbols);
        processDQ(cmd, cmdSymbols);
        
        List<Segment> segments = split(cmd, cmdSymbols);
        
        evaluate(segments, stdout);
        
        writeToOut(stdout, Symbol.NEW_LINE_S);
        
        closeOutputStream(stdout);
    }
}