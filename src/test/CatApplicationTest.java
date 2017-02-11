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
import sg.edu.nus.comp.cs4218.impl.app.CatApplication;

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
	public void testNullPointerStdinException() throws CatException {
		try{
			cat.run(null, null, System.out);
		}catch(CatException e){
			assertEquals("cat: Null Pointer Exception", e.getMessage());
		}
	}

	@Test
	public void testNullPointerStdoutException() throws CatException {
		try{
			cat.run(null, System.in, null);
		}catch(CatException e){
			assertEquals("cat: Null Pointer Exception", e.getMessage());
		}
	}

	@Test
	public void testEmptyArgWithSimulatedStdin() throws CatException {
		ByteArrayInputStream in = new ByteArrayInputStream("2".getBytes());
		System.setIn(in);
		cat.run(null, System.in, System.out);
		assertEquals("2",outContent.toString());
	}
	
	@Test
	public void testInvalidFilePathException() throws CatException {
		String[] input ={"this is a non directory"};
		try{
			cat.run(input, System.in, System.out);
		}catch(CatException e){
			assertEquals("cat: Could not read file", e.getMessage());
		}
	}
	
	@Test
	public void testIsFileDirectoryException () throws CatException {
		String[] input ={"/"};
		try{
			cat.run(input, System.in, System.out);
		}catch(CatException e){
			assertEquals("cat: This is a directory", e.getMessage());
		}
	}
	
	@Test
	public void testValidFilePath () throws CatException {
		String[] input ={"txt/test"};
		cat.run(input, System.in, System.out);
		assertEquals("a simple test", outContent.toString());
	}

}
