/**
 * Cat JUnit Test
 */
package sg.edu.nus.comp.cs4218.test;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	public void test() {
		fail("Not yet implemented");
	}

}
