package ef1.sort;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.SortApplication;

public class SortApplicationTest {
	final static String PATH_SEPARATOR = File.separator;
	final static String LINE_SEPARATOR = System.getProperty("line.separator");
	final static String RELATIVE_TEST_DIRECTORY =
			"test" + PATH_SEPARATOR + "ef1" + PATH_SEPARATOR + "sort" + PATH_SEPARATOR;
	private ByteArrayOutputStream out = new ByteArrayOutputStream();

	@AfterClass
	public static void tearDownAfterTest() {
		System.setOut(System.out);
	}

	@Test
	public void testSortStringsSimple() {

		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortStringsSimple";
		assertEquals("simple sort", "a" + LINE_SEPARATOR + "b" + LINE_SEPARATOR + "c"
				+ LINE_SEPARATOR + "d" + LINE_SEPARATOR + "e",
				sort.sortStringsSimple("sort " + file));
	}

	@Test
	public void testSortStringsCapital() {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortStringsCapital";
		assertEquals("simple sort", "A" + LINE_SEPARATOR + "B" + LINE_SEPARATOR + "L"
				+ LINE_SEPARATOR + "L" + LINE_SEPARATOR + "S",
				sort.sortStringsCapital("sort " + file));
	}

	@Test
	public void testSortNumbers() {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortNumbers";
		assertEquals("simple sort",
				"1" + LINE_SEPARATOR + "10" + LINE_SEPARATOR + "2" + LINE_SEPARATOR + "6"
						+ LINE_SEPARATOR + "8" + LINE_SEPARATOR + "9",
				sort.sortNumbers("sort " + file));
	}

	@Test
	public void testSortSimpleCapital() {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortSimpleCapital";
		assertEquals("simple sort",
				"A" + LINE_SEPARATOR + "E" + LINE_SEPARATOR + "O" + LINE_SEPARATOR + "T"
						+ LINE_SEPARATOR + "f" + LINE_SEPARATOR + "r" + LINE_SEPARATOR + "s"
						+ LINE_SEPARATOR + "t" + LINE_SEPARATOR + "w",
				sort.sortSimpleCapital("sort " + file));
	}

	@Test
	public void testSortSimpleNumbers() {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortSimpleNumbers";
		assertEquals("simple sort",
				"4" + LINE_SEPARATOR + "5" + LINE_SEPARATOR + "6" + LINE_SEPARATOR + "7"
						+ LINE_SEPARATOR + "h" + LINE_SEPARATOR + "i" + LINE_SEPARATOR + "j"
						+ LINE_SEPARATOR + "k" + LINE_SEPARATOR + "l",
				sort.sortSimpleNumbers("sort " + file));
	}

	// @Test
	// public void testSortSimpleSpecialChars() {
	// SortApplication sort = new SortApplication();
	// String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR +
	// "testSortSimpleSpecialChars";
	// assertEquals("simple sort",
	// "\"" + LINE_SEPARATOR + "\'" + LINE_SEPARATOR + "*" + LINE_SEPARATOR +
	// ";" + LINE_SEPARATOR + "<"
	// + LINE_SEPARATOR + ">" + LINE_SEPARATOR + "`" + LINE_SEPARATOR + "|",
	// sort.sortSimpleNumbers("sort " + file));
	// }

	@Test
	public void testSortSpecialChars() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String inFilePath =
				RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortSpecialChars";
		sort.sortSpecialChars("sort " + inFilePath);
	}

	@Test
	public void testSortSpecialCharsWithOpt() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String inFilePath =
				RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortSpecialChars";
		String outFilePath =
				RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testSortSpecialChars";

		try {
			String expectedOut = new String(Files.readAllBytes(Paths.get(outFilePath)));
		} catch (IOException ex) {
			System.out.println(ex);
		}

		assertEquals(SortException.class.getName(), sort.sortSpecialChars("sort -n " + inFilePath));
	}

	@Test
	public void testSortCapitalNumbers() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String inFilePath =
				RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortCapitalNumbers";
		sort.sortCapitalNumbers("sort " + inFilePath);
	}

	// TODO Modify later to throw exception
	@Test
	public void testSortCapitalNumbersWithOpt() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String inFilePath =
				RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortCapitalNumbers";
		String outFilePath = RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR
				+ "testSortCapitalNumbersWithOpt";

		try {
			String expectedOut = new String(Files.readAllBytes(Paths.get(outFilePath)));
		} catch (IOException ex) {
			System.out.println(ex);
		}

		assertEquals(SortException.class.getName(),
				sort.sortCapitalNumbers("sort -n " + inFilePath));
	}

	@Test
	public void testSortCapitalSpecialChars() {
		SortApplication sort = new SortApplication();
		String inFilePath =
				RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortCapitalSpecialChars";
		sort.sortCapitalSpecialChars("sort " + inFilePath);
	}

	@Test
	public void testSortNumbersSpecialChars() {
		SortApplication sort = new SortApplication();
		String inFilePath =
				RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortNumbersSpecialChars";
		sort.sortNumbersSpecialChars("sort " + inFilePath);
	}

	@Test
	public void testSortSimpleSpecialChars() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String inFilePath =
				RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortSimpleSpecialChars";
		sort.sortSimpleSpecialChars("sort " + inFilePath);
	}

	@Test
	public void testSortSimpleCapitalNumber() {
		SortApplication sort = new SortApplication();
		String inFilePath =
				RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortSimpleCapitalNumber";
		sort.sortSimpleCapitalNumber("sort " + inFilePath);
	}

	@Test
	public void testSortSimpleCapitalSpecialChars() {
		SortApplication sort = new SortApplication();
		String inFilePath = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR
				+ "testSortSimpleCapitalSpecialChars";
		sort.sortSimpleCapitalSpecialChars("sort " + inFilePath);
	}

	@Test
	public void testSortSimpleNumbersSpecialChars() {
		SortApplication sort = new SortApplication();
		String inFilePath = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR
				+ "testSortSimpleNumbersSpecialChars";
		sort.sortSimpleNumbersSpecialChars("sort " + inFilePath);
	}

	@Test
	public void testSortCapitalNumbersSpecialChars() {
		SortApplication sort = new SortApplication();
		String inFilePath = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR
				+ "testSortCapitalNumbersSpecialChars";
		sort.sortCapitalNumbersSpecialChars("sort " + inFilePath);
	}

	@Test
	public void testSortAll() {
		SortApplication sort = new SortApplication();
		String inFilePath = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortAll";
		sort.sortAll("sort " + inFilePath);
	}

	@Test(expected = SortException.class)
	public void testRunNullArgNullStdin() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();

		sort.run(null, null, System.out);
	}

	@Test(expected = SortException.class)
	public void testRunNullStdout() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRunOption";
		String[] args = {"-n", file};

		sort.run(args, System.in, null);

	}

	@Test(expected = SortException.class)
	public void testRandomTwoArg() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		System.setOut(new PrintStream(out));
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRunNoOption";
		String[] args = {file, "-l"};
		sort.run(args, System.in, System.out);
	}

	@Test
	public void testRunSortStartLineNumberNoOpt() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRunNoOption";
		String[] args = {file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunSortStartLineNumberWithOpt() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRunOption";
		String[] args = {"-n", file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunSortStartLineNumberWithOptSpace() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRunOptionSpace";
		String[] args = {"-n", file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunMixed() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRunMixed";
		String[] args = {file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunSortAll() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortAll";
		String[] args = {file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunSortCapitalNumbers() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortCapitalNumbers";
		String[] args = {file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunSortCapitalSpecialChars() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file =
				RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortCapitalSpecialChars";
		String[] args = {file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunSortNumbers() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortNumbers";
		String[] args = {file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunSortNumbersSpecialChars() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file =
				RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortNumbersSpecialChars";
		String[] args = {file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunSortSimpleCapital() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortSimpleCapital";
		String[] args = {file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunSortSimpleCapitalSpace() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file =
				RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortSimpleCapitalSpace";
		String[] args = {file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunSortSimpleCapitalNumber() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file =
				RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortSimpleCapitalNumber";
		String[] args = {file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunSortSimpleCapitalSpecialChars() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR
				+ "testSortSimpleCapitalSpecialChars";
		String[] args = {file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunSortSimpleNumbers() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortSimpleNumbers";
		String[] args = {file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunSortSimpleSpecialChars() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file =
				RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortSimpleSpecialChars";
		String[] args = {file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunSortSpecialChars() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortSpecialChars";
		String[] args = {file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunSortStringsCaptial() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortStringsCapital";
		String[] args = {file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunSortStringsSimple() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortStringsSimple";
		String[] args = {file};
		System.setOut(new PrintStream(out));
		sort.run(args, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunSortEmptyFile() throws AbstractApplicationException {
		String expectedOutput = "";
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testSortEmptyFile";
		String[] args = {file};
		System.setOut(new PrintStream(out));

		sort.run(args, System.in, System.out);

		try {
			expectedOutput = new String(Files.readAllBytes(Paths.get(
					RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testSortEmptyFile")));
		} catch (IOException e) {
			System.out.println(e);
		}

		assertEquals(expectedOutput, out.toString());
	}

	@Test
	public void testSortFromStdin() throws AbstractApplicationException {
		String inputFile =
				RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR + "testRunSortFromStdin";
		String input = "";
		String[] args = {};
		System.setOut(new PrintStream(out));
		SortApplication sort = new SortApplication();

		try {
			input = new String(Files.readAllBytes(Paths.get(inputFile)));
		} catch (IOException e) {
			System.out.println(e);
		}

		InputStream is = new ByteArrayInputStream(input.getBytes());

		sort.run(args, is, System.out);
		out.toString();
	}

	@Test(expected = SortException.class)
	public void testRunSortNullStdout() throws AbstractApplicationException, ShellException {
		ShellImpl shell = new ShellImpl();
		shell.parseAndEvaluate("sort -n");
	}

}
