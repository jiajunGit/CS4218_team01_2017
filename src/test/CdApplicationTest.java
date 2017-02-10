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

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CdApplicationTest {

	private InputStream input;
	private OutputStream output;
	private static CdApplication cdApp;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cdApp = new CdApplication();
	}

	@Test(expected = CdException.class)
	public void testCdNullArgumentsException() throws CdException {
		// CdApplication cdApp = new CdApplication();

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
		cdApp = new CdApplication();
		String[] args = { "./directoryDoesNotExist" };

		cdApp.run(args, input, output);
	}

	@Test
	public void testCdDirectoryExist() throws CdException {
		cdApp = new CdApplication();
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
		cdApp = new CdApplication();
		String previousPath = Environment.currentDirectory + File.separator + "randomDirectory";
		File testFile = new File(previousPath);
		testFile.mkdir();
		String[] args = { "." + File.separator + "randomDirectory" };

		cdApp.run(args, input, output);

		assertEquals(previousPath, Environment.currentDirectory);

		testFile.delete();
	}
	
	@Test
	public void testCdDirectoryManyRelative() throws CdException {
		cdApp = new CdApplication();
		String previousPath = Environment.currentDirectory + File.separator + "randomDirectory";
		File testFile = new File(previousPath);
		testFile.mkdir();
		String[] args = { "randomDirectory" };

		cdApp.run(args, input, output);

		assertEquals(previousPath, Environment.currentDirectory);

		testFile.delete();
	}

}
