package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class ShellImplTest {
	final static String PATH_SEPARATOR = File.separator;
	final static String LINE_SEPARATOR = System.getProperty("line.separator");
	final static String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "IOredirect"
			+ PATH_SEPARATOR;
	
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
}
