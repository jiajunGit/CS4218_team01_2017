package ef2.wc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.WcException;
import sg.edu.nus.comp.cs4218.impl.app.WcApplication;

public class WcApplicationSampleTest {
	private static final String FILE_SEPARATOR = File.separator;
	final static String PATH_SEPARATOR = File.separator;
	final static String LINE_SEPARATOR = System.getProperty("line.separator");
	final static String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "ef2"
			+ PATH_SEPARATOR + "wc" + PATH_SEPARATOR + "input" + PATH_SEPARATOR;
	private static final String TEST_FILE_EMPTY = RELATIVE_TEST_DIRECTORY + "empty.txt";
	private static final String TEST_FILE_SINGLE_WORD = RELATIVE_TEST_DIRECTORY + "singleWord.txt";
	private static final String TEST_FILE_TITLES = RELATIVE_TEST_DIRECTORY + "titles.txt";
	private static final File TITLE_FILES = new File(TEST_FILE_TITLES);
	private static final long TITLE_FILES_TOTAL_BYTES = TITLE_FILES.length();
	private static final String TEST_FILE_NAME_HAS_SPACE = RELATIVE_TEST_DIRECTORY + "name has space.txt";

	WcApplication app;
	OutputStream stdout;
	InputStream stdin;

	@Before
	public void setUp() throws Exception {
		app = new WcApplication();
		stdin = writeToStdin("");
		stdout = new ByteArrayOutputStream();
	}

	static InputStream writeToStdin(String fileName) throws IOException {
		InputStream stdin = new ByteArrayInputStream(fileName.getBytes());
		return stdin;

	}

	@Test(expected = WcException.class)
	public void testWcWithNullStdout() throws AbstractApplicationException {
		String[] args = null;
		app.run(args, stdin, null);
	}

	@Test(expected = WcException.class)
	public void testWcWithNullArgsAndNullStdin() throws AbstractApplicationException {
		String[] args = null;
		app.run(args, null, stdout);
	}

	@Test(expected = WcException.class)
	public void testWcWithEmptyArgsAndNullStdin() throws AbstractApplicationException {
		String[] args = new String[0];
		app.run(args, null, stdout);
	}

	@Test
	public void testWcWithEmptyFile() throws AbstractApplicationException {
		String filePath = TEST_FILE_EMPTY;
		String[] args = { filePath };
		app.run(args, stdin, stdout);
		assertEquals("       0       0       0", stdout.toString());
	}

	@Test
	public void testWcWithNoFlag() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { filePath };
		app.run(args, stdin, stdout);
		assertEquals("       5       1       0", stdout.toString());
	}

	@Test(expected = WcException.class)
	public void testWcWithInvalidFlag() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-x", filePath };
		app.run(args, stdin, stdout);
	}

	@Test(expected = WcException.class)
	public void testWcWithMixValidAndInvalidFlags() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-m", "-x", "-l", filePath };
		app.run(args, stdin, stdout);
	}

	@Test(expected = WcException.class)
	public void testWcWithInvalidComplexFlags() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-mxl", filePath };
		app.run(args, stdin, stdout);
	}

	@Test
	public void testWcWithOnlyCharFlag() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-m", filePath };
		app.run(args, stdin, stdout);
		assertEquals("       5", stdout.toString());
	}

	@Test
	public void testWcWithOnlyWordFlag() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-w", filePath };
		app.run(args, stdin, stdout);
		assertEquals("       1", stdout.toString());
	}

	@Test
	public void testWcWithOnlyNewlineFlag() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-l", filePath };
		app.run(args, stdin, stdout);
		assertEquals("       0", stdout.toString());
	}

	@Test
	public void testWcWithComplexFlags() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-mlw", filePath };
		app.run(args, stdin, stdout);
		assertEquals("       5       1       0", stdout.toString());
	}

	@Test
	public void testWcWithMultipleSingleFlags() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-w", "-m", "-l", filePath };
		app.run(args, stdin, stdout);
		assertEquals("       5       1       0", stdout.toString());
	}

	@Test
	public void testWcWithMixFlags() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-w", "-ml", "-mlw", filePath };
		app.run(args, stdin, stdout);
		assertEquals("       5       1       0", stdout.toString());
	}

	@Test
	public void testWcWithRepeativeSingleFlags() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-w", "-w", "-w", filePath };
		app.run(args, stdin, stdout);
		assertEquals("       1", stdout.toString());
	}

	@Test
	public void testWcWithDuplicateFlags() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-wwwww", filePath };
		app.run(args, stdin, stdout);
		assertEquals("       1", stdout.toString());
	}

	@Test
	public void testWcWithDifferentFlagOrders() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		ByteArrayOutputStream output1 = new ByteArrayOutputStream();
		String[] args1 = { "-wlm", filePath };
		app.run(args1, stdin, output1);
		String[] args2 = { "-lmw", filePath };
		ByteArrayOutputStream output2 = new ByteArrayOutputStream();
		app.run(args2, stdin, output2);
		assertEquals(output1.toString(), output2.toString());
		assertEquals("       5       1       0", output1.toString());
	}

	@Test(expected = WcException.class)
	public void testWcWithOutOfOrderFlag() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { filePath, "-w" };
		app.run(args, stdin, stdout);
	}

	@Test(expected = WcException.class)
	public void testWcWithInOrderFlagAndOutOfOrderFlag() throws AbstractApplicationException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "-w", filePath, "-w" };
		app.run(args, stdin, stdout);
	}

	@Test
	public void testWcWithMultipleFiles() throws AbstractApplicationException {
		String[] args = { TEST_FILE_EMPTY, TEST_FILE_SINGLE_WORD, TEST_FILE_TITLES };
		app.run(args, stdin, stdout);
		String emptyFileExpected = "       0       0       0" + LINE_SEPARATOR;
		String singleWordFileExpected = "       5       1       0" + LINE_SEPARATOR;
		String titlesFileExpected = String.format("%8d     717     250", TITLE_FILES_TOTAL_BYTES);
		long totalBytes = TITLE_FILES_TOTAL_BYTES + new File(TEST_FILE_SINGLE_WORD).length() + 0;
		String totalExpected = String.format("%8d     718     250 total%s", totalBytes, LINE_SEPARATOR);
		String expectedResult = String.format("%s%s%s", emptyFileExpected, singleWordFileExpected, titlesFileExpected);
		assertEquals(expectedResult, stdout.toString());
	}

	@Test(expected = WcException.class)
	public void testWcWithInvalidFiles() throws AbstractApplicationException {
		String[] args = { TEST_FILE_EMPTY, "-w", TEST_FILE_SINGLE_WORD };
		app.run(args, stdin, stdout);
	}

	@Test
	public void testWcReadFromStdin() throws AbstractApplicationException, IOException {
		String[] args = {};
		stdin = writeToStdin("hello world");
		app.run(args, stdin, stdout);
		String expected = "      11       2       0";
		assertEquals(expected, stdout.toString());
	}

	@Test
	public void testWcReadFromStdinWithFlag() throws AbstractApplicationException, IOException {
		String[] args = { "-m" };
		stdin = writeToStdin("hello world");
		app.run(args, stdin, stdout);
		String expected = "      11";
		assertEquals(expected, stdout.toString());
	}

	@Test(expected = WcException.class)
	public void testWcReadFromStdinWithInvalidFlag() throws AbstractApplicationException, IOException {
		String[] args = { "-x" };
		stdin = writeToStdin("hello world");
		app.run(args, stdin, stdout);
	}

	@Test
	public void testWcWithBothStdinAndFile() throws AbstractApplicationException, IOException {
		String[] args = { TEST_FILE_SINGLE_WORD };
		stdin = writeToStdin("not single word");
		String singleWordFileExpected = "       5       1       0";
		app.run(args, stdin, stdout);
		assertEquals(singleWordFileExpected, stdout.toString());
	}

	@Test
	public void testWcWithFileNameContainSpace() throws AbstractApplicationException {
		String[] args = { TEST_FILE_NAME_HAS_SPACE };
		File testFile = new File(TEST_FILE_NAME_HAS_SPACE);
		long byteCount = testFile.length();
		String singleWordFileExpected = String.format("%8d       6       5", byteCount);
		app.run(args, stdin, stdout);
		assertEquals(singleWordFileExpected, stdout.toString());
	}

	@Test
	public void testPrintCharacterCountInFile() {
		String expected = String.format("%8d", TITLE_FILES_TOTAL_BYTES);
		String result = app.printCharacterCountInFile(String.format("wc -m %s", TEST_FILE_TITLES));
		assertEquals(expected, result);
	}

	@Test
	public void testPrintCharacterCountInInvalidFile() {
		String result = app.printCharacterCountInFile("wc -m invalid.txt");
		assertEquals("Invalid File", result);
	}

	@Test
	public void testPrintWordCountInFile() {
		String expected = "     717";
		String result = app.printWordCountInFile(String.format("wc -w %s", TEST_FILE_TITLES));
		assertEquals(expected, result);
	}

	@Test
	public void testPrintWordCountInInvalidFile() {
		String result = app.printWordCountInFile("wc -m invalid.txt");
		assertEquals("Invalid File", result);
	}

	@Test
	public void testPrintNewlineCountInFile() {
		String expected = "     250";
		String result = app.printNewlineCountInFile(String.format("wc -l %s", TEST_FILE_TITLES));
		assertEquals(expected, result);
	}

	@Test
	public void testPrintNewlineCountInInvalidFile() {
		String expected = "Invalid File";
		String result = app.printNewlineCountInFile("wc -m invalid.txt");
		assertEquals(expected, result);
	}

	@Test
	public void printAllCountsInFile() {
		String expected = String.format("%8d     717     250", TITLE_FILES_TOTAL_BYTES);
		String result = app.printAllCountsInFile(String.format("wc %s", TEST_FILE_TITLES));
		assertEquals(expected, result);
	}

	@Test
	public void printAllCountsInInvalidFile() {
		String result = app.printAllCountsInFile("wc -m invalid.txt");
		assertEquals("Invalid File", result);
	}

}
