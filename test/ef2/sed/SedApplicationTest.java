package ef2.sed;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.impl.app.SedApplication;

public class SedApplicationTest {

	private static SedApplication sed;

	private static final String NEWLINE = System.lineSeparator();
	public static final String PATH_SEPARATOR = File.separator;
	public static final String RELATIVE_TEST_DIRECTORY = "test" + PATH_SEPARATOR + "ef2" + PATH_SEPARATOR + "sed" + PATH_SEPARATOR + "sed";
	public static final String ABSOLUTE_TEST_DIRECTORY = Environment.currentDirectory + PATH_SEPARATOR + RELATIVE_TEST_DIRECTORY;

	private static final String TWO_LINE_FILE_PATH = "test" + PATH_SEPARATOR + "ef2" + PATH_SEPARATOR + "sed" + PATH_SEPARATOR + "input" + PATH_SEPARATOR + "two-lines.txt";
	private static final String EMPTY_FILE_PATH = "test" + PATH_SEPARATOR + "ef2" + PATH_SEPARATOR + "sed" + PATH_SEPARATOR + "input" + PATH_SEPARATOR + "empty.txt";
	private static final String NUMBER_FILE_PATH = "test" + PATH_SEPARATOR + "ef2" + PATH_SEPARATOR + "sed" + PATH_SEPARATOR + "input" + PATH_SEPARATOR + "number.txt";
	private static final String HELLO_WORLD_FILE_PATH = "test" + PATH_SEPARATOR + "ef2" + PATH_SEPARATOR + "sed" + PATH_SEPARATOR + "input" + PATH_SEPARATOR + "hello world.txt";
	
	private static InputStream twoLineFileInputStream;
	private static InputStream emptyFileInputStream;
	private static InputStream numberFileInputStream;
	private static InputStream hellowWorldFileInputStream;
	
	private InputStream stdin;
	private OutputStream stdout;
	
	@BeforeClass
	public static void setup() {
		Environment.setDefaultDirectory();
		sed = new SedApplication();
	}

	@AfterClass
	public static void reset() {
	    Environment.setDefaultDirectory();
	}
	
	@Before
	public void setupBeforeTest() throws FileNotFoundException {
		
		assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in"));
		assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in2"));
		assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out"));
		
		stdout = new ByteArrayOutputStream();
		
		twoLineFileInputStream = new FileInputStream(new File(TWO_LINE_FILE_PATH));
		emptyFileInputStream = new FileInputStream(new File(EMPTY_FILE_PATH));
		numberFileInputStream = new FileInputStream(new File(NUMBER_FILE_PATH));
		hellowWorldFileInputStream = new FileInputStream(new File(HELLO_WORLD_FILE_PATH));
	}

	@After
	public void tearDownAfterTest() {
		assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in"));
		assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in2"));
		assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out"));
	}

	@Test
	public void testSedWithSpaceRegex() throws SedException {

		String[] arg = { "s/ /tail/g" };
		String content = NEWLINE + NEWLINE + "  head heat of the moment hi" + NEWLINE
				+ "piehihahead head" + NEWLINE + "head who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "tailtailheadtailheattailoftailthetailmomenttailhi"
				+ NEWLINE + "piehihaheadtailhead" + NEWLINE + "headtailwhotailaretailu?"
				+ NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedWithEmptyRegex() throws SedException {

		String[] arg = { "s//tail/g" };
		String content = NEWLINE + NEWLINE + "  head heat of the moment hi" + NEWLINE
				+ "piehihahead head" + NEWLINE + "head who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  head heat of the moment hi" + NEWLINE
				+ "piehihahead head" + NEWLINE + "head who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedWithSpaceReplacement() throws SedException {

		String[] arg = { "s/head/ /g" };
		String content = NEWLINE + NEWLINE + "  head heat of the moment hi" + NEWLINE
				+ "piehihahead head" + NEWLINE + "head who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "    heat of the moment hi" + NEWLINE
				+ "piehiha   " + NEWLINE + "  who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedWithEmptyReplacement() throws SedException {

		String[] arg = { "s/head//g" };
		String content = NEWLINE + NEWLINE + "  head heat of the moment hi" + NEWLINE
				+ "piehihahead head" + NEWLINE + "head who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "   heat of the moment hi" + NEWLINE
				+ "piehiha " + NEWLINE + " who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedReplaceAllWithGSeparator() throws SedException {

		String[] arg = { "sghihihigagg" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  a heat of the moment hi" + NEWLINE
				+ "piehihaa a" + NEWLINE + "a who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedReplaceAllWithGSeparatorAndEmptyReplacement() throws SedException {

		String[] arg = { "sghihihiggg" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "   heat of the moment hi" + NEWLINE
				+ "piehiha " + NEWLINE + " who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedReplaceAllWithGSeparatorAndEmptyRegex() throws SedException {

		String[] arg = { "sggagg" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedReplaceAllWithGSeparatorAndEmptyRegexAndReplacement() throws SedException {

		String[] arg = { "sgggg" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test(expected = SedException.class)
	public void testSedReplaceAllWithTwoGSeparatorAtTheBack() throws SedException {

		String[] arg = { "sghihihigaggg" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedReplaceAllWithTwoGSeparatorInTheMiddle() throws SedException {

		String[] arg = { "sghihihiggagg" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedReplaceAllWithTwoGSeparatorAtTheFront() throws SedException {

		String[] arg = { "sgghihihigagg" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);
	}

	@Test
	public void testSedReplaceFirstWithGSeparator() throws SedException {

		String[] arg = { "sghihihigag" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  a heat of the moment hi" + NEWLINE
				+ "piehihaa hihihi" + NEWLINE + "a who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedReplaceFirstWithGSeparatorAndEmptyReplacement() throws SedException {

		String[] arg = { "sghihihigg" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "   heat of the moment hi" + NEWLINE
				+ "piehiha hihihi" + NEWLINE + " who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedReplaceFirstWithGSeparatorAndEmptyRegex() throws SedException {

		String[] arg = { "sggag" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedReplaceFirstWithGSeparatorAndEmptyRegexAndReplacement() throws SedException {

		String[] arg = { "sggg" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test(expected = SedException.class)
	public void testSedReplaceFirstWithTwoGSeparatorInTheMiddle() throws SedException {

		String[] arg = { "sghihihiggag" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  a heat of the moment hi" + NEWLINE
				+ "piehihaa hihihi" + NEWLINE + "a who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test(expected = SedException.class)
	public void testSedReplaceFirstWithTwoGSeparatorAtTheFront() throws SedException {

		String[] arg = { "sgghihihigag" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  a heat of the moment hi" + NEWLINE
				+ "piehihaa hihihi" + NEWLINE + "a who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedReplaceAllWithSSeparator() throws SedException {

		String[] arg = { "sshihihisasg" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  a heat of the moment hi" + NEWLINE
				+ "piehihaa a" + NEWLINE + "a who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedReplaceAllWithSSeparatorAndEmptyReplacement() throws SedException {

		String[] arg = { "sshihihissg" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "   heat of the moment hi" + NEWLINE
				+ "piehiha " + NEWLINE + " who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedReplaceAllWithSSeparatorAndEmptyRegex() throws SedException {

		String[] arg = { "sssasg" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedReplaceAllWithSSeparatorAndEmptyRegexAndReplacement() throws SedException {

		String[] arg = { "ssssg" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test(expected = SedException.class)
	public void testSedReplaceAllWithSSeparatorWithTwoSSeparatorsAtTheBack() throws SedException {

		String[] arg = { "sshihihisassg" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedReplaceAllWithSSeparatorWithTwoSSeparatorsInTheMiddle() throws SedException {

		String[] arg = { "sshihihissasg" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedReplaceAllWithSSeparatorWithTwoSSeparatorsAtTheFront() throws SedException {

		String[] arg = { "ssshihihisasg" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);
	}

	@Test
	public void testSedReplaceFirstWithSSeparator() throws SedException {

		String[] arg = { "sshihihisas" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  a heat of the moment hi" + NEWLINE
				+ "piehihaa hihihi" + NEWLINE + "a who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedReplaceFirstWithSSeparatorAndEmptyReplacement() throws SedException {

		String[] arg = { "sshihihiss" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "   heat of the moment hi" + NEWLINE
				+ "piehiha hihihi" + NEWLINE + " who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedReplaceFirstWithSSeparatorAndEmptyRegex() throws SedException {

		String[] arg = { "sssas" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedReplaceFirstWithSSeparatorAndEmptyRegexAndReplacement() throws SedException {

		String[] arg = { "ssss" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test(expected = SedException.class)
	public void testSedReplaceFirstWithTwoSSeparatorAtTheBack() throws SedException {

		String[] arg = { "sshihihisass" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedReplaceFirstWithTwoSSeparatorsInTheMiddle() throws SedException {

		String[] arg = { "sshihihissas" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedReplaceFirstWithTwoSSeparatorsAtTheFront() throws SedException {

		String[] arg = { "ssshihihisas" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);
	}

	@Test
	public void testSedWithBackSlashAsSeparator() throws SedException {

		String[] arg = { "s\\hihihi\\a\\g" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  a heat of the moment hi" + NEWLINE
				+ "piehihaa a" + NEWLINE + "a who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidRegex() throws SedException {

		String[] arg = { "s\\*?hihihi\\a\\g" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedReplaceAllWithExtraSeparatorAtTheBack() throws SedException {

		String[] arg = { "s\\hihihi\\a\\g\\" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedReplaceFirstWithMissingSeparatorAtTheBack() throws SedException {

		String[] arg = { "s\\hihihi\\a" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedReplaceFirstWithGSuffixAtTheBack() throws SedException {

		String[] arg = { "s\\hihihi\\g" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedReplaceFirstWithInvalidSuffixAtTheBack() throws SedException {

		String[] arg = { "s\\hihihi\\k" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedReplaceFirstWithNoReplacementStringAndSeparatorAtTheBack() throws SedException {

		String[] arg = { "s\\hihihi\\" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);
	}

	@Test
	public void testSedReplaceFirstWithNoReplacementStringAndNoRegex() throws SedException {

		String[] arg = { "s\\\\\\" };
		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedReplaceFirstWithOverlappingMatchedSubString() throws SedException {

		String[] arg = { "s\\hihihi\\%\\" };
		String content = NEWLINE + NEWLINE + "  hihihihihi heat of the moment hi"
				+ NEWLINE + "piehihahihihi hihihihihi" + NEWLINE + "hihihihhi who are u?"
				+ NEWLINE;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  %hihi heat of the moment hi" + NEWLINE
				+ "piehiha% hihihihihi" + NEWLINE + "%hhi who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedWithRelativeFilePath() throws SedException {

		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		String fileName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in";

		assertTrue(createFile(fileName, content));

		String relativePath = RELATIVE_TEST_DIRECTORY + PATH_SEPARATOR + "in";
		String[] arg = { "s\\hihihi\\a\\g", relativePath };

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, null, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  a heat of the moment hi" + NEWLINE
				+ "piehihaa a" + NEWLINE + "a who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test
	public void testSedWithAbsoluteFilePath() throws SedException {

		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		String fileName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in";

		assertTrue(createFile(fileName, content));

		String[] arg = { "s\\hihihi\\a\\g", fileName };

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, null, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEWLINE + NEWLINE + "  a heat of the moment hi" + NEWLINE
				+ "piehihaa a" + NEWLINE + "a who are u?" + NEWLINE;

		assertEquals(out, expected);
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidFilePath() throws SedException {

		String fileName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in2";

		String[] arg = { "s\\hihihi\\a\\g", fileName };

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, null, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedWithNullOutput() throws SedException {

		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		String fileName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in";

		assertTrue(createFile(fileName, content));

		String[] arg = { "s\\hihihi\\a\\g", fileName };

		sed.run(arg, null, null);
	}

	@Test(expected = SedException.class)
	public void testSedWithNullInputAndNoFileSpecified() throws SedException {

		String[] arg = { "s\\hihihi\\a\\g" };

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, null, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedWithNullArgs() throws SedException {

		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());

		sed.run(null, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedWithZeroArgCount() throws SedException {

		String[] arg = {};

		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());

		sed.run(arg, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedWithThreeArgCount() throws SedException {

		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		String fileOneName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in";
		String fileTwoName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in2";

		assertTrue(createFile(fileOneName, content));
		assertTrue(createFile(fileTwoName, content));

		String[] arg = { "s\\hihihi\\a\\g", fileOneName, fileTwoName };

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, null, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedWithClosedOutput() throws SedException, IOException {

		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		String fileName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in";
		String outFileName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out";

		assertTrue(createFile(fileName, content));

		String[] arg = { "s\\hihihi\\a\\g", fileName };

		FileOutputStream stdout = new FileOutputStream(outFileName);
		stdout.close();

		sed.run(arg, null, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedWithClosedInput() throws SedException, IOException {

		String content = NEWLINE + NEWLINE + "  hihihi heat of the moment hi" + NEWLINE
				+ "piehihahihihi hihihi" + NEWLINE + "hihihi who are u?" + NEWLINE;

		String fileName = ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in";

		assertTrue(createFile(fileName, content));

		String[] arg = { "s\\hihihi\\a\\g" };

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		FileInputStream stdin = new FileInputStream(fileName);
		stdin.close();

		sed.run(arg, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedWithDirectoryAsFilePath() throws SedException {

		String[] arg = { "s\\hihihi\\a\\g", ABSOLUTE_TEST_DIRECTORY };

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, null, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testSedWithNullArgument() throws SedException {
		String args[] = null;
		stdin = hellowWorldFileInputStream;
		sed.run(args, stdin, stdout);
		// "error on sed command - fails to throw exception with null args";
	}

	@Test(expected = SedException.class)
	public void testSedWithEmptyArgument() throws SedException {
		String args[] = {};
		stdin = new ByteArrayInputStream(new byte[0]);
		sed.run(args, stdin, stdout);
		// "error on sed command - fails to throw exception with empty args";
	}

	@Test(expected = SedException.class)
	public void testSedWithSingleArgument() throws SedException {
		String args[] = { "arg1" };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - fails to throw exception with insuffcient
		// args";
	}

	@Test(expected = SedException.class)
	public void testSedWithNullStdout() throws SedException {
		String args[] = { "s-c-a-g", EMPTY_FILE_PATH };
		stdin = new ByteArrayInputStream(new byte[0]);
		stdout = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - fails to throw exception with empty stdout";
	}

	@Test(expected = SedException.class)
	public void testSedWithNullStdinAndNonFileArg() throws SedException {
		String args[] = { "s|a|b|" };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - fails to throw exception with null stdin and
		// non file arg";
	}

	@Test(expected = SedException.class)
	public void testSedWithNullStdinAndNonExistentFile() throws SedException {
		String args[] = { "s|a|b|", "non-existent.txt" };
		stdin = null;
		sed.run(args, stdin, stdout);
		// String msg =
		// "error on sed command - fails to throw exception with null stdin and
		// non-existent file ";
	}

	@Test
	public void testSedWithEmptyFile() throws SedException {
		String args[] = { "s|a|b|", EMPTY_FILE_PATH };
		stdin = null;
		String expected = "";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with empty file";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithTwoLineFile() throws SedException {
		String args[] = { "s|a|b|", TWO_LINE_FILE_PATH };
		stdin = null;
		String expected = "Hey, good to know <you>!" + NEWLINE + "This is b small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ # no new line here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with two line file";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithFileThatNameIncludesSpace() throws SedException {
		String args[] = { "s|1|2|", HELLO_WORLD_FILE_PATH };
		stdin = null;
		String expected = "hello world!";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with file whose name contains space";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithEmptyFileInputStream() throws SedException {
		String args[] = { "s|a|b|" };
		stdin = emptyFileInputStream;
		String expected = "";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with empty file input stream";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithNumberFileInputStream() throws SedException {
		String args[] = { "s*3*76*" };
		stdin = numberFileInputStream;
		String expected = "01276456789";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with two line file input stream";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithTwoLineFileInputStream() throws SedException {
		// mock current directory to a fake non-root one
		String args[] = { "s|a|b|" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to know <you>!" + NEWLINE + "This is b small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ # no new line here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with two line file input stream";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithTwoLineFileInputStreamAndEmptyFile() throws SedException {
		String args[] = { "s|a|b|", EMPTY_FILE_PATH };
		stdin = twoLineFileInputStream;
		String expected = "";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with two line file inputstream and empty file";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test(expected = SedException.class)
	public void testSedWithExtraArgs1() throws SedException {
		String args[] = { "s|a|b|", EMPTY_FILE_PATH, "-l" };
		stdin = null;
		sed.run(args, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedWithExtraArgs2() throws SedException {
		String args[] = { "s|0|1|", NUMBER_FILE_PATH, TWO_LINE_FILE_PATH, "-l" };
		stdin = null;
		sed.run(args, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat1() throws SedException {
		String args[] = { "|0|1|", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat2() throws SedException {
		// mock current directory to a fake non-root one
		String args[] = { "s|0|1|gg", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat3() throws SedException {
		String args[] = { "s-0|1|g", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat4() throws SedException {
		String args[] = { "s-0|1g", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat5() throws SedException {
		String args[] = { "s|0| m| |g", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalRegex1() throws SedException {
		String args[] = { "s|0| m| |g", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal regular expression";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat6() throws SedException {
		String args[] = { "s||0||1||", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat7() throws SedException {
		// mock current directory to a fake non-root one
		String args[] = { "s|", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat8() throws SedException {
		String args[] = { "m|1|2|", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalRegrex() throws SedException {
		String args[] = { "s|[|1|", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal regrex";
	}

	@Test
	public void testSedWithGlobalReplacement() throws SedException {
		String args[] = { "s|l|*|g" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to know <you>!" + NEWLINE + "This is a sma** fi*e consists of {1+1+0} *ines."
				+ NEWLINE + "/* Hope this he*ps */ # no new *ine here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with global replacement";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithDifferentSeparator1() throws SedException {
		String args[] = { "ssls*sg" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to know <you>!" + NEWLINE + "This is a sma** fi*e consists of {1+1+0} *ines."
				+ NEWLINE + "/* Hope this he*ps */ # no new *ine here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with different separator";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithDifferentSeparator2() throws SedException {
		String args[] = { "s/l/*/g" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to know <you>!" + NEWLINE + "This is a sma** fi*e consists of {1+1+0} *ines."
				+ NEWLINE + "/* Hope this he*ps */ # no new *ine here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with different separator";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithDifferentSeparator3() throws SedException {
		String args[] = { "s,l,*,g" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to know <you>!" + NEWLINE + "This is a sma** fi*e consists of {1+1+0} *ines."
				+ NEWLINE + "/* Hope this he*ps */ # no new *ine here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with different separator";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithEmptyReplacment() throws SedException {
		String args[] = { "s|l||" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to know <you>!" + NEWLINE + "This is a smal file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this heps */ # no new line here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with empty replacement";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithComplexReplacement1() throws SedException {
		String args[] = { "s|no| *&/s\\$|" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to k *&/s\\$w <you>!" + NEWLINE + "This is a small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ #  *&/s\\$ new line here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex replacement";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithComplexReplacement2() throws SedException {
		String args[] = { "s|o|[^]|" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, g[^]od to know <you>!" + NEWLINE + "This is a small file c[^]nsists of {1+1+0} lines."
				+ NEWLINE + "/* H[^]pe this helps */ # no new line here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex replacement";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithComplexReplacement3() throws SedException {
		String args[] = { "s|o   |% #$%^&|" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to know <you>!" + NEWLINE + "This is a small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ # no new line here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex replacement";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithEmtpyRegexpAndEmptyReplacement() throws SedException {
	    String args[] = { "s|||" };
		stdin = twoLineFileInputStream;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - empty regex and empty replacement";
		String expected = "Hey, good to know <you>!" + NEWLINE
		                + "This is a small file consists of {1+1+0} lines." + NEWLINE
		                + "/* Hope this helps */ # no new line here";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithEmtpyRegexp() throws SedException {
		String args[] = { "s||m|g" };
		stdin = twoLineFileInputStream;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - empty regex";
		String expected = "Hey, good to know <you>!" + NEWLINE
		                + "This is a small file consists of {1+1+0} lines." + NEWLINE
		                + "/* Hope this helps */ # no new line here";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithComplexRegexp1() throws SedException {
		String args[] = { "s|^This|r|g" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to know <you>!" + NEWLINE + "r is a small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ # no new line here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex regular expression";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithComplexRegexp2() throws SedException {
		String args[] = { "s|o{2,3}d*|r|" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, gr to know <you>!" + NEWLINE + "This is a small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ # no new line here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex regular expression";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithComplexRegexp3() throws SedException {
		String args[] = { "s|[^a-zA-Z ]|-|g" };
		stdin = twoLineFileInputStream;
		String expected = "Hey- good to know -you----This is a small file consists of ------- lines----- Hope this helps -- - no new line here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex regular expression";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithComplexRegexp4() throws SedException {
		String args[] = { "s-good|know-r-" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, r to know <you>!" + NEWLINE + "This is a small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ # no new line here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex regular expression";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testReplaceFirstSubStringInFile() throws SedException {
		String cmd = "sed \"s|o||\"  " + TWO_LINE_FILE_PATH;
		String expected = "Hey, god to know <you>!" + NEWLINE + "This is a small file cnsists of {1+1+0} lines."
				+ NEWLINE + "/* Hpe this helps */ # no new line here" + NEWLINE;

		String msg = "error on sed command - incorrect output with method replaceFirstSubStringInFile";
		assertEquals(msg, expected, sed.replaceFirstSubStringInFile(cmd));
	}

	@Test
	public void testReplaceAllSubstringsInFile() throws SedException {
		String cmd = "sed \"s|o||g\"  " + TWO_LINE_FILE_PATH;
		String expected = "Hey, gd t knw <yu>!" + NEWLINE + "This is a small file cnsists f {1+1+0} lines." + NEWLINE
				+ "/* Hpe this helps */ # n new line here" + NEWLINE;

		String msg = "error on sed command - incorrect output with method replaceAllSubstringsInFile";
		assertEquals(msg, expected, sed.replaceAllSubstringsInFile(cmd));
	}

	@Test
	public void testReplaceFirstSubStringFromStdin() throws SedException {
		String cmd = "cat " + TWO_LINE_FILE_PATH + " | sed \"s|o||\" ";
		String expected = "Hey, god to know <you>!" + NEWLINE + "This is a small file cnsists of {1+1+0} lines."
				+ NEWLINE + "/* Hpe this helps */ # no new line here" + NEWLINE;

		String msg = "error on sed command - incorrect output with method replaceFirstSubStringFromStdin";
		assertEquals(msg, expected, sed.replaceFirstSubStringFromStdin(cmd));
	}

	@Test
	public void testReplaceAllSubstringsInStdin() throws SedException {
		String cmd = "cat " + TWO_LINE_FILE_PATH + " | sed \"s|o||g\" ";
		String expected = "Hey, gd t knw <yu>!" + NEWLINE + "This is a small file cnsists f {1+1+0} lines." + NEWLINE
				+ "/* Hpe this helps */ # n new line here" + NEWLINE;

		String msg = "error on sed command - incorrect output with method replaceAllSubstringsInStdin";
		assertEquals(msg, expected, sed.replaceAllSubstringsInStdin(cmd));
	}

	@Test
	public void testReplaceSubstringWithInvalidReplacement() throws SedException {
		String cmd = "cat " + TWO_LINE_FILE_PATH + " | sed \"s|||g\" ";
		String expected = "Hey, good to know <you>!" + NEWLINE
        		        + "This is a small file consists of {1+1+0} lines." + NEWLINE
        		        + "/* Hope this helps */ # no new line here" + NEWLINE;
		String msg = "error on sed command - incorrect output with method replaceSubstringWithInvalidReplacement";
		assertEquals(msg, expected, sed.replaceSubstringWithInvalidReplacement(cmd));
	}

	@Test
	public void testReplaceSubstringWithInvalidRegex() throws SedException {
		String cmd = "cat " + TWO_LINE_FILE_PATH + " | sed \"s|[||g\" ";
		stdin = twoLineFileInputStream;
		String expected = "sed: Invalid regex specified";
		String msg = "error on sed command - incorrect output with method replaceSubstringWithInvalidRegex";
		assertEquals(msg, expected, sed.replaceSubstringWithInvalidRegex(cmd));
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
