package test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class ShellImplTest {
	final static String PATH_SEPARATOR = File.separator;
	final static String LINE_SEPARATOR = System.getProperty("line.separator");
	final static String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "IOredirect"
			+ PATH_SEPARATOR;
	final static String RELATIVE_TEST_SHELL_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "shell" + PATH_SEPARATOR;
	private ShellImpl shell = new ShellImpl();
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUpBeforeTest(){
		shell = new ShellImpl();
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDownAfterTest(){
		System.setOut(System.out);
	}

	@Test
	public void testRedirOutputEcho(){
		ShellImpl shell = new ShellImpl();


		try {
			clearFromFile(RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRedirectOutput.txt");
			String expectedOutput = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testRedirectOutput.txt")));
			shell.redirectOutput("echo helloworld > " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRedirectOutput.txt");
			String actualOutput = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRedirectOutput.txt")));

			Assert.assertEquals(expectedOutput, actualOutput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void redirectInputWithNoFile(){
		ShellImpl shell = new ShellImpl();
		Assert.assertEquals("shell: " +sg.edu.nus.comp.cs4218.Shell.EXP_SYNTAX, shell.redirectInputWithNoFile("cat < "));

	}

	@Test
	public void redirectOutputWithNoFile(){
		ShellImpl shell = new ShellImpl();

		Assert.assertEquals("shell: " +sg.edu.nus.comp.cs4218.Shell.EXP_SYNTAX, shell.redirectInputWithNoFile("cat > "));
	}

	@Test
	public void redirectInputWithException(){
		ShellImpl shell = new ShellImpl();

		Assert.assertEquals("shell: " +sg.edu.nus.comp.cs4218.Shell.EXP_SYNTAX, shell.redirectInputWithException("cat < input.txt < input.txt "));
	}

	@Test
	public void redirectOutputWithException(){
		ShellImpl shell = new ShellImpl();

		Assert.assertEquals("shell: " +sg.edu.nus.comp.cs4218.Shell.EXP_SYNTAX, shell.redirectInputWithException("cat > input.txt > input.txt "));
	}	

	@Test
	public void redirectInputInvalidFile(){
		ShellImpl shell = new ShellImpl();

		Assert.assertEquals("shell: " +sg.edu.nus.comp.cs4218.Shell.EXP_STDIN, shell.redirectInputWithException("cat < doesntexist.txt "));

	}

	@Test
	public void redirectOutputNonExistFile(){
		File noExist = new File(RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "outputNoExist.txt");

		Assert.assertFalse(noExist.exists());

		try{
			String expectedOutput = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "outputNoExist.txt")));
			shell.redirectOutput("echo le wild file appears > " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "outputNoExist.txt");
			String actualOutput = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "outputNoExist.txt")));

			Assert.assertEquals(expectedOutput, actualOutput);

			File nowExists = new File(RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "outputNoExist.txt");

			if(nowExists.exists()){
				nowExists.delete();
			}

		}catch (IOException e){
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testRedirOutputBeforeEcho(){
		ShellImpl shell = new ShellImpl();


		try {
			clearFromFile(RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRedirectOutput.txt");
			String expectedOutput = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testRedirectOutput.txt")));
			shell.redirectOutput("> " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRedirectOutput.txt" + " echo helloworld");
			String actualOutput = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRedirectOutput.txt")));

			Assert.assertEquals(expectedOutput, actualOutput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test 
	public void testRedirInputBeforeCat(){
		ShellImpl shell = new ShellImpl();
		try {
			String expectedOutput = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testRedirectInput.txt")));
			Assert.assertEquals(expectedOutput, shell.redirectInput("< " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRedirectInput.txt" + " cat"));
		} catch (IOException e) {
			e.printStackTrace();
		}    	
	}

	@Test
	public void testRedirInputCat(){
		ShellImpl shell = new ShellImpl();
		try {
			String expectedOutput = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testRedirectInput.txt")));
			Assert.assertEquals(expectedOutput, shell.redirectInput("cat < " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRedirectInput.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Quoting Commands Section
	 * Single quote: disables the interpretation of all special symbols 
	 * Double quote: disable the interpretation of all special symbols, except for single quote and back quote
	 * Back quote: used for command substitution
	 * 
	 * Patterns: BB, DD, SS, BDDB, DBBD, BSSB, DSSD
	 */
	//Test section for single quotes
	@Test
	public void testSingleQuoteSimple() throws AbstractApplicationException, ShellException {
		String output = shell.processSQ("'hi'");
		String expectedOut = "hi";
		assertEquals(expectedOut, output);
	}
	
	@Test
	public void testSingleQuoteComplex() throws AbstractApplicationException, ShellException {
		String output = shell.processSQ("'cat head `echo tail` pwd cd cal grep -> \t * \" ` | < > ; '");
		String expectedOut = "cat head `echo tail` pwd cd cal grep -> \t * \" ` | < > ; ";
		assertEquals(expectedOut, output);
	}
	
	@Test
	public void testSingleQuoteNullArg() throws AbstractApplicationException, ShellException {
		String output = shell.processSQ(null);
		String expectedOut = null;
		assertEquals(expectedOut, output);
	}
	
	@Test(expected=ShellException.class)
	public void testSingleQuoteOddSingleQuoteException() throws AbstractApplicationException, ShellException {
		shell.processSQ(" ''''odd number of single quote' ");
	}
	
	//Test section for double quotes
	@Test
	public void testDoubleQuoteSimple() throws AbstractApplicationException, ShellException {
		String output = shell.processDQ("\"hi\"");
		String expectedOut = "hi";
		assertEquals(expectedOut, output);
	}
	
	@Test
	public void testDoubleQuoteComplex() throws AbstractApplicationException, ShellException {
		String output = shell.processDQ("\"hi <> | * echo test grep tail pwd cd cal cat --> \t ;\"");
		String expectedOut = "hi <> | * echo test grep tail pwd cd cal cat --> \t ;";
		assertEquals(expectedOut, output);
	}
	
	@Test
	public void testDoubleQuoteNullArg() throws AbstractApplicationException, ShellException {
		String output = shell.processDQ(null);
		String expectedOut = null;
		assertEquals(expectedOut, output);
	}
	
	@Test(expected=ShellException.class)
	public void testDoubleQuoteOddNumberDoubleQuoteException() throws AbstractApplicationException, ShellException {
		shell.processDQ("\"\"odd number double quote\"");
	}
	
	//Test section for double quotes combination with single quote and back quote
	@Test
	public void testDoubleQuoteWithInnerBackquote() throws AbstractApplicationException, ShellException {
		String output = shell.processDQ("\"hi `echo inner quote`\""); // "hi `echo inner quote`"
		String expectedOut = "hi inner quote "; //extra space because of inner echo
		assertEquals(expectedOut, output);
	}

	@Test
	public void testDoubleQuoteWithInnerSinglequote() throws AbstractApplicationException, ShellException {
		String output = shell.processDQ("\"hi 'echo inner quote'\""); // "hi '`echo inner quote`'"
		String expectedOut = "hi echo inner quote";
		assertEquals(expectedOut, output);
	}
	
	@Test(expected=ShellException.class)
	public void testDoubleQuoteWithOddBackquoteException() throws AbstractApplicationException, ShellException {
		String output = shell.processDQ("\"hi ``inner backquote`\"");
	}
	
	@Test(expected=ShellException.class)
	public void testDoubleQuoteWithOddSinglequoteException() throws AbstractApplicationException, ShellException {
		String output = shell.processDQ("\"hi '''inner singlequote''\"");
	}
	
	//Test Section for backquote
	@Test
	public void testBackquoteEcho() throws AbstractApplicationException, ShellException {
		String output = shell.processBQ("`echo hi`");
		String expectedOut = "hi ";
		assertEquals(expectedOut, output);
	}
	
	@Test
	public void testBackquoteCat() throws AbstractApplicationException, ShellException {
		String output = shell.processBQ("`cat`");
		ByteArrayInputStream in = new ByteArrayInputStream("hello world".getBytes());
		System.setIn(in);
		String expectedOut = "hello world";
		assertEquals(expectedOut, output);
	}
	
	@Test
	public void testBackquotePwd() throws AbstractApplicationException, ShellException {
		String output = shell.processBQ("`pwd`");
		String expectedOut = Environment.currentDirectory+" ";
		assertEquals(expectedOut, output);
	}
	
	@Test
	public void testBackquoteHead() throws AbstractApplicationException, ShellException {
		String output = shell.processBQ("`head`");
		ByteArrayInputStream in = new ByteArrayInputStream("hello world".getBytes());
		System.setIn(in);
		String expectedOut = "hello world";
		assertEquals(expectedOut, output);
	}

	/**
	 * Calls Command
	 */

	@Test(expected=ShellException.class)
	public void testCallInvalidArgument() throws AbstractApplicationException, ShellException{
		shell.parseAndEvaluate("\"echo\"", System.out);
	}
	
	@Test(expected=ShellException.class)
	public void testCallInvalidCommand() throws AbstractApplicationException, ShellException{
		shell.parseAndEvaluate("ls", System.out);
	}
	
	@Test
	public void testCallExtraOption() throws AbstractApplicationException, ShellException{
		shell.parseAndEvaluate("echo -l hello", outContent);
		
		assertEquals("-l hello" + LINE_SEPARATOR, outContent.toString());
	}
	
	@Test
	public void testCallArg() throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("cat " + RELATIVE_TEST_SHELL_DIRECTORY + "input" + PATH_SEPARATOR + "testCallArg", System.out);
		String expectedOut = "";

		try {
			expectedOut = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_SHELL_DIRECTORY + PATH_SEPARATOR + "output" + PATH_SEPARATOR + "testCallArg")));
		} catch (IOException e) {
			System.out.println(e);
		}

		assertEquals(expectedOut, outContent.toString());
	}

	@Test
	public void testCallArg1() throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("echo 'black cat'", System.out);
		String expectedOut = "black cat" + LINE_SEPARATOR;

		assertEquals(expectedOut, outContent.toString());
	}

	@Test
	public void testCall() throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("pwd", System.out);
		String expectedOut = System.getProperty("user.dir") + LINE_SEPARATOR;

		assertEquals(expectedOut, outContent.toString());
	}

	@Test
	public void testCallOption() throws AbstractApplicationException, ShellException {
		String expectedOut = "";
		shell.parseAndEvaluate("cal -m", System.out);

		try {
			expectedOut = new String(Files.readAllBytes(Paths.get("src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "calendar" + PATH_SEPARATOR + "currentMonthMonday")));
		} catch (IOException e) {
			System.out.println(e);
		}

		assertEquals(expectedOut, outContent.toString());
	}

	@Test
	public void testCallOptArg() throws AbstractApplicationException, ShellException {
		String expectedOut = "";

		shell.parseAndEvaluate("head -n 6 " + RELATIVE_TEST_SHELL_DIRECTORY + "input" + PATH_SEPARATOR + "testCallOptArg", System.out);

		try {
			expectedOut = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_SHELL_DIRECTORY + PATH_SEPARATOR + "output" + PATH_SEPARATOR + "testCallOptArg")));
		} catch (IOException e) {
			System.out.println(e);
		}

		assertEquals(expectedOut, outContent.toString());
	}

	@Test
	public void testLoneSingleSemicolon(){
		try {
			shell.parseAndEvaluate(";");
			fail();
		} catch (AbstractApplicationException | ShellException e) {
			assertEquals("shell: " +sg.edu.nus.comp.cs4218.Shell.EXP_SYNTAX, e.getMessage());
		}

	}
	
	/**
	 * Tests for sequence
	 */

	@Test
	public void testLoneMultipleSemicolon(){
		try {
			shell.parseAndEvaluate(";;;;");
			fail();
		} catch (AbstractApplicationException | ShellException e) {
			assertEquals("shell: " +sg.edu.nus.comp.cs4218.Shell.EXP_SYNTAX, e.getMessage());
		}

	}

	@Test
	public void testNoCommandBeforeSemicolon(){
		try {
			shell.parseAndEvaluate(";echo hello");
			fail();
		} catch (AbstractApplicationException | ShellException e) {
			assertEquals("shell: " +sg.edu.nus.comp.cs4218.Shell.EXP_SYNTAX, e.getMessage());
		}

	}

	@Test
	public void testNoCommandAfterSemicolon(){
		try {
			shell.parseAndEvaluate("echo hello;");
			fail();
		} catch (AbstractApplicationException | ShellException e) {
			assertEquals("shell: " +sg.edu.nus.comp.cs4218.Shell.EXP_SYNTAX, e.getMessage());
		}   	
	}

	@Test
	public void testInvalidCommandBeforeSemicolon(){
		try {
			shell.parseAndEvaluate("tai file.txt;echo hello");
			fail();
		} catch (AbstractApplicationException | ShellException e) {
			assertEquals("shell: tai: " +sg.edu.nus.comp.cs4218.Shell.EXP_INVALID_APP, e.getMessage());
		}

	}

	@Test
	public void testInvalidCommandAfterSemicolon(){
		try {
			shell.parseAndEvaluate("echo hello;pw");
			fail();
		} catch (AbstractApplicationException | ShellException e) {
			assertEquals("shell: pw: " +sg.edu.nus.comp.cs4218.Shell.EXP_INVALID_APP, e.getMessage());
		}

	}

	@Test
	public void testSequenceTwoValidCommands(){
		try {
			String actual = shell.parseAndEvaluate("echo hello;echo world");
			Assert.assertEquals("hello" + System.lineSeparator() + "world" + System.lineSeparator(), actual);
		} catch (AbstractApplicationException | ShellException e) {
			fail();
		}
	}

	@Test
	public void testSequenceMultipleValidCommands(){
		try {
			String expected = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_SHELL_DIRECTORY + "output" + PATH_SEPARATOR + "testSeqOut")));
			shell.parseAndEvaluate("echo hello;tail -n 13 " + RELATIVE_TEST_SHELL_DIRECTORY + "input" + PATH_SEPARATOR  + "testSeqIn; echo world; head " + RELATIVE_TEST_SHELL_DIRECTORY + "input" + PATH_SEPARATOR + "testSeqIn", outContent);
			Assert.assertEquals(expected, outContent.toString());
		} catch (AbstractApplicationException | ShellException | IOException e) {
			e.printStackTrace();
			fail();
		}
	}

	private void clearFromFile(String fileName) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new File(fileName));
		writer.print("");
		writer.close();
	}
}
