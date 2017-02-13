package test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.app.SortApplication;

public class SortApplicationTest {
	final static String PATH_SEPARATOR = File.separator;
	final static String LINE_SEPARATOR = System.getProperty("line.separator");
	final static String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "sort"
			+ PATH_SEPARATOR;
	private ByteArrayOutputStream out = new ByteArrayOutputStream();

	@BeforeClass
	public static void setUpOnce() {
		try {
			copyInputToTestFolder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDownOnce() {
	}

	private static void copyInputToTestFolder() throws IOException {
		File folder = new File(RELATIVE_TEST_DIRECTORY + "input");
		// System.out.println(folder.getAbsolutePath());
		for (final File fileEntry : folder.listFiles()) {
			Path source = Paths.get(fileEntry.getAbsolutePath());
			Path dest = Paths.get(RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + fileEntry.getName());
			Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	@Test
	public void testSortStringsSimple() {

		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortStringsSimple";
		assertEquals("simple sort",
				"a" + LINE_SEPARATOR + "b" + LINE_SEPARATOR + "c" + LINE_SEPARATOR + "d" + LINE_SEPARATOR + "e",
				sort.sortStringsSimple("sort " + file));
	}

	@Test
	public void testSortStringsCapital() {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortStringsCapital";
		assertEquals("simple sort",
				"A" + LINE_SEPARATOR + "B" + LINE_SEPARATOR + "L" + LINE_SEPARATOR + "L" + LINE_SEPARATOR + "S",
				sort.sortStringsCapital("sort " + file));
	}

	@Test
	public void testSortNumbers() {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortNumbers";
		assertEquals("simple sort", "1" + LINE_SEPARATOR + "10" + LINE_SEPARATOR + "2" + LINE_SEPARATOR + "6"
				+ LINE_SEPARATOR + "8" + LINE_SEPARATOR + "9", sort.sortNumbers("sort " + file));
	}

	@Test
	public void testSortSimpleCapital() {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortSimpleCapital";
		assertEquals("simple sort",
				"A" + LINE_SEPARATOR + "E" + LINE_SEPARATOR + "O" + LINE_SEPARATOR + "T" + LINE_SEPARATOR + "f"
						+ LINE_SEPARATOR + "r" + LINE_SEPARATOR + "s" + LINE_SEPARATOR + "t" + LINE_SEPARATOR + "w",
				sort.sortSimpleCapital("sort " + file));
	}

	@Test
	public void testSortSimpleNumbers() {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortSimpleNumbers";
		assertEquals("simple sort",
				"4" + LINE_SEPARATOR + "5" + LINE_SEPARATOR + "6" + LINE_SEPARATOR + "7" + LINE_SEPARATOR + "h"
						+ LINE_SEPARATOR + "i" + LINE_SEPARATOR + "j" + LINE_SEPARATOR + "k" + LINE_SEPARATOR + "l",
				sort.sortSimpleNumbers("sort " + file));
	}

//	@Test
//	public void testSortSimpleSpecialChars() {
//		SortApplication sort = new SortApplication();
//		String file = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortSimpleSpecialChars";
//		assertEquals("simple sort",
//				"\"" + LINE_SEPARATOR + "\'" + LINE_SEPARATOR + "*" + LINE_SEPARATOR + ";" + LINE_SEPARATOR + "<"
//						+ LINE_SEPARATOR + ">" + LINE_SEPARATOR + "`" + LINE_SEPARATOR + "|",
//				sort.sortSimpleNumbers("sort " + file));
//	}

	@Test
	public void testSortSpecialChars() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String inFilePath = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortSpecialChars";
		String outFilePath = RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testSortSpecialChars";
		String expectedOut = "";
		String[] args = { inFilePath };

		try {
			expectedOut = new String(Files.readAllBytes(Paths.get(outFilePath)));
		} catch (IOException ex) {
			System.out.println(ex);
		}

		assertEquals(expectedOut, sort.sortSpecialChars("sort " + inFilePath));
	}
	
	@Test
	public void testSortSpecialCharsWithOpt() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String inFilePath = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortSpecialChars";
		String outFilePath = RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testSortSpecialChars";
		String expectedOut = "";
		String[] args = { inFilePath };

		try {
			expectedOut = new String(Files.readAllBytes(Paths.get(outFilePath)));
		} catch (IOException ex) {
			System.out.println(ex);
		}

		assertEquals(SortException.class.getName(), sort.sortSpecialChars("sort -n " + inFilePath));
	}
	
	@Test
	public void testSortCapitalNumbers() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String inFilePath = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortCapitalNumbers";
		String outFilePath = RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testSortCapitalNumbers";
		String expectedOut = "";
		String[] args = { inFilePath };

		try {
			expectedOut = new String(Files.readAllBytes(Paths.get(outFilePath)));
		} catch (IOException ex) {
			System.out.println(ex);
		}

		assertEquals(expectedOut, sort.sortCapitalNumbers("sort " + inFilePath));
	}
	
	
	//TODO Modify later to throw exception
	@Test
	public void testSortCapitalNumbersWithOpt() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String inFilePath = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortCapitalNumbers";
		String outFilePath = RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testSortCapitalNumbersWithOpt";
		String expectedOut = "";
		String[] args = { inFilePath };

		try {
			expectedOut = new String(Files.readAllBytes(Paths.get(outFilePath)));
		} catch (IOException ex) {
			System.out.println(ex);
		}
		
		assertEquals(SortException.class.getName(), sort.sortCapitalNumbers("sort -n " + inFilePath));
	}
	
	@Test
	public void testSortCapitalSpecialChars(){
		SortApplication sort = new SortApplication();
		String inFilePath = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortCapitalSpecialChars";
		String outFilePath = RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testSortCapitalSpecialChars";
		String expectedOut = "";
		String[] args = { inFilePath };

		try {
			expectedOut = new String(Files.readAllBytes(Paths.get(outFilePath)));
		} catch (IOException ex) {
			System.out.println(ex);
		}

		assertEquals(expectedOut, sort.sortCapitalSpecialChars("sort " + inFilePath));
	}
	
	@Test
	public void testSortNumbersSpecialChars(){
		SortApplication sort = new SortApplication();
		String inFilePath = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortNumbersSpecialChars";
		String outFilePath = RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testSortNumbersSpecialChars";
		String expectedOut = "";
		String[] args = { inFilePath };

		try {
			expectedOut = new String(Files.readAllBytes(Paths.get(outFilePath)));
		} catch (IOException ex) {
			System.out.println(ex);
		}

		assertEquals(expectedOut, sort.sortNumbersSpecialChars("sort " + inFilePath));
	}
	
	@Test
	public void testSortSimpleSpecialChars() throws AbstractApplicationException{
		SortApplication sort = new SortApplication();
		String inFilePath = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortSimpleSpecialChars";
		String outFilePath = RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testSortSimpleSpecialChars";
		String expectedOut = "";
		String[] args = { inFilePath };

		try {
			expectedOut = new String(Files.readAllBytes(Paths.get(outFilePath)));
		} catch (IOException ex) {
			System.out.println(ex);
		}

		assertEquals(expectedOut, sort.sortSimpleSpecialChars("sort " + inFilePath));
	}

	@Test
	public void testSortSimpleCapitalNumber(){
		SortApplication sort = new SortApplication();
		String inFilePath = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortSimpleSpecialChars";
		String outFilePath = RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testSortSimpleSpecialChars";
		String expectedOut = "";
		String[] args = { inFilePath };

		try {
			expectedOut = new String(Files.readAllBytes(Paths.get(outFilePath)));
		} catch (IOException ex) {
			System.out.println(ex);
		}

		assertEquals(expectedOut, sort.sortSimpleCapitalNumber("sort " + inFilePath));
	}
	
	@Test
	public void testSortSimpleCapitalSpecialChars(){
		SortApplication sort = new SortApplication();
		String inFilePath = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testSortSimpleCapitalSpecialChars";
		String outFilePath = RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testSortSimpleCapitalSpecialChars";
		String expectedOut = "";
		String[] args = { inFilePath };

		try {
			expectedOut = new String(Files.readAllBytes(Paths.get(outFilePath)));
		} catch (IOException ex) {
			System.out.println(ex);
		}

		assertEquals(expectedOut, sort.sortSimpleCapitalSpecialChars("sort " + inFilePath));
	}
	
	@Test(expected = SortException.class)
	public void testRunNullArgNullStdin() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();

		sort.run(null, null, System.out);
	}

	@Test(expected = SortException.class)
	public void testRunNullStdout() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testRunOption";
		String[] args = { "-n", file };

		sort.run(args, System.in, null);

	}

	@Test(expected = SortException.class)
	public void testRandomTwoArg() throws AbstractApplicationException {
		SortApplication sort = new SortApplication();
		System.setOut(new PrintStream(out));
		String file = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testRunNoOption";
		String[] args = { file, "-l" };
		sort.run(args, System.in, System.out);
	}

	@Test
	public void testRunSortStartLineNumberNoOpt() throws AbstractApplicationException {
		String expectedOutput = "";
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testRunNoOption";
		String[] args = { file };
		System.setOut(new PrintStream(out));

		sort.run(args, System.in, System.out);

		try {
			expectedOutput = new String(Files.readAllBytes(
					Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testRunNoOption")));
		} catch (IOException e) {
			System.out.println(e);
		}

		assertEquals(expectedOutput, out.toString());
	}

	@Test
	public void testRunSortStartLineNumberWithOpt() throws AbstractApplicationException {
		String expectedOutput = "";
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testRunOption";
		String[] args = { "-n", file };
		System.setOut(new PrintStream(out));

		sort.run(args, System.in, System.out);

		try {
			expectedOutput = new String(Files
					.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testRunOption")));
		} catch (IOException e) {
			System.out.println(e);
		}

		assertEquals(expectedOutput, out.toString());
	}

	@Test
	public void testRunMixed() throws AbstractApplicationException {
		String expectedOutput = "";
		SortApplication sort = new SortApplication();
		String file = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testRunMixed";
		String[] args = { file };
		System.setOut(new PrintStream(out));

		sort.run(args, System.in, System.out);

		try {
			expectedOutput = new String(Files
					.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testRunMixed")));
		} catch (IOException e) {
			System.out.println(e);
		}

		assertEquals(expectedOutput, out.toString());
	}

	@Test
	public void testSortFromStdin() throws AbstractApplicationException {
		String inputFile = RELATIVE_TEST_DIRECTORY + "toTest" + PATH_SEPARATOR + "testRunSortFromStdin";
		String input = "";
		String outputFile = RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR + "testRunSortFromStdin";
		String expectedOutput = "";
		String[] args = {};
		System.setOut(new PrintStream(out));
		SortApplication sort = new SortApplication();

		try {
			input = new String(Files.readAllBytes(Paths.get(inputFile)));
		} catch (IOException e) {
			System.out.println(e);
		}

		InputStream is = new ByteArrayInputStream(input.getBytes());

		try {
			expectedOutput = new String(Files.readAllBytes(Paths.get(outputFile)));
		} catch (IOException e) {
			System.out.println(e);
		}

		sort.run(args, is, System.out);

		assertEquals(expectedOutput, out.toString());
	}

}
