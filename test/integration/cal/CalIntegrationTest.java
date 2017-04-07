package integration.cal;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class CalIntegrationTest {

	private static ShellImpl shell;
	private static byte[] buf;

	public static final String PATH_SEPARATOR = File.separator;
	public static final String NEW_LINE = System.lineSeparator();

	public static final String NEW_LINE_IN_EXPECTED = getNewLineInExpectedFile();

	private static final String RELATIVE_EXPECTED_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR
			+ "integration" + PATH_SEPARATOR + "cal" + PATH_SEPARATOR + "expected";

	private static final String ABSOLUTE_EXPECTED_DIRECTORY = Environment.currentDirectory + PATH_SEPARATOR
			+ RELATIVE_EXPECTED_DIRECTORY;

	private static final String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR
			+ "integration" + PATH_SEPARATOR + "cal" + PATH_SEPARATOR + "testDir";

	private static final String ABSOLUTE_TEST_DIRECTORY = Environment.currentDirectory + PATH_SEPARATOR
			+ RELATIVE_TEST_DIRECTORY;

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
		deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in");
		deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out");
		Environment.setDefaultDirectory();
	}

	@Test
	public void testCalWithWc() throws AbstractApplicationException, ShellException {

		String command = "cal -m `echo new years eve > " + RELATIVE_TEST_DIRECTORY + "\"`echo`\"" + PATH_SEPARATOR
				+ "out " + ";cat < " + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "out|wc -l`|wc -w";

		String out = shell.parseAndEvaluate(command);

		String expected = "     463" + NEW_LINE;

		assertEquals(out, expected);
	}

	@Test
	public void testCalWithSed() throws AbstractApplicationException, ShellException {

		String command = "cal `head " + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "in|sort -n"
				+ "|sed ss-42s-ms`|sed sg'19 20'g'51 52'g";

		String fileName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in";
		String fileContents = "1900" + NEW_LINE + "-42" + NEW_LINE;
		assertTrue(createFile(fileName, fileContents));

		String out = shell.parseAndEvaluate(command);

		String expected = "                              1900                              " + NEW_LINE
				+ "      January               February               March        " + NEW_LINE
				+ "Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su" + NEW_LINE
				+ "1  2  3  4  5  6  7            1  2  3  4            1  2  3  4 " + NEW_LINE
				+ "8  9  10 11 12 13 14  5  6  7  8  9  10 11  5  6  7  8  9  10 11" + NEW_LINE
				+ "15 16 17 18 51 52 21  12 13 14 15 16 17 18  12 13 14 15 16 17 18" + NEW_LINE
				+ "22 23 24 25 26 27 28  51 52 21 22 23 24 25  19 20 21 22 23 24 25" + NEW_LINE
				+ "29 30 31              26 27 28              26 27 28 29 30 31   " + NEW_LINE
				+ "       April                  May                   June        " + NEW_LINE
				+ "Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su" + NEW_LINE
				+ "                  1      1  2  3  4  5  6               1  2  3 " + NEW_LINE
				+ "2  3  4  5  6  7  8   7  8  9  10 11 12 13  4  5  6  7  8  9  10" + NEW_LINE
				+ "9  10 11 12 13 14 15  14 15 16 17 18 51 52  11 12 13 14 15 16 17" + NEW_LINE
				+ "16 17 18 51 52 21 22  21 22 23 24 25 26 27  18 19 20 21 22 23 24" + NEW_LINE
				+ "23 24 25 26 27 28 29  28 29 30 31           25 26 27 28 29 30   " + NEW_LINE
				+ "30                                                              " + NEW_LINE
				+ "        July                 August              September      " + NEW_LINE
				+ "Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su" + NEW_LINE
				+ "                  1         1  2  3  4  5                  1  2 " + NEW_LINE
				+ "2  3  4  5  6  7  8   6  7  8  9  10 11 12  3  4  5  6  7  8  9 " + NEW_LINE
				+ "9  10 11 12 13 14 15  13 14 15 16 17 18 19  10 11 12 13 14 15 16" + NEW_LINE
				+ "16 17 18 51 52 21 22  20 21 22 23 24 25 26  17 18 19 20 21 22 23" + NEW_LINE
				+ "23 24 25 26 27 28 29  27 28 29 30 31        24 25 26 27 28 29 30" + NEW_LINE
				+ "30 31                                                           " + NEW_LINE
				+ "      October               November              December      " + NEW_LINE
				+ "Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su" + NEW_LINE
				+ "1  2  3  4  5  6  7            1  2  3  4                  1  2 " + NEW_LINE
				+ "8  9  10 11 12 13 14  5  6  7  8  9  10 11  3  4  5  6  7  8  9 " + NEW_LINE
				+ "15 16 17 18 51 52 21  12 13 14 15 16 17 18  10 11 12 13 14 15 16" + NEW_LINE
				+ "22 23 24 25 26 27 28  51 52 21 22 23 24 25  17 18 19 20 21 22 23" + NEW_LINE
				+ "29 30 31              26 27 28 29 30        24 25 26 27 28 29 30" + NEW_LINE
				+ "                                            31                  " + NEW_LINE;

		assertEquals(out, expected);
	}

	@Test
	public void testCalWithDate() throws AbstractApplicationException, ShellException {

		String command = "cal \"`echo -m 5 1723\";echo -m 7 1288\"|grep 24|echo -m2 1672|sed ss\"2 1672\"ss`\" "
				+ "    `echo ' 2 '  ` ` echo \"'1600'\"|grep 1600 `;  date <";

		String out = shell.parseAndEvaluate(command);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
		String dateString = ZonedDateTime.now().format(formatter);

		String expected = getContents(ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "leapYearMonth1600");
		expected = makeContentsCompatible(expected);

		expected += dateString;
		expected += NEW_LINE;

		assertEquals(out, expected);
	}

	@Test
	public void testCalWithSort() throws AbstractApplicationException, ShellException {

		String command = "cal `sort \"`echo`\"" + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "in*    `";

		String fileContents = "2017" + NEW_LINE + "-m" + NEW_LINE + "\t";
		assertTrue(createFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in", fileContents));

		String out = shell.parseAndEvaluate(command);

		String expected = getContents(ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "year2017Monday");
		expected = makeContentsCompatible(expected);

		assertEquals(out, expected);
	}

	@Test
	public void testCalWithGrep() throws AbstractApplicationException, ShellException {

		String command = " cal `echo -m      16 > " + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "out"
				+ ";sed ss\"16\"s'12's <" + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "ou*t ` "
				+ "` echo '      ' | sed sg'  'g\" \"gg ` " + "     `head " + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR
				+ "out*|sed s\\'-m 16'\\1000\\` " + "| grep '7'";

		String out = shell.parseAndEvaluate(command);

		String expectedFilePath = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out";
		assertTrue(Environment.isFile(expectedFilePath));

		String fileContents = getContents(expectedFilePath);
		assertEquals(fileContents, "-m 16" + NEW_LINE);

		String expectedStdoutContents = "1  2  3  4  5  6  7 " + NEW_LINE + "15 16 17 18 19 20 21" + NEW_LINE
				+ "22 23 24 25 26 27 28" + NEW_LINE;
		assertEquals(out, expectedStdoutContents);
	}

	@Test
	public void testCalWithTail() throws AbstractApplicationException, ShellException {

		String command = "cal `echo \"this year is 2017\"|sed sg'this year is'ggg` >" + RELATIVE_TEST_DIRECTORY
				+ PATH_SEPARATOR + "out " + "; tail -n 2 " + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "*o*u*t*";

		String out = shell.parseAndEvaluate(command);

		String expectedFileContents = getContents(ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "year2017");
		expectedFileContents = makeContentsCompatible(expectedFileContents);

		String expectedFilePath = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out";
		assertTrue(Environment.isFile(expectedFilePath));

		String fileContents = getContents(expectedFilePath);
		assertEquals(fileContents, expectedFileContents);

		String expectedStdoutContents = "29 30 31              26 27 28 29 30        24 25 26 27 28 29 30" + NEW_LINE
				+ "                                            31                  " + NEW_LINE;
		assertEquals(out, expectedStdoutContents);
	}

	@Test
	public void testCalWithEcho() throws AbstractApplicationException, ShellException {

		String command = "cal >" + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "out `echo -m|grep ''` "
				+ "`echo 20|    head``echo 17  |tail` " + ";    echo `head " + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR
				+ "*out`";

		String out = shell.parseAndEvaluate(command);

		String expectedFilePath = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out";
		assertTrue(Environment.isFile(expectedFilePath));

		String expectedFileContents = getContents(ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "year2017Monday");
		expectedFileContents = makeContentsCompatible(expectedFileContents);
		String fileContents = getContents(expectedFilePath);

		String expectedFromStdout = "2017 January February March Mo Tu We Th Fr Sa Su Mo Tu We Th Fr Sa "
				+ "Su Mo Tu We Th Fr Sa Su 1 1 2 3 4 5 1 2 3 4 5 2 3 4 5 6 7 8 6 7 8 "
				+ "9 10 11 12 6 7 8 9 10 11 12 9 10 11 12 13 14 15 13 14 15 16 17 18 "
				+ "19 13 14 15 16 17 18 19 16 17 18 19 20 21 22 20 21 22 23 24 25 26 "
				+ "20 21 22 23 24 25 26 23 24 25 26 27 28 29 27 28 27 28 29 30 31 30 " + "31 April May June" + NEW_LINE;

		assertEquals(out, expectedFromStdout);

		assertEquals(fileContents, expectedFileContents);
	}

	@Test
	public void testCalWithHead() throws AbstractApplicationException, ShellException {

		String command = "cal `head " + RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "i*n` 2000";

		String fileName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in";
		String fileContents = "-m" + NEW_LINE + "2" + NEW_LINE;
		assertTrue(createFile(fileName, fileContents));

		String out = shell.parseAndEvaluate(command);

		String expected = getContents(ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "monthYearMondayLeapYear");
		expected = makeContentsCompatible(expected);

		assertEquals(out, expected);
	}

	@Test
	public void testCalWithPwd() throws AbstractApplicationException, ShellException {

		String expectedDir = Environment.currentDirectory;

		String command = "cal `echo 1900|head -n 2147483646|tail`;    pwd";

		String out = shell.parseAndEvaluate(command);

		String expectedCalOut = getContents(ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "year1900NotLeapYear");
		expectedCalOut = makeContentsCompatible(expectedCalOut);

		String expected = expectedCalOut + expectedDir + NEW_LINE;
		assertEquals(out, expected);
	}

	@Test
	public void testCalWithCd() throws AbstractApplicationException, ShellException {

		String expectedDir = Environment.currentDirectory + PATH_SEPARATOR + "src" + PATH_SEPARATOR + "test"
				+ PATH_SEPARATOR + "integration" + PATH_SEPARATOR + "cal" + PATH_SEPARATOR + "testDir";

		String command = "cal 2 2020; cd src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "integration"
				+ PATH_SEPARATOR + "cal" + PATH_SEPARATOR + "testDir*; pwd  ";

		String out = shell.parseAndEvaluate(command);

		String expectedCalOut = getContents(ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "monthYearLeapYear");
		expectedCalOut = makeContentsCompatible(expectedCalOut);

		String expected = expectedCalOut + NEW_LINE + expectedDir + NEW_LINE;
		assertEquals(out, expected);
	}

	@Test
	public void testCalWithCal() throws AbstractApplicationException, ShellException {

		String command = "cal  ;cal -m";
		String out = shell.parseAndEvaluate(command);

		String filePath = getCurrentMonthAbsoluteFilePath();
		String current = getContents(filePath);
		current = makeContentsCompatible(current);

		filePath = getCurrentMonthMondayAbsoluteFilePath();
		String currentWithMonday = getContents(filePath);
		currentWithMonday = makeContentsCompatible(currentWithMonday);

		String expected = current + currentWithMonday;

		assertEquals(out, expected);
	}

	private int getCurrentMonth() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}

	private String getCurrentMonthMondayAbsoluteFilePath() {
		int currentMonth = getCurrentMonth();
		switch (currentMonth) {
		case Calendar.MARCH:
			return ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "currentMonthMondayMarch";
		case Calendar.APRIL:
			return ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "currentMonthMondayApril";
		case Calendar.MAY:
			return ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "currentMonthMondayMay";
		case Calendar.JUNE:
			return ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "currentMonthMondayJune";
		default:
			break;
		}
		return "";
	}

	private String getCurrentMonthAbsoluteFilePath() {
		int currentMonth = getCurrentMonth();
		switch (currentMonth) {
		case Calendar.MARCH:
			return ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "currentMonthMarch";
		case Calendar.APRIL:
			return ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "currentMonthApril";
		case Calendar.MAY:
			return ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "currentMonthMay";
		case Calendar.JUNE:
			return ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "currentMonthJune";
		default:
			break;
		}
		return "";
	}

	private String makeContentsCompatible(String contents) {

		if (contents == null) {
			return "";
		}

		String newLine = NEW_LINE_IN_EXPECTED != null ? NEW_LINE_IN_EXPECTED : "";
		newLine = Pattern.quote(newLine);
		contents.replaceAll(newLine, NEW_LINE);

		return contents;
	}

	private static String getNewLineInExpectedFile() {
		return getContents(ABSOLUTE_EXPECTED_DIRECTORY + PATH_SEPARATOR + "newLine");
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
