package sg.edu.nus.comp.cs4218.impl;

import java.io.InputStream;
import java.util.LinkedList;

import sg.edu.nus.comp.cs4218.Symbol;

/**
 * A class used to store different call command components, namely the application name, 
 * the arguments (if any), the input redirection file path (if any) and output redirection file path (if
 * any), link type to next command (if any), reference to next command (if any), input stream provided by
 * previous command (if any).
 * 
 */

public class Command {

    private String appName;
    private String stdinName;
    private String stdoutName;
    
    private LinkedList<String> arguments;
    
    private Link link;
    private Command nextCommand;
    
    private InputStream stdin;
    
    public Command() {
        
        appName = "";
        stdinName = "";
        stdoutName = "";
        arguments = new LinkedList<String>();
        link = Link.NONE;
        nextCommand = null;
        stdin = null;
    }
    
    public void setLinkToNextCommand( char symbol ) {
        
        switch(symbol){
        
            case Symbol.SEQ_OP:
                link = Link.SEQUENCE;
                break;
                
            case Symbol.PIPE_OP:
                link = Link.PIPE;
                break;
                
            default:
                link = Link.NONE;
                break;
        }
    }
    
    public void setLinkToNextCommand( Stage stage ) {
        
        if(stage != null){
            switch(stage){
            
                case PIPE:
                    link = Link.PIPE;
                    return;
                    
                case SEQUENCE:
                    link = Link.SEQUENCE;
                    return;
                    
                default:
                    break;
            }
        }
        link = Link.NONE;
    }
    
    public Link getLinkTypeToNextCommand() {
        return link;
    }
    
    public void setNextCommand( Command next ) {
        nextCommand = next;
    }
    
    public Command getNextCommand() {
        return nextCommand;
    }
    
    public InputStream getStdin() {
        return stdin;
    }
    
    public void setStdin( InputStream inputStream ) {
        stdin = inputStream;
    }
    
    public String getStdinName() {
        return stdinName;
    }
    
    public boolean hasStdin() {
        return ( stdin != null || (stdinName != null && !stdinName.isEmpty()) );
    }
    
    public void setStdinName( String inputStreamName ) {
        stdinName = inputStreamName;
    }
    
    public String getStdoutName() {
        return stdoutName;
    }
    
    public boolean hasStdout() {
        return (stdoutName != null && !stdoutName.isEmpty());
    }
    
    public void setStdoutName( String outputStreamName ) {
        stdoutName = outputStreamName;
    }
    
    public String[] getArguments() {

        String[] args = new String[arguments.size()];
        
        int index = 0;
        for( String arg : arguments ){
            args[index++] = arg;
        }
        return args;
    }
    
    public void addArguments(String arg) {
        arguments.add(arg);
    }
    
    public String getAppName() {
        return appName;
    }
    
    public boolean hasAppName() {
        return (appName != null && !appName.isEmpty());
    }
    
    public void setAppName(String applicationName) {
        appName = applicationName;
    }
}
