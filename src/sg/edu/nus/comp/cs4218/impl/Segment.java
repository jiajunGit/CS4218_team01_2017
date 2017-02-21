package sg.edu.nus.comp.cs4218.impl;

import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.exception.ShellException;

/**
 * A class used to store strings containing sub-commands split from commands
 */

public class Segment {

    private String m_Segment;
    private String m_SegmentSymbol;
    private boolean m_hasGlob;
    
    public Segment( String segment, String segmentSymbol, boolean hasGlob ) throws ShellException {
        m_Segment = segment != null ? segment : "";
        m_SegmentSymbol = segmentSymbol != null ? segmentSymbol : "";
        m_hasGlob = hasGlob;
        if(m_Segment.length() != m_SegmentSymbol.length()){
            throw new ShellException(Shell.EXP_INTERNAL);
        }
    }
    
    public Segment( char segment, char segmentSymbol, boolean hasGlob ) throws ShellException {
        this( Character.toString(segment), Character.toString(segmentSymbol), hasGlob );
    }
    
    public boolean hasGlob() {
        return m_hasGlob;
    }
    
    public String getSegment() {
        return m_Segment;
    }
    
    public String getSegmentSymbol() {
        return m_SegmentSymbol;
    }
}
