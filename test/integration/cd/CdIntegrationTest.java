package integration.cd;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Symbol;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class CdIntegrationTest {

	private static ShellImpl shell;

	private static byte[] buf;

	public static final String NEW_LINE = System.lineSeparator();
	public static final String PATH_SEPARATOR = File.separator;

	public static final String NEW_LINE_IN_EXPECTED = getNewLineInExpectedFile();

	private static final String RELATIVE_EXPECTED_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR
			+ "integration" + PATH_SEPARATOR + "cd" + PATH_SEPARATOR + "expected";

	private static final String ABSOLUTE_EXPECTED_DIRECTORY = Environment.currentDirectory + PATH_SEPARATOR
			+ RELATIVE_EXPECTED_DIRECTORY;

	private static final String COMMON_TEST_DIRECTORY_PREFIX = "testDir";

	private static final String TEST_DIRECTORY_ONE_NAME = "testDirOne";

	private static final String RELATIVE_TEST_DIRECTORY_ONE = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR
			+ "integration" + PATH_SEPARATOR + "cd" + PATH_SEPARATOR + TEST_DIRECTORY_ONE_NAME;

	private static final String ABSOLUTE_TEST_DIRECTORY_ONE = Environment.currentDirectory + PATH_SEPARATOR
			+ RELATIVE_TEST_DIRECTORY_ONE;

	private static final String TEST_DIRECTORY_TWO_NAME = "testDirTwo";

	private static final String RELATIVE_TEST_DIRECTORY_TWO = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR
			+ "integration" + PATH_SEPARATOR + "cd" + PATH_SEPARATOR + TEST_DIRECTORY_TWO_NAME;

	private static final String ABSOLUTE_TEST_DIRECTORY_TWO = Environment.currentDirectory + PATH_SEPARATOR
			+ RELATIVE_TEST_DIRECTORY_TWO;

	private static final String TEST_DIRECTORY_THREE_NAME = "testDirThree";

	private static final String RELATIVE_TEST_DIRECTORY_THREE = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR
			+ "integration" + PATH_SEPARATOR + "cd" + PATH_SEPARATOR + TEST_DIRECTORY_THREE_NAME;

	private static final String ABSOLUTE_TEST_DIRECTORY_THREE = Environment.currentDirectory + PATH_SEPARATOR
			+ RELATIVE_TEST_DIRECTORY_THREE;

	private static final String TEST_EMPTY_DIRECTORY_NAME = "emptyDir";

	private static final String RELATIVE_EMPTY_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR
			+ "integration" + PATH_SEPARATOR + "cd" + PATH_SEPARATOR + TEST_EMPTY_DIRECTORY_NAME;

	private static final String ABSOLUTE_EMPTY_TEST_DIRECTORY = Environment.currentDirectory + PATH_SEPARATOR
			+ RELATIVE_EMPTY_TEST_DIRECTORY;

	@BeforeClass
	public static void setup() {
		shell = new ShellImpl();
		buf = new byte[10000];
	}

	@Before
	public void setupBeforeTest() {
		Environment.setDefaultDirectory();
	}

	@After
	public void tearDownAfterTest() {
		deleteFile(ABSOLUTE_TEST_DIRECTORY_ONE + PATH_SEPARATOR + "in");
		deleteFile(ABSOLUTE_TEST_DIRECTORY_ONE + PATH_SEPARATOR + "in.txt");
		deleteFile(ABSOLUTE_TEST_DIRECTORY_TWO + PATH_SEPARATOR + "in");
		deleteFile(ABSOLUTE_TEST_DIRECTORY_TWO + PATH_SEPARATOR + "in.txt");
		deleteFile(ABSOLUTE_TEST_DIRECTORY_THREE + PATH_SEPARATOR + "in");
		deleteFile(ABSOLUTE_TEST_DIRECTORY_THREE + PATH_SEPARATOR + "in.txt");
		deleteFile(ABSOLUTE_EMPTY_TEST_DIRECTORY + PATH_SEPARATOR + "in.txt");
		deleteFile(ABSOLUTE_EMPTY_TEST_DIRECTORY);
		Environment.setDefaultDirectory();
	}

	@Test
	public void testCdWithWc() throws AbstractApplicationException, ShellException {

		String command = "wc `cd " + ABSOLUTE_TEST_DIRECTORY_TWO + "`-m in";

		String fileContents = "12" + NEW_LINE + "`Echo hello > " + ABSOLUTE_TEST_DIRECTORY_TWO + PATH_SEPARATOR
				+ "in; cal`" + NEW_LINE + "8122" + NEW_LINE + "300000" + NEW_LINE + "-36" + NEW_LINE + "37" + NEW_LINE
				+ "-14" + NEW_LINE + "1" + NEW_LINE + "1000" + NEW_LINE + "-17" + NEW_LINE + "35" + NEW_LINE + "100"
				+ NEW_LINE + "\"1000\"" + NEW_LINE + "10" + NEW_LINE + "0" + NEW_LINE + "0" + NEW_LINE + "'21000'"
				+ NEW_LINE + "`cd '" + ABSOLUTE_TEST_DIRECTORY_ONE + "'; cat *i`" + NEW_LINE + "10" + NEW_LINE + "-212"
				+ NEW_LINE + "200" + NEW_LINE + "2100" + NEW_LINE + "| echo 'echo says hi'";
		assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY_TWO + PATH_SEPARATOR + "in", fileContents));

		String out = shell.parseAndEvaluate(command);

		String readContents = getContents(ABSOLUTE_TEST_DIRECTORY_TWO + PATH_SEPARATOR + "in");
		int expectedCharCount = readContents.getBytes().length;

		String expected = "     " + expectedCharCount + NEW_LINE;

		assertEquals(out, expected);
	}

	@Test
	public void testCdWithSed() throws AbstractApplicationException, ShellException {

		String command = "cd " + ABSOLUTE_TEST_DIRECTORY_TWO + PATH_SEPARATOR + "..; sed s/cake/food/ *"
				+ COMMON_TEST_DIRECTORY_PREFIX + "*" + PATH_SEPARATOR + Symbol.CURRENT_DIR_S + PATH_SEPARATOR + "*.tx*";

		String fileContentsOne = "There is cake at the end of this level" + NEW_LINE
				+ "All this cake is making me hungry for cake" + NEW_LINE + "cake is what some people live for"
				+ NEW_LINE + "Without cake there is nothing";
		String fileContentsTwo = "Cake is bought for everyone" + NEW_LINE
				+ " Hide the Cake before the cake people sees it" + NEW_LINE
				+ "Eat this cake to transport yourself to the lair of the cake monster" + NEW_LINE
				+ "Prepare yourself with some cake armour and cake" + NEW_LINE + "cake is the name of the cake monster"
				+ NEW_LINE + "take care and may ...." + NEW_LINE;

		assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY_ONE + PATH_SEPARATOR + "in", fileContentsOne));
		assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY_TWO + PATH_SEPARATOR + "in.txt", fileContentsTwo));

		String out = shell.parseAndEvaluate(command);

		String expected = NEW_LINE + "Cake is bought for everyone" + NEW_LINE
				+ " Hide the Cake before the food people sees it" + NEW_LINE
				+ "Eat this food to transport yourself to the lair of the cake monster" + NEW_LINE
				+ "Prepare yourself with some food armour and cake" + NEW_LINE + "food is the name of the cake monster"
				+ NEW_LINE + "take care and may ...." + NEW_LINE + NEW_LINE;

		assertEquals(out, expected);
	}

	@Test
	public void testCdWithDate() throws AbstractApplicationException, ShellException {

		String command = "cd " + ABSOLUTE_TEST_DIRECTORY_TWO + "; date `cat *in*" + "`";

		String fileContents = "12" + NEW_LINE + "`Echo hello > " + ABSOLUTE_TEST_DIRECTORY_TWO + PATH_SEPARATOR
				+ "in; cal`" + NEW_LINE + "8122" + NEW_LINE + "300000" + NEW_LINE + "-36" + NEW_LINE + "37" + NEW_LINE
				+ "-14" + NEW_LINE + "1" + NEW_LINE + "1000" + NEW_LINE + "-17" + NEW_LINE + "35" + NEW_LINE + "100"
				+ NEW_LINE + "\"1000\"" + NEW_LINE + "10" + NEW_LINE + "0" + NEW_LINE + "0" + NEW_LINE + "'21000'"
				+ NEW_LINE + "`cd '" + ABSOLUTE_TEST_DIRECTORY_ONE + "'; cat *i`" + NEW_LINE + "10" + NEW_LINE + "-212"
				+ NEW_LINE + "200" + NEW_LINE + "2100" + NEW_LINE + "| echo 'echo says hi'";
		assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY_TWO + PATH_SEPARATOR + "in", fileContents));

		String out = shell.parseAndEvaluate(command);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
		String expected = NEW_LINE + ZonedDateTime.now().format(formatter) + NEW_LINE;

		assertEquals(out, expected);
	}

	@Test
	public void testCdWithSort() throws AbstractApplicationException, ShellException {

		String command = "cd " + RELATIVE_TEST_DIRECTORY_ONE + "; sort -n `cd " + ABSOLUTE_EXPECTED_DIRECTORY + "`testWithSort";

		String out = shell.parseAndEvaluate(command);

		String expected = NEW_LINE + "-212" + NEW_LINE + "-200" + NEW_LINE + "-36" + NEW_LINE + "-17" + NEW_LINE + "-14"
				+ NEW_LINE + "0" + NEW_LINE + "0" + NEW_LINE + "1" + NEW_LINE + "10" + NEW_LINE + "10" + NEW_LINE + "12"
				+ NEW_LINE + "35" + NEW_LINE + "37" + NEW_LINE + "100" + NEW_LINE + "200" + NEW_LINE + "1000" + NEW_LINE
				+ "1000" + NEW_LINE + "1000" + NEW_LINE + "2100" + NEW_LINE + "8122" + NEW_LINE + "9128" + NEW_LINE
				+ "21000" + NEW_LINE + "300000" + NEW_LINE;

		assertEquals(out, expected);
	}

	@Test
	public void testCdWithGrep() throws AbstractApplicationException, ShellException {

		String command = "cd " + ABSOLUTE_TEST_DIRECTORY_THREE + ";grep '\"over the moon\"' .." + PATH_SEPARATOR
				+ COMMON_TEST_DIRECTORY_PREFIX + "*" + PATH_SEPARATOR + "i*";

		String fileContentsOne = "over the moon moon the over" + NEW_LINE
				+ "ove\"over the moon\" moon \"over the moon\"" + NEW_LINE + "     \t\"over the moon" + NEW_LINE
				+ NEW_LINE + "   over the moon\" over the moon\"  " + NEW_LINE
				+ " 'over the moon' moon \"over the moon " + NEW_LINE + " '\"over the moon\"'";

		String fileContentsTwo = "moon \"over the moon\" the over" + NEW_LINE
				+ "moon the '\"over the moon\"' \"over the moon\"" + NEW_LINE
				+ "moo says the cow over the moon 'over the moon'" + NEW_LINE + "  overthemoon  'over the moon'"
				+ NEW_LINE + NEW_LINE;

		assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY_ONE + PATH_SEPARATOR + "in.txt", fileContentsOne));
		assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY_TWO + PATH_SEPARATOR + "in", fileContentsTwo));

		String out = shell.parseAndEvaluate(command);

		String expected = NEW_LINE + ABSOLUTE_TEST_DIRECTORY_ONE + PATH_SEPARATOR + "in.txt: "
				+ "ove\"over the moon\" moon \"over the moon\"" + NEW_LINE + ABSOLUTE_TEST_DIRECTORY_ONE
				+ PATH_SEPARATOR + "in.txt: " + " '\"over the moon\"'" + NEW_LINE + ABSOLUTE_TEST_DIRECTORY_TWO
				+ PATH_SEPARATOR + "in: " + "moon \"over the moon\" the over" + NEW_LINE + ABSOLUTE_TEST_DIRECTORY_TWO
				+ PATH_SEPARATOR + "in: " + "moon the '\"over the moon\"' \"over the moon\"" + NEW_LINE;

		assertEquals(out, expected);
	}

	@Test
	public void testCdWithTail() throws AbstractApplicationException, ShellException {

		String command = "cd " + RELATIVE_EMPTY_TEST_DIRECTORY + "; tail -n 2147483647 \"`echo *.*`\"";

		assertTrue(createDirectory(ABSOLUTE_EMPTY_TEST_DIRECTORY));

		String fileContents = "2" + NEW_LINE + "3" + NEW_LINE + NEW_LINE + "4" + NEW_LINE;

		assertTrue(createFile(ABSOLUTE_EMPTY_TEST_DIRECTORY + PATH_SEPARATOR + "in.txt", fileContents));

		String out = shell.parseAndEvaluate(command);

		String expected = NEW_LINE + "2" + NEW_LINE + "3" + NEW_LINE + NEW_LINE + "4" + NEW_LINE;

		assertEquals(out, expected);
	}

	@Test
	public void testCdWithEcho() throws AbstractApplicationException, ShellException {

		String command = "cd " + ABSOLUTE_TEST_DIRECTORY_THREE + "| echo .." + PATH_SEPARATOR
				+ COMMON_TEST_DIRECTORY_PREFIX + "*" + PATH_SEPARATOR + "in*";

		assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY_ONE + PATH_SEPARATOR + "in.txt", ""));
		assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY_TWO + PATH_SEPARATOR + "in", ""));

		String out = shell.parseAndEvaluate(command);

		String expected = ABSOLUTE_TEST_DIRECTORY_ONE + PATH_SEPARATOR + "in.txt " + ABSOLUTE_TEST_DIRECTORY_TWO
				+ PATH_SEPARATOR + "in" + NEW_LINE;

		assertEquals(out, expected);
	}

	@Test
	public void testCdWithHead() throws AbstractApplicationException, ShellException {

		String command = "cd " + RELATIVE_TEST_DIRECTORY_TWO + PATH_SEPARATOR + "|head -n 4 ." + PATH_SEPARATOR + "*in";

		String fileContents = "   February 2020    " + NEW_LINE + "Su Mo Tu We Th Fr Sa" + NEW_LINE
				+ "                  1 " + NEW_LINE + "2  3  4  5  6  7  8 " + NEW_LINE + "9  10 11 12 13 14 15"
				+ NEW_LINE + "16 17 18 19 20 21 22" + NEW_LINE + "23 24 25 26 27 28 29";
		assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY_TWO + PATH_SEPARATOR + "in", fileContents));

		String out = shell.parseAndEvaluate(command);

		String expected = "   February 2020    " + NEW_LINE + "Su Mo Tu We Th Fr Sa" + NEW_LINE + "                  1 "
				+ NEW_LINE + "2  3  4  5  6  7  8 " + NEW_LINE;

		assertEquals(out, expected);
	}

	@Test
	public void testCdWithPwd() throws AbstractApplicationException, ShellException {

		String command = "cd " + RELATIVE_TEST_DIRECTORY_THREE + "*" + PATH_SEPARATOR + Symbol.CURRENT_DIR_S
				+ PATH_SEPARATOR + Symbol.PREV_DIR_S + PATH_SEPARATOR + TEST_DIRECTORY_ONE_NAME + "|pwd";

		String out = shell.parseAndEvaluate(command);

		String expected = ABSOLUTE_TEST_DIRECTORY_ONE + NEW_LINE;

		assertEquals(out, expected);
	}

	@Test
	public void testCdWithCd() throws AbstractApplicationException, ShellException {

		String command = "cd " + RELATIVE_TEST_DIRECTORY_THREE + "; cd ..; cd ." + PATH_SEPARATOR
				+ TEST_DIRECTORY_TWO_NAME + "; cat in";

		String fileContents = "Reached destination!";
		assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY_TWO + PATH_SEPARATOR + "in", fileContents));

		String out = shell.parseAndEvaluate(command);

		String expected = NEW_LINE + NEW_LINE + NEW_LINE + "Reached destination!" + NEW_LINE;

		assertEquals(out, expected);
	}

	@Test
	public void testCdWithCal() throws AbstractApplicationException, ShellException {

		String command = "cd " + RELATIVE_TEST_DIRECTORY_THREE + " >; cal `cat *in`";

		String fileContents = "2 2020" + NEW_LINE;
		assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY_THREE + PATH_SEPARATOR + "in", fileContents));

		String out = shell.parseAndEvaluate(command);

		String expected = "   February 2020    " + NEW_LINE + "Su Mo Tu We Th Fr Sa" + NEW_LINE + "                  1 "
				+ NEW_LINE + "2  3  4  5  6  7  8 " + NEW_LINE + "9  10 11 12 13 14 15" + NEW_LINE
				+ "16 17 18 19 20 21 22" + NEW_LINE + "23 24 25 26 27 28 29" + NEW_LINE;

		assertEquals(out, expected);
	}

	private static String getContents(String fileName) {

		String out = null;
		try {

			FileInputStream fs = new FileInputStream(fileName);

			StringBuilder sb = new StringBuilder();
			while (true) {
				int bytesRead = fs.read(buf);
				if (bytesRead < 0) {
					break;
				}
				sb.append(new String(buf, 0, bytesRead));
			}
			out = sb.toString();

			fs.close();

		} catch (IOException e) {
		}
		return out;
	}

	private static String getNewLineInExpectedFile() {
		return getContents(ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "newLine");
	}

	private static boolean createFile(String file, String content) {
		String fileName = file;
		
		if (fileName == null || content == null) {
			return false;
		}

		fileName = Environment.getAbsPath(fileName);

		if (!Environment.isExists(fileName)) {
			if (!Environment.createNewFile(fileName)) {
				return false;
			}
		} else if (Environment.isDirectory(fileName)) {
			return false;
		}

		try {
			FileOutputStream os = new FileOutputStream(fileName);
			os.write(content.getBytes());
			os.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	private static boolean createDirectory(String dirName) {

		if (dirName == null) {
			return false;
		}

		String tempDirName = Environment.getAbsPath(dirName);
		if (Environment.isDirectory(tempDirName)) {
			return true;
		}

		boolean isCreated = false;
		File file = new File(dirName);
		try {
			isCreated = file.mkdir();
		} catch (SecurityException e) {
		}

		return isCreated;
	}

	private static boolean deleteFile(String absPath) {

		if (absPath == null) {
			return false;
		}

		if (!Environment.isExists(absPath)) {
			return true;
		}

		boolean isDeleted = false;
		File file = new File(absPath);
		try {
			isDeleted = file.delete();
		} catch (SecurityException e) {
		}

		return isDeleted;
	}
}
