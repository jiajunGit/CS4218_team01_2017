/*
 * This test class only contains valid bugs (bug 1, 2 and 5) as reported in the
 * bug report.
 */
package hackathon;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.PwdException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.PwdApplication;

public class HackathonTest {
	
	private final static String PATH_SEPARATOR = File.separator;
	private final static String LINE_SEPARATOR = System.getProperty("line.separator");
	
	private ShellImpl shell = new ShellImpl();
	
	@Test(expected = PwdException.class) 
	public void testPwd() throws PwdException {
		
		PwdApplication pwd = new PwdApplication();
		String[] args = {"random"};
		pwd.run(args, System.in, System.out);
	}
	
	@Test 
	public void testHeadMultipleOptions() throws AbstractApplicationException, ShellException {
		
		String outputHead = shell.parseAndEvaluate("head -n 2 -n 5 input" + PATH_SEPARATOR + "input01");
		assertEquals("1" + LINE_SEPARATOR + "2" + LINE_SEPARATOR  + "3" + LINE_SEPARATOR + "4" + LINE_SEPARATOR + "5" + LINE_SEPARATOR, outputHead);
	}
	
	@Test
	public void testTailMultipleOptions() throws AbstractApplicationException, ShellException {
		
		String outputTail = shell.parseAndEvaluate("tail -n 2 -n 5 input" + PATH_SEPARATOR + "input01");
		assertEquals("44" + LINE_SEPARATOR + "55" + LINE_SEPARATOR  + "alice" + LINE_SEPARATOR + "bob" + LINE_SEPARATOR + "charlie" + LINE_SEPARATOR, outputTail);		
	}
	
	@Test(expected = SortException.class) 
	public void testRunSortNullStdout() throws AbstractApplicationException, ShellException { 
		
		shell.parseAndEvaluate("sort -n");
	}  
}
