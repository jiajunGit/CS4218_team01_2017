/**
 * Cat JUnit Test
 */
package test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.EchoException;
import sg.edu.nus.comp.cs4218.impl.app.CatApplication;

/**
 * @author Leon
 *
 */
public class CatApplicationTest {

	private static CatApplication cat;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		cat= new CatApplication();
		System.setOut(new PrintStream(outContent));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.setOut(null);
		cat=null;
	}

	@Test
	public void testNullPointerStdinException() throws EchoException {
		try{
			cat.run(null, null, System.out);
		}catch(CatException e){
			assertEquals("cat: Null Pointer Exception", e.getMessage());
		}
	}

	@Test
	public void testNullPointerStdoutException() throws EchoException {
		try{
			cat.run(null, System.in, null);
		}catch(CatException e){
			assertEquals("cat: Null Pointer Exception", e.getMessage());
		}
	}

	@Test
	public void simulateStdin() throws CatException {
		ByteArrayInputStream in = new ByteArrayInputStream("2".getBytes());
		System.setIn(in);
		cat.run(null, System.in, System.out);
		assertEquals("2",outContent.toString());
	}

}
