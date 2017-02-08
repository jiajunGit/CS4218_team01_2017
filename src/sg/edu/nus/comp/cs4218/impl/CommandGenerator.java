package sg.edu.nus.comp.cs4218.impl;

import java.util.LinkedList;
import java.util.List;

import sg.edu.nus.comp.cs4218.Symbol;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.Shell;

public class CommandGenerator {

    private Globber globber;
    
    private List<Segment> segments;
    
    private LinkedList<Command> commands;
    
    private Command currentCmd;
    private Command prevCmd;
    
    public CommandGenerator( Globber cmdGlobber, List<Segment> cmdSegments ) {
        reset();
        segments = cmdSegments;
        globber = cmdGlobber;
    }
    
    public List<Command> generateCommands() throws ShellException {
        
        if(globber == null || segments == null){
            return new LinkedList<Command>();
        }
        
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
                for( String glob : globs ){
                    if( currentCmd == null ){
                        currentCmd = new Command();
                    }
                    currentStage = populateCommand( glob, currentStage, true );
                }
            }
        }
        
        if( currentCmd != null ) {
            processPostCommand(currentStage);
        }
        
        List<Command> cmdCommands = commands;
        reset();
        return cmdCommands;
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
        
        currentCmd.setStdinName(stdin);
    }
    
    private Stage populateCommandForInputRedir( String segment, char symbol, boolean isGlobbedArgs ) 
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
                    return populateCommandForNormal(segment, Symbol.UNRELATED);
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
        currentCmd.setStdoutName(stdout);
    }
    
    private Stage populateCommandForOutputRedir( String segment, char symbol, boolean isGlobbedArgs ) 
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
                    return populateCommandForNormal(segment, Symbol.UNRELATED);
                } else {
                    setStdout(segment);
                    return Stage.NORMAL;
                }
                
        }
        return Stage.OUTPUT_REDIR;
    }
    
    private Stage populateCommandForNormal( String segment, char symbol ) 
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
                
                if(currentCmd.hasAppName()){
                    currentCmd.addArguments(segment);
                } else{
                    currentCmd.setAppName(segment);
                }
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
    
    private Stage populateCommandForPipe( String segment, char symbol ) throws ShellException {
        
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
                return populateCommandForNormal(segment, symbol);
        }
    }
    
    private void processPostSeq() throws ShellException {
        currentCmd.setLinkToNextCommand(Stage.SEQUENCE);
        processPostCommand();
    }
    
    private Stage populateCommandForSeq( String segment, char symbol ) throws ShellException {
        
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
                    return populateCommandForNormal(segment, symbol);
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
                return populateCommandForInputRedir(segment, symbol, isGlobbedArgs);
            case OUTPUT_REDIR:
                return populateCommandForOutputRedir(segment, symbol, isGlobbedArgs);
            case PIPE:
                return populateCommandForPipe(segment, symbol);
            case SEQUENCE:
                return populateCommandForSeq(segment, symbol);
            default:
                return populateCommandForNormal(segment, symbol);
        }
    }
}
