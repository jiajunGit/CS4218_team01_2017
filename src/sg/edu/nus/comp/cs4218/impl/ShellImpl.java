package sg.edu.nus.comp.cs4218.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Stack;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.Symbol;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;

import sg.edu.nus.comp.cs4218.impl.app.CalApplication;
import sg.edu.nus.comp.cs4218.impl.app.CatApplication;
import sg.edu.nus.comp.cs4218.impl.app.CdApplication;
import sg.edu.nus.comp.cs4218.impl.app.DateApplication;
import sg.edu.nus.comp.cs4218.impl.app.EchoApplication;
import sg.edu.nus.comp.cs4218.impl.app.GrepApplication;
import sg.edu.nus.comp.cs4218.impl.app.HeadApplication;
import sg.edu.nus.comp.cs4218.impl.app.PwdApplication;
import sg.edu.nus.comp.cs4218.impl.app.SedApplication;
import sg.edu.nus.comp.cs4218.impl.app.SortApplication;
import sg.edu.nus.comp.cs4218.impl.app.TailApplication;
import sg.edu.nus.comp.cs4218.impl.app.WcApplication;
import sg.edu.nus.comp.cs4218.impl.cmd.CallCommand;

/**
 * A Shell is a command interpreter and forms the backbone of the entire
 * program. Its responsibility is to interpret commands that the user type and
 * to run programs that the user specify in her command lines.
 * 
 * <p>
 * <b>Command format:</b>
 * <code>&lt;Pipe&gt; | &lt;Sequence&gt; | &lt;Call&gt;</code>
 * </p>
 */

public class ShellImpl implements Shell {

	/**
	 * Parses and evaluates two commands separated by the pipe operator
	 * 
	 * 
	 * @param args
	 *            String containing the two commands separated by the pipe
	 *            operator to be evaluated
	 * 
	 * @return String containing the output after evaluating the commands given
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the commands given.
	 * @throws ShellException
	 *             If an exception happens while processing the commands given.
	 * 
	 */
	@Override
	public String pipeTwoCommands(String args) {
		String out = "";
		try {
			out = parseAndEvaluate(args);
		} catch (AbstractApplicationException | ShellException e) {
			out = e.getMessage();
		}
		return out;
	}

	/**
	 * Parses and evaluates multiple commands separated by the pipe operator
	 * 
	 * 
	 * @param args
	 *            String containing the multiple commands separated by the pipe
	 *            operator to be evaluated
	 * 
	 * @return String containing the output after evaluating the commands given
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the commands given.
	 * @throws ShellException
	 *             If an exception happens while processing the commands given.
	 * 
	 */
	@Override
	public String pipeMultipleCommands(String args) {
		String out = "";
		try {
			out = parseAndEvaluate(args);
		} catch (AbstractApplicationException | ShellException e) {
			out = e.getMessage();
		}
		return out;
	}

	/**
	 * Parses and evaluates commands separated by the pipe operator that will
	 * result in an exception
	 * 
	 * @param args
	 *            String containing commands separated by the pipe operator that
	 *            will result in an exception
	 * 
	 * @return String containing the output after evaluating the commands given
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the commands given.
	 * @throws ShellException
	 *             If an exception happens while processing the commands given.
	 * 
	 */
	@Override
	public String pipeWithException(String args) {
		String out = "";
		try {
			out = parseAndEvaluate(args);
		} catch (AbstractApplicationException | ShellException e) {
			out = e.getMessage();
		}
		return out;
	}

	/**
	 * Parses and evaluates commands containing glob path(s) that cannot be
	 * expanded
	 * 
	 * @param args
	 *            String containing commands containing glob path(s) that cannot
	 *            be expanded
	 * 
	 * @return String containing the output after evaluating the commands given
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the commands given.
	 * @throws ShellException
	 *             If an exception happens while processing the commands given.
	 * 
	 */
	@Override
	public String globNoPaths(String args) {
		String out = "";
		try {
			out = parseAndEvaluate(args);
		} catch (AbstractApplicationException | ShellException e) {
			out = e.getMessage();
		}
		return out;
	}

	/**
	 * Parses and evaluates commands containing glob path(s) that be expanded to
	 * only one file path
	 * 
	 * @param args
	 *            String containing commands containing glob path(s) that be
	 *            expanded to only one file path
	 * 
	 * @return String containing the output after evaluating the commands given
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the commands given.
	 * @throws ShellException
	 *             If an exception happens while processing the commands given.
	 * 
	 */
	@Override
	public String globOneFile(String args) {
		String out = "";
		try {
			out = parseAndEvaluate(args);
		} catch (AbstractApplicationException | ShellException e) {
			out = e.getMessage();
		}
		return out;
	}

	/**
	 * Parses and evaluates commands containing glob path(s) that be expanded to
	 * at least one file path
	 * 
	 * @param args
	 *            String containing commands containing glob path(s) that be
	 *            expanded to at least one file path
	 * 
	 * @return String containing the output after evaluating the commands given
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the commands given.
	 * @throws ShellException
	 *             If an exception happens while processing the commands given.
	 * 
	 */
	@Override
	public String globFilesDirectories(String args) {
		String out = "";
		try {
			out = parseAndEvaluate(args);
		} catch (AbstractApplicationException | ShellException e) {
			out = e.getMessage();
		}
		return out;
	}

	/**
	 * Parses and evaluates commands containing glob path(s) that will result in
	 * an exception
	 * 
	 * @param args
	 *            String containing commands containing glob path(s) that will
	 *            result in an exception
	 * 
	 * @return String containing the output after evaluating the commands given
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the commands given.
	 * @throws ShellException
	 *             If an exception happens while processing the commands given.
	 * 
	 */
	@Override
	public String globWithException(String args) {
		String out = "";
		try {
			out = parseAndEvaluate(args);
		} catch (AbstractApplicationException | ShellException e) {
			out = e.getMessage();
		}
		return out;
	}

	/**
	 * Parses and evaluates commands containing input redirect symbol
	 * 
	 * @param args
	 *            String containing commands containing containing input
	 *            redirect symbol
	 * 
	 * @return String containing the output after evaluating the commands given
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the commands given.
	 * @throws ShellException
	 *             If an exception happens while processing the commands given.
	 * 
	 */
	@Override
	public String redirectInput(String args) {
		String out = "";
		try {
			out = parseAndEvaluate(args);
		} catch (AbstractApplicationException | ShellException e) {
			out = e.getMessage();
		}
		return out;
	}

	/**
	 * Parses and evaluates commands containing output redirect symbol
	 * 
	 * @param args
	 *            String containing commands containing containing output
	 *            redirect symbol
	 * 
	 * @return String containing the output after evaluating the commands given
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the commands given.
	 * @throws ShellException
	 *             If an exception happens while processing the commands given.
	 * 
	 */
	@Override
	public String redirectOutput(String args) {
		String out = "";
		try {
			out = parseAndEvaluate(args);
		} catch (AbstractApplicationException | ShellException e) {
			out = e.getMessage();
		}
		return out;
	}

	/**
	 * Parses and evaluates commands containing input redirect symbol with no
	 * files specified
	 * 
	 * @param args
	 *            String containing commands containing containing input
	 *            redirect symbol with no files specified
	 * 
	 * @return String containing the output after evaluating the commands given
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the commands given.
	 * @throws ShellException
	 *             If an exception happens while processing the commands given.
	 * 
	 */
	@Override
	public String redirectInputWithNoFile(String args) {
		String out = "";
		try {
			out = parseAndEvaluate(args);
		} catch (AbstractApplicationException | ShellException e) {
			out = e.getMessage();
		}
		return out;
	}

	/**
	 * Parses and evaluates commands containing output redirect symbol with no
	 * files specified
	 * 
	 * @param args
	 *            String containing commands containing containing output
	 *            redirect symbol with no files specified
	 * 
	 * @return String containing the output after evaluating the commands given
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the commands given.
	 * @throws ShellException
	 *             If an exception happens while processing the commands given.
	 * 
	 */
	@Override
	public String redirectOutputWithNoFile(String args) {
		String out = "";
		try {
			out = parseAndEvaluate(args);
		} catch (AbstractApplicationException | ShellException e) {
			out = e.getMessage();
		}
		return out;
	}

	/**
	 * Parses and evaluates commands containing input redirect symbol that will
	 * result in exception
	 * 
	 * @param args
	 *            String containing commands containing containing input
	 *            redirect symbol that will result in exception
	 * 
	 * @return String containing the output after evaluating the commands given
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the commands given.
	 * @throws ShellException
	 *             If an exception happens while processing the commands given.
	 * 
	 */
	@Override
	public String redirectInputWithException(String args) {
		String out = "";
		try {
			out = parseAndEvaluate(args);
		} catch (AbstractApplicationException | ShellException e) {
			out = e.getMessage();
		}
		return out;
	}

	/**
	 * Parses and evaluates commands containing output redirect symbol that will
	 * result in exception
	 * 
	 * @param args
	 *            String containing commands containing containing output
	 *            redirect symbol that will result in exception
	 * 
	 * @return String containing the output after evaluating the commands given
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the commands given.
	 * @throws ShellException
	 *             If an exception happens while processing the commands given.
	 * 
	 */
	@Override
	public String redirectOutputWithException(String args) {
		String out = "";
		try {
			out = parseAndEvaluate(args);
		} catch (AbstractApplicationException | ShellException e) {
			out = e.getMessage();
		}
		return out;
	}

	/**
	 * Parses and evaluates commands containing command substitution
	 * 
	 * @param args
	 *            String containing commands containing containing command
	 *            substitution
	 * 
	 * @return String containing the output after evaluating the commands given
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the commands given.
	 * @throws ShellException
	 *             If an exception happens while processing the commands given.
	 * 
	 */
	@Override
	public String performCommandSubstitution(String args) {
		String out = "";
		try {
			out = parseAndEvaluate(args);
		} catch (AbstractApplicationException | ShellException e) {
			out = e.getMessage();
		}
		return out;
	}

	/**
	 * Parses and evaluates commands containing command substitution
	 * 
	 * @param args
	 *            String containing commands containing containing command
	 *            substitution that will result in exception
	 * 
	 * @return String containing the output after evaluating the commands given
	 *         that will result in exception
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the commands given.
	 * @throws ShellException
	 *             If an exception happens while processing the commands given.
	 * 
	 */
	@Override
	public String performCommandSubstitutionWithException(String args) {
		String out = "";
		try {
			out = parseAndEvaluate(args);
		} catch (AbstractApplicationException | ShellException e) {
			out = e.getMessage();
		}
		return out;
	}

	/**
	 * Static method to run the application as specified by the application
	 * command keyword and arguments.
	 * 
	 * @param app
	 *            String containing the keyword that specifies what application
	 *            to run.
	 * @param args
	 *            String array containing the arguments to pass to the
	 *            applications for running.
	 * @param inputStream
	 *            InputputStream for the application to get arguments from, if
	 *            needed.
	 * @param outputStream
	 *            OutputStream for the application to print its output to.
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while running any of the
	 *             application(s).
	 * @throws ShellException
	 *             If an unsupported or invalid application command is detected.
	 */
	public static void runApp(String app, String[] argsArray, InputStream inputStream, OutputStream outputStream)
			throws AbstractApplicationException, ShellException {
		Application absApp = null;
		if (("cat").equals(app)) {// cat [FILE]...
			absApp = new CatApplication();
		} else if (("echo").equals(app)) {// echo [args]...
			absApp = new EchoApplication();
		} else if (("cal").equals(app)) {
			absApp = new CalApplication();
		} else if (("cd").equals(app)) {
			absApp = new CdApplication();
		} else if (("grep").equals(app)) {
			absApp = new GrepApplication();
		} else if (("head").equals(app)) {
			absApp = new HeadApplication();
		} else if (("pwd").equals(app)) {
			absApp = new PwdApplication();
		} else if (("sort").equals(app)) {
			absApp = new SortApplication();
		} else if (("tail").equals(app)) {
			absApp = new TailApplication();
		} else if (("wc").equals(app)) {
			absApp = new WcApplication();
		} else if (("sed").equals(app)) {
			absApp = new SedApplication();
		} else if (("date").equals(app)) {
			absApp = new DateApplication();
		} else { // invalid command
			throw new ShellException(app + ": " + EXP_INVALID_APP);
		}
		absApp.run(argsArray, inputStream, outputStream);
	}

	/**
	 * Static method to close an outputStream. If outputStream provided is
	 * System.out, it will be ignored.
	 * 
	 * @param outputStream
	 *            OutputStream to be closed.
	 * 
	 * @throws ShellException
	 *             If outputStream cannot be closed successfully.
	 */
	public static void closeOutputStream(OutputStream outputStream) throws ShellException {
		if (outputStream != null && outputStream != System.out) {
			try {
				outputStream.close();
			} catch (IOException e) {
			}
		}
	}

	// TODO
	// Shell methods

	private static InputStream defaultInputStream = null;

	public static InputStream getDefaultInputStream() {
		return defaultInputStream;
	}

	private static StringBuilder generateSymbols(String cmdline) throws ShellException {

		StringBuilder symbols = new StringBuilder(cmdline.length());

		Stack<Character> quoteStack = new Stack<Character>();
		for (int i = 0, length = cmdline.length(); i < length; ++i) {

			char currentChar = cmdline.charAt(i);

			switch (currentChar) {

			case Symbol.SINGLE_QUOTE:
				if (quoteStack.isEmpty()) {
					quoteStack.push(Symbol.SINGLE_QUOTE);
					symbols.append(Symbol.SINGLE_QUOTE);
				} else if (quoteStack.peek() == Symbol.SINGLE_QUOTE) {
					quoteStack.pop();
					if (quoteStack.isEmpty()) {
						symbols.append(Symbol.SINGLE_QUOTE);
					} else {
						symbols.append(Symbol.UNRELATED);
					}
				} else {
					quoteStack.push(Symbol.SINGLE_QUOTE);
					symbols.append(Symbol.UNRELATED);
				}
				break;

			case Symbol.DOUBLE_QUOTE:
			case Symbol.BACK_QUOTE:
				if (quoteStack.isEmpty()) {
					quoteStack.push(currentChar);
					symbols.append(currentChar);
				} else if (quoteStack.peek() == currentChar) {
					quoteStack.pop();
					if (quoteStack.isEmpty()) {
						symbols.append(currentChar);
					} else {
						symbols.append(Symbol.UNRELATED);
					}
				} else if (quoteStack.peek() != Symbol.SINGLE_QUOTE) {
					quoteStack.push(currentChar);
					symbols.append(Symbol.UNRELATED);
				} else {
					symbols.append(Symbol.UNRELATED);
				}
				break;

			case Symbol.SPACE:
			case Symbol.TAB:
			case Symbol.SEQ_OP:
			case Symbol.PIPE_OP:
			case Symbol.INPUT_OP:
			case Symbol.OUTPUT_OP:
			case Symbol.GLOB_OP:
				if (quoteStack.isEmpty()) {
					symbols.append(currentChar);
				} else {
					symbols.append(Symbol.UNRELATED);
				}
				break;

			default:
				if (quoteStack.isEmpty()) {
					symbols.append(Symbol.UNQUOTED_UNRELATED);
				} else {
					symbols.append(Symbol.UNRELATED);
				}
				break;
			}
		}

		if (!quoteStack.isEmpty()) {
			throw new ShellException(EXP_SYNTAX);
		}
		return symbols;
	}

	private void writeToStdout(OutputStream stdout, String content) {
		if (stdout != null && content != null && !content.isEmpty()) {
			try {
				stdout.write(content.getBytes());
				stdout.flush();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * Checks if input has new line characters found within the input given.
	 * 
	 * @param input
	 *            String containing the command
	 * 
	 * @return A boolean value indicating if new line characters are found
	 *         within the input \ given.
	 */
	public boolean hasNewLineViolation(String input) {

		int fromIndex = 0;
		int newLineLength = Symbol.NEW_LINE_S.length();
		int lastNewLineIndex = -Symbol.NEW_LINE_S.length();

		while (true) {

			int currentIndex = input.indexOf(Symbol.NEW_LINE_S, fromIndex);

			if (currentIndex < 0) {
				break;
			}

			if (lastNewLineIndex < 0 || (currentIndex - newLineLength) == lastNewLineIndex) {
				lastNewLineIndex = currentIndex;
			} else {
				return true;
			}

			fromIndex = currentIndex + newLineLength;
		}
		return (lastNewLineIndex >= 0 && ((input.length() - newLineLength) != lastNewLineIndex));
	}

	/**
	 * Parses and generates symbols for the input. Symbols generated by this
	 * function will be later used by evaluateOneCommand function to split and
	 * evaluate commands.
	 * 
	 * @param input
	 *            String containing the command
	 * 
	 * @return String containing the symbols generated for input
	 * 
	 * @throws ShellException
	 *             If a syntax error is detected while generating symbols for
	 *             input
	 */
	public static String generateSymbolString(String input) throws ShellException {
		if (input == null) {
			return "";
		}
		StringBuilder out = generateSymbols(input);
		return out.toString();
	}

	private CallCommand splitIntoCommands(String input, StringBuilder inputSymbols) throws ShellException {

		StringBuilder segment = new StringBuilder();
		StringBuilder segmentSymbol = new StringBuilder();

		CallCommand prevCmd = null;
		CallCommand firstCmd = null;

		for (int i = 0, length = inputSymbols.length(); i < length; ++i) {

			switch (inputSymbols.charAt(i)) {

			case Symbol.PIPE_OP:
			case Symbol.SEQ_OP:

				if (segment.length() > 0) {

					CallCommand cmd = new CallCommand(segment.toString(), segmentSymbol.toString());
					cmd.setLinkToNextCommand(inputSymbols.charAt(i));

					if (firstCmd == null) {
						firstCmd = cmd;
					}

					if (prevCmd != null) {
						prevCmd.setNextCommand(cmd);
						cmd.setLinkToPrevCommand(prevCmd.getLinkTypeToNextCommand());
					}
					prevCmd = cmd;

					segment.setLength(0);
					segmentSymbol.setLength(0);

				} else {
					throw new ShellException(Shell.EXP_SYNTAX);
				}
				break;

			default:

				segment.append(input.charAt(i));
				segmentSymbol.append(inputSymbols.charAt(i));
				break;
			}
		}

		if (segment.length() > 0) {

			CallCommand cmd = new CallCommand(segment.toString(), segmentSymbol.toString());
			cmd.setLinkToNextCommand(Link.NONE);

			if (firstCmd == null) {
				firstCmd = cmd;
			}

			if (prevCmd != null) {
				prevCmd.setNextCommand(cmd);
				cmd.setLinkToPrevCommand(prevCmd.getLinkTypeToNextCommand());
			}

		} else {
			throw new ShellException(Shell.EXP_SYNTAX);
		}

		return firstCmd;
	}

	private void executeCommands(CallCommand firstCmd, OutputStream stdout)
			throws ShellException, AbstractApplicationException {

		InputStream defaultStdin = getDefaultInputStream();

		CallCommand currentCmd = firstCmd;
		while (currentCmd != null) {
			currentCmd.evaluate(defaultStdin, stdout);
			currentCmd = currentCmd.getNextCommand();
		}
	}

	private StringBuilder generateSymbolsForBQ(String cmdline) throws ShellException {

		StringBuilder symbols = new StringBuilder(cmdline.length());

		Stack<Character> quoteStack = new Stack<Character>();
		for (int i = 0, length = cmdline.length(); i < length; ++i) {

			char currentChar = cmdline.charAt(i);

			switch (currentChar) {

			case Symbol.SINGLE_QUOTE:
				if (quoteStack.isEmpty()) {
					quoteStack.push(Symbol.SINGLE_QUOTE);
					symbols.append(Symbol.SINGLE_QUOTE);
				} else if (quoteStack.peek() == Symbol.SINGLE_QUOTE) {
					quoteStack.pop();
					if (quoteStack.isEmpty()) {
						symbols.append(Symbol.SINGLE_QUOTE);
					} else {
						symbols.append(Symbol.UNRELATED);
					}
				} else {
					quoteStack.push(Symbol.SINGLE_QUOTE);
					symbols.append(Symbol.UNRELATED);
				}
				break;

			case Symbol.DOUBLE_QUOTE:
				if (quoteStack.isEmpty()) {
					quoteStack.push(currentChar);
					symbols.append(currentChar);
				} else if (quoteStack.peek() == currentChar) {
					quoteStack.pop();
					if (quoteStack.isEmpty()) {
						symbols.append(currentChar);
					} else {
						symbols.append(Symbol.UNRELATED);
					}
				} else if (quoteStack.peek() != Symbol.SINGLE_QUOTE) {
					quoteStack.push(currentChar);
					symbols.append(Symbol.UNRELATED);
				} else {
					symbols.append(Symbol.UNRELATED);
				}
				break;

			case Symbol.BACK_QUOTE:
				if (quoteStack.isEmpty()) {
					throw new ShellException(EXP_SYNTAX);
				} else if (quoteStack.peek() == Symbol.BACK_QUOTE) {
					quoteStack.pop();
					if (quoteStack.isEmpty()) {
						throw new ShellException(EXP_SYNTAX);
					} else {
						symbols.append(Symbol.UNRELATED);
					}
				} else if (quoteStack.peek() != Symbol.SINGLE_QUOTE) {
					quoteStack.push(currentChar);
					symbols.append(Symbol.UNRELATED);
				} else {
					symbols.append(Symbol.UNRELATED);
				}
				break;

			case Symbol.SPACE:
			case Symbol.TAB:
			case Symbol.SEQ_OP:
			case Symbol.PIPE_OP:
			case Symbol.INPUT_OP:
			case Symbol.OUTPUT_OP:
			case Symbol.GLOB_OP:
				if (quoteStack.isEmpty()) {
					symbols.append(currentChar);
				} else {
					symbols.append(Symbol.UNRELATED);
				}
				break;

			default:
				if (quoteStack.isEmpty()) {
					symbols.append(Symbol.UNQUOTED_UNRELATED);
				} else {
					symbols.append(Symbol.UNRELATED);
				}
				break;
			}
		}

		if (!quoteStack.isEmpty()) {
			throw new ShellException(EXP_SYNTAX);
		}
		return symbols;
	}

	private String parseAndEvaluateBQ(String input) throws ShellException, AbstractApplicationException {

		StringBuilder cmdSymbols = generateSymbolsForBQ(input);

		CallCommand firstCmd = splitIntoCommands(input, cmdSymbols);
		if (firstCmd == null) {
			throw new ShellException(EXP_SYNTAX);
		}

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		executeCommands(firstCmd, outStream);

		return outStream.toString();
	}

	private String processBQOut(String output) {
		return output.replace(Symbol.NEW_LINE_S, Symbol.SPACE_S);
	}

	private boolean isWhiteSpace(char character) {
		return (character == Symbol.SPACE || character == Symbol.TAB);
	}

	private String generateSymbolsForBQOut(String output) {
		StringBuilder outputSymbolsBuilder = new StringBuilder(output.length());
		for (int i = 0, length = output.length(); i < length; ++i) {
			if (isWhiteSpace(output.charAt(i))) {
				outputSymbolsBuilder.append(Symbol.SPACE);
			} else {
				outputSymbolsBuilder.append(Symbol.UNRELATED);
			}
		}
		return outputSymbolsBuilder.toString();
	}

	private void processBQ(StringBuilder cmd, StringBuilder cmdSymbols, boolean isIgnoreEmptyStrings)
			throws ShellException, AbstractApplicationException {

		int fromIndex = 0;

		while (true) {

			int start = cmdSymbols.indexOf(Symbol.BACK_QUOTE_S, fromIndex);
			if (start < 0) {
				break;
			}

			int end = cmdSymbols.indexOf(Symbol.BACK_QUOTE_S, start + 1);
			if (end < 0) {
				throw new ShellException(EXP_SYNTAX);
			}

			String output = parseAndEvaluateBQ(cmd.substring(start + 1, end));
			output = processBQOut(output);
			String outputSymbols = Symbol.EMPTY_OUTPUT_S;

			if (!isIgnoreEmptyStrings && output.isEmpty()) {
				output = Symbol.EMPTY_OUTPUT_S;
			} else {
				outputSymbols = generateSymbolsForBQOut(output);
			}

			// Replace
			cmd.replace(start, end + 1, output);
			cmdSymbols.replace(start, end + 1, outputSymbols);

			fromIndex = start + outputSymbols.length();
		}
	}

	private String parseAndEvaluateSQ(String input) throws ShellException {
		if (input.indexOf(Symbol.SINGLE_QUOTE) >= 0) {
			throw new ShellException(EXP_SYNTAX);
		}
		return input;
	}

	private String generateSymbolsForSQOut(String output) {
		StringBuilder sb = new StringBuilder(output.length());
		for (int i = 0, size = output.length(); i < size; ++i) {
			sb.append(Symbol.UNRELATED);
		}
		return sb.toString();
	}

	private void processSQ(StringBuilder cmd, StringBuilder cmdSymbols, boolean isIgnoreEmptyStrings)
			throws ShellException {

		int fromIndex = 0;

		while (true) {

			int start = cmdSymbols.indexOf(Symbol.SINGLE_QUOTE_S, fromIndex);
			if (start < 0) {
				break;
			}

			int end = cmdSymbols.indexOf(Symbol.SINGLE_QUOTE_S, start + 1);
			if (end < 0) {
				throw new ShellException(EXP_SYNTAX);
			}

			String output = parseAndEvaluateSQ(cmd.substring(start + 1, end));
			String outputSymbol = Symbol.EMPTY_OUTPUT_S;

			if (!isIgnoreEmptyStrings && output.isEmpty()) {
				output = Symbol.EMPTY_OUTPUT_S;
			} else {
				outputSymbol = generateSymbolsForSQOut(output);
			}

			// Replace
			cmd.replace(start, end + 1, output);
			cmdSymbols.replace(start, end + 1, outputSymbol);

			fromIndex = start + outputSymbol.length();
		}
	}

	private StringBuilder generateSymbolsForDQ(String input) throws ShellException {

		StringBuilder symbols = new StringBuilder(input.length());

		Stack<Character> quoteStack = new Stack<Character>();
		for (int i = 0, length = input.length(); i < length; ++i) {

			char currentChar = input.charAt(i);

			switch (currentChar) {

			case Symbol.SINGLE_QUOTE:
				if (quoteStack.isEmpty()) {
					quoteStack.push(Symbol.SINGLE_QUOTE);
					symbols.append(Symbol.SINGLE_QUOTE);
				} else if (quoteStack.peek() == Symbol.SINGLE_QUOTE) {
					quoteStack.pop();
					if (quoteStack.isEmpty()) {
						symbols.append(Symbol.SINGLE_QUOTE);
					} else {
						symbols.append(Symbol.UNRELATED);
					}
				} else {
					quoteStack.push(Symbol.SINGLE_QUOTE);
					symbols.append(Symbol.UNRELATED);
				}
				break;

			case Symbol.BACK_QUOTE:
				if (quoteStack.isEmpty()) {
					quoteStack.push(currentChar);
					symbols.append(currentChar);
				} else if (quoteStack.peek() == currentChar) {
					quoteStack.pop();
					if (quoteStack.isEmpty()) {
						symbols.append(currentChar);
					} else {
						symbols.append(Symbol.UNRELATED);
					}
				} else if (quoteStack.peek() != Symbol.SINGLE_QUOTE) {
					quoteStack.push(currentChar);
					symbols.append(Symbol.UNRELATED);
				} else {
					symbols.append(Symbol.UNRELATED);
				}
				break;

			case Symbol.DOUBLE_QUOTE:
				if (quoteStack.isEmpty()) {
					throw new ShellException(EXP_SYNTAX);
				} else if (quoteStack.peek() == Symbol.DOUBLE_QUOTE) {
					quoteStack.pop();
					if (quoteStack.isEmpty()) {
						throw new ShellException(EXP_SYNTAX);
					} else {
						symbols.append(Symbol.UNRELATED);
					}
				} else if (quoteStack.peek() != Symbol.SINGLE_QUOTE) {
					quoteStack.push(currentChar);
					symbols.append(Symbol.UNRELATED);
				} else {
					symbols.append(Symbol.UNRELATED);
				}
				break;

			case Symbol.SPACE:
			case Symbol.TAB:
			case Symbol.SEQ_OP:
			case Symbol.PIPE_OP:
			case Symbol.INPUT_OP:
			case Symbol.OUTPUT_OP:
				if (quoteStack.isEmpty()) {
					symbols.append(currentChar);
				} else {
					symbols.append(Symbol.UNRELATED);
				}
				break;

			default:
				symbols.append(Symbol.UNRELATED);
				break;
			}
		}

		if (!quoteStack.isEmpty()) {
			throw new ShellException(EXP_SYNTAX);
		}
		return symbols;
	}

	private String parseAndEvaluateDQ(String input) throws ShellException, AbstractApplicationException {

		StringBuilder cmdSymbols = generateSymbolsForDQ(input);

		StringBuilder cmd = new StringBuilder(input);

		processSQ(cmd, cmdSymbols, true);
		processBQ(cmd, cmdSymbols, true);

		return cmd.toString();
	}

	private String generateSymbolsForDQOut(String output) {
		StringBuilder sb = new StringBuilder(output.length());
		for (int i = 0, size = output.length(); i < size; ++i) {
			sb.append(Symbol.UNRELATED);
		}
		return sb.toString();
	}

	private void processDQ(StringBuilder cmd, StringBuilder cmdSymbols, boolean isIgnoreEmptyStrings)
			throws ShellException, AbstractApplicationException {

		int fromIndex = 0;

		while (true) {

			int start = cmdSymbols.indexOf(Symbol.DOUBLE_QUOTE_S, fromIndex);
			if (start < 0) {
				break;
			}

			int end = cmdSymbols.indexOf(Symbol.DOUBLE_QUOTE_S, start + 1);
			if (end < 0) {
				throw new ShellException(EXP_SYNTAX);
			}

			String output = parseAndEvaluateDQ(cmd.substring(start + 1, end));
			String outputSymbol = Symbol.EMPTY_OUTPUT_S;

			if (!isIgnoreEmptyStrings && output.isEmpty()) {
				output = Symbol.EMPTY_OUTPUT_S;
			} else {
				outputSymbol = generateSymbolsForDQOut(output);
			}

			// Replace
			cmd.replace(start, end + 1, output);
			cmdSymbols.replace(start, end + 1, outputSymbol);

			fromIndex = start + outputSymbol.length();
		}
	}

	/**
	 * Searches for and processes the commands enclosed by non-nested single
	 * quotes. If no single quotes are found, the input is returned unchanged.
	 * If single quotes are found, the single quotes and its enclosed contents
	 * substituted with the output from processing the contents enclosed in the
	 * single quotes.
	 * 
	 * @param input
	 *            String containing the commands.
	 * 
	 * @return String with the single quotes command processed.
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the content in the
	 *             single quotes.
	 * @throws ShellException
	 *             If an exception happens while processing the content in the
	 *             single quotes.
	 */
	public String processSQ(String input) throws ShellException, AbstractApplicationException {

		if (input == null) {
			return null;
		}
		StringBuilder cmdSymbols = generateSymbols(input);
		StringBuilder cmd = new StringBuilder(input);
		processSQ(cmd, cmdSymbols, true);

		return cmd.toString();
	}

	/**
	 * Searches for and processes the commands enclosed by non-nested double
	 * quotes. If no double quotes are found, the input is returned unchanged.
	 * If double quotes are found, the double quotes and its enclosed contents
	 * substituted with the output from processing the contents enclosed in the
	 * double quotes.
	 * 
	 * @param input
	 *            String containing the commands.
	 * 
	 * @return String with the double quotes command processed.
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the content in the
	 *             double quotes.
	 * @throws ShellException
	 *             If an exception happens while processing the content in the
	 *             double quotes.
	 */
	public String processDQ(String input) throws ShellException, AbstractApplicationException {

		if (input == null) {
			return null;
		}
		StringBuilder cmdSymbols = generateSymbols(input);
		StringBuilder cmd = new StringBuilder(input);
		processDQ(cmd, cmdSymbols, true);

		return cmd.toString();
	}

	/**
	 * Parses and evaluates user's command line.
	 * 
	 * @param input
	 *            String containing the command to be evaluated
	 * 
	 * @return String containing the output after evaluating the command given
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the command given.
	 * @throws ShellException
	 *             If an exception happens while processing the command given.
	 * 
	 */
	public String parseAndEvaluate(String input) throws AbstractApplicationException, ShellException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		parseAndEvaluate(input, out);
		String outString = out.toString();
		try {
			out.close();
		} catch (IOException e) {
		}
		return outString;
	}

	/**
	 * Parses and evaluates user's command line.
	 * 
	 * @param cmdline
	 *            String containing the command to be evaluated
	 * @param stdout
	 *            Default stdout to write results obtained after evaluating the
	 *            command found in cmdline
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while processing the command given.
	 * @throws ShellException
	 *             If an exception happens while processing the command given.
	 */
	@Override
	public void parseAndEvaluate(String cmdLine, OutputStream stdout)
			throws AbstractApplicationException, ShellException {
		
		String cmdline = cmdLine;
		
		try {

			if (cmdline == null) {
				throw new ShellException(EXP_SYNTAX);
			}
			if (stdout == null) {
				throw new ShellException(EXP_STDOUT);
			}

			cmdline = cmdline.trim();

			if (cmdline.length() <= 0 || hasNewLineViolation(cmdline)) {
				throw new ShellException(EXP_SYNTAX);
			}

			cmdline = cmdline.replace(Symbol.NEW_LINE_S, Symbol.EMPTY_S);

			if (cmdline.length() <= 0) {
				throw new ShellException(EXP_SYNTAX);
			}

			StringBuilder cmdSymbols = generateSymbols(cmdline);

			CallCommand firstCmd = splitIntoCommands(cmdline, cmdSymbols);
			if (firstCmd == null) {
				throw new ShellException(EXP_SYNTAX);
			}

			executeCommands(firstCmd, stdout);

			writeToStdout(stdout, Symbol.NEW_LINE_S);

		} finally {
			closeOutputStream(stdout);
		}
	}
}