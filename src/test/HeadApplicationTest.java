/**
 * HeadApplication Test
 */
package test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.impl.app.HeadApplication;

public class HeadApplicationTest {
	private static HeadApplication head;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static String PATH_SEPARATOR = File.separator;
	private final static String LINE_SEPARATOR = System.getProperty("line.separator");
	private final static String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "head"
			+ PATH_SEPARATOR;
	private final static String ABSOLUTE_TEST_DIRECTORY = new File(RELATIVE_TEST_DIRECTORY).getAbsolutePath()
			+ PATH_SEPARATOR;
	private final static String TEST_STRING_11_LINES = "1" + LINE_SEPARATOR + "2" + LINE_SEPARATOR + "3"
			+ LINE_SEPARATOR + "4" + LINE_SEPARATOR + "5" + LINE_SEPARATOR + "6" + LINE_SEPARATOR + "7" + LINE_SEPARATOR
			+ "8" + LINE_SEPARATOR + "9" + LINE_SEPARATOR + "10" + LINE_SEPARATOR + "11";

	private final static String TEST_STRING_10_LINES = "1" + LINE_SEPARATOR + "2" + LINE_SEPARATOR + "3"
			+ LINE_SEPARATOR + "4" + LINE_SEPARATOR + "5" + LINE_SEPARATOR + "6" + LINE_SEPARATOR + "7" + LINE_SEPARATOR
			+ "8" + LINE_SEPARATOR + "9" + LINE_SEPARATOR + "10";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		head = new HeadApplication();
		System.setOut(new PrintStream(outContent));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.setOut(null);
		head = null;
	}

	// Test section for I/O validation
	@Test(expected = HeadException.class)
	public void testEmptyStdinWithNullArgException() throws HeadException {
		head.run(null, null, System.out);
	}

	@Test(expected = HeadException.class)
	public void testEmptyStdinWith1ArgException() throws HeadException {
		String[] arg = {};
		head.run(arg, null, System.out);
	}

	@Test(expected = HeadException.class)
	public void testEmptyStdinWith2ArgException() throws HeadException {
		String[] arg = { "-n", "10" };
		head.run(arg, null, System.out);
	}

	@Test(expected = HeadException.class)
	public void testEmptyStdinWith3ArgException() throws HeadException {
		String[] arg = { "-n", "10", "" };
		head.run(arg, null, System.out);
	}

	@Test(expected = HeadException.class)
	public void testEmptyStdoutWithNullArgException() throws HeadException {
		head.run(null, System.in, null);
	}

	@Test(expected = HeadException.class)
	public void testEmptyStdoutWith1ArgException() throws HeadException {
		String[] arg = {};
		head.run(arg, System.in, null);
	}

	// Test Section for File validation
	@Test(expected = HeadException.class)
	public void testInvalidFileFormatDefaultException() throws HeadException {
		String[] arg = { "INVALID_FILE" };
		head.run(arg, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void testValidRelativeFilePathNonExistentFileDefaultException() throws HeadException {
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "invalidfile";
		String[] arg = { file };
		head.run(arg, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void testValidAbsoluteFilePathNonExistentFileDefaultException() throws HeadException {
		String file = ABSOLUTE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "invalidfile";
		String[] arg = { file };
		head.run(arg, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void testInvalidFileFormatNonDefaultException() throws HeadException {
		String[] arg = { "-n", "10", "INVALID_FILE" };
		head.run(arg, System.in, System.out);
	}

	// Test section for input validation
	@Test(expected = HeadException.class)
	public void testInvalidLineNumException2Arg() throws HeadException {
		String[] arg = { "-n", "xx" };
		head.run(arg, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void testInvalidLineNumException3Arg() throws HeadException {
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
		String[] arg = { "-n", "xx", file };
		head.run(arg, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void testNoLineNum2ArgException() throws HeadException {
		String[] arg = { "-n", "" };
		head.run(arg, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void testNoLineNum3ArgException() throws HeadException {
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
		String[] arg = { "-n", "", file };
		head.run(arg, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void testIntegerOverflowException() throws HeadException {
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";

		String[] arg = { "-n", "2947483647", file }; // maxint=2147483647
		head.run(arg, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void testPrintFromFileWithNegativeNumOfLinesException() throws HeadException {
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
		String[] arg = { "-n", "-10", file };
		head.run(arg, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void testPrintFromFileWithMissingOptionException() throws HeadException {
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
		String[] arg = { "", "-10", file };
		head.run(arg, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void testPrintFromStdinWithNegativeNumOfLines2ArgException() throws HeadException {
		String[] arg = { "-n", "-10" };
		head.run(arg, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void testPrintFromStdinWithInvalidLines2ArgException() throws HeadException {
		String[] arg = { "-n", "+10" };
		head.run(arg, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void test1ArgumentsWithInvalidOptionException() throws HeadException {
		String[] arg = { "-" };
		head.run(arg, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void test2ArgumentsWithInvalidOptionsException() throws HeadException {
		String[] arg = { "-x", "5" };
		head.run(arg, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void test3ArgumentsWithInvalidOptionsException() throws HeadException {
		String[] arg = { "-x", "5", "INVALIDFILE" };
		head.run(arg, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void testExcessArgException() throws HeadException {
		String[] arg = { "-n", "10", "EXCESS01", "EXCESS02" };
		head.run(arg, System.in, System.out);
	}

	// Test application print from stdin behaviour
	@Test
	public void testPrintFromStdinWithNullArg() throws HeadException {
		String testStdinInput = TEST_STRING_11_LINES;
		String expectedOutput = TEST_STRING_10_LINES;
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		head.run(null, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString());
	}

	@Test
	public void testPrintFromStdinWithEmptyArg() throws HeadException {
		String[] arg = {};
		String testStdinInput = TEST_STRING_11_LINES;
		String expectedOutput = TEST_STRING_10_LINES;
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString());
	}

	@Test
	public void testPrintFromStdinWith1EmptyArg() throws HeadException {
		String[] arg = { "" };
		String testStdinInput = TEST_STRING_11_LINES;
		String expectedOutput = TEST_STRING_10_LINES;
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString());
	}

	@Test
	public void testPrintFromStdinWith2EmptyArg() throws HeadException {
		String[] arg = { "", "" };
		String testStdinInput = TEST_STRING_11_LINES;
		String expectedOutput = TEST_STRING_10_LINES;
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString());
	}

	@Test
	public void testPrintFrom3EmptyArgException() throws HeadException {
		String[] arg = { "", "", "" };
		String testStdinInput = TEST_STRING_11_LINES;
		String expectedOutput = TEST_STRING_10_LINES;
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString());
	}

	@Test
	public void testPrintFromStdinWithOptions2Args() throws HeadException {
		String testStdinInput = TEST_STRING_11_LINES;
		String expectedOutput = TEST_STRING_10_LINES;
		String[] arg = { "-n", "10" };
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString());
	}

	@Test
	public void testPrintFromStdinMoreThanMaxLines2Args() throws HeadException {
		String testStdinInput = TEST_STRING_10_LINES;
		String expectedOutput = TEST_STRING_10_LINES;
		String[] arg = { "-n", "99" };
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString());
	}

	@Test
	public void testPrintFromStdinWithOptions3Args() throws HeadException {
		String testStdinInput = TEST_STRING_11_LINES;
		String expectedOutput = TEST_STRING_10_LINES;
		String[] arg = { "-n", "10", "" };
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString());
	}

	@Test
	public void testPrintFromStdinMoreThanMaxLines3Args() throws HeadException {
		String testStdinInput = TEST_STRING_10_LINES;
		String expectedOutput = TEST_STRING_10_LINES;
		String[] arg = { "-n", "99", "" };
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString());
	}

	// Test application print from file behaviour
	@Test
	public void testPrintFromFile1Arg() throws HeadException {
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
		String[] arg = { file };
		String expectedOutput = TEST_STRING_10_LINES;
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString());
	}

	@Test
	public void testPrintFromFile2Arg() throws HeadException {
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
		String[] arg = { "", file };
		String expectedOutput = TEST_STRING_10_LINES;
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString());
	}

	@Test
	public void testPrintFromFile3Arg() throws HeadException {
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
		String[] arg = { "", "", file };
		String expectedOutput = TEST_STRING_10_LINES;
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString());
	}

	@Test
	public void testPrintFromFileNLines() throws HeadException {
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
		String[] arg = { "-n", "10", file };
		String expectedOutput = TEST_STRING_10_LINES;
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString());
	}

	@Test
	public void testPrintFromFileMoreThanMaxLines() throws HeadException {
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
		String[] arg = { "-n", "99", file };
		String expectedOutput = TEST_STRING_11_LINES;
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString());
	}
}
