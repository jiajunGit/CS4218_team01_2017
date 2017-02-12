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

	private final static String testString10lines= "2" + LINE_SEPARATOR +
			"3" + LINE_SEPARATOR +
			"4" + LINE_SEPARATOR +
			"5" + LINE_SEPARATOR +
			"6" + LINE_SEPARATOR +
			"7" + LINE_SEPARATOR +
			"8" + LINE_SEPARATOR +
			"9" + LINE_SEPARATOR +
			"10"+ LINE_SEPARATOR +
			"11"+ LINE_SEPARATOR;

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

	@Test
	public void testEmptyStdinWithNullArgException() throws TailException {
		try{
			tail.run(null, null, System.out);
		}catch(TailException e){
			assertEquals("tail:InputStream not provided",e.getMessage());
		}
	}
	
	@Test
	public void testEmptyStdoutWithNullArgException() throws TailException {
		try{
			tail.run(null, System.in, null);
		}catch(TailException e){
			assertEquals("tail:OutputStream not provided",e.getMessage());
		}
	}
	
	@Test
	public void testEmptyStdoutWithValidArgException() throws TailException {
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testTail11Lines";
		String[] arg = {"-n 10", file};
		try{
			tail.run(arg, System.in, null);
		}catch(TailException e){
			assertEquals("tail:OutputStream not provided",e.getMessage());
		}
	}
	
	@Test
	public void testPrintFromInvalidFileException() throws TailException{
		String[] arg = {"-n 99", "INVALID_FILE"};
		try{
			tail.run(arg, System.in, System.out);
		}catch(TailException e){
			assertEquals("tail:Specify a file which exists",e.getMessage());
		}
	}
	
	@Test
	public void testPrintFromFileWithInvalidNumOfLinesException() throws TailException{
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testTail11Lines";
		String[] arg = {"-n xx", file};
		try{
			tail.run(arg, System.in, System.out);
		}catch(TailException e){
			assertEquals("tail:Specify proper number with \"-n\" option",e.getMessage());
		}
	}
	
	@Test
	public void testPrintFromFileWithNegativeNumOfLinesException() throws TailException{
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testTail11Lines";
		String[] arg = {"-n -10", file};
		try{
			tail.run(arg, System.in, System.out);
		}catch(TailException e){
			assertEquals("tail:Specify proper number with \"-n\" option",e.getMessage());
		}
	}
	
	@Test
	public void testPrintFromFileWithMaxIntNumOfLinesException() throws TailException{
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testTail11Lines";
		
		String[] arg = {"-n 2947483647", file}; //maxint=2147483647
		try{
			tail.run(arg, System.in, System.out);
		}catch(TailException e){
			assertEquals("tail:Specify proper number with \"-n\" option",e.getMessage());
		}
	}
	
	@Test
	public void testInvalidOptionsException() throws TailException{
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testTail11Lines";
		String[] arg = {"INVALID", file};
		try{
			tail.run(arg, System.in, System.out);
		}catch(TailException e){
			assertEquals("tail:Invalid command format",e.getMessage());
		}
	}
	
	@Test
	public void testExcessArgException() throws TailException{
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testTail11Lines";
		String[] arg = {"-n 10", file, "EXCESS01", "EXCESS02"};
		try{
			tail.run(arg, System.in, System.out);
		}catch(TailException e){
			assertEquals("tail:Invalid command format",e.getMessage());
		}
	}
	
	@Test
	public void testPrintFromEmptyStdinWithEmptyArg() throws TailException{
		String[] arg = {};
		String expectedString = LINE_SEPARATOR;
		tail.run(arg, System.in, System.out);
		assertEquals(expectedString,outContent.toString());
	}
	
	@Test
	public void testPrintFromStdinWithEmptyArg() throws TailException{
		String[] arg = {};
		String testStdinInput = testString11lines;
		String expectedOutput = testString10lines;
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		tail.run(arg, System.in, System.out);
		assertEquals(expectedOutput,outContent.toString());
	}
	
	@Test
	public void testPrintFromStdinNoOption() throws TailException {
		String testStdinInput = testString11lines;
		String expectedOutput = testString10lines;
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		tail.run(null, System.in, System.out);
		assertEquals(expectedOutput,outContent.toString());
	}
	
	@Test
	public void testPrintFromStdinNLines() throws TailException {
		String testStdinInput = testString11lines;
		String expectedOutput = testString10lines;
		String[] arg = {"-n 10"};
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		tail.run(arg, System.in, System.out);
		assertEquals(expectedOutput,outContent.toString());
	}
	
	@Test
	public void testPrintFromStdinMoreThanMaxLines() throws TailException {
		String testStdinInput = testString10lines;
		String expectedOutput = testString10lines;
		String[] arg = {"-n 99"};
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		tail.run(arg, System.in, System.out);
		assertEquals(expectedOutput,outContent.toString());
	}
	
	@Test
	public void testPrintFromFileNoOption() throws TailException{
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testTail11Lines";
		String[] arg = {file};
		String expectedOutput = testString10lines;
		tail.run(arg, System.in, System.out);
		assertEquals(expectedOutput,outContent.toString());
	}
	
	@Test
	public void testPrintFromFileNLines() throws TailException{
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testTail11Lines";
		String[] arg = {"-n 10", file};
		String expectedOutput = testString10lines;
		tail.run(arg, System.in, System.out);
		assertEquals(expectedOutput,outContent.toString());
	}
	
	@Test
	public void testPrintFromFileMoreThanMaxLines() throws TailException{
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testTail11Lines";
		String[] arg = {"-n 99", file};
		String expectedOutput = testString11lines;
		tail.run(arg, System.in, System.out);
		assertEquals(expectedOutput,outContent.toString());
	}


}
