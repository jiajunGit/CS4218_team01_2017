package sg.edu.nus.comp.cs4218.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.ShellException;

public class OutStream {

    private IOStreamType type;
    private String name;
    
    public static final String ERROR_EXP_NAME_UNDEFINED = "Output stream name not defined";
    public static final String ERROR_UNSUPPORTED_OPERATION = "Unsupported operation in outstream";
    public static final String EXP_STDOUT = "Error writing to stdout.";
    
    public OutStream() {
        type = IOStreamType.DEFAULT;
        name = "";
    }
    
    public void setName( String stdoutName ) {
        if( stdoutName != null && !stdoutName.isEmpty() ){
            type = IOStreamType.WITH_NAME;
            name = stdoutName;
            generateFile(name);
        } else {
            type = IOStreamType.NULL;
        }
    }
    
    public boolean hasStdout() {
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
    
    private boolean generateFile( String stdoutName ) {
        
        String absPath = Environment.getAbsPath(stdoutName);
        if(absPath.isEmpty()){
            return false;
        }
        
        if( Environment.isDirectory(absPath) ){
            return false;
        }
        
        if( !Environment.isExists(absPath) && !Environment.createNewFile(absPath) ){
            return false;
        }
        
        return true;
    }
    
    private OutputStream generateStreamFromName( String stdoutName ) 
            throws ShellException {
        
        try {
                
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
            
            return new FileOutputStream(absPath);
                
        } catch (SecurityException | IOException e) {
            throw new ShellException(EXP_STDOUT);
        }
    }
    
    public OutputStream getStream(OutputStream defaultStream) 
            throws ShellException {
        
        switch(type){
            case DEFAULT:
                return defaultStream;
            case NULL:
                return null;
            case WITH_NAME:
                return generateStreamFromName(name);
            default:
                throw new ShellException(ERROR_UNSUPPORTED_OPERATION);
        }
    }
}
