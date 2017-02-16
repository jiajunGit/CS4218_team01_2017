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
	private final static String testString11lines = "1" +  LINE_SEPARATOR +
			"2" + LINE_SEPARATOR +
			"3" + LINE_SEPARATOR +
			"4" + LINE_SEPARATOR +
			"5" + LINE_SEPARATOR +
			"6" + LINE_SEPARATOR +
			"7" + LINE_SEPARATOR +
			"8" + LINE_SEPARATOR +
			"9" + LINE_SEPARATOR +
			"10" + LINE_SEPARATOR +
			"11" + LINE_SEPARATOR;

	private final static String testString10lines= "1" +  LINE_SEPARATOR +
			"2" + LINE_SEPARATOR +
			"3" + LINE_SEPARATOR +
			"4" + LINE_SEPARATOR +
			"5" + LINE_SEPARATOR +
			"6" + LINE_SEPARATOR +
			"7" + LINE_SEPARATOR +
			"8" + LINE_SEPARATOR +
			"9" + LINE_SEPARATOR +
			"10" + LINE_SEPARATOR;
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

	@Test
	public void testEmptyStdinWithNullArgException() throws HeadException {
		try{
			head.run(null, null, System.out);
		}catch(HeadException e){
			assertEquals("head:InputStream not provided",e.getMessage());
		}
	}

	@Test
	public void testEmptyStdoutWithNullArgException() throws HeadException {
		try{
			head.run(null, System.in, null);
		}catch(HeadException e){
			assertEquals("head:OutputStream not provided",e.getMessage());
		}
	}

	@Test
	public void testEmptyStdoutWithValidArgException() throws HeadException {
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
		String[] arg = {"-n","10", file};
		try{
			head.run(arg, System.in, null);
		}catch(HeadException e){
			assertEquals("head:OutputStream not provided",e.getMessage());
		}
	}

	@Test
	public void testPrintFromNonExistentFileException() throws HeadException{
		String[] arg = {"-n", "99", "INVALID_FILE"};
		try{
			head.run(arg, System.in, System.out);
		}catch(HeadException e){
			assertEquals("head:Specify a file which exists",e.getMessage());
		}
	}

	@Test
	public void testPrintFromFileWithInvalidNumOfLinesException() throws HeadException{
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
		String[] arg = {"-n", "xx", file};
		try{
			head.run(arg, System.in, System.out);
		}catch(HeadException e){
			assertEquals("head:Invalid command format",e.getMessage());
		}
	}

	@Test
	public void testPrintFromFileWithMissingNumOfLinesException() throws HeadException{
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
		String[] arg = {"-n","", file};
		try{
			head.run(arg, System.in, System.out);
		}catch(HeadException e){
			assertEquals("head:Invalid command format",e.getMessage());
		}
	}

	@Test
	public void testPrintFromFileWithEmptyOptionException() throws HeadException{
		String[] arg = {""};
		String expectedString = LINE_SEPARATOR;
		head.run(arg, System.in, System.out);
		assertEquals(expectedString,outContent.toString());
	}

	@Test
	public void testPrintFrom2EmptyArgException() throws HeadException{
		String[] arg = {"",""};
		String expectedString = LINE_SEPARATOR;
		head.run(arg, System.in, System.out);
		assertEquals(expectedString,outContent.toString());
	}
	
	@Test
	public void testPrintFrom3EmptyArgException() throws HeadException{
		String[] arg = {"","",""};
		String expectedString = LINE_SEPARATOR;
		head.run(arg, System.in, System.out);
		assertEquals(expectedString,outContent.toString());
	}
	
	@Test
	public void testExcessArgException() throws HeadException{
		String[] arg = {"-n", "10","EXCESS01", "EXCESS02"};
		try{
			head.run(arg, System.in, System.out);
		}catch(HeadException e){
			assertEquals("head:Invalid command format",e.getMessage());
		}
	}

	@Test
	public void testPrintFromFileWithNegativeNumOfLinesException() throws HeadException{
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
		String[] arg = {"-n", "-10", file};
		try{
			head.run(arg, System.in, System.out);
		}catch(HeadException e){
			assertEquals("head:Specify proper number with \"-n\" option",e.getMessage());
		}
	}

	@Test
	public void testPrintFromFileWithMaxIntNumOfLinesException() throws HeadException{
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";

		String[] arg = {"-n", "2947483647", file}; //maxint=2147483647
		try{
			head.run(arg, System.in, System.out);
		}catch(HeadException e){
			assertEquals("head:Invalid command format",e.getMessage());
		}
	}
	
	@Test
	public void test1ArgumentsWithInvalidInputException() throws HeadException{
		String[] arg = {"-x"};
		try{
			head.run(arg, System.in, System.out);
		}catch(HeadException e){
			assertEquals("head:Specify a file which exists",e.getMessage());
		}
	}
	
	@Test
	public void test2ArgumentsWithInvalidOptionsException() throws HeadException{
		String[] arg = {"-x", "5"};
		try{
			head.run(arg, System.in, System.out);
		}catch(HeadException e){
			assertEquals("head:Invalid command format",e.getMessage());
		}
	}
	
	@Test
	public void test3ArgumentsWithInvalidOptionsException() throws HeadException{
		String[] arg = {"-x", "5", "INVALIDFILE"};
		try{
			head.run(arg, System.in, System.out);
		}catch(HeadException e){
			assertEquals("head:Invalid command format",e.getMessage());
		}
	}
	
	@Test
	public void testPrintFromEmptyStdinWithEmptyArg() throws HeadException{
		String[] arg = {};
		String expectedString = LINE_SEPARATOR;
		head.run(arg, System.in, System.out);
		assertEquals(expectedString,outContent.toString());
	}


	@Test
	public void testPrintFromStdinWithEmptyArg() throws HeadException{
		String[] arg = {};
		String testStdinInput = testString11lines;
		String expectedOutput = testString10lines;
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput,outContent.toString());
	}

	@Test
	public void testPrintFromStdinNoOption() throws HeadException {
		String testStdinInput = testString11lines;
		String expectedOutput = testString10lines;
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		head.run(null, System.in, System.out);
		assertEquals(expectedOutput,outContent.toString());
	}

	@Test
	public void testPrintFromStdinNLines() throws HeadException {
		String testStdinInput = testString11lines;
		String expectedOutput = testString10lines;
		String[] arg = {"-n", "10"};
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput,outContent.toString());
	}

	@Test
	public void testPrintFromStdinMoreThanMaxLines() throws HeadException {
		String testStdinInput = testString10lines;
		String expectedOutput = testString10lines;
		String[] arg = {"-n", "99"};
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput,outContent.toString());
	}

	@Test
	public void testPrintFromFileNoOption() throws HeadException{
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
		String[] arg = {file};
		String expectedOutput = testString10lines;
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput,outContent.toString());
	}

	@Test
	public void testPrintFromFileNLines() throws HeadException{
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
		String[] arg = {"-n", "10", file};
		String expectedOutput = testString10lines;
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput,outContent.toString());
	}

	@Test
	public void testPrintFromFileMoreThanMaxLines() throws HeadException{
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testHead11Lines";
		String[] arg = {"-n", "99", file};
		String expectedOutput = testString11lines;
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput,outContent.toString());
	}
}
