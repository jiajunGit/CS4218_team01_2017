package sg.edu.nus.comp.cs4218.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class FileRegexFilter implements FilenameFilter {

    private String m_Regex;
    
    public static final String EXP_REGEX = "Invalid regex pattern";
    
    public FileRegexFilter( String regex ) throws PatternSyntaxException {
        
        if(regex == null){
            throw new PatternSyntaxException(EXP_REGEX, regex, 0);
        }
        Pattern.compile(regex);
        
        m_Regex = regex;
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.matches(m_Regex);
    }
}