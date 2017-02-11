package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.CdException;
import sg.edu.nus.comp.cs4218.impl.app.CdApplication;
import sg.edu.nus.comp.cs4218.impl.app.EchoApplication;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CdApplicationTest {

	private InputStream input;
	private OutputStream output;
	private CdApplication cdApp;
	private static String revertDir = Environment.currentDirectory;

	@Before
	public void setUpBeforeTest() throws Exception {
		cdApp = new CdApplication();
	}
	
	@After
	public void tearDownAfterTest(){
		Environment.currentDirectory = revertDir;
	}

	@Test(expected = CdException.class)
	public void testCdNullArgumentsException() throws CdException {
		cdApp.run(null, input, output);
	}

	@Test(expected = CdException.class)
	public void testCdDirectoryUnspecifiedException() throws CdException {
		String[] args = {};

		cdApp.run(args, input, output);
	}

	@Test(expected = CdException.class)
	public void testCdMoreThanOneArgException() throws CdException {
		String[] args = { "HelloWorldDirectory", "HelloWorld2Directory" };

		cdApp.run(args, input, output);
	}

	@Test(expected = CdException.class)
	public void testDirectoryDoesNotExistException() throws CdException {
		String[] args = { "./directoryDoesNotExist" };

		cdApp.run(args, input, output);
	}

	@Test
	public void testCdDirectoryExist() throws CdException {
		String previousPath = Environment.currentDirectory + File.separator + "randomDirectory";
		File testFile = new File(previousPath);
		testFile.mkdir();
		String[] args = { "randomDirectory" };

		cdApp.run(args, input, output);

		assertEquals(previousPath, Environment.currentDirectory);

		testFile.delete();
	}
	
	@Test
	public void testCdDirectoryRelLocal() throws CdException {
		String previousPath = Environment.currentDirectory + File.separator + "randomDirectory";
		File testFile = new File(previousPath);
		testFile.mkdir();
		String[] args = { "." + File.separator + "randomDirectory" };

		cdApp.run(args, input, output);

		assertEquals(previousPath, Environment.currentDirectory);

		testFile.delete();
	}
	
	//Assume that previous directory exists
	@Test
	public void testCdDirectoryRelBack() throws CdException {
		String previousPath = Environment.currentDirectory;
		File testFile = new File(previousPath);
		testFile = new File(testFile.getParent());
		String[] args = {".."};

		cdApp.run(args, input, output);
		
		assertEquals(testFile.getAbsolutePath(), Environment.currentDirectory);
	}
	
	//Assume that previous directory exists
	@Test
	public void testCdDirectoryRelBackDir() throws CdException {
		String previousPath = Environment.currentDirectory;
		File testFile = new File(previousPath);
		testFile = new File(testFile.getParent() + File.separator + "randomDirectory" );
		testFile.mkdir();
		String[] args = { ".." + File.separator + "randomDirectory" };

		cdApp.run(args, input, output);

		assertEquals(testFile.getAbsolutePath(), Environment.currentDirectory);

		testFile.delete();
	}
	
	@Test
	public void testCdDirectoryAbsolute() throws CdException {
		String previousPath = Environment.currentDirectory + File.separator + "randomDirectory";
		File testFile = new File(previousPath);
		testFile.mkdir();
		String[] args = { previousPath };

		cdApp.run(args, input, output);

		assertEquals(previousPath, Environment.currentDirectory);

		testFile.delete();
	}

}
