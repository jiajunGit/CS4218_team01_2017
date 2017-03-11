package sg.edu.nus.comp.cs4218.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.ShellException;

public class InStream {

    private IOStreamType type;
    private InputStream stdinFromPrevCmd;
    private String name;
    
    public static final String ERROR_EXP_NAME_UNDEFINED = "Input stream name not defined";
    public static final String EXP_STDIN = "Error writing to stdin.";
    
    public InStream() {
        type = IOStreamType.DEFAULT;
        stdinFromPrevCmd = null;
        name = "";
    }
    
    public void setName( String stdinName ) {
        if( stdinName != null && !stdinName.isEmpty() ){
            type = IOStreamType.WITH_NAME;
            name = stdinName;
        } else {
            type = IOStreamType.NULL;
        }
    }
    
    public void setStdin( InputStream stdin ) {
        type = IOStreamType.FROM_PREV_CMD;
        stdinFromPrevCmd = stdin;
    }
    
    public boolean hasStdin() {
        return type != IOStreamType.DEFAULT;
    }
    
    public IOStreamType getType() {
        return type;
    }
    
    public String getName() throws ShellException {
        if(type != IOStreamType.WITH_NAME){
            throw new ShellException(ERROR_EXP_NAME_UNDEFINED);
        }
        return name;
    }
    
    private InputStream generateStreamFromName( String stdinName ) throws ShellException {
        
        try {
            
            String absPath = Environment.getAbsPath(stdinName);
            if(absPath.isEmpty()){
                throw new ShellException(EXP_STDIN);
            }
            
            if( !Environment.isFile(absPath) ){
                throw new ShellException(EXP_STDIN);
            }
            
            return new FileInputStream(absPath);
            
        } catch (SecurityException | IOException e) {
            throw new ShellException(EXP_STDIN);
        }
    }
    
    public InputStream getStream(InputStream defaultStream) throws ShellException {
        switch(type){
            case DEFAULT:
                return defaultStream;
            case WITH_NAME:
                return generateStreamFromName(name);
            case FROM_PREV_CMD:
                return stdinFromPrevCmd;
            default:
                return null;
        }
    }
}
