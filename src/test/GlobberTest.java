package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Symbol;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.Globber;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class GlobberTest {

	private static Globber globber;

	private static String absTestDirPath;
	private static String relativeDirPath;
	private static String baseDirName;

	@BeforeClass
	public static void setUp() {

		globber = new Globber();
		absTestDirPath = Environment.currentDirectory + Symbol.PATH_SEPARATOR_S + "src" + Symbol.PATH_SEPARATOR_S
				+ "test" + Symbol.PATH_SEPARATOR_S + "glob";
		relativeDirPath = "src" + Symbol.PATH_SEPARATOR_S + "test" + Symbol.PATH_SEPARATOR_S + "glob";
		baseDirName = "glob";

		GlobTestHelper.setupGlobFiles(absTestDirPath);
	}

	@AfterClass
	public static void tearDown() {

		globber = null;
		absTestDirPath = "";
		relativeDirPath = "";
		baseDirName = "";
	}

	@Test
	public void testRelativePathWithConsecutiveGlobCharacters() throws ShellException {

		String input = relativeDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "271**";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 2);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "271.txt";

		assertEquals(out[0], expected);

		expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "2712";

		assertEquals(out[1], expected);
	}

	@Test
	public void testAbsolutePathWithConsecutiveGlobCharacters() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "271**";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 2);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "271.txt";

		assertEquals(out[0], expected);

		expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "2712";

		assertEquals(out[1], expected);
	}

	@Test
	public void testEmptyInput() throws ShellException {

		String inputSymbols = ShellImpl.generateSymbolString("");

		String[] out = globber.processGlob("", inputSymbols);

		assertTrue(out.length == 0);
	}

	@Test
	public void testAbsolutePathWithEndingGlobCharacter() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "271*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 2);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "271.txt";

		assertEquals(out[0], expected);

		expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "2712";

		assertEquals(out[1], expected);
	}

	@Test
	public void testRelativePathWithEndingGlobCharacter() throws ShellException {

		String input = relativeDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "271*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 2);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "271.txt";

		assertEquals(out[0], expected);

		expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "2712";

		assertEquals(out[1], expected);
	}

	@Test
	public void testRelativePathWithEndingPathSeparator() throws ShellException {

		String input = relativeDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "*12" + Symbol.PATH_SEPARATOR_S;

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 1);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "2712";

		assertEquals(out[0], expected);
	}

	@Test
	public void testRelativePathWithStartingGlobCharacter() throws ShellException {

		String input = relativeDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "*12";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 1);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "2712";

		assertEquals(out[0], expected);
	}

	@Test
	public void testRelativePathWithPreviousDirectorySymbol() throws ShellException {

		String input = relativeDirPath + Symbol.PATH_SEPARATOR_S + Symbol.PREV_DIR_S + Symbol.PATH_SEPARATOR_S
				+ baseDirName + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + Symbol.CURRENT_DIR_S
				+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "*12";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 1);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "2712";

		assertEquals(out[0], expected);
	}

	@Test
	public void testRelativePathWithCurrentDirectorySymbol() throws ShellException {

		String input = relativeDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S
				+ Symbol.CURRENT_DIR_S + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "*12";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 1);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "2712";

		assertEquals(out[0], expected);
	}

	@Test
	public void testAbsolutePathWithPreviousDirectorySymbol() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + Symbol.PREV_DIR_S + Symbol.PATH_SEPARATOR_S
				+ baseDirName + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + Symbol.CURRENT_DIR_S
				+ Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "*12";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 1);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "2712";

		assertEquals(out[0], expected);
	}

	@Test
	public void testAbsolutePathWithCurrentDirectorySymbol() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S
				+ Symbol.CURRENT_DIR_S + Symbol.PATH_SEPARATOR_S + "2712" + Symbol.PATH_SEPARATOR_S + "*12";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 1);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "2712";

		assertEquals(out[0], expected);
	}

	@Test
	public void testAbsolutePathWithEndingPathSeparator() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "*12" + Symbol.PATH_SEPARATOR_S;

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 1);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "2712";

		assertEquals(out[0], expected);
	}

	@Test
	public void testAbsolutePathWithStartingGlobCharacter() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "*12";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 1);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "2712";

		assertEquals(out[0], expected);
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

		String input = relativeDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "27'.*'";

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

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "27'.*'";

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

		String input = relativeDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "*'\\E2712\\Q'*";

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

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "*'\\E2712\\Q'*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 0);
	}

	@Test
	public void testPathWithNonMatchingCases() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + "FiLe WiTh SpAcEs" + Symbol.PATH_SEPARATOR_S + "*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		Environment.setDefaultDirectory();

		String[] expected = {
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "FiLe WiTh SpAcEs" + Symbol.PATH_SEPARATOR_S + "New folder",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "FiLe WiTh SpAcEs" + Symbol.PATH_SEPARATOR_S
						+ "file with spaces",
				absTestDirPath + Symbol.PATH_SEPARATOR_S + "FiLe WiTh SpAcEs" + Symbol.PATH_SEPARATOR_S
						+ "file with spaces.txt" };

		assertEquals(out[0], expected[0]);
		assertEquals(out[1], expected[1]);
		assertEquals(out[2], expected[2]);
	}

	@Test
	public void testPathWithSpaces() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + "file with spaces" + Symbol.PATH_SEPARATOR_S + "*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		Environment.setDefaultDirectory();

		String[] expected = {

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "file with spaces" + Symbol.PATH_SEPARATOR_S + "New folder",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "file with spaces" + Symbol.PATH_SEPARATOR_S
						+ "file with spaces",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "file with spaces" + Symbol.PATH_SEPARATOR_S
						+ "file with spaces.txt" };

		assertEquals(out[0], expected[0]);
		assertEquals(out[1], expected[1]);
		assertEquals(out[2], expected[2]);
	}

	@Test
	public void testRelativePathWithSingleGlobCharacter() throws ShellException {

		Environment.currentDirectory = absTestDirPath + Symbol.PATH_SEPARATOR_S + "file with spaces";

		String input = "*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		Environment.setDefaultDirectory();

		String[] expected = {

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "file with spaces" + Symbol.PATH_SEPARATOR_S + "New folder",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "file with spaces" + Symbol.PATH_SEPARATOR_S
						+ "file with spaces",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "file with spaces" + Symbol.PATH_SEPARATOR_S
						+ "file with spaces.txt", };

		assertEquals(out[0], expected[0]);
		assertEquals(out[1], expected[1]);
		assertEquals(out[2], expected[2]);
	}

	@Test
	public void testRelativePath() throws ShellException {

		String input = relativeDirPath + Symbol.PATH_SEPARATOR_S + "ca*" + Symbol.PATH_SEPARATOR_S + "cat"
				+ Symbol.PATH_SEPARATOR_S + "2712*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 6);

		String[] expected = {

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt" };

		assertEquals(out[0], expected[0]);
		assertEquals(out[1], expected[1]);
		assertEquals(out[2], expected[2]);
		assertEquals(out[3], expected[3]);
		assertEquals(out[4], expected[4]);
		assertEquals(out[5], expected[5]);
	}

	@Test
	public void testMultipleDirectoriesInMultipleDirectories() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + "ca*" + Symbol.PATH_SEPARATOR_S + "c*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 8);

		String[] expected = {

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cab",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "car",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cab",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cab",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "car",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat" };

		assertEquals(out[0], expected[0]);
		assertEquals(out[1], expected[1]);
		assertEquals(out[2], expected[2]);
		assertEquals(out[3], expected[3]);
		assertEquals(out[4], expected[4]);
		assertEquals(out[5], expected[5]);
		assertEquals(out[6], expected[6]);
		assertEquals(out[7], expected[7]);
	}

	@Test
	public void testMultipleFilesInMultipleDirectories() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + "ca*" + Symbol.PATH_SEPARATOR_S + "cat"
				+ Symbol.PATH_SEPARATOR_S + "2712*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 6);

		String[] expected = {

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cab" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "car" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712",

				absTestDirPath + Symbol.PATH_SEPARATOR_S + "cat" + Symbol.PATH_SEPARATOR_S + "cat"
						+ Symbol.PATH_SEPARATOR_S + "2712.txt" };

		assertEquals(out[0], expected[0]);
		assertEquals(out[1], expected[1]);
		assertEquals(out[2], expected[2]);
		assertEquals(out[3], expected[3]);
		assertEquals(out[4], expected[4]);
		assertEquals(out[5], expected[5]);
	}

	@Test
	public void testMultipleDirectories() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "ca*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 3);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cab";

		assertEquals(out[0], expected);

		expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "car";

		assertEquals(out[1], expected);

		expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "cat";

		assertEquals(out[2], expected);
	}

	@Test
	public void testSingleDirectory() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-ca*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 1);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "-carr";

		assertEquals(out[0], expected);
	}

	@Test
	public void testInvalidCharactersInPath() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + ":(-.-):" + Symbol.PATH_SEPARATOR_S + "-.-"
				+ Symbol.PATH_SEPARATOR_S + Symbol.PATH_SEPARATOR_S + "c*r*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 0);
	}

	@Test
	public void testNonExistentPath() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + "(-.-)" + Symbol.PATH_SEPARATOR_S + "-.-"
				+ Symbol.PATH_SEPARATOR_S + Symbol.PATH_SEPARATOR_S + "c*r*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 0);
	}

	@Test
	public void testMultiplePathSeparator() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
				+ Symbol.PATH_SEPARATOR_S + Symbol.PATH_SEPARATOR_S + Symbol.PATH_SEPARATOR_S + "c*r*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 3);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
				+ Symbol.PATH_SEPARATOR_S + "car";

		assertEquals(out[0], expected);

		expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
				+ Symbol.PATH_SEPARATOR_S + "car.txt";

		assertEquals(out[1], expected);

		expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
				+ Symbol.PATH_SEPARATOR_S + "carrier";

		assertEquals(out[2], expected);
	}

	@Test
	public void testMultipleFileGlob() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
				+ Symbol.PATH_SEPARATOR_S + "c*r*";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 3);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
				+ Symbol.PATH_SEPARATOR_S + "car";

		assertEquals(out[0], expected);

		expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
				+ Symbol.PATH_SEPARATOR_S + "car.txt";

		assertEquals(out[1], expected);

		expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
				+ Symbol.PATH_SEPARATOR_S + "carrier";

		assertEquals(out[2], expected);
	}

	@Test
	public void testMultipleFilesInSingleDirectory() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
				+ Symbol.PATH_SEPARATOR_S + "c*r";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 2);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
				+ Symbol.PATH_SEPARATOR_S + "car";

		assertEquals(out[0], expected);

		expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + "-.-" + Symbol.PATH_SEPARATOR_S + "-.-"
				+ Symbol.PATH_SEPARATOR_S + "carrier";

		assertEquals(out[1], expected);
	}

	@Test
	public void testSingleFile() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "27*2";

		String inputSymbols = ShellImpl.generateSymbolString(input);

		String[] out = globber.processGlob(input, inputSymbols);

		assertTrue(out.length == 1);

		String expected = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "2712";

		assertEquals(out[0], expected);
	}

	@Test
	public void testNullInputSymbol() throws ShellException {

		String input = absTestDirPath + Symbol.PATH_SEPARATOR_S + ".cab.car" + Symbol.PATH_SEPARATOR_S + "2712"
				+ Symbol.PATH_SEPARATOR_S + "27*2";

		String[] out = globber.processGlob(input, null);

		assertTrue(out.length == 0);
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
