package sg.edu.nus.comp.cs4218.impl.cmd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.Symbol;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.Globber;
import sg.edu.nus.comp.cs4218.impl.IOStreamType;
import sg.edu.nus.comp.cs4218.impl.InStream;
import sg.edu.nus.comp.cs4218.impl.Link;
import sg.edu.nus.comp.cs4218.impl.OutStream;
import sg.edu.nus.comp.cs4218.impl.Segment;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.Stage;

public class CallCommand implements Command {

    public static final String EXP_INVALID_APP = "Invalid app.";
    public static final String EXP_SYNTAX = "Invalid syntax encountered.";
    public static final String EXP_REDIR_PIPE = "File output redirection and pipe operator cannot be used side by side.";
    public static final String EXP_MULTI_STDIN = "Multiple input streams.";
    public static final String EXP_SAME_REDIR = "Input redirection file same as output redirection file.";
    public static final String EXP_STDOUT = "Error writing to stdout.";
    public static final String EXP_STDIN = "Error writing to stdin.";
    public static final String EXP_NOT_SUPPORTED = " not supported yet";
    public static final String EXP_INTERNAL = "Internal Error occurred";
    
    private String cmdInput;
    private String cmdSymbols;
    
    private Link nextLink;
    private CallCommand nextCmd;
    
    private Link prevLink;
    
    private InputStream defaultInputStream;
    
    private String appName;
    
    private OutStream streamOut;
    private InStream streamIn;
    
    private LinkedList<String> arguments;
    
    private Globber globber;
    
    public CallCommand( String command, String commandSymbols ) {
        
        cmdInput = command;
        cmdSymbols = commandSymbols;
        
        appName = "";
        
        streamIn = new InStream();
        streamOut = new OutStream();
        
        arguments = new LinkedList<String>();
        
        nextLink = Link.NONE;
        nextCmd = null;
        
        prevLink = Link.NONE;
        
        globber = new Globber();
        
        defaultInputStream = null;
    }
    
    private InputStream getStdin( InputStream defaultStream ) throws ShellException {
        return streamIn.getStream(defaultStream);
    }
    
    private void setStdin( InputStream inputStream ) {
        streamIn.setStdin(inputStream);
    }
    
    private String getStdinName() throws ShellException {
        return streamIn.getName();
    }
    
    public boolean hasStdin() {
        return streamIn.hasStdin();
    }
    
    public IOStreamType getStdinType() {
        return streamIn.getType();
    }
    
    private void setStdinName( String inputStreamName ) {
        streamIn.setName(inputStreamName);
    }
    
    private String getStdoutName() throws ShellException {
        return streamOut.getName();
    }
    
    public boolean hasStdout() {
        return streamOut.hasStdout();
    }
    
    public IOStreamType getStdoutType() {
        return streamOut.getType();
    }
    
    private void setStdoutName(String outputStreamName) {
        streamOut.setName(outputStreamName);
    }
    
    private OutputStream getStdout( OutputStream defaultStream ) throws ShellException {
        return streamOut.getStream(defaultStream);
    }
    
    public String[] getArguments() {

        String[] args = new String[arguments.size()];
        
        int index = 0;
        for( String arg : arguments ){
            args[index++] = arg;
        }
        return args;
    }
    
    private void addArguments(String arg) {
        arguments.add(arg);
    }
    
    private String getAppName() {
        return appName;
    }
    
    private boolean hasAppName() {
        return (appName != null && !appName.isEmpty());
    }
    
    private void setAppName(String applicationName) {
        appName = applicationName;
    }
    
    public Link getLinkTypeToNextCommand() {
        return nextLink;
    }
    
    public Link getLinkTypeToPrevCommand() {
        return prevLink;
    }
    
    public void setNextCommand( CallCommand next ) {
        nextCmd = next;
    }
    
    public CallCommand getNextCommand() {
        return nextCmd;
    }
    
    public void setLinkToNextCommand( char symbol ) {
        switch(symbol){
            case Symbol.SEQ_OP:
                nextLink = Link.SEQUENCE;
                break;
            case Symbol.PIPE_OP:
                nextLink = Link.PIPE;
                break;
            default:
                nextLink = Link.NONE;
                break;
        }
    }
    
    public void setLinkToPrevCommand( char symbol ) {
        switch(symbol){
            case Symbol.SEQ_OP:
                prevLink = Link.SEQUENCE;
                break;
            case Symbol.PIPE_OP:
                prevLink = Link.PIPE;
                break;
            default:
                prevLink = Link.NONE;
                break;
        }
    }
    
    public void setLinkToNextCommand( Link link ) {
        nextLink = link;
    }
    
    public void setLinkToPrevCommand( Link link ) {
        prevLink = link;
    }
    
    private void setDefaultInputStream( InputStream defaultStream ) {
        defaultInputStream = defaultStream;
    }
    
    private InputStream getDefaultInputStream() {
        return defaultInputStream;
    }
    
    // TODO
    // Shell features
    
    private CallCommand splitIntoCommands( String input, StringBuilder inputSymbols ) throws ShellException {
        
        StringBuilder segment = new StringBuilder();
        StringBuilder segmentSymbol = new StringBuilder();
        
        CallCommand prevCmd = null;
        CallCommand firstCmd = null;
        
        for( int i = 0, length = inputSymbols.length(); i < length; ++i ){
            
            switch(inputSymbols.charAt(i)){
            
                case Symbol.PIPE_OP:
                case Symbol.SEQ_OP:
                    
                    if(segment.length() > 0){
                        
                        CallCommand cmd = new CallCommand( segment.toString(), segmentSymbol.toString() );
                        cmd.setLinkToNextCommand(inputSymbols.charAt(i));
                        
                        if(firstCmd == null){
                            firstCmd = cmd;
                        }
                        
                        if( prevCmd != null ){
                            prevCmd.setNextCommand(cmd);
                            cmd.setLinkToPrevCommand(prevCmd.getLinkTypeToNextCommand());
                        }
                        prevCmd = cmd;
                        
                        segment.setLength(0);
                        segmentSymbol.setLength(0);
                        
                    } else {
                        throw new ShellException(Shell.EXP_SYNTAX);
                    }
                    break;
                    
                default:
                    
                    segment.append(input.charAt(i));
                    segmentSymbol.append(inputSymbols.charAt(i));
                    break;
            }
        }
        
        if(segment.length() > 0){
            
            CallCommand cmd = new CallCommand( segment.toString(), segmentSymbol.toString() );
            cmd.setLinkToNextCommand(Link.NONE);
            
            if(firstCmd == null){
                firstCmd = cmd;
            }
            
            if( prevCmd != null ){
                prevCmd.setNextCommand(cmd);
                cmd.setLinkToPrevCommand(prevCmd.getLinkTypeToNextCommand());
            }
            
        } else {
            throw new ShellException(Shell.EXP_SYNTAX);
        }
        
        return firstCmd;
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
                    } else{
                        symbols.append(Symbol.UNRELATED);
                    }
                    break;
                    
                case Symbol.BACK_QUOTE:
                    if(quoteStack.isEmpty()){
                        throw new ShellException(EXP_SYNTAX);
                    } else if( quoteStack.peek() == Symbol.BACK_QUOTE ){
                        quoteStack.pop();
                        if(quoteStack.isEmpty()){
                            throw new ShellException(EXP_SYNTAX);
                        }else{
                            symbols.append(Symbol.UNRELATED);
                        }
                    } else if( quoteStack.peek() != Symbol.SINGLE_QUOTE ) {
                        quoteStack.push(currentChar);
                        symbols.append(Symbol.UNRELATED);
                    } else{
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
                    if(quoteStack.isEmpty()){
                        symbols.append(Symbol.UNQUOTED_UNRELATED);
                    } else {
                        symbols.append(Symbol.UNRELATED);
                    }
                    break;
            }
        }
        
        if( !quoteStack.isEmpty() ){
            throw new ShellException(EXP_SYNTAX);
        }
        return symbols;
    }
    
    private void writeToStdout( OutputStream stdout, String content ) {
        if( stdout != null && content != null && !content.isEmpty() ){
            try { 
                stdout.write(content.getBytes());
                stdout.flush();
            } catch (IOException e) {}
        }
    }
    
    private void executeCommands( CallCommand firstCmd, OutputStream stdout ) 
            throws ShellException, AbstractApplicationException {
        
        InputStream defaultStdin = getDefaultInputStream();
        
        CallCommand currentCmd = firstCmd;
        while( currentCmd != null ){
            currentCmd.evaluate(defaultStdin, stdout);
            currentCmd = currentCmd.getNextCommand();
        }
    }
    
    private String parseAndEvaluateBQ( String input ) throws ShellException, AbstractApplicationException {

        StringBuilder cmdSymbols = generateSymbolsForBQ(input);
        
        CallCommand firstCmd = splitIntoCommands(input, cmdSymbols);
        if( firstCmd == null ){
            throw new ShellException(EXP_SYNTAX);
        }
        
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        executeCommands(firstCmd, outStream);
        
        return outStream.toString();
    }
    
    private String processBQOut( String output ) {
        return output.replace(Symbol.NEW_LINE_S, Symbol.SPACE_S);
    }
    
    private boolean isWhiteSpace( char character ) {
        return (character == Symbol.SPACE || character == Symbol.TAB);
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
    
    private void processBQ( StringBuilder cmd, StringBuilder cmdSymbols, boolean isIgnoreEmptyStrings ) 
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
            String outputSymbols = Symbol.EMPTY_OUTPUT_S;
            
            if( !isIgnoreEmptyStrings && output.isEmpty() ){
                output = Symbol.EMPTY_OUTPUT_S;
            } else {
                outputSymbols = generateSymbolsForBQOut(output);
            }
            
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
    
    private void processSQ( StringBuilder cmd, StringBuilder cmdSymbols, boolean isIgnoreEmptyStrings ) 
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
            String outputSymbol = Symbol.EMPTY_OUTPUT_S;
            
            if(!isIgnoreEmptyStrings && output.isEmpty()){
                output = Symbol.EMPTY_OUTPUT_S;
            } else {
                outputSymbol = generateSymbolsForSQOut(output);
            }
            
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
                    } else{
                        symbols.append(Symbol.UNRELATED);
                    }
                    break;
                    
                case Symbol.DOUBLE_QUOTE:
                    if( quoteStack.isEmpty() ){
                        throw new ShellException(EXP_SYNTAX);
                    } else if( quoteStack.peek() == Symbol.DOUBLE_QUOTE ) {
                        quoteStack.pop();
                        if(quoteStack.isEmpty()){
                            throw new ShellException(EXP_SYNTAX);
                        } else{
                            symbols.append(Symbol.UNRELATED);
                        }
                    } else if( quoteStack.peek() != Symbol.SINGLE_QUOTE ) {
                        quoteStack.push(currentChar);
                        symbols.append(Symbol.UNRELATED);
                    } else {
                        symbols.append(Symbol.UNRELATED);
                    }
                    break;

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
        
        processSQ(cmd, cmdSymbols, true);
        processBQ(cmd, cmdSymbols, true);
        
        return cmd.toString();
    }
    
    private String generateSymbolsForDQOut( String output ) {
        StringBuilder sb = new StringBuilder(output.length());
        for( int i = 0, size = output.length(); i < size; ++i ){
            sb.append(Symbol.UNRELATED);
        }
        return sb.toString();
    }
    
    private void processDQ( StringBuilder cmd, StringBuilder cmdSymbols, boolean isIgnoreEmptyStrings ) 
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
            String outputSymbol = Symbol.EMPTY_OUTPUT_S;
            
            if( !isIgnoreEmptyStrings && output.isEmpty() ){
                output = Symbol.EMPTY_OUTPUT_S;
            } else {
                outputSymbol = generateSymbolsForDQOut(output);
            }
            
            // Replace
            cmd.replace(start, end+1, output);
            cmdSymbols.replace(start, end+1, outputSymbol);
            
            fromIndex = start + outputSymbol.length();
        }
    }
    
    private boolean isContent( StringBuilder fullString, int index ) {
        
        if(index < 0 || index >= fullString.length()) {
            return false;
        }
        
        switch( fullString.charAt(index) ) {
            case Symbol.SPACE:
            case Symbol.TAB:
            case Symbol.PIPE_OP:
            case Symbol.SEQ_OP:
            case Symbol.INPUT_OP:
            case Symbol.OUTPUT_OP:
            case Symbol.EMPTY_OUTPUT:
            case Symbol.LINE_FEED:
            case Symbol.CARRIAGE_RETURN:
                return false;
            default:
                break;
        }
        return true;
    }
    
    private int inverseSearch( StringBuilder fullString, int startIndex, char charNotToFind, int defaultReturn ) {
        
        if(startIndex < 0 || startIndex >= fullString.length()){
            return defaultReturn;
        }
        while( startIndex < fullString.length() && fullString.charAt(startIndex) == charNotToFind ) {
            ++startIndex;
        }
        return (startIndex < fullString.length() ? startIndex : defaultReturn);
    }
    
    private void processEmptyOutputs( StringBuilder cmd, StringBuilder cmdSymbols ) {
        
        int index = 0;
        
        while(index < cmd.length()){
            
            if( cmdSymbols.charAt(index) == Symbol.EMPTY_OUTPUT ) {
                
                int start = index;
                int end = inverseSearch( cmdSymbols, start + 1, Symbol.EMPTY_OUTPUT, start + 1 );
                
                boolean isContentBeforeStart = isContent(cmdSymbols, start-1);
                boolean isContentAfterEnd = isContent(cmdSymbols, end);
                
                if( !isContentBeforeStart && !isContentAfterEnd ){
                    cmd.replace(start, end, Symbol.EMPTY_OUTPUT_S);
                    cmdSymbols.replace(start, end, Symbol.EMPTY_OUTPUT_S);
                } else {
                    cmd.replace(start, end, Symbol.EMPTY_S);
                    cmdSymbols.replace(start, end, Symbol.EMPTY_S);
                    continue;
                }
            }
            ++index;
        } 
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
                    throw new ShellException(Shell.EXP_SYNTAX);
                    
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
                    segment.append(cmd.charAt(i));
                    segmentSymbol.append(cmdSymbols.charAt(i));
                    break;
                    
                case Symbol.EMPTY_OUTPUT:   // This case makes it accept empty arg strings
                    
                    if(segment.length() > 0){
                        segments.add( new Segment( segment.toString(), segmentSymbol.toString(), hasGlob ) );
                        segment.setLength(0);
                        segmentSymbol.setLength(0);
                    }
                    segments.add(new Segment( new String(), new String(), false ));
                    hasGlob = false;
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
    
    // TODO
    // Command Generation
    
    private void setStdin( String stdin ) throws ShellException {
        
        if( getStdoutType() == IOStreamType.WITH_NAME ){
            String stdout = getStdoutName();
            if(stdout.compareToIgnoreCase(stdin) == 0){
                throw new ShellException(Shell.EXP_SAME_REDIR);
            }
        }
        
        if(prevLink == Link.PIPE){
            throw new ShellException(Shell.EXP_MULTI_STDIN);
        }
        
        setStdinName(stdin);
    }
    
    private Stage populateCommandForInputRedir( String segment, String segmentSymbols, char symbol, 
                                                boolean isGlobbedArgs ) throws ShellException {
        
        switch( symbol ){
                
            case Symbol.INPUT_OP:
                throw new ShellException(Shell.EXP_SYNTAX);
                
            case Symbol.OUTPUT_OP:
                
                if( nextLink == Link.PIPE ){
                    throw new ShellException(Shell.EXP_SYNTAX);
                }
                if(hasStdout()){
                    throw new ShellException(Shell.EXP_SYNTAX);
                }
                if(!hasStdin()){
                    setStdinName("");
                }
                return Stage.OUTPUT_REDIR;
                
            default:

                if( isGlobbedArgs ){
                    if(hasStdin()){
                        throw new ShellException(Shell.EXP_MULTI_STDIN);
                    } else {
                        setStdin(segment);
                    }
                } else if(hasStdin()){
                    return populateCommandForNormal(segment, segmentSymbols, Symbol.UNRELATED);
                } else{
                    setStdin(segment);
                    return Stage.NORMAL;
                }
        }
        return Stage.INPUT_REDIR;
    }
    
    private void setStdout( String stdout ) throws ShellException {
        
        if( getStdinType() == IOStreamType.WITH_NAME ){
            String stdin = getStdinName();
            if( stdin.compareToIgnoreCase(stdout) == 0 ) {
                throw new ShellException(Shell.EXP_SAME_REDIR);
            }
        }
        
        if( nextLink == Link.PIPE ){
            throw new ShellException(Shell.EXP_REDIR_PIPE);
        }
        
        setStdoutName(stdout);
    }
    
    private Stage populateCommandForOutputRedir( String segment, String segmentSymbols, char symbol, 
                                                 boolean isGlobbedArgs ) throws ShellException {
        
        switch( symbol ){
                
            case Symbol.INPUT_OP:
                
                if(prevLink == Link.PIPE){
                    throw new ShellException(Shell.EXP_MULTI_STDIN);
                }
                if(hasStdin()){
                    throw new ShellException(Shell.EXP_SYNTAX);
                }
                if(!hasStdout()){
                    setStdoutName("");
                }
                return Stage.INPUT_REDIR;
                
            case Symbol.OUTPUT_OP:
                throw new ShellException(Shell.EXP_SYNTAX);
                
            default:

                if( isGlobbedArgs ){
                    if(hasStdout()){
                        throw new ShellException(Shell.EXP_SYNTAX);
                    } else {
                        setStdout(segment);
                    }
                } else if(hasStdout()){
                    return populateCommandForNormal(segment, segmentSymbols, Symbol.UNRELATED);
                } else {
                    setStdout(segment);
                    return Stage.NORMAL;
                }
                
        }
        return Stage.OUTPUT_REDIR;
    }
    
    private void populateCommandEmptyArgs() throws ShellException {
        if(hasAppName()) {
            addArguments("");
        } else {
            throw new ShellException(Shell.EXP_SYNTAX);
        }
    }
    
    private void populateCommandNonEmptyArgs( String segment, String segmentSymbols ) 
            throws ShellException {
        
        if(hasAppName()) {
            addArguments(segment);
        } else if( segmentSymbols != null && segmentSymbols.length() > 0 ) {
            char firstSymbol = segmentSymbols.charAt(0);
            if(firstSymbol == Symbol.UNQUOTED_UNRELATED ){
                int index = 1;
                while(index < segmentSymbols.length()){
                    if(segmentSymbols.charAt(index) != Symbol.UNQUOTED_UNRELATED){
                        throw new ShellException(Shell.EXP_SYNTAX);
                    }
                    ++index;
                }
                setAppName(segment);
            } else{
                throw new ShellException(Shell.EXP_SYNTAX);
            }
        } else {
            throw new ShellException(Shell.EXP_SYNTAX);
        }
    }
    
    private void populateCommandArgs( String segment, String segmentSymbols ) 
            throws ShellException {
        
        if(!segment.isEmpty()){
            populateCommandNonEmptyArgs(segment, segmentSymbols);
        } else {
            populateCommandEmptyArgs();
        }
    }
    
    private Stage populateCommandForNormal( String segment, String segmentSymbols, char symbol ) 
            throws ShellException {
        
        switch( symbol ){
                
            case Symbol.INPUT_OP:
                
                if(prevLink == Link.PIPE){
                    throw new ShellException(Shell.EXP_MULTI_STDIN);
                }
                if(hasStdin()){
                    throw new ShellException(Shell.EXP_SYNTAX);
                } else {
                    return Stage.INPUT_REDIR;
                }
                
            case Symbol.OUTPUT_OP:
                
                if( nextLink == Link.PIPE ){
                    throw new ShellException(Shell.EXP_REDIR_PIPE);
                }
                if(hasStdout()){
                    throw new ShellException(Shell.EXP_SYNTAX);
                } else {
                    return Stage.OUTPUT_REDIR;
                }
                
            default:
                
                populateCommandArgs(segment, segmentSymbols);
                break;
        }
        return Stage.NORMAL;
    }
    
    private char getSymbol( String segmentSymbol ) {
        if(segmentSymbol != null && segmentSymbol.length() == 1){
            char symbol = segmentSymbol.charAt(0);
            switch( symbol ){
                case Symbol.INPUT_OP:
                case Symbol.OUTPUT_OP:
                    return symbol;
                default:
                    break;
            }
        }
        return Symbol.UNRELATED;
    }
    
    // This method assumes segment to be string with all special symbols disabled
    private Stage populateCommand( String segment, Stage stage, boolean isGlobbedArgs) 
            throws ShellException {
        
        return populateCommand( segment, null, stage, isGlobbedArgs );
    }
    
    private Stage populateCommand( String segment, String segmentSymbol, Stage stage, boolean isGlobbedArgs ) 
            throws ShellException{
        
        char symbol = getSymbol(segmentSymbol);
        
        switch( stage ){
            case INPUT_REDIR:
                return populateCommandForInputRedir(segment, segmentSymbol, symbol, isGlobbedArgs);
            case OUTPUT_REDIR:
                return populateCommandForOutputRedir(segment, segmentSymbol, symbol, isGlobbedArgs);
            default:
                return populateCommandForNormal(segment, segmentSymbol, symbol);
        }
    }
    
    private void processPostCommand( Stage stage ) throws ShellException {
        
        switch(stage){
            case INPUT_REDIR:
                if(prevLink == Link.PIPE){
                    throw new ShellException(Shell.EXP_MULTI_STDIN);
                }
                if( !hasStdin() ){
                    setStdinName("");
                }
                break;
            case OUTPUT_REDIR:
                if( nextLink == Link.PIPE ){
                    throw new ShellException(Shell.EXP_REDIR_PIPE);
                }
                if( !hasStdout() ){
                    setStdoutName("");
                }
                break;  
            default:
                break;
        }
        
        if( !hasAppName() ){
            throw new ShellException(Shell.EXP_SYNTAX);
        }
    }
    
    public void populateCommand( List<Segment> segments ) throws ShellException {
        
        if(segments == null){
            return;
        }
        
        Stage currentStage = Stage.NORMAL;
        
        for( Segment segment : segments ) {
            if(segment == null){
                continue;
            }
            if(!segment.hasGlob()){
                currentStage = populateCommand( segment.getSegment(), segment.getSegmentSymbol(), currentStage, false );
            } else{
                String[] globs = globber.processGlob( segment.getSegment(), segment.getSegmentSymbol() );
                if(globs.length > 0){
                    for( String glob : globs ){
                        currentStage = populateCommand( glob, currentStage, true );
                    }
                } else {
                    currentStage = populateCommand( segment.getSegment(), segment.getSegmentSymbol(), currentStage, false );
                }
            }
        }
        
        processPostCommand(currentStage);
    }
    
    public static void closeInputStream(InputStream inputStream)
            throws ShellException {
        if (inputStream != null && inputStream != System.in) {
            try { inputStream.close(); } 
            catch (IOException e) {}
        }
    }
    
    public static void closeOutputStream(OutputStream outputStream)
            throws ShellException {
        if (outputStream != null && outputStream != System.out) {
            try { outputStream.close(); } 
            catch (IOException e) {}
        }
    }
    
    private void executeCommandToPipe() throws ShellException, AbstractApplicationException {
        
        InputStream defaultInputStream = getDefaultInputStream();
        InputStream inStream = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        
        try{
            
            String appName = getAppName();
            String []args = getArguments();
            inStream = getStdin(defaultInputStream);
            ShellImpl.runApp(appName, args, inStream, outStream);
            
        } finally {
            
            if(inStream != defaultInputStream){
                closeInputStream(inStream);
            }
        }
        
        if( nextCmd != null ){
            ByteArrayInputStream newInStream = new ByteArrayInputStream(outStream.toByteArray());
            nextCmd.setStdin(newInStream);
        } else{
            throw new ShellException(EXP_SYNTAX);
        }
    }
    
    private void executeCommandToSeq( OutputStream stdout ) throws ShellException, AbstractApplicationException {
        
        InputStream defaultInputStream = getDefaultInputStream();
        InputStream inStream = null;
        OutputStream outStream = null;
        
        try{
            
            String appName = getAppName();
            String []args = getArguments();
            inStream = getStdin(defaultInputStream);
            outStream = getStdout(stdout);
            ShellImpl.runApp(appName, args, inStream, outStream);
            writeToStdout(outStream, Symbol.NEW_LINE_S);
            
        } finally {
            
            if(inStream != defaultInputStream){
                closeInputStream(inStream);
            }
            if(outStream != stdout){
                closeOutputStream(outStream);
            }
        }
    }
    
    private void executeCommand( OutputStream stdout ) 
            throws ShellException, AbstractApplicationException {
        
        if( nextLink == Link.PIPE ){
            executeCommandToPipe();
        } else if( nextLink == Link.SEQUENCE ) {
            executeCommandToSeq(stdout);
        } else {
            
            InputStream defaultInputStream = getDefaultInputStream();
            InputStream inStream = null;
            OutputStream outStream = null;
            
            try{
                
                String appName = getAppName();
                String []args = getArguments();
                inStream = getStdin(defaultInputStream);
                outStream = getStdout(stdout);
                ShellImpl.runApp(appName, args, inStream, outStream);
                
            } finally {
                
                if(inStream != defaultInputStream){
                    closeInputStream(inStream);
                }
                if(outStream != stdout){
                    closeOutputStream(outStream);
                }
            }
        }
    }

    /**
     * Evaluates sub-command using data provided through stdin stream. Writes
     * result to stdout stream.
     * 
     * @param stdin
     *            InputStream to get data from.
     * @param stdout
     *            OutputStream to write resultant data to.
     * 
     * @throws AbstractApplicationException
     *             If an exception happens while evaluating the sub-command.
     * @throws ShellException
     *             If an exception happens while evaluating the sub-command.
     */
    @Override
    public void evaluate(InputStream stdin, OutputStream stdout) 
            throws AbstractApplicationException, ShellException {

        if( cmdSymbols == null || cmdInput == null ){
            throw new ShellException(Shell.EXP_INTERNAL);
        }
        
        setDefaultInputStream(stdin);
        
        StringBuilder inputSymbols = new StringBuilder(cmdSymbols);
        StringBuilder input = new StringBuilder(cmdInput);
        
        processBQ(input, inputSymbols, false);
        processSQ(input, inputSymbols, false);
        processDQ(input, inputSymbols, false);
        processEmptyOutputs(input, inputSymbols);
        
        List<Segment> segments = split(input, inputSymbols);
        
        populateCommand(segments);
        
        executeCommand(stdout);
    }

    @Override
    public void terminate() {}
}
