/**
 * Tail Application test
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
import sg.edu.nus.comp.cs4218.exception.TailException;
import sg.edu.nus.comp.cs4218.impl.app.TailApplication;

public class TailApplicationTest {
	private static TailApplication tail;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static String PATH_SEPARATOR = File.separator;
	private final static String LINE_SEPARATOR = System.getProperty("line.separator");
	private final static String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "tail"
			+ PATH_SEPARATOR;
	private final static String ABSOLUTE_TEST_DIRECTORY = new File(RELATIVE_TEST_DIRECTORY).getAbsolutePath() + PATH_SEPARATOR;
	private final static String TESTSTRING11LINES = "1" +  LINE_SEPARATOR +
			"2" + LINE_SEPARATOR +
			"3" + LINE_SEPARATOR +
			"4" + LINE_SEPARATOR +
			"5" + LINE_SEPARATOR +
			"6" + LINE_SEPARATOR +
			"7" + LINE_SEPARATOR +
			"8" + LINE_SEPARATOR +
			"9" + LINE_SEPARATOR +
			"10" + LINE_SEPARATOR +
			"11";

	private final static String TESTSTRING10LINES= "2" + LINE_SEPARATOR +
			"3" + LINE_SEPARATOR +
			"4" + LINE_SEPARATOR +
			"5" + LINE_SEPARATOR +
			"6" + LINE_SEPARATOR +
			"7" + LINE_SEPARATOR +
			"8" + LINE_SEPARATOR +
			"9" + LINE_SEPARATOR +
			"10"+ LINE_SEPARATOR +
			"11";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		tail = new TailApplication();
		System.setOut(new PrintStream(outContent));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.setOut(null);
		tail = null;
	}
	
	//Test section for I/O validation
		@Test(expected=TailException.class)
		public void testEmptyStdinWithNullArgException() throws TailException {
			tail.run(null, null, System.out);
		}

		@Test(expected=TailException.class)
		public void testEmptyStdinWith1ArgException() throws TailException {
			String[] arg={};
			tail.run(arg, null, System.out);
		}

		@Test(expected=TailException.class)
		public void testEmptyStdinWith2ArgException() throws TailException {
			String[] arg={"-n","10"};
			tail.run(arg, null, System.out);
		}

		@Test(expected=TailException.class)
		public void testEmptyStdinWith3ArgException() throws TailException {
			String[] arg={"-n","10",""};
			tail.run(arg, null, System.out);
		}

		@Test(expected=TailException.class)
		public void testEmptyStdoutWithNullArgException() throws TailException {
			tail.run(null, System.in, null);
		}

		@Test(expected=TailException.class)
		public void testEmptyStdoutWith1ArgException() throws TailException {
			String[] arg = {};
			tail.run(arg, System.in, null);
		}

		//Test Section for File validation
		@Test(expected=TailException.class)
		public void testInvalidFileFormatDefaultException() throws TailException{
			String[] arg = {"INVALID_FILE"};
			tail.run(arg, System.in, System.out);
		}

		@Test(expected=TailException.class)
		public void testValidRelativeFilePathNonExistentFileDefaultException() throws TailException{
			String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "invalidfile";
			String[] arg = {file};
			tail.run(arg, System.in, System.out);
		}

		@Test(expected=TailException.class)
		public void testValidAbsoluteFilePathNonExistentFileDefaultException() throws TailException{
			String file = ABSOLUTE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "invalidfile";
			String[] arg = {file};
			tail.run(arg, System.in, System.out);
		}

		@Test(expected=TailException.class)
		public void testInvalidFileFormatNonDefaultException() throws TailException{
			String[] arg = {"-n", "10", "INVALID_FILE"};
			tail.run(arg, System.in, System.out);
		}

		//Test section for input validation
		@Test(expected=TailException.class)
		public void testInvalidLineNumException2Arg() throws TailException{
			String[] arg = {"-n", "xx"};
			tail.run(arg, System.in, System.out);
		}
		
		@Test(expected=TailException.class)
		public void testInvalidLineNumException3Arg() throws TailException{
			String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
			String[] arg = {"-n", "xx", file};
			tail.run(arg, System.in, System.out);
		}

		@Test(expected=TailException.class)
		public void testNoLineNum2ArgException() throws TailException{
			String[] arg = {"-n",""};
			tail.run(arg, System.in, System.out);
		}
		
		@Test(expected=TailException.class)
		public void testNoLineNum3ArgException() throws TailException{
			String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
			String[] arg = {"-n","", file};
			tail.run(arg, System.in, System.out);
		}

		@Test(expected=TailException.class)
		public void testIntegerOverflowException() throws TailException{
			String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";

			String[] arg = {"-n", "2947483647", file}; //maxint=2147483647
			tail.run(arg, System.in, System.out);
		}

		@Test(expected=TailException.class)
		public void testPrintFromFileWithNegativeNumOfLinesException() throws TailException{
			String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
			String[] arg = {"-n", "-10", file};
			tail.run(arg, System.in, System.out);
		}
		
		@Test(expected=TailException.class)
		public void testPrintFromFileWithMissingOptionException() throws TailException{
			String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
			String[] arg = {"", "-10", file};
			tail.run(arg, System.in, System.out);
		}
		
		@Test(expected=TailException.class)
		public void testPrintFromStdinWithNegativeNumOfLines2ArgException() throws TailException{
			String[] arg = {"-n", "-10"};
			tail.run(arg, System.in, System.out);
		}
		
		@Test(expected=TailException.class)
		public void testPrintFromStdinWithInvalidLines2ArgException() throws TailException{
			String[] arg = {"-n", "+10"};
			tail.run(arg, System.in, System.out);
		}
		
		@Test(expected=TailException.class)
		public void test1ArgumentsWithInvalidOptionException() throws TailException{
			String[] arg = {"-"};
			tail.run(arg, System.in, System.out);
		}

		@Test(expected=TailException.class)
		public void test2ArgumentsWithInvalidOptionsException() throws TailException{
			String[] arg = {"-x", "5"};
			tail.run(arg, System.in, System.out);
		}
		
		@Test(expected=TailException.class)
		public void test3ArgumentsWithInvalidOptionsException() throws TailException{
			String[] arg = {"-x", "5", "INVALIDFILE"};
			tail.run(arg, System.in, System.out);
		}
		
		@Test(expected=TailException.class)
		public void testExcessArgException() throws TailException{
			String[] arg = {"-n", "10","EXCESS01", "EXCESS02"};
			tail.run(arg, System.in, System.out);
		}

		//Test application print from stdin behaviour
		@Test
		public void testPrintFromStdinWithNullArg() throws TailException {
			String testStdinInput = TESTSTRING11LINES;
			String expectedOutput = TESTSTRING10LINES;
			ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
			System.setIn(in);
			tail.run(null, System.in, System.out);
			assertEquals(expectedOutput,outContent.toString());
		}
		
		@Test
		public void testPrintFromStdinWithEmptyArg() throws TailException{
			String[] arg = {};
			String testStdinInput = TESTSTRING11LINES;
			String expectedOutput = TESTSTRING10LINES;
			ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
			System.setIn(in);
			tail.run(arg, System.in, System.out);
			assertEquals(expectedOutput,outContent.toString());
		}
		
		@Test
		public void testPrintFromStdinWith1EmptyArg() throws TailException{
			String[] arg = {""};
			String testStdinInput = TESTSTRING11LINES;
			String expectedOutput = TESTSTRING10LINES;
			ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
			System.setIn(in);
			tail.run(arg, System.in, System.out);
			assertEquals(expectedOutput,outContent.toString());
		}
		
		@Test
		public void testPrintFromStdinWith2EmptyArg() throws TailException{
			String[] arg = {"",""};
			String testStdinInput = TESTSTRING11LINES;
			String expectedOutput = TESTSTRING10LINES;
			ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
			System.setIn(in);
			tail.run(arg, System.in, System.out);
			assertEquals(expectedOutput,outContent.toString());
		}
		
		@Test
		public void testPrintFrom3EmptyArgException() throws TailException{
			String[] arg = {"","",""};
			String testStdinInput = TESTSTRING11LINES;
			String expectedOutput = TESTSTRING10LINES;
			ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
			System.setIn(in);
			tail.run(arg, System.in, System.out);
			assertEquals(expectedOutput,outContent.toString());
		}

		@Test
		public void testPrintFromStdinWithOptions2Args() throws TailException {
			String testStdinInput = TESTSTRING11LINES;
			String expectedOutput = TESTSTRING10LINES;
			String[] arg = {"-n", "10"};
			ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
			System.setIn(in);
			tail.run(arg, System.in, System.out);
			assertEquals(expectedOutput,outContent.toString());
		}

		@Test
		public void testPrintFromStdinMoreThanMaxLines2Args() throws TailException {
			String testStdinInput = TESTSTRING10LINES;
			String expectedOutput = TESTSTRING10LINES;
			String[] arg = {"-n", "99"};
			ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
			System.setIn(in);
			tail.run(arg, System.in, System.out);
			assertEquals(expectedOutput,outContent.toString());
		}
		
		@Test
		public void testPrintFromStdinWithOptions3Args() throws TailException {
			String testStdinInput = TESTSTRING11LINES;
			String expectedOutput = TESTSTRING10LINES;
			String[] arg = {"-n", "10", ""};
			ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
			System.setIn(in);
			tail.run(arg, System.in, System.out);
			assertEquals(expectedOutput,outContent.toString());
		}

		@Test
		public void testPrintFromStdinMoreThanMaxLines3Args() throws TailException {
			String testStdinInput = TESTSTRING10LINES;
			String expectedOutput = TESTSTRING10LINES;
			String[] arg = {"-n", "99", ""};
			ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
			System.setIn(in);
			tail.run(arg, System.in, System.out);
			assertEquals(expectedOutput,outContent.toString());
		}

		
		//Test application print from file behaviour
		@Test
		public void testPrintFromFile1Arg() throws TailException{
			String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testTail11Lines";
			String[] arg = {file};
			String expectedOutput = TESTSTRING10LINES;
			tail.run(arg, System.in, System.out);
			assertEquals(expectedOutput,outContent.toString());
		}
		
		@Test
		public void testPrintFromFile2Arg() throws TailException{
			String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testTail11Lines";
			String[] arg = {"",file};
			String expectedOutput = TESTSTRING10LINES;
			tail.run(arg, System.in, System.out);
			assertEquals(expectedOutput,outContent.toString());
		}
		
		@Test
		public void testPrintFromFile3Arg() throws TailException{
			String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testTail11Lines";
			String[] arg = {"","",file};
			String expectedOutput = TESTSTRING10LINES;
			tail.run(arg, System.in, System.out);
			assertEquals(expectedOutput,outContent.toString());
		}

		@Test
		public void testPrintFromFileNLines() throws TailException{
			String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testTail11Lines";
			String[] arg = {"-n", "10", file};
			String expectedOutput = TESTSTRING10LINES;
			tail.run(arg, System.in, System.out);
			assertEquals(expectedOutput,outContent.toString());
		}

		@Test
		public void testPrintFromFileMoreThanMaxLines() throws TailException{
			String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testTail11Lines";
			String[] arg = {"-n", "99", file};
			String expectedOutput = TESTSTRING11LINES;
			tail.run(arg, System.in, System.out);
			assertEquals(expectedOutput,outContent.toString());
		}

}
