package sg.edu.nus.comp.cs4218.impl;

import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.exception.ShellException;

/**
 * A class used to store strings containing sub-commands split from commands
 */

public class Segment {

	private String mSegment;
	private String mSegmentSymbol;
	private boolean mHasGlob;

	public Segment(String segment, String segmentSymbol, boolean hasGlob) throws ShellException {
		mSegment = segment != null ? segment : "";
		mSegmentSymbol = segmentSymbol != null ? segmentSymbol : "";
		mHasGlob = hasGlob;
		if (mSegment.length() != mSegmentSymbol.length()) {
			throw new ShellException(Shell.EXP_INTERNAL);
		}
	}

	public Segment(char segment, char segmentSymbol, boolean hasGlob) throws ShellException {
		this(Character.toString(segment), Character.toString(segmentSymbol), hasGlob);
	}

	public boolean hasGlob() {
		return mHasGlob;
	}

	public String getSegment() {
		return mSegment;
	}

	public String getSegmentSymbol() {
		return mSegmentSymbol;
	}
}
