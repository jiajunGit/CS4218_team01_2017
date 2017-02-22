package sg.edu.nus.comp.cs4218.impl;

import java.util.LinkedList;
import java.util.List;

import sg.edu.nus.comp.cs4218.Symbol;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.Shell;

/**
 * A class used to generate command objects to be used later by shellImpl for evaluation of commands
 */

public class CommandGenerator {

    private Globber globber;
    
    private List<Segment> segments;
    
    private LinkedList<Command> commands;
    
    private Command currentCmd;
    private Command prevCmd;
    
    public CommandGenerator( Globber cmdGlobber, List<Segment> cmdSegments ) {
        segments = cmdSegments;
        globber = cmdGlobber;
    }
    
    /**
     * Generate command objects using the list of segments provided when initializing this
     * class's constructor
     *      
     * @return 
     *      List of command objects
     * 
     * @throws ShellException
     *      If an exception happens while generating command objects from the list of segments provided.
     * 
     */ 
    public List<Command> generateCommands() throws ShellException {
        
        if(globber == null || segments == null){
            return new LinkedList<Command>();
        }
        
        reset();
        
        Stage currentStage = Stage.NORMAL;
        
        for( Segment segment : segments ) {
            if(segment == null){
                continue;
            }
            if( currentCmd == null ){
                currentCmd = new Command();
            }
            if(!segment.hasGlob()){
                currentStage = populateCommand( segment.getSegment(), segment.getSegmentSymbol(), currentStage, false );
            } else{
                String[] globs = globber.processGlob( segment.getSegment(), segment.getSegmentSymbol() );
                
                if(globs.length > 0){
                    for( String glob : globs ){
                        if( currentCmd == null ){
                            currentCmd = new Command();
                        }
                        currentStage = populateCommand( glob, currentStage, true );
                    }
                } else {
                    currentStage = populateCommand( segment.getSegment(), segment.getSegmentSymbol(), currentStage, false );
                }
            }
        }
        
        if( currentCmd != null ) {
            processPostCommand(currentStage);
        }
        
        return commands;
    }
    
    private void reset() {
        commands = new LinkedList<Command>();
        currentCmd = null;
        prevCmd = null;
    }
    
    private char getSymbol( String segmentSymbol ) {
        if(segmentSymbol != null && segmentSymbol.length() == 1){
            char symbol = segmentSymbol.charAt(0);
            switch( symbol ){
                case Symbol.PIPE_OP:
                case Symbol.SEQ_OP:
                case Symbol.INPUT_OP:
                case Symbol.OUTPUT_OP:
                    return symbol;
                default:
                    break;
            }
        }
        return Symbol.UNRELATED;
    }
    
    private void setStdin( String stdin ) throws ShellException {
        
        String stdout = currentCmd.getStdoutName();
        if(!stdout.isEmpty() && stdout.compareToIgnoreCase(stdin) == 0){
            throw new ShellException(Shell.EXP_SAME_REDIR);
        }
        
        if(prevCmd != null && prevCmd.getLinkTypeToNextCommand() == Link.PIPE){
            throw new ShellException(Shell.EXP_MULTI_STDIN);
        }
        
        currentCmd.setStdinName(stdin != null ? stdin : "");
    }
    
    private Stage populateCommandForInputRedir( String segment, String segmentSymbols, char symbol, boolean isGlobbedArgs ) 
            throws ShellException {
        
        switch( symbol ){
            
            case Symbol.PIPE_OP:
                
                if(currentCmd.hasStdin()){
                    return Stage.PIPE;
                } else{
                    throw new ShellException(Shell.EXP_SYNTAX);
                }
                
            case Symbol.SEQ_OP:
                
                if(currentCmd.hasStdin()){
                    return Stage.SEQUENCE;
                } else{
                    throw new ShellException(Shell.EXP_SYNTAX);
                }
                
            case Symbol.INPUT_OP:
                throw new ShellException(Shell.EXP_SYNTAX);
                
            case Symbol.OUTPUT_OP:
                
                if(currentCmd.hasStdin()){
                    return Stage.OUTPUT_REDIR;
                } else{
                    throw new ShellException(Shell.EXP_SYNTAX);
                }
                
            default:

                if( isGlobbedArgs ){
                    if(currentCmd.hasStdin()){
                        throw new ShellException(Shell.EXP_MULTI_STDIN);
                    } else {
                        setStdin(segment);
                    }
                } else if(currentCmd.hasStdin()){
                    return populateCommandForNormal(segment, segmentSymbols, Symbol.UNRELATED);
                } else{
                    setStdin(segment);
                    return Stage.NORMAL;
                }
        }
        return Stage.INPUT_REDIR;
    }
    
    private void setStdout( String stdout ) throws ShellException {
        
        String stdin = currentCmd.getStdinName();
        if(!stdin.isEmpty() && stdin.compareToIgnoreCase(stdout) == 0){
            throw new ShellException(Shell.EXP_SAME_REDIR);
        }
        currentCmd.setStdoutName(stdout != null ? stdout : "");
    }
    
    private Stage populateCommandForOutputRedir( String segment, String segmentSymbols, char symbol, boolean isGlobbedArgs ) 
            throws ShellException {
        
        switch( symbol ){
        
            case Symbol.PIPE_OP:
                throw new ShellException(Shell.EXP_REDIR_PIPE);
                
            case Symbol.SEQ_OP:
                
                if(currentCmd.hasStdout()){
                    return Stage.SEQUENCE;
                } else{
                    throw new ShellException(Shell.EXP_SYNTAX);
                }
                
            case Symbol.INPUT_OP:
                
                if(currentCmd.hasStdout()){
                    return Stage.INPUT_REDIR;
                } else{
                    throw new ShellException(Shell.EXP_SYNTAX);
                }
                
            case Symbol.OUTPUT_OP:
                throw new ShellException(Shell.EXP_SYNTAX);
                
            default:

                if( isGlobbedArgs ){
                    if(currentCmd.hasStdout()){
                        throw new ShellException(Shell.EXP_SYNTAX);
                    } else {
                        setStdout(segment);
                    }
                } else if(currentCmd.hasStdout()){
                    return populateCommandForNormal(segment, segmentSymbols, Symbol.UNRELATED);
                } else {
                    setStdout(segment);
                    return Stage.NORMAL;
                }
                
        }
        return Stage.OUTPUT_REDIR;
    }
    
    private void populateCommandEmptyArgs() throws ShellException {
        if(currentCmd.hasAppName()) {
            currentCmd.addArguments("");
        } else {
            throw new ShellException(Shell.EXP_SYNTAX);
        }
    }
    
    private void populateCommandNonEmptyArgs( String segment, String segmentSymbols ) throws ShellException {
        if(currentCmd.hasAppName()) {
            currentCmd.addArguments(segment);
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
                currentCmd.setAppName(segment);
            } else{
                throw new ShellException(Shell.EXP_SYNTAX);
            }
        } else {
            throw new ShellException(Shell.EXP_SYNTAX);
        }
    }
    
    private void populateCommandArgs( String segment, String segmentSymbols ) throws ShellException {
        if(!segment.isEmpty()){
            populateCommandNonEmptyArgs(segment, segmentSymbols);
        } else {
            populateCommandEmptyArgs();
        }
    }
    
    private Stage populateCommandForNormal( String segment, String segmentSymbols, char symbol ) 
            throws ShellException {
        
        switch( symbol ){
        
            case Symbol.PIPE_OP:
                
                if(currentCmd.hasAppName()){
                    return Stage.PIPE;
                } else{
                    throw new ShellException(Shell.EXP_SYNTAX);
                }
                
            case Symbol.SEQ_OP:
                
                if(currentCmd.hasAppName()){
                    return Stage.SEQUENCE;
                } else{
                    throw new ShellException(Shell.EXP_SYNTAX);
                }
                
            case Symbol.INPUT_OP:
                
                if(currentCmd.hasStdin()){
                    throw new ShellException(Shell.EXP_SYNTAX);
                } else {
                    return Stage.INPUT_REDIR;
                }
                
            case Symbol.OUTPUT_OP:
                
                if(currentCmd.hasStdout()){
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
    
    private void processPostCommand( Stage stage ) throws ShellException {
        
        switch(stage){
        
            case INPUT_REDIR:
            case OUTPUT_REDIR:
            case PIPE:
            case SEQUENCE:
                throw new ShellException(Shell.EXP_SYNTAX);   
            default:
                break;
        }
        
        if( !currentCmd.hasAppName() ){
            throw new ShellException(Shell.EXP_SYNTAX);
        }
        if(prevCmd != null){
            prevCmd.setNextCommand(currentCmd);
        }
        commands.add(currentCmd);
    }
    
    private void processPostCommand() throws ShellException {
        
        if( !currentCmd.hasAppName() ){
            throw new ShellException(Shell.EXP_SYNTAX);
        }
        if(prevCmd != null){
            prevCmd.setNextCommand(currentCmd);
        }
        commands.add(currentCmd);
        
        prevCmd = currentCmd;
        currentCmd = null;
    }
    
    private void processPostPipe() throws ShellException {
        
        if( currentCmd.hasStdout() ){
            throw new ShellException(Shell.EXP_REDIR_PIPE);
        }
        currentCmd.setLinkToNextCommand(Stage.PIPE);
        processPostCommand();
    }
    
    private Stage populateCommandForPipe( String segment, String segmentSymbols, char symbol ) throws ShellException {
        
        switch( symbol ){
        
            case Symbol.PIPE_OP:
                throw new ShellException(Shell.EXP_SYNTAX);
                
            case Symbol.SEQ_OP:
                throw new ShellException(Shell.EXP_SYNTAX);
                
            case Symbol.INPUT_OP:
                throw new ShellException(Shell.EXP_MULTI_STDIN);
                
            case Symbol.OUTPUT_OP:
                processPostPipe();
                return Stage.OUTPUT_REDIR;
                
            default:
                processPostPipe();
                currentCmd = new Command();
                return populateCommandForNormal(segment, segmentSymbols, symbol);
        }
    }
    
    private void processPostSeq() throws ShellException {
        currentCmd.setLinkToNextCommand(Stage.SEQUENCE);
        processPostCommand();
    }
    
    private Stage populateCommandForSeq( String segment, String segmentSymbols, char symbol ) throws ShellException {
        
            switch( symbol ){
            
                case Symbol.PIPE_OP:
                    throw new ShellException(Shell.EXP_SYNTAX);
                    
                case Symbol.SEQ_OP:
                    throw new ShellException(Shell.EXP_SYNTAX);
                    
                case Symbol.INPUT_OP:
                    processPostSeq();
                    return Stage.INPUT_REDIR;
                    
                case Symbol.OUTPUT_OP:
                    processPostSeq();
                    return Stage.OUTPUT_REDIR;
                    
                default:
                    processPostSeq();
                    currentCmd = new Command();
                    return populateCommandForNormal(segment, segmentSymbols, symbol);
            }
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
            case PIPE:
                return populateCommandForPipe(segment, segmentSymbol, symbol);
            case SEQUENCE:
                return populateCommandForSeq(segment, segmentSymbol, symbol);
            default:
                return populateCommandForNormal(segment, segmentSymbol, symbol);
        }
    }
}
