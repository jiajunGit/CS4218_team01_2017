package sg.edu.nus.comp.cs4218;

import java.io.File;

public final class Symbol {

	public static final char SPACE = ' ';
	public static final char TAB = '\t';
	public static final char SINGLE_QUOTE = '\'';
	public static final char DOUBLE_QUOTE = '\"';
	public static final char BACK_QUOTE = '`';
	public static final char PIPE_OP = '|';
	public static final char SEQ_OP = ';';
	public static final char INPUT_OP = '<';
	public static final char OUTPUT_OP = '>';
	public static final char GLOB_OP = '*';
	public static final char UNRELATED = '_';
	public static final char UNQUOTED_UNRELATED = '$';
	public static final char EMPTY_OUTPUT = '#';
	public static final char LINE_FEED = '\n';
	public static final char CARRIAGE_RETURN = '\r';
	public static final char PATH_SEPARATOR = File.separatorChar;
	public static final char CURRENT_DIR = '.';

	public static final String SPACE_S = " ";
	public static final String TAB_S = "\t";
	public static final String SINGLE_QUOTE_S = "\'";
	public static final String DOUBLE_QUOTE_S = "\"";
	public static final String BACK_QUOTE_S = "`";
	public static final String PIPE_OP_S = "|";
	public static final String SEQ_OP_S = ";";
	public static final String INPUT_OP_S = "<";
	public static final String OUTPUT_OP_S = ">";
	public static final String GLOB_OP_S = "*";
	public static final String UNRELATED_S = "_";
	public static final String UNQUOTED_UNRELATED_S = "$";
	public static final String EMPTY_OUTPUT_S = "#";
	public static final String NEW_LINE_S = System.lineSeparator();
	public static final String PATH_SEPARATOR_S = File.separator;
	public static final String CURRENT_DIR_S = ".";
	public static final String PREV_DIR_S = "..";
	public static final String EMPTY_S = "";

	public static final String REGEX_WINDOWS_PATH_SEPARATOR = "\\\\";
	public static final String REGEX_NON_WINDOWS_PATH_SEPARATOR = "/";
	public static final String REGEX_WILDCARD = ".*";
	public static final String REGEX_CASE_INSENSITIVE = "(?i)";
	public static final String REGEX_EMPTY_OUTPUT = "#+";

	private Symbol() {
	}
}
