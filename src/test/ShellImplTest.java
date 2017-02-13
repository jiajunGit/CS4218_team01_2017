package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
	
	@Test
	public void testRedirectInput(){
		ShellImpl shell = new ShellImpl();
		try {
			String expectedOutput = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testRedirectInput.txt")));
			Assert.assertEquals(expectedOutput, shell.redirectInput("cat < " + RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRedirectInput.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    @Test
    public void testRedirectOutput(){
		ShellImpl shell = new ShellImpl();
		
		try {
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
		Assert.assertEquals("sg.edu.nus.comp.cs4218.exception.ShellException", shell.redirectInputWithNoFile("cat < "));
		
    }

    @Test
    public void redirectOutputWithNoFile(){
		ShellImpl shell = new ShellImpl();
		
		Assert.assertEquals("sg.edu.nus.comp.cs4218.exception.ShellException", shell.redirectInputWithNoFile("cat > "));
    }

    @Test
    public void redirectInputWithException(){
		ShellImpl shell = new ShellImpl();
		
		Assert.assertEquals("sg.edu.nus.comp.cs4218.exception.ShellException", shell.redirectInputWithException("cat < input.txt < input.txt "));
    }

    @Test
    public void redirectOutputWithException(){
		ShellImpl shell = new ShellImpl();
		
		Assert.assertEquals("sg.edu.nus.comp.cs4218.exception.ShellException", shell.redirectInputWithException("cat > input.txt > input.txt "));
    }	
    
    /**
     * Calls Command
     */
    
    @Test(expected=ShellException.class)
    public void testInvalidCommand() throws AbstractApplicationException, ShellException{
    	shell.parseAndEvaluate("\"echo\"", System.out);
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
    	
    	assertEquals(expectedOut + LINE_SEPARATOR, outContent.toString());
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
}
