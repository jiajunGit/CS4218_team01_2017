/**
 * Echo JUnit Testing
 */
package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sg.edu.nus.comp.cs4218.exception.EchoException;
import sg.edu.nus.comp.cs4218.impl.app.EchoApplication;

/**
 * @author Leon
 *
 */
public class EchoApplicationTest {

	// Variables for testing
	private static EchoApplication echo;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		echo = new EchoApplication();
		System.setOut(new PrintStream(outContent));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.setOut(null);
		echo = null;
	}

	@Test
	public void testNullArguments() throws EchoException {
		try {
			echo.run(null, System.in, System.out);
		} catch (EchoException e) {
			assertEquals("echo: Null arguments", e.getMessage());
		}
	}

	@Test
	public void testNullOutputStream() throws EchoException {
		String[] input = { "NON-EMPTY" };
		try {
			echo.run(input, System.in, null);
		} catch (EchoException e) {
			assertEquals("echo: OutputStream not provided", e.getMessage());
		}
	}

	@Test
	public void testNullArgumentsAndOutputStream() throws EchoException {
		try {
			echo.run(null, System.in, null);
		} catch (EchoException e) {
			assertEquals("echo: Null arguments", e.getMessage());
		}
	}

	@Test
	public void testSingleArgument() throws EchoException {
		String[] input = { "INPUT" };
		String expected = "INPUT";
		echo.run(input, System.in, System.out);
		assertEquals(expected, outContent.toString());
	}

	@Test
	public void testDoubleArgument() throws EchoException {
		String[] input = { "INPUT 1", " INPUT2" };
		String expected = "INPUT 1  INPUT2";
		echo.run(input, System.in, System.out);
		assertEquals(expected, outContent.toString());
	}

	@Test
	public void testEmptyArgument() throws EchoException {
		String[] input = { "" };
		String expected = ""; // not sure if this is the requirement
		echo.run(input, System.in, System.out);
		assertEquals(expected, outContent.toString());
	}
}
