package sg.edu.nus.comp.cs4218.impl.cmd;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.Symbol;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.SimpleCommand;

/**
 * A Call Command is a sub-command consisting of at least one non-keyword and
 * quoted (if any).
 * 
 * <p>
 * <b>Command format:</b> <code>(&lt;non-Keyword&gt; | &lt;quoted&gt;)*</code>
 * </p>
 */

public class CallCommand implements Command {
    
    private ShellImpl shell;
    
    private StringBuilder cmd;
    private StringBuilder cmdSymbols;
    
	private String cmdline;

	public CallCommand(String cmdline) {
		this.cmdline = cmdline;
		shell = new ShellImpl();
	}

	public CallCommand() {
		this("");
	}

	/**
	 * Evaluates sub-command using data provided through stdin stream. Writes
	 * result to stdout stream.
	 * 
	 * @param stdin
	 *            InputStream to get data from.
	 * @param stdout
	 *            OutputStream to write resultant data to.
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while evaluating the sub-command.
	 * @throws ShellException
	 *             If an exception happens while evaluating the sub-command.
	 */
	@Override
	public void evaluate(InputStream stdin, OutputStream stdout)
			throws AbstractApplicationException, ShellException {
		shell.evaluateOneCommand(cmd, cmdSymbols, stdin, stdout);
	}

	/**
     * Parses the sub-command
     * 
     * @throws ShellException
     *             If an exception happens while parsing the sub-command, or if
     *             the input redirection file path is same as that of the output
     *             redirection file path.
     */
    public void parse() throws ShellException {
        
        if(cmdline == null){
            throw new ShellException(Shell.EXP_SYNTAX);
        }
        
        cmdline = cmdline.trim();
        
        if( cmdline.length() <= 0 || shell.hasNewLineViolation(cmdline) ) {
            throw new ShellException(Shell.EXP_SYNTAX);
        }
        
        cmdline = cmdline.replace(Symbol.NEW_LINE_S, Symbol.EMPTY_S);
        
        if( cmdline.length() <= 0) {
            throw new ShellException(Shell.EXP_SYNTAX);
        }
        
        cmdSymbols = shell.generateSymbolsForInput(cmdline);
        cmd = new StringBuilder(cmdline);
    }
	
	/**
	 * Terminates current execution of the command (unused for now)
	 */
	@Override
	public void terminate() {}
}
