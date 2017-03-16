/**
 * PWD application test
 */
package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.PwdException;
import sg.edu.nus.comp.cs4218.impl.app.PwdApplication;

public class PwdApplicationTest {
	private static PwdApplication pwd;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static String LINE_SEPARATOR = System.getProperty("line.separator");
	private final static String CURRENT_DIRECTORY = new File(Environment.currentDirectory).getAbsolutePath();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pwd = new PwdApplication();
		System.setOut(new PrintStream(outContent));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.setOut(null);
		pwd = null;
	}

	@Test
	public void testEmptyStdoutException() {
		try {
			pwd.run(null, System.in, null);
		} catch (PwdException e) {
			assertEquals("pwd: OutputStream not provided", e.getMessage());
		}
	}

	@Test
	public void testDirectoryReporting() throws PwdException {
		pwd.run(null, System.in, System.out);
		String file = CURRENT_DIRECTORY;
		assertEquals(file, outContent.toString());
	}

}
