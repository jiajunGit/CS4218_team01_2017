package sg.edu.nus.comp.cs4218.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import sg.edu.nus.comp.cs4218.Symbol;
import sg.edu.nus.comp.cs4218.exception.ShellException;

/**
 * A class used to store different call command components, namely the
 * application name, the arguments (if any), the input redirection file path (if
 * any) and output redirection file path (if any), link type to next command (if
 * any), reference to next command (if any), input stream provided by previous
 * command (if any).
 * 
 */

public class Command {

	private String appName;

	private OutStream stdout;
	private InStream stdin;

	private LinkedList<String> arguments;

	private Link link;
	private Command nextCommand;

	public Command() {

		appName = "";

		stdin = new InStream();
		stdout = new OutStream();

		arguments = new LinkedList<String>();

		link = Link.NONE;
		nextCommand = null;
	}

	public void setLinkToNextCommand(char symbol) {

		switch (symbol) {

		case Symbol.SEQ_OP:
			link = Link.SEQUENCE;
			break;

		case Symbol.PIPE_OP:
			link = Link.PIPE;
			break;

		default:
			link = Link.NONE;
			break;
		}
	}

	public void setLinkToNextCommand(Stage stage) {

		if (stage != null) {
			switch (stage) {

			case PIPE:
				link = Link.PIPE;
				return;

			case SEQUENCE:
				link = Link.SEQUENCE;
				return;

			default:
				break;
			}
		}
		link = Link.NONE;
	}

	public Link getLinkTypeToNextCommand() {
		return link;
	}

	public void setNextCommand(Command next) {
		nextCommand = next;
	}

	public Command getNextCommand() {
		return nextCommand;
	}

	public InputStream getStdin(InputStream defaultStream) throws ShellException {
		return stdin.getStream(defaultStream);
	}

	public void setStdin(InputStream inputStream) {
		stdin.setStdin(inputStream);
	}

	public String getStdinName() throws ShellException {
		return stdin.getName();
	}

	public boolean hasStdin() {
		return stdin.hasStdin();
	}

	public IOStreamType getStdinType() {
		return stdin.getType();
	}

	public void setStdinName(String inputStreamName) {
		stdin.setName(inputStreamName);
	}

	public String getStdoutName() throws ShellException {
		return stdout.getName();
	}

	public boolean hasStdout() {
		return stdout.hasStdout();
	}

	public IOStreamType getStdoutType() {
		return stdout.getType();
	}

	public void setStdoutName(String outputStreamName) {
		stdout.setName(outputStreamName);
	}

	public OutputStream getStdout(OutputStream defaultStream) throws ShellException {
		return stdout.getStream(defaultStream);
	}

	public String[] getArguments() {

		String[] args = new String[arguments.size()];

		int index = 0;
		for (String arg : arguments) {
			args[index++] = arg;
		}
		return args;
	}

	public void addArguments(String arg) {
		arguments.add(arg);
	}

	public String getAppName() {
		return appName;
	}

	public boolean hasAppName() {
		return (appName != null && !appName.isEmpty());
	}

	public void setAppName(String applicationName) {
		appName = applicationName;
	}
}
