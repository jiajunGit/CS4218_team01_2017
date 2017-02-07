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

	//Variables for testing
	private static EchoApplication echo;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

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
		echo=null;
	}

	@Test
	public void testNullArguments() throws EchoException {
		try{
			echo.run(null, System.in, System.out);
		}catch(EchoException e){
			assertEquals("echo: Null arguments", e.getMessage());
		}
	}
	
	@Test
	public void testNullOutputStream() throws EchoException {
		String[] input = {"NON-EMPTY"};
		try{
			echo.run(input, System.in, null);
		}catch(EchoException e){
			assertEquals("echo: OutputStream not provided", e.getMessage());
		}
	}
	
	@Test
	public void testNullArgumentsAndOutputStream() throws EchoException {
		try{
			echo.run(null, System.in, null);
		}catch(EchoException e){
			assertEquals("echo: Null arguments", e.getMessage());
		}
	}
	
	@Test
	public void testSingleArgument() throws EchoException {
		String[] input = {"INPUT"};
		String expected = "INPUT \n";
		echo.run(input, System.in, System.out );
		assertEquals(expected, outContent.toString());
	}
	
	@Test
	public void testDoubleArgument() throws EchoException {
		String[] input = {"INPUT 1"," INPUT2"};
		String expected = "INPUT 1  INPUT2 \n";
		echo.run(input, System.in, System.out );
		assertEquals(expected, outContent.toString());
	}
	
	@Test
	public void testEmptyArgument() throws EchoException {
		String[] input = {""};
		String expected = " \n";  //not sure if this is the requirement 
		echo.run(input, System.in, System.out );
		assertEquals(expected, outContent.toString());
	}
	/*@Test
	 *Not sure if should be handled by quoting
	public void testSingleQuotedArgument() throws EchoException {
		String[] input = {"'INPUT: should ignore all special characters %^$#@!*&'"};
		String expected = "INPUT: should ignore all special characters %^$#@!*&\n";
		echo.run(input, System.in, System.out );
		assertEquals(expected, outContent.toString());
	}*/

}
