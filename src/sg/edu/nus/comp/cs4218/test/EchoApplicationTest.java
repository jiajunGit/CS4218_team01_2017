/**
 * 
 */
package sg.edu.nus.comp.cs4218.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.EchoException;
import sg.edu.nus.comp.cs4218.impl.app.EchoApplication;

/**
 * @author Leon
 *
 */
public class EchoApplicationTest {
	
	//Variables for testing
	private EchoApplication echo;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Starting EchoApplication Test");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Ending EchoApplication Test");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		echo = new EchoApplication();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws EchoException {
		String[] input = {"INPUT"};
		String expected = "OUTPUT";
		echo.run(input, System.in, System.out );
	}

}
