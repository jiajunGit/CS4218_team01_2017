package test.ef2.commandsubstitution;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class CommandSubstitutionTest {

	private ShellImpl shell = new ShellImpl();
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static String PATH_SEPARATOR = File.separator;
	private final static String LINE_SEPARATOR = System.getProperty("line.separator");
	final static String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "ef2"
			+ PATH_SEPARATOR + "commandsubstitution" + PATH_SEPARATOR + "input" + PATH_SEPARATOR;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpBeforeTest() {
		shell = new ShellImpl();
		System.setOut(new PrintStream(outContent));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDownAfterTest() {
		System.setOut(System.out);
	}

	@Test
	public void testBackquoteEcho() throws AbstractApplicationException, ShellException {
		String output = shell.performCommandSubstitution("echo `echo hi`");
		String expectedOut = "hi" + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}

	@Test
	public void testBackquoteEchoWithSemiColon() throws AbstractApplicationException, ShellException {
		String output = shell.performCommandSubstitution("echo `echo hello;echo world`");
		String expectedOut = "hello world" + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}

	@Test
	public void testBackquoteEchoWithDoubleQuote() throws AbstractApplicationException, ShellException {
		String output = shell.performCommandSubstitution("echo `echo hello;echo \"echo world*&^%;#@|\"`");
		String expectedOut = "hello echo world*&^%;#@|" + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}

	@Test
	public void testBackquoteEchoWithSingleQuote() throws AbstractApplicationException, ShellException {
		String output = shell.performCommandSubstitution("echo `echo hello;echo 'echo world *&^%;#@|'`");
		String expectedOut = "hello echo world *&^%;#@|" + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}

	@Test
	public void testInvalidSubCmd() throws AbstractApplicationException, ShellException {
		String output = shell.performCommandSubstitutionWithException("echo `hi`");
		String expectedOut = "shell: hi: Invalid app.";
		assertEquals(expectedOut, output);
	}

	@Test
	public void testInvalidSubCmdSyntax() throws AbstractApplicationException, ShellException {
		String output = shell.performCommandSubstitutionWithException("echo `hi");
		String expectedOut = "shell: Invalid syntax encountered.";
		assertEquals(expectedOut, output);
	}

	@Test
	public void testBackquoteCat() throws AbstractApplicationException, ShellException {
		String file = RELATIVE_TEST_DIRECTORY + "test11Lines";
		String output = shell.performCommandSubstitution("echo `cat " + file + "`");
		String expectedOut = "1 2 3 4 5 6 7 8 9 10 11" + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}

	@Test
	public void testBackquotePwd() throws AbstractApplicationException, ShellException {
		String output = shell.performCommandSubstitution("echo `pwd`");
		String expectedOut = Environment.currentDirectory + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}

	@Test
	public void testDoubleQuoteWithinBackQuote() throws AbstractApplicationException, ShellException {
		String output = shell.performCommandSubstitution("echo ` echo \"hi\" `");
		String expectedOut = "hi" + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}
	
	@Test
	public void testSingleQuoteWithinBackquote() {
		String output = shell.performCommandSubstitution("echo ` echo 'hi' `");
		String expectedOut = "hi" + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}
	
	@Test
	public void testBackquoteWithinDoubleQuoteWithinBackQuote() throws AbstractApplicationException, ShellException {
		String output = shell.performCommandSubstitution("echo ` echo \"`echo 'hi'`\" `");
		String expectedOut = "hi" + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}
	
	@Test
	public void testBackquoteHead() throws AbstractApplicationException, ShellException {
		String file = RELATIVE_TEST_DIRECTORY + "test11Lines";
		String output = shell.performCommandSubstitution("echo `head " + file + "`");
		String expectedOut = "1 2 3 4 5 6 7 8 9 10" + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}

	@Test
	public void testBackquoteTail() throws AbstractApplicationException, ShellException {
		String file = RELATIVE_TEST_DIRECTORY + "test11Lines";
		String output = shell.performCommandSubstitution("echo `tail " + file + "`");
		String expectedOut = "2 3 4 5 6 7 8 9 10 11" + LINE_SEPARATOR;
		assertEquals(expectedOut, output);
	}

}
