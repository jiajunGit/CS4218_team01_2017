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
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class ShellImplTest {
    
	final static String PATH_SEPARATOR = File.separator;
	final static String LINE_SEPARATOR = System.getProperty("line.separator");
	
	final static String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "IOredirect"+ PATH_SEPARATOR;
	final static String RELATIVE_TEST_SHELL_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "shell" + PATH_SEPARATOR;
	private final static String RELATIVE_TEST_GLOB_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "glob";
    private final static String ABSOLUTE_TEST_GLOB_DIRECTORY = Environment.currentDirectory + PATH_SEPARATOR + RELATIVE_TEST_GLOB_DIRECTORY;
    
	private ShellImpl shell = new ShellImpl();
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@BeforeClass
    public static void setup() {
        GlobTestHelper.setupGlobFiles(ABSOLUTE_TEST_GLOB_DIRECTORY);
    }
    
    @AfterClass
    public static void tearDown() {
        GlobTestHelper.cleanUpGlobFiles(ABSOLUTE_TEST_GLOB_DIRECTORY);
    }
	
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

		Assert.assertEquals("shell: " +sg.edu.nus.comp.cs4218.Shell.EXP_SYNTAX, shell.redirectOutputWithNoFile("cat > "));
	}

	@Test
	public void redirectInputWithException(){
		ShellImpl shell = new ShellImpl();

		Assert.assertEquals("shell: " +sg.edu.nus.comp.cs4218.Shell.EXP_SYNTAX, shell.redirectInputWithException("cat < input.txt < input.txt "));
	}

	@Test
	public void redirectOutputWithException(){
		ShellImpl shell = new ShellImpl();

		Assert.assertEquals("shell: " +sg.edu.nus.comp.cs4218.Shell.EXP_SYNTAX, shell.redirectOutputWithException("cat > input.txt > input.txt "));
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
			String expectedOutput = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testReditInput")));
			Assert.assertEquals(expectedOutput, shell.redirectInput("< " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRedirInput" + " cat"));
		} catch (IOException e) {
			e.printStackTrace();
		}    	
	}

	@Test
	public void testRedirInputCat(){
		ShellImpl shell = new ShellImpl();
		try {
			String expectedOutput = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testReditInput")));
			Assert.assertEquals(expectedOutput, shell.redirectInput("cat < " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRedirInput"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test 
	public void testRedirectInputOutputCat(){
		ShellImpl shell = new ShellImpl();
		try {
			String expectedOutput = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "catOut")));
			String inPath = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "newIOinput";
			String outPath = RELATIVE_TEST_DIRECTORY + "output" + PATH_SEPARATOR + "catOut";
			shell.parseAndEvaluate("cat < " + inPath + " > " + outPath);
			String actualOutput = new String(Files.readAllBytes(Paths.get(outPath)));

			Assert.assertEquals(expectedOutput, actualOutput);
			
			File nowExists = new File(RELATIVE_TEST_DIRECTORY + "output" + PATH_SEPARATOR + "catOut");

			if(nowExists.exists()){
				nowExists.delete();
			}
		} catch (IOException | AbstractApplicationException | ShellException e) {
			e.printStackTrace();
		}		
	}
	
	@Test
	public void testRedirectInputOutputHead(){
		ShellImpl shell = new ShellImpl();
		try {
			String expectedOutput = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "headOut")));
			String inPath = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "newIOinput";
			String outPath = RELATIVE_TEST_DIRECTORY + "output" + PATH_SEPARATOR + "headOut";
			shell.parseAndEvaluate("head < " + inPath + " > " + outPath);
			String actualOutput = new String(Files.readAllBytes(Paths.get(outPath)));

			Assert.assertEquals(expectedOutput, actualOutput);
			
			File nowExists = new File(RELATIVE_TEST_DIRECTORY + "output" + PATH_SEPARATOR + "headOut");

			if(nowExists.exists()){
				nowExists.delete();
			}
		} catch (IOException | AbstractApplicationException | ShellException e) {
			e.printStackTrace();
		}		
	}
	
	@Test
	public void testRedirectInputOutputTail(){
		ShellImpl shell = new ShellImpl();
		try {
			String expectedOutput = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "tailOut")));
			String inPath = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "newIOinput";
			String outPath = RELATIVE_TEST_DIRECTORY + "output" + PATH_SEPARATOR + "tailOut";
			shell.parseAndEvaluate("tail < " + inPath + " > " + outPath);
			String actualOutput = new String(Files.readAllBytes(Paths.get(outPath)));

			Assert.assertEquals(expectedOutput, actualOutput);
			
			File nowExists = new File(RELATIVE_TEST_DIRECTORY + "output" + PATH_SEPARATOR + "tailOut");

			if(nowExists.exists()){
				nowExists.delete();
			}
		} catch (IOException | AbstractApplicationException | ShellException e) {
			e.printStackTrace();
		}			
	}
	
	@Test
	public void testRedirectInputOutputSort(){
		ShellImpl shell = new ShellImpl();
		try {
			String expectedOutput = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "sortOut")));
			String inPath = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "newIOinput";
			String outPath = RELATIVE_TEST_DIRECTORY + "output" + PATH_SEPARATOR + "sortOut";
			shell.parseAndEvaluate("sort -n < " + inPath + " > " + outPath);
			String actualOutput = new String(Files.readAllBytes(Paths.get(outPath)));

			Assert.assertEquals(expectedOutput, actualOutput);
			
			File nowExists = new File(RELATIVE_TEST_DIRECTORY + "output" + PATH_SEPARATOR + "sortOut");

			if(nowExists.exists()){
				nowExists.delete();
			}
		} catch (IOException | AbstractApplicationException | ShellException e) {
			e.printStackTrace();
		}		
	}
	
	@Test
	public void testRedirectInputOutputGrep(){
		ShellImpl shell = new ShellImpl();
		try {
			String expectedOutput = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "grepOut")));
			String inPath = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "newIOinput";
			String outPath = RELATIVE_TEST_DIRECTORY + "output" + PATH_SEPARATOR + "grepOut";
			shell.parseAndEvaluate("grep Please < " + inPath + " > " + outPath);
			String actualOutput = new String(Files.readAllBytes(Paths.get(outPath)));

			Assert.assertEquals(expectedOutput, actualOutput);
			
			File nowExists = new File(RELATIVE_TEST_DIRECTORY + "output" + PATH_SEPARATOR + "grepOut");

			if(nowExists.exists()){
				nowExists.delete();
			}
		} catch (IOException | AbstractApplicationException | ShellException e) {
			e.printStackTrace();
		}			
	}
	
	@Test
	public void testRedirectInputOutputCal(){
		ShellImpl shell = new ShellImpl();
		try {
			String expectedOutput = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "year2017")));
			String outPath = RELATIVE_TEST_DIRECTORY + "output" + PATH_SEPARATOR + "calOut";
			shell.parseAndEvaluate("cal 2017 > " + outPath);
			String actualOutput = new String(Files.readAllBytes(Paths.get(outPath)));

			Assert.assertEquals(expectedOutput, actualOutput);
			
			File nowExists = new File(RELATIVE_TEST_DIRECTORY + "output" + PATH_SEPARATOR + "calOut");

			if(nowExists.exists()){
				nowExists.delete();
			}
		} catch (IOException | AbstractApplicationException | ShellException e) {
			e.printStackTrace();
		}			
	}

	@Test
	public void testRedirectInputOutputPwd(){
		ShellImpl shell = new ShellImpl();
		try {
			String expectedOutput = Environment.currentDirectory + LINE_SEPARATOR;
			String outPath = RELATIVE_TEST_DIRECTORY + "output" + PATH_SEPARATOR + "pwdOut";
			shell.parseAndEvaluate("pwd > " + outPath);
			String actualOutput = new String(Files.readAllBytes(Paths.get(outPath)));

			Assert.assertEquals(expectedOutput, actualOutput);
			
			File nowExists = new File(RELATIVE_TEST_DIRECTORY + "output" + PATH_SEPARATOR + "pwdOut");

			if(nowExists.exists()){
				nowExists.delete();
			}
		} catch (IOException | AbstractApplicationException | ShellException e) {
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
	
	/**
	 * Calls Command
	 */
	@Test(expected=ShellException.class)
    public void testCallInvalidCaseSensitiveAppName() throws AbstractApplicationException, ShellException{
        shell.parseAndEvaluate("Echo", System.out);
    }
	
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

	/**
     * Tests for glob
     */
    
    @Test
    public void testGlobWithGlobInQuotes() {
        String command = "echo \"" + RELATIVE_TEST_GLOB_DIRECTORY + "\"" + PATH_SEPARATOR + "\"cab*\"";
        String output = shell.globNoPaths(command);
        String expected = RELATIVE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + "cab*" + LINE_SEPARATOR;
        assertEquals(output, expected);
    }
    
    /**
     * Tests if the developer have sanitized characters used to sanitize the glob string before using it to 
     * construct the regex pattern used to match file/directory names.
     * 
     * Should not match any file or directories
     */
    @Test
    public void testGlobWithGlobWithEscapeCharacters() {
        String command = "echo \"" + RELATIVE_TEST_GLOB_DIRECTORY + "\"" + PATH_SEPARATOR + "*\"\\E-.-\\Q\"*";
        String output = shell.globNoPaths(command);
        String expected = RELATIVE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + "*\\E-.-\\Q*" + LINE_SEPARATOR;
        assertEquals(output, expected);
    }
    
    /**
     * Tests if the developer have sanitized the glob string before using it to construct the regex pattern used to 
     * match file/directory names.
     * 
     * Should not match any file or directories
     */
    @Test
    public void testGlobWithGlobWithSpecialCharacters() {
        String command = "echo \"" + RELATIVE_TEST_GLOB_DIRECTORY + "\"" + PATH_SEPARATOR + "\"-..*\"";
        String output = shell.globNoPaths(command);
        String expected = RELATIVE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + "-..*" + LINE_SEPARATOR;
        assertEquals(output, expected);
    }
    
    @Test
    public void testGlobInvalidPathWithFileAsDirectory() {
        String command = "echo \"" + ABSOLUTE_TEST_GLOB_DIRECTORY + "\"" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-" 
                         + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "*";
        String output = shell.globNoPaths(command);
        String expected = ABSOLUTE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-" 
                          + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "*" + LINE_SEPARATOR;
        assertEquals(output, expected);
    }
    
    @Test
    public void testGlobNoMatchingRelativePaths() {
        String command = "echo \"" + RELATIVE_TEST_GLOB_DIRECTORY + "\"" + PATH_SEPARATOR + "t*";
        String output = shell.globNoPaths(command);
        String expected = RELATIVE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + "t*" + LINE_SEPARATOR;
        assertEquals(output, expected);
    }
    
    @Test
    public void testGlobOneDirectory() {
        String command = "echo \"" + ABSOLUTE_TEST_GLOB_DIRECTORY + "\"" + PATH_SEPARATOR + "cab*";
        String output = shell.globOneFile(command);
        String expected = ABSOLUTE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + "cab" + LINE_SEPARATOR;
        assertEquals(output, expected);
    }
    
    @Test
    public void testGlobOneFileWithSpaces() {
        String command = "echo \"" + RELATIVE_TEST_GLOB_DIRECTORY + "\"" + PATH_SEPARATOR + "\"FiLe WiTh SpAcEs\"" + PATH_SEPARATOR + "*\"ile with spac\"*\"s.\"*";
        String output = shell.globOneFile(command);
        String expected = ABSOLUTE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + "FiLe WiTh SpAcEs" + PATH_SEPARATOR + "file with spaces.txt" + LINE_SEPARATOR;
        assertEquals(output, expected);
    }
    
    @Test
    public void testGlobAfterFullDirectoryName() {
        String command = "echo \"" + RELATIVE_TEST_GLOB_DIRECTORY + "\"" + PATH_SEPARATOR + "\".cab.car\"**";
        String output = shell.globOneFile(command);
        String expected = ABSOLUTE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + ".cab.car" + LINE_SEPARATOR;
        assertEquals(output, expected);
    }
    
    @Test
    public void testGlobMultipleFilesAndDirectoriesInSingleDirectory() {
        String command = "echo \"" + ABSOLUTE_TEST_GLOB_DIRECTORY + "\"" + PATH_SEPARATOR + "*\".cab.car\"" + PATH_SEPARATOR + "*";
        String output = shell.globFilesDirectories(command);
        String expected = ABSOLUTE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.- "
                          + ABSOLUTE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr "
                          + ABSOLUTE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712 "
                          + ABSOLUTE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712 2712 2712 "
                          + ABSOLUTE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab "
                          + ABSOLUTE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car "
                          + ABSOLUTE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat" + LINE_SEPARATOR;
        assertEquals(output, expected);
    }
    
    @Test
    public void testGlobMultipleFilesAndDirectoriesInMultipleDirectories() {
        String command = "echo \"" + RELATIVE_TEST_GLOB_DIRECTORY + "\"" + PATH_SEPARATOR + "ca*" + PATH_SEPARATOR + "c*t" + PATH_SEPARATOR + "2712*";
        String output = shell.globFilesDirectories(command);
        String expected = ABSOLUTE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712 "
                          + ABSOLUTE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712.txt "
                          + ABSOLUTE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712 "
                          + ABSOLUTE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712.txt "
                          + ABSOLUTE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712 "
                          + ABSOLUTE_TEST_GLOB_DIRECTORY + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712.txt"
                          + LINE_SEPARATOR;
        assertEquals(output, expected);
    }
    
    @Test
    public void testGlobWithMultipleOutputRedir() {
        String command = "echo hi >\"" + RELATIVE_TEST_GLOB_DIRECTORY + "\"" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712*";
        String output = shell.globWithException(command);
        String expected = "shell: Invalid syntax encountered.";
        assertEquals(output, expected);
    }
	
    @Test
    public void testGlobWithMultipleInputRedir() {
        String command = "cat <\"" + ABSOLUTE_TEST_GLOB_DIRECTORY + "\"" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712*";
        String output = shell.globWithException(command);
        String expected = "shell: Multiple input streams.";
        assertEquals(output, expected);
    }
    
	private void clearFromFile(String fileName) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new File(fileName));
		writer.print("");
		writer.close();
	}
}
