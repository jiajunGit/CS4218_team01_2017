package ef2.shell.pipe;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class PipeTest {

	static ShellImpl shell;
	static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	final static String LINE_SEPARATOR = System.lineSeparator();
	final static String PATH_SEPARATOR = File.separator;
	final static String RELATIVE_TEST_PIPE_DIR = "test" + PATH_SEPARATOR + "ef2"
			+ PATH_SEPARATOR + "shell" + PATH_SEPARATOR;

	@Before
	public void setUpBeforeTest() throws Exception {
		outContent = new ByteArrayOutputStream();
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shell = new ShellImpl();
		System.setOut(new PrintStream(outContent));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.setOut(System.out);
	}

	@Test
	public void testFailedPipeException() throws AbstractApplicationException, ShellException {
		assertEquals("sort:Specify a file which exists",
				shell.pipeWithException("echo \"hello world\" | sort aFileThatDoesNotExist"));
	}

	@Test
	public void testInvalidPipeException() throws AbstractApplicationException, ShellException {
		assertEquals("shell: gerp: Invalid app.", shell.pipeWithException("echo \"hello world\" | gerp random"));
	}

	@Test
	public void testPipeTwoCommands() throws AbstractApplicationException, ShellException {
		assertEquals("helloworld" + LINE_SEPARATOR, shell.pipeTwoCommands("echo helloworld | grep hello"));
	}

	@Test
	public void testPipeMultipleCommands() throws AbstractApplicationException, ShellException, IOException {
		shell.pipeMultipleCommands("cat " + RELATIVE_TEST_PIPE_DIR + "pipe"
				+ PATH_SEPARATOR + "input" + PATH_SEPARATOR + "testTwoPipes" + " | grep security | grep solution");
	}

}
