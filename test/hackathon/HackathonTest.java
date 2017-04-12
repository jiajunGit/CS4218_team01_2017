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
	final static String PATH_SEPARATOR = File.separator;
	final static String LINE_SEPARATOR = System.getProperty("line.separator");
	
	@Test(expected = PwdException.class) public void testPwd() throws PwdException {
		PwdApplication pwd = new PwdApplication();
		String[] args = {"random"};
		pwd.run(args, System.in, System.out);
		}
	
	/*
	 * Hacking team wrote 2 test cases for 1 bug (bug number 2), so this test case is
	 * a combination of the 2 test cases. This test case represents 1 bug.
	 */
	@Test public void testHeadMultipleOptions() throws AbstractApplicationException, ShellException {
		ShellImpl shellHead = new ShellImpl();
		String outputHead = shellHead.parseAndEvaluate("head -n 2 -n 5 input" + PATH_SEPARATOR + "input01");
		assertEquals("1" + LINE_SEPARATOR + "2" + LINE_SEPARATOR  + "3" + LINE_SEPARATOR + "4" + LINE_SEPARATOR + "5" + LINE_SEPARATOR, outputHead);
		
		ShellImpl shellTail = new ShellImpl();
		String outputTail = shellTail.parseAndEvaluate("tail -n 2 -n 5 input" + PATH_SEPARATOR + "input01");
		assertEquals("44" + LINE_SEPARATOR + "55" + LINE_SEPARATOR  + "alice" + LINE_SEPARATOR + "bob" + LINE_SEPARATOR + "charlie" + LINE_SEPARATOR, outputTail);		
		}
	
	@Test(expected = SortException.class) public void testRunSortNullStdout() throws AbstractApplicationException, ShellException 
	{ 
		ShellImpl shell = new ShellImpl();
		shell.parseAndEvaluate("sort -n");
	}  
}
