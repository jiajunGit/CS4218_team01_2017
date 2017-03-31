package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.Globber;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class GlobberTest {

	private static Globber globber;

	private static final String PATH_SEPARATOR = File.separator;
	private static final String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "glob";
	private static final String ABSOLUTE_TEST_DIRECTORY = Environment.currentDirectory + PATH_SEPARATOR + RELATIVE_TEST_DIRECTORY;
	private static final String SECOND_ABSOLUTE_TEST_DIRECTORY = Environment.currentDirectory + PATH_SEPARATOR + "src";
	
	@BeforeClass
	public static void setUp() {
		globber = new Globber();
	}

	@AfterClass
	public static void tearDown() {
		globber = null;
	}

	@Test
	public void testEmptyInput() throws ShellException {

		String inputSymbols = ShellImpl.generateSymbolString("");

		String[] out = globber.processGlob("", inputSymbols);

		assertTrue(out.length == 0);
	}

	/**
	 * Tests if the developer have sanitized the glob string before using it to
	 * construct the regex pattern used to match file/directory names. Relative
	 * path is used.
	 * 
	 * Should not match any file or directories
	 */
	@Test
	public void testRelativePathWithSpecialCharacters() throws ShellException {

		String input = RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
					 + PATH_SEPARATOR + "27'.*'";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 0);
	}

	/**
	 * Tests if the developer have sanitized the glob string before using it to
	 * construct the regex pattern used to match file/directory names. Absolute
	 * path is used.
	 * 
	 * Should not match any file or directories
	 */
	@Test
	public void testAbsolutePathWithSpecialCharacters() throws ShellException {

		String input = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
					 + PATH_SEPARATOR + "27'.*'";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 0);
	}

	/**
	 * Tests if the developer have sanitized characters used to sanitize the
	 * glob string before using it to construct the regex pattern used to match
	 * file/directory names. Relative path is used.
	 * 
	 * Should not match any file or directories
	 */
	@Test
	public void testRelativePathWithEscapeCharacters() throws ShellException {

		String input = RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
					 + PATH_SEPARATOR + "*'\\E2712\\Q'*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 0);
	}

	/**
	 * Tests if the developer have sanitized characters used to sanitize the
	 * glob string before using it to construct the regex pattern used to match
	 * file/directory names. Absolute path is used.
	 * 
	 * Should not match any file or directories
	 */
	@Test
	public void testAbsolutePathWithEscapeCharacters() throws ShellException {

		String input = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
					 + PATH_SEPARATOR + "*'\\E2712\\Q'*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 0);
	}

	@Test
	public void testInvalidCharactersInPath() throws ShellException {

		String input = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + ":(-.-):" + PATH_SEPARATOR + "-.-"
					 + PATH_SEPARATOR + PATH_SEPARATOR + "c*r*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 0);
	}

	@Test
	public void testNonExistentPath() throws ShellException {

		String input = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "(-.-)" + PATH_SEPARATOR + "-.-" 
					 + PATH_SEPARATOR + PATH_SEPARATOR + "c*r*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 0);
	}

	@Test
	public void testNullInputSymbol() throws ShellException {

		String input = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712" 
					 + PATH_SEPARATOR + "27*2";

		String[] out = globber.processGlob(input, null);

		assertTrue(out.length == 0);
	}
	
	@Test
	public void testSingleFile() throws ShellException {
		
		String input = RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "." + PATH_SEPARATOR + "*";
		String expected = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "README.md";
		
		String inputSymbols = ShellImpl.generateSymbolString(input);
		String[] out = globber.processGlob(input, inputSymbols);
		
		assertTrue(out.length == 1);
		
		assertEquals( expected, out[0] );
	}

	@Test
	public void testMultipleFiles() throws ShellException {
		
		String input = RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + ".." + PATH_SEPARATOR + ".." + PATH_SEPARATOR + "*";
		
		String []expected = { SECOND_ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "sg",
							  SECOND_ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "test" };
		
		String inputSymbols = ShellImpl.generateSymbolString(input);
		String[] out = globber.processGlob(input, inputSymbols);
		
		assertTrue(out.length == 2);
		
		assertEquals( expected[0], out[0] );
		assertEquals( expected[1], out[1] );
	}
	
	@Test
	public void testNullInput() throws ShellException {

		String inputSymbols = ShellImpl.generateSymbolString(null);

		String[] out = globber.processGlob(null, inputSymbols);

		assertTrue(out.length == 0);
	}

	@Test
	public void testNullArguments() throws ShellException {
		String[] out = globber.processGlob(null, null);
		assertTrue(out.length == 0);
	}
}
