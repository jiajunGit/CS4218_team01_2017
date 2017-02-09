package sg.edu.nus.comp.cs4218.impl;

import java.io.File;
import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Symbol;
import sg.edu.nus.comp.cs4218.Utility;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.Shell;

public class Globber {

    private String PATH_REGEX;
    
    public Globber() {
        if(Utility.isWindows()){
            PATH_REGEX = Symbol.REGEX_WINDOWS_PATH_SEPARATOR;
        } else {
            PATH_REGEX = Symbol.REGEX_NON_WINDOWS_PATH_SEPARATOR;
        }
    }
    
    public String[] processGlob( String input, String inputSymbols ) throws ShellException {
        
        if( input == null || inputSymbols == null ){
            return new String[0];
        }
        
        int index = inputSymbols.indexOf(Symbol.GLOB_OP);
        
        if(index < 0){
            return new String[0];
        }
        
        String path = Environment.currentDirectory;
        if( index > 0 ) {
            path = Environment.getAbsPath(input.substring(0, index));
            if(path.isEmpty()){
                return new String[0];
            }
        }
        
        String dirPathStr = path;
        if(!Environment.isDirectory(path)){
            dirPathStr = Environment.getParentPathFrom(path);
            if(dirPathStr.isEmpty() || !Environment.isDirectory(path)){
                return new String[0];
            }
        }
        
        int start = findGlobStart( input, index );
        if( start < 0 ){
            return new String[0];
        }
        
        String pathToSplit = input.substring(start, input.length());
        String pathSymbolToSplit = inputSymbols.substring(start, inputSymbols.length());
        
        String[] segments = pathToSplit.split(PATH_REGEX);
        String[] symbolSegments = pathSymbolToSplit.split(PATH_REGEX);
        
        LinkedList<String> dirPaths = new LinkedList<String>();
        LinkedList<String> listReference = dirPaths;
        dirPaths.add(dirPathStr);
        
        try{
            
            for( int i = 0, size = segments.length; i < size; ++i ) {
                
                if( segments[i].length() <= 0 ){
                    continue;
                }
                    
                LinkedList<String> newDirPaths = new LinkedList<String>();
                for( String dirPath : dirPaths ){
                    
                    if( !Environment.isDirectory(dirPath) ){
                        continue;
                    }
                        
                    File dir = new File(dirPath);
                    String regex = generateGlobRegex(segments[i], symbolSegments[i]);
                    FileRegexFilter filter = new FileRegexFilter(regex);
                    
                    String[] fileNames = dir.list(filter);
                    
                    for( String fileName : fileNames ){
                        
                        String newPath = dirPath;
                        newPath += Symbol.PATH_SEPARATOR;
                        newPath += fileName;
                        
                        newDirPaths.add(newPath);
                    }
                }
                dirPaths = newDirPaths;
            }
            
        } catch( PatternSyntaxException e ){
            throw new ShellException(Shell.EXP_INTERNAL);
        }
        
        if( dirPaths != listReference ){
            return dirPaths.toArray(new String[dirPaths.size()]);
        } else{
            return new String[0];
        }  
    }
    
    private String generateGlobRegex( String glob, String globSymbol ) {
        
        StringBuilder sb = new StringBuilder(glob.length());
        StringBuilder finalRegex = new StringBuilder(glob.length()*2);
        
        finalRegex.append(Symbol.REGEX_CASE_INSENSITIVE);
        
        for( int i = 0, length = globSymbol.length(); i < length; ++i ){
            
            char currentChar = globSymbol.charAt(i);
            
            if( currentChar != Symbol.GLOB_OP ){
                sb.append(currentChar);
            } else {
                if( sb.length() > 0 ){
                    String pattern = Pattern.quote(sb.toString());
                    sb.setLength(0);
                    finalRegex.append(pattern);
                }
                finalRegex.append(Symbol.REGEX_WILDCARD);
            }
        }
        
        if(sb.length() > 0){
            String pattern = Pattern.quote(sb.toString());
            sb.setLength(0);
            finalRegex.append(pattern);
        }
        return finalRegex.toString();
    }
    
    private boolean isPathSeparator( char character ) {
        return character == Symbol.PATH_SEPARATOR;
    }
    
    private int findGlobStart( String input, int globIndex ) {
        
        if(globIndex < 0 || globIndex >= input.length()){
            return -1;
        }
        
        globIndex = globIndex - 1;
        while(globIndex >= 0){
            if(isPathSeparator(input.charAt(globIndex))){
                return globIndex + 1;
            }
            --globIndex;
        }
        return 0;
    }
}
