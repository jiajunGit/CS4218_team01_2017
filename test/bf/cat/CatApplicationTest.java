/**
 * Cat JUnit Test   
 */
package bf.cat;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.impl.app.CatApplication;

public class CatApplicationTest {

	private static CatApplication cat;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static String PATH_SEPARATOR = File.separator;
	private final static String LINE_SEPARATOR = System.getProperty("line.separator");
	private final static String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "cat"
			+ PATH_SEPARATOR;
	private final static String TESTINPUT1 = "1" + LINE_SEPARATOR + "2" + LINE_SEPARATOR + "3" + LINE_SEPARATOR + ""
			+ LINE_SEPARATOR + "a" + LINE_SEPARATOR + "B" + LINE_SEPARATOR + "7" + LINE_SEPARATOR + "*" + LINE_SEPARATOR
			+ "9" + LINE_SEPARATOR + "10" + LINE_SEPARATOR + "1%" + LINE_SEPARATOR;
	private final static String TESTINPUT2 = "alice" + LINE_SEPARATOR + "bob" + LINE_SEPARATOR + "eve" + LINE_SEPARATOR
			+ "mallory" + LINE_SEPARATOR + "" + LINE_SEPARATOR + "" + LINE_SEPARATOR + "end of file" + LINE_SEPARATOR;
	private final static String TESTINPUT3 = "this" + LINE_SEPARATOR + "is" + LINE_SEPARATOR + "the" + LINE_SEPARATOR
			+ "3rd" + LINE_SEPARATOR + "test" + LINE_SEPARATOR + "input" + LINE_SEPARATOR;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		cat = new CatApplication();
		System.setOut(new PrintStream(outContent));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.setOut(null);
		cat = null;
	}

	@Test
	public void testNullPointerStdinException() throws CatException {
		try {
			cat.run(null, null, System.out);
		} catch (CatException e) {
			assertEquals("cat: Null Pointer Exception", e.getMessage());
		}
	}

	@Test
	public void testNullPointerStdoutException() throws CatException {
		try {
			cat.run(null, System.in, null);
		} catch (CatException e) {
			assertEquals("cat: Null Pointer Exception", e.getMessage());
		}
	}

	@Test
	public void testPrintFromStdinWithNullArg() throws CatException {
		ByteArrayInputStream in = new ByteArrayInputStream(TESTINPUT1.getBytes());
		System.setIn(in);
		cat.run(null, System.in, System.out);
		assertEquals(TESTINPUT1, outContent.toString());
	}

	@Test
	public void testPrintFromStdinWithEmptyArg() throws CatException {
		ByteArrayInputStream in = new ByteArrayInputStream(TESTINPUT1.getBytes());
		String[] arg = {};
		System.setIn(in);
		cat.run(arg, System.in, System.out);
		assertEquals(TESTINPUT1, outContent.toString());
	}

	@Test
	public void testInvalidFilePathException() throws CatException {
		String[] arg = { "this is a non directory" };
		try {
			cat.run(arg, System.in, System.out);
		} catch (CatException e) {
			assertEquals("cat: Could not read file", e.getMessage());
		}
	}

	@Test
	public void testIsFileDirectoryException() throws CatException {
		String[] arg = { RELATIVE_TEST_DIRECTORY + "input" };
		try {
			cat.run(arg, System.in, System.out);
		} catch (CatException e) {
			assertEquals("cat: This is a directory", e.getMessage());
		}
	}

	@Test
	public void testSingleFileRead() throws CatException {
		String[] input = { RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "input01" };
		cat.run(input, System.in, System.out);
		assertEquals(TESTINPUT1, outContent.toString());
	}

	@Test
	public void testMultipleFileRead() throws CatException {
		String[] input = { RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "input01",
				RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "input02",
				RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "input03" };
		cat.run(input, System.in, System.out);
		assertEquals(TESTINPUT1 + TESTINPUT2 + TESTINPUT3, outContent.toString());
	}

}
