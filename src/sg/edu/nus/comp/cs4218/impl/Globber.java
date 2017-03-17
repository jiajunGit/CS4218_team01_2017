package sg.edu.nus.comp.cs4218.impl;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Symbol;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.Shell;

/**
 * A class used to expand glob paths.
 */

public class Globber {

	private String pathRegex;

	public Globber() {
		pathRegex = Pattern.quote(Symbol.PATH_SEPARATOR_S);
	}

	/**
	 * Expands the glob path if the path can be expanded or else it returns the
	 * input unchanged. Hidden files are ignored by glob.
	 * 
	 * @param input
	 *            String containing the glob path
	 * @param inputSymbols
	 *            String containing the symbols generated from input by
	 *            generateSymbols method in shellImpl
	 * 
	 * @return An string array containing all the paths expanded from the glob
	 *         path provided
	 * 
	 * @throws ShellException
	 *             If an exception happens while expanding the glob path
	 */
	public String[] processGlob(String input, String inputSymbols) throws ShellException {

		if (input == null || inputSymbols == null) {
			return new String[0];
		}

		int index = inputSymbols.indexOf(Symbol.GLOB_OP);

		if (index < 0) {
			return new String[0];
		}

		int start = findGlobStart(input, index);
		if (start < 0) {
			return new String[0];
		}

		int dirPathEnd = start;
		String dirPathStr = input.substring(0, dirPathEnd);

		if (dirPathStr.isEmpty()) {
			dirPathStr = Environment.currentDirectory;
		} else {
			dirPathStr = Environment.getAbsPath(dirPathStr);
		}

		if (dirPathStr.isEmpty() || !Environment.isDirectory(dirPathStr)) {
			return new String[0];
		}

		String pathToSplit = input.substring(start, input.length());
		String pathSymbolToSplit = inputSymbols.substring(start, inputSymbols.length());

		String[] segments = pathToSplit.split(pathRegex);
		String[] symbolSegments = splitSegmentSymbols(pathToSplit, pathSymbolToSplit, pathRegex, segments.length);

		LinkedList<String> dirPaths = new LinkedList<String>();
		LinkedList<String> listReference = dirPaths;

		if (!Environment.isHidden(dirPathStr)) {
			dirPaths.add(dirPathStr);
		}

		try {

			for (int i = 0, size = segments.length; i < size; ++i) {

				if (segments[i].length() <= 0) {
					continue;
				}

				PathSymbolType pathSymbolType = getpathSymbolType(segments[i]);

				String regex = generateGlobRegex(segments[i], symbolSegments[i]);
				FileRegexFilter filter = new FileRegexFilter(regex);

				LinkedList<String> newDirPaths = new LinkedList<String>();
				for (String dirPath : dirPaths) {

					if (!Environment.isDirectory(dirPath)) {
						continue;
					}

					switch (pathSymbolType) {
    					case CURRENT:
    						if (!Environment.isHidden(dirPath)) {
    							newDirPaths.add(dirPath);
    						}
    						break;
    					case PREVIOUS:
    						String prevPath = Environment.getAbsPath(dirPath + Symbol.PATH_SEPARATOR_S + Symbol.PREV_DIR_S);
    						if (!prevPath.isEmpty() && !Environment.isHidden(prevPath)) {
    							newDirPaths.add(prevPath);
    						}
    						break;
    					default:
    					    File dir = new File(dirPath);
    	                    String[] fileNames = dir.list(filter);
    						for (String fileName : fileNames) {
    							String newPath = dirPath;
    							newPath += Symbol.PATH_SEPARATOR;
    							newPath += fileName;
    							if (!Environment.isHidden(newPath)) {
    								newDirPaths.add(newPath);
    							}
    						}
    						break;
					}
				}
				dirPaths = newDirPaths;
			}

		} catch (PatternSyntaxException e) {
			throw new ShellException(Shell.EXP_INTERNAL);
		}

		if (dirPaths != listReference) {
			String[] outDirPaths = dirPaths.toArray(new String[dirPaths.size()]);
			Arrays.sort(outDirPaths);
			return outDirPaths;
		} else {
			return new String[0];
		}
	}

	private PathSymbolType getpathSymbolType(String segment) {
		if (Symbol.CURRENT_DIR_S.equals(segment)) {
			return PathSymbolType.CURRENT;
		} else if (Symbol.PREV_DIR_S.equals(segment)) {
			return PathSymbolType.PREVIOUS;
		} else {
			return PathSymbolType.NORMAL;
		}
	}

	private String[] splitSegmentSymbols(String pathToSplit, String pathSymbolToSplit, String regex, int numSegments) {

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(pathToSplit);

		String[] splitPaths = new String[numSegments];

		int index = 0;
		int startFrom = 0;
		while (matcher.find()) {

			int start = matcher.start();
			int end = matcher.end();

			if (end > start) {
				splitPaths[index++] = pathSymbolToSplit.substring(startFrom, start);
				startFrom = end;
			}
		}

		if (startFrom < pathSymbolToSplit.length()) {
			splitPaths[index] = pathSymbolToSplit.substring(startFrom);
		}

		return splitPaths;
	}

	private String generateGlobRegex(String glob, String globSymbol) {

		StringBuilder sb = new StringBuilder(glob.length());
		StringBuilder finalRegex = new StringBuilder(glob.length() * 2);

		finalRegex.append(Symbol.REGEX_CASE_INSENSITIVE);

		for (int i = 0, length = globSymbol.length(); i < length; ++i) {

			if (globSymbol.charAt(i) != Symbol.GLOB_OP) {
				sb.append(glob.charAt(i));
			} else {
				if (sb.length() > 0) {
					String pattern = Pattern.quote(sb.toString());
					sb.setLength(0);
					finalRegex.append(pattern);
				}
				finalRegex.append(Symbol.REGEX_WILDCARD);
			}
		}

		if (sb.length() > 0) {
			String pattern = Pattern.quote(sb.toString());
			sb.setLength(0);
			finalRegex.append(pattern);
		}
		return finalRegex.toString();
	}

	private boolean isPathSeparator(char character) {
		return character == Symbol.PATH_SEPARATOR;
	}

	private int findGlobStart(String input, int globInd) {
		int globIndex = globInd;

		if (globIndex < 0 || globIndex >= input.length()) {
			return -1;
		}

		globIndex = globIndex - 1;
		while (globIndex >= 0) {
			if (isPathSeparator(input.charAt(globIndex))) {
				return globIndex + 1;
			}
			--globIndex;
		}
		return 0;
	}
}
