package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.app.Wc;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.exception.WcException;

public class WcApplication implements Wc {

	boolean isAll = true;
	boolean isM = false;
	boolean isW = false;
	boolean isL = false;
	boolean displayIsPrimed = false;

	private static final int TEMP_BUF_SZ = 10000;
	private static byte[] tempBuf = new byte[TEMP_BUF_SZ];

	int noCharsStdin = -1;
	int noWordsStdin = -1;
	int noNewlinesStdin = -1;

	Vector<String> fileNames;
	Vector<String> toDisplay = new Vector<String>();

	/**
	 * Runs the grep application with the specified arguments.
	 * 
	 * @param args
	 *            Array of arguments for the application. Order is option(s) first
	 *            followed by file(s). No files can be given. If
	 *            this is the case, then count for stdin
	 * 
	 * @param stdin
	 *            An InputStream. The input for the command is read from this
	 *            inputStream if no files are specified.
	 * @param stdout
	 *            An OutputStream. The output of the command is written to this
	 *            OutputStream.
	 * 
	 * @throws SortException
	 *             If arguments do not follow assumption expected of args and if. 
	 *             invalid options are given Also
	 *             thrown if there are any I/O errors to/from stdin, stdout or
	 *             file.
	 * 
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		// TODO Auto-generated method stub
		if (stdout == null) {
			throw new WcException("stdout invalid");
		}

		if (args == null) {
			throw new WcException("null args");
		}

		fileNames = new Vector<String>();
		toDisplay = new Vector<String>();
		String result = "";

		int currArg = 0;

		while (currArg < args.length && args[currArg].charAt(0) == '-') {
			findOps(args[currArg]);
			currArg++;
		}

		while (currArg < args.length) {
			fileNames.add(args[currArg]);
			toDisplay.add("");
			currArg++;
			displayIsPrimed = true;
		}

		String combinedNames = combineFileNames();

		if (combinedNames.isEmpty()) {
			if (stdin == null) {
				throw new WcException("expect stdin but stdin is null");
			}

			result = loadFromStdIn(stdin);
		}

		if (!combinedNames.isEmpty()) {
			result = loadFromFiles(combinedNames);
		}

		try {
			String[] resultArray = result.split(System.getProperty("line.separator"));
			for (int resIndex = 0; resIndex < resultArray.length; resIndex++) {
				if (resultArray[resIndex].equals("Invalid File")) {
					throw new WcException("Invalid File");
				}
				if (resIndex != resultArray.length - 1) {
					stdout.write((resultArray[resIndex] + System.getProperty("line.separator")).getBytes());
					stdout.flush();
				}

				if (resIndex == resultArray.length - 1) {
					stdout.write(resultArray[resIndex].getBytes());
					stdout.flush();
				}

			}

		} catch (IOException e) {
			throw new WcException("Cannot write to stdout");
		}
	}

	/**
	 * Gets the count from the files in the parameter. Files must not contain
	 * spaces. Files with spaces would already have been handled by app.run(..)
	 * 
	 * @param combinedNames the string containing filenames seperated by space
	 * @return the resultant with formatted counts
	 */
	private String loadFromFiles(String combinedNames) {
		String result = "";
		if (isAll) {
			String mCommand = "wc -mwl " + combinedNames;
			result = printAllCountsInFile(mCommand);
			isM = false;
			isW = false;
			isL = false;
		}

		if (isM) {
			String mCommand = "wc -m " + combinedNames;
			result = printCharacterCountInFile(mCommand);
		}

		if (isW) {
			String mCommand = "wc -w " + combinedNames;
			result = printWordCountInFile(mCommand);
		}

		if (isL) {
			String mCommand = "wc -l " + combinedNames;
			result = printNewlineCountInFile(mCommand);
		}

		return result;
	}

	/**
	 * Gets the count from stdin
	 * 
	 * @param stdin the InputStream
	 * @return the result with formatted counts
	 */
	private String loadFromStdIn(InputStream stdin) {
		String result = "";
		if (isAll) {
			String mCommand = "wc -mwl ";
			result = printAllCountsInStdin(mCommand, stdin);
			isM = false;
			isW = false;
			isL = false;
		}

		if (isM) {
			String mCommand = "wc -m";
			result = printCharacterCountInStdin(mCommand, stdin);
		}

		if (isW) {
			String mCommand = "wc -w";
			result = printWordCountInStdin(mCommand, stdin);
		}

		if (isL) {
			String mCommand = "wc -l";
			result = printNewlineCountInStdin(mCommand, stdin);
		}

		return result;
	}

	
	/**
	 * combines the file names from the name list into a string
	 * 
	 * @return the string containing gile names
	 */
	private String combineFileNames() {
		// TODO Auto-generated method stub
		StringBuilder result = new StringBuilder();
		for (int curName = 0; curName < fileNames.size(); curName++) {
			result.append(fileNames.get(curName));
			if (curName != fileNames.size() - 1) {
				result.append(" ");
			}
		}

		return result.toString();
	}

	
	/**
	 * Finds all the options in the string
	 * 
	 * @param string the command
	 * @throws WcException when there is an invalid option
	 */
	private void findOps(String string) throws WcException {
		if (string.length() == 1) {
			throw new WcException("Invalid Option");
		}

		for (int currChar = 1; currChar < string.length(); currChar++) {
			if (string.charAt(currChar) == 'm') {
				isM = true;
				isAll = false;
				continue;
			}

			if (string.charAt(currChar) == 'w') {
				isW = true;
				isAll = false;
				continue;
			}

			if (string.charAt(currChar) == 'l') {
				isL = true;
				isAll = false;
				continue;
			}

			throw new WcException("Invalid Options");
		}

	}

	/* 
	 * Returns formatted string with  char count according to string parameter command
	 * 
	 * args the command to print char. strictly "wc -m filename(s)"
	 * 
	 * (non-Javadoc)
	 * @see sg.edu.nus.comp.cs4218.app.Wc#printCharacterCountInFile(java.lang.String)
	 */
	@Override
	public String printCharacterCountInFile(String args) {
		String[] argsArr = args.split(" ");
		StringBuilder result = new StringBuilder();
		int tempChar;
		Vector<Integer> noChars = new Vector<Integer>();
		int spaces = 8;

		if (!displayIsPrimed) {
			if (argsArr[1].charAt(0) == '-') {
				primeDisplay(argsArr.length - 2);
				displayIsPrimed = true;
			}
			if (argsArr[1].charAt(0) != '-') {
				primeDisplay(argsArr.length - 1);
				displayIsPrimed = true;
			}
		}

		if (fileNames != null) {
			for (int startIndex = 0; startIndex < fileNames.size(); startIndex++) {
				try {
					tempChar = getNoCharsInFile(fileNames.get(startIndex));
					if (String.valueOf(tempChar).length() >= 8) {
						spaces = String.valueOf(tempChar).length() + 1;
					}
					noChars.add(tempChar);

				} catch (WcException e) {
					return "Invalid File";
				}
			}
		}

		if (fileNames == null) {
			int startIndex = 2;

			if (argsArr[1].charAt(0) != '-') {
				startIndex = 1;
			}

			for (startIndex += 0; startIndex < argsArr.length; startIndex++) {
				try {
					tempChar = getNoCharsInFile(argsArr[startIndex]);
					if (String.valueOf(tempChar).length() >= 8) {
						spaces = String.valueOf(tempChar).length() + 1;
					}
					noChars.add(tempChar);

				} catch (WcException e) {
					return "Invalid File";
				}
			}
		}

		for (int toAdd = 0; toAdd < noChars.size(); toAdd++) {
			addToDisplay(toAdd, noChars.get(toAdd), spaces);
		}

		for (int currDisp = 0; currDisp < toDisplay.size(); currDisp++) {
			result.append(toDisplay.get(currDisp));
			if (currDisp != toDisplay.size() - 1) {
				result.append(System.lineSeparator());
			}
		}

		return result.toString();
	}

	/**
	 * adds the info to formatted string list
	 * 
	 * @param fileIndex the index of the file counted
	 * @param toAdd the count to add
	 * @param offset the number of spaces allocated for each count
	 */
	private void addToDisplay(int fileIndex, Integer toAdd, int offset) {
		StringBuilder newInfoB = new StringBuilder();
		newInfoB.append(toDisplay.get(fileIndex));
		int toAddLength = toAdd.toString().length();
		for (int currOff = 0; currOff < offset - toAddLength; currOff++) {
			newInfoB.append(" ");
		}

		newInfoB.append(toAdd.toString());
		toDisplay.set(fileIndex, newInfoB.toString());
	}

	
	/**
	 * initialize the first i elements of the list of strings to display
	 * 
	 * @param i 
	 */
	private void primeDisplay(int i) {
		// TODO Auto-generated method stub
		for (int index = 0; index < i; index++) {
			toDisplay.add("");
		}

	}

	/**
	 * Get the number of chaaceters in a single file
	 * 
	 * @param fileName 
	 * @return the number of characters
	 * @throws WcException
	 */
	private int getNoCharsInFile(String fileName) throws WcException {
		// TODO Auto-generated method stub
		String string = Environment.getAbsPath(fileName);
		File toFind = new File(string);
		int noChars = 0;
		// try {
		// Scanner inFile = new Scanner(toFind);
		// while(inFile.hasNextLine()) {
		// String line = inFile.nextLine();
		// noChars += line.length();
		// noChars++;
		// }
		// inFile.close();
		// } catch (FileNotFoundException e) {
		// throw new WcException("Invalid File");
		// }
		String contents = "";
		try {
			contents = new String(Files.readAllBytes(toFind.toPath()));
		} catch (IOException e) {
			throw new WcException("Invalid File");
		}

		noChars = contents.length();

		return noChars;
	}


	/* 
	 * Returns formatted string with word count according to string parameter command
	 * 
	 * args the command to print word. strictly "wc -w filename(s)"
	 * 
	 * (non-Javadoc)
	 * @see sg.edu.nus.comp.cs4218.app.Wc#printCharacterCountInFile(java.lang.String)
	 */
	@Override
	public String printWordCountInFile(String args) {
		String[] argsArr = args.split(" ");
		StringBuilder result = new StringBuilder();
		int tempWord;
		Vector<Integer> noWords = new Vector<Integer>();
		int spaces = 8;

		if (!displayIsPrimed) {
			primeDisplay(argsArr.length - 2);
			displayIsPrimed = true;
		}

		if (fileNames == null) {
			int startIndex = 2;
			if (argsArr[1].charAt(0) == '-') {
				startIndex = 2;
			}

			if (argsArr[1].charAt(0) != '-') {
				startIndex = 1;
			}

			for (startIndex += 0; startIndex < argsArr.length; startIndex++) {
				try {
					tempWord = getNoWordsInFile(argsArr[startIndex]);
					if (String.valueOf(tempWord).length() >= 8) {
						spaces = String.valueOf(tempWord).length() + 1;
					}
					noWords.add(tempWord);

				} catch (WcException e) {
					return "Invalid File";
				}
			}
		}

		if (fileNames != null) {
			for (int startIndex = 0; startIndex < fileNames.size(); startIndex++) {
				try {
					tempWord = getNoWordsInFile(fileNames.get(startIndex));
					if (String.valueOf(tempWord).length() >= 8) {
						spaces = String.valueOf(tempWord).length() + 1;
					}
					noWords.add(tempWord);

				} catch (WcException e) {
					return "Invalid File";
				}
			}
		}

		for (int toAdd = 0; toAdd < noWords.size(); toAdd++) {
			addToDisplay(toAdd, noWords.get(toAdd), spaces);
		}

		for (int currDisp = 0; currDisp < toDisplay.size(); currDisp++) {
			result.append(toDisplay.get(currDisp));
			if (currDisp != toDisplay.size() - 1) {
				result.append(System.lineSeparator());
			}
		}

		return result.toString();
	}

	/**
	 * Returns the number of words in the given file
	 * 
	 * @param fileName
	 * @return the number of words in the file fileName
	 * @throws WcException
	 */
	private int getNoWordsInFile(String fileName) throws WcException {
		String string = Environment.getAbsPath(fileName);
		File toFind = new File(string);
		int noWords = 0;
		try {
			Scanner inFile = new Scanner(toFind);
			while (inFile.hasNext()) {
				inFile.next();
				noWords++;
			}
			inFile.close();
		} catch (FileNotFoundException e) {
			throw new WcException("Invalid File");
		}

		return noWords;
	}


	/* 
	 * Returns formatted string according to string parameter command
	 * 
	 * args the command to print newLines. strictly "wc -l filename(s)"
	 * 
	 * (non-Javadoc)
	 * @see sg.edu.nus.comp.cs4218.app.Wc#printCharacterCountInFile(java.lang.String)
	 */
	@Override
	public String printNewlineCountInFile(String args) {
		String[] argsArr = args.split(" ");
		StringBuilder result = new StringBuilder();
		int tempNewLine;
		Vector<Integer> noNewline = new Vector<Integer>();
		int spaces = 8;

		if (!displayIsPrimed) {
			primeDisplay(argsArr.length - 2);
			displayIsPrimed = true;
		}

		if (fileNames == null) {
			int startIndex = 2;
			if (argsArr[1].charAt(0) == '-') {
				startIndex = 2;
			}

			if (argsArr[1].charAt(0) != '-') {
				startIndex = 1;
			}
			for (startIndex += 0; startIndex < argsArr.length; startIndex++) {
				try {
					tempNewLine = getNoNewlineInFile(argsArr[startIndex]);
					if (String.valueOf(tempNewLine).length() >= 8) {
						spaces = String.valueOf(tempNewLine).length() + 1;
					}
					noNewline.add(tempNewLine);

				} catch (WcException e) {
					return "Invalid File";
				}
			}
		}

		if (fileNames != null) {
			for (int startIndex = 0; startIndex < fileNames.size(); startIndex++) {
				try {
					tempNewLine = getNoNewlineInFile(fileNames.get(startIndex));
					if (String.valueOf(tempNewLine).length() >= 8) {
						spaces = String.valueOf(tempNewLine).length() + 1;
					}
					noNewline.add(tempNewLine);

				} catch (WcException e) {
					return "Invalid File";
				}
			}
		}

		for (int toAdd = 0; toAdd < noNewline.size(); toAdd++) {
			addToDisplay(toAdd, noNewline.get(toAdd), spaces);
		}

		for (int currDisp = 0; currDisp < toDisplay.size(); currDisp++) {
			result.append(toDisplay.get(currDisp));
			if (currDisp != toDisplay.size() - 1) {
				result.append(System.lineSeparator());
			}
		}

		return result.toString();
	}

	/**
	 * Returns the number of newLines in a single filee
	 * 
	 * @param fileName
	 * @return the number of newLines in the file fileName
	 * @throws WcException
	 */
	private int getNoNewlineInFile(String fileName) throws WcException {
		// TODO Auto-generated method stub
		String string = Environment.getAbsPath(fileName);
		File toFind = new File(string);
		int noNewLines = 0;
		try {
			Scanner inFile = new Scanner(toFind);
			while (inFile.hasNextLine()) {
				inFile.nextLine();
				noNewLines++;
			}
			inFile.close();
		} catch (FileNotFoundException e) {
			throw new WcException("Invalid File");
		}

		String contents = "";
		try {
			contents = new String(Files.readAllBytes(toFind.toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		noNewLines = contents.length() - contents.replace("\n", "").length();

		return noNewLines;
	}

	/* 
	 * See javadoc. returns all the counts in the files in the string args
	 * 
	 * String args is an entire command. This command MUST contain ALL mwl options
	 * in any order. This methods expects args to be this way as it has been 
	 * filtered by app.run before being called
	 * 
	 * (non-Javadoc)
	 * @see sg.edu.nus.comp.cs4218.app.Wc#printAllCountsInFile(java.lang.String)
	 */
	@Override
	public String printAllCountsInFile(String args) {
		String[] argsArr = args.split(" ");

		int currArg = 1;

		while (currArg < argsArr.length && argsArr[currArg].charAt(0) == '-') {
			try {
				findOps(argsArr[currArg]);
			} catch (WcException e) {
				return "Invalid Options";
			}
			currArg++;
		}

		printCharacterCountInFile(args);

		printWordCountInFile(args);

		return printNewlineCountInFile(args);
	}

	/* 
	 * Returns the character count in stdin.
	 * 
	 * @param args the string command. has to be "wc -m" as it has been filtered by
	 * 			app.run(..)
	 * 
	 * @param stdin the InputStream to be counted
	 * 
	 * (non-Javadoc)
	 * @see sg.edu.nus.comp.cs4218.app.Wc#printCharacterCountInStdin(java.lang.String, java.io.InputStream)
	 */
	@Override
	public String printCharacterCountInStdin(String args, InputStream stdin) {
		if (stdin == null) {
			return "Specify file or stdin";
		}

		StringBuilder result = new StringBuilder();
		int tempChar;
		Vector<Integer> noChars = new Vector<Integer>();
		int spaces = 8;

		if (!displayIsPrimed) {
			primeDisplay(1);
			displayIsPrimed = true;
		}

		tempChar = getNoCharsInStdin(stdin);
		if (String.valueOf(tempChar).length() >= 8) {
			spaces = String.valueOf(tempChar).length() + 1;
		}
		noChars.add(tempChar);

		for (int toAdd = 0; toAdd < noChars.size(); toAdd++) {
			addToDisplay(toAdd, noChars.get(toAdd), spaces);
		}

		for (int currDisp = 0; currDisp < toDisplay.size(); currDisp++) {
			result.append(toDisplay.get(currDisp));
			if (currDisp != toDisplay.size() - 1) {
				result.append(System.lineSeparator());
			}
		}

		return result.toString();
	}
	
	
	/**
	 * @param stdin the input stream to count
	 * @return the number of chars in stdin
	 */
	private int getNoCharsInStdin(InputStream stdin) {
		int noChars = 0;
		// Scanner inFile = new Scanner(stdin);
		// while(inFile.hasNextLine()) {
		// String line = inFile.nextLine();
		// noChars += line.length();
		// noChars++;
		// }

		if (noCharsStdin == -1) {
			int bytesRead = 0;
			StringBuilder content = new StringBuilder();
			while (true) {
				try {
					bytesRead = stdin.read(tempBuf);
					if (bytesRead <= -1) {
						break;
					}
					content.append(new String(tempBuf, 0, bytesRead));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			String line = content.toString();

			noChars = line.length();
			noCharsStdin = noChars;
			noWordsStdin = line.split("\\s+").length;
			noNewlinesStdin = line.length() - line.replace("\n", "").length();
		}

		if (noCharsStdin != -1) {
			noChars = noCharsStdin;
		}

		return noChars;
	}

	/* 
	 * Returns the word count in stdin.
	 * 
	 * @param args the string command. has to be "wc -w" as it has been filtered by
	 * 			app.run(..)
	 * 
	 * @param stdin the InputStream to be counted
	 * 
	 * (non-Javadoc)
	 * @see sg.edu.nus.comp.cs4218.app.Wc#printWordCountInStdin(java.lang.String, java.io.InputStream)
	 */
	@Override
	public String printWordCountInStdin(String args, InputStream stdin) {
		if (stdin == null) {
			return "Specify file or stdin";
		}

		StringBuilder result = new StringBuilder();
		int tempWord;
		Vector<Integer> noWords = new Vector<Integer>();
		int spaces = 8;

		if (!displayIsPrimed) {
			primeDisplay(1);
			displayIsPrimed = true;
		}

		tempWord = getNoWordsInStdin(stdin);
		if (String.valueOf(tempWord).length() >= 8) {
			spaces = String.valueOf(tempWord).length() + 1;
		}
		noWords.add(tempWord);

		for (int toAdd = 0; toAdd < noWords.size(); toAdd++) {
			addToDisplay(toAdd, noWords.get(toAdd), spaces);
		}

		for (int currDisp = 0; currDisp < toDisplay.size(); currDisp++) {
			result.append(toDisplay.get(currDisp));
			if (currDisp != toDisplay.size() - 1) {
				result.append(System.lineSeparator());
			}
		}

		return result.toString();
	}

	/**
	 * returnt the number of words in std in
	 * 
	 * @param stdin
	 * @return the no of words in stdin
	 */
	private int getNoWordsInStdin(InputStream stdin) {
		int noWords = 0;
		if (noWordsStdin == -1) {
			int bytesRead = 0;
			StringBuilder content = new StringBuilder();
			while (true) {
				try {
					bytesRead = stdin.read(tempBuf);
					if (bytesRead <= -1) {
						break;
					}
					content.append(new String(tempBuf, 0, bytesRead));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			String line = content.toString();

			noCharsStdin = line.length();
			noWords = line.split("\\s+").length;
			noNewlinesStdin = line.length() - line.replace("\n", "").length();
			noWordsStdin = noWords;
		}

		if (noCharsStdin != -1) {
			noWords = noWordsStdin;
		}

		return noWords;
	}

	/* 
	 * Returns the newline count in stdin.
	 * 
	 * @param args the string command. has to be "wc -l" as it has been filtered by
	 * 			app.run(..)
	 * 
	 * @param stdin the InputStream to be counted
	 * 
	 * (non-Javadoc)
	 * @see sg.edu.nus.comp.cs4218.app.Wc#printNewLineCountInStdin(java.lang.String, java.io.InputStream)
	 */
	@Override
	public String printNewlineCountInStdin(String args, InputStream stdin) {
		if (stdin == null) {
			return "Specify file or stdin";
		}

		StringBuilder result = new StringBuilder();
		int tempNewline;
		Vector<Integer> noNewlines = new Vector<Integer>();
		int spaces = 8;

		if (!displayIsPrimed) {
			primeDisplay(1);
			displayIsPrimed = true;
		}

		tempNewline = getNoNewlinesInStdin(stdin);
		if (String.valueOf(tempNewline).length() >= 8) {
			spaces = String.valueOf(tempNewline).length() + 1;
		}
		noNewlines.add(tempNewline);

		for (int toAdd = 0; toAdd < noNewlines.size(); toAdd++) {
			addToDisplay(toAdd, noNewlines.get(toAdd), spaces);
		}

		for (int currDisp = 0; currDisp < toDisplay.size(); currDisp++) {
			result.append(toDisplay.get(currDisp));
			if (currDisp != toDisplay.size() - 1) {
				result.append(System.lineSeparator());
			}
		}

		return result.toString();
	}

	/**
	 * @param stdin the InputStream to count
	 * @return the number of newlines in stdin
	 */
	private int getNoNewlinesInStdin(InputStream stdin) {
		int noNewLines = 0;
		if (noNewlinesStdin == -1) {
			int bytesRead = 0;
			StringBuilder content = new StringBuilder();
			while (true) {
				try {
					bytesRead = stdin.read(tempBuf);
					if (bytesRead <= -1) {
						break;
					}
					content.append(new String(tempBuf, 0, bytesRead));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			String line = content.toString();

			noCharsStdin = line.length();
			noWordsStdin = line.split("\\s+").length;
			noNewLines = line.length() - line.replace("\n", "").length();
			noNewlinesStdin = noNewLines;
		}

		if (noNewlinesStdin != -1) {
			noNewLines = noNewlinesStdin;
		}

		return noNewLines;
	}

	/* 
	 * Returns the character, word and newline count in stdin.
	 * 
	 * @param args the string command. has to be "wc -mwl" or "wc" as it has been filtered by
	 * 			app.run(..)
	 * 
	 * @param stdin the InputStream to be counted
	 * 
	 * (non-Javadoc)
	 * @see sg.edu.nus.comp.cs4218.app.Wc#printCharacterCountInStdin(java.lang.String, java.io.InputStream)
	 */
	@Override
	public String printAllCountsInStdin(String args, InputStream stdin) {
		if (stdin == null) {
			return "Specify file or stdin";
		}
		String[] argsArr = args.split(" ");

		int currArg = 1;

		while (currArg < argsArr.length && argsArr[currArg].charAt(0) == '-') {
			try {
				findOps(argsArr[currArg]);
			} catch (WcException e) {
				return "Invalid Options";
			}
			currArg++;
		}

		printCharacterCountInStdin(args, stdin);

		printWordCountInStdin(args, stdin);

		return printNewlineCountInStdin(args, stdin);
	}

}
