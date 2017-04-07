
package integration.cat;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class CatIntegrationTest {
	private static ShellImpl shell;
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String PATH_SEPARATOR = File.separator;
	private static final String RELATIVE_INPUT_DIR =
			"src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "integration" + PATH_SEPARATOR
					+ "cat" + PATH_SEPARATOR + "input" + PATH_SEPARATOR;
	private static final String RELATIVE_EXP_DIR = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR
			+ "integration" + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "expected" + PATH_SEPARATOR;
	private static final String ABS_DIR = Environment.currentDirectory + PATH_SEPARATOR;
	private static final String RELATIVE_FOOBAR = RELATIVE_INPUT_DIR + "foobar";
	private static final String RELATIVE_RICK1 = RELATIVE_INPUT_DIR + "rickroll1";
	private static final String RELATIVE_RICK2 = RELATIVE_INPUT_DIR + "rickroll2";
	private static final String RELATIVE_TOTO1 = RELATIVE_INPUT_DIR + "toto1";
	private static final String RELATIVE_TOTO2 = RELATIVE_INPUT_DIR + "toto2";
	private static final String RELATIVE_STRANGER1 = RELATIVE_INPUT_DIR + "stranger1";
	private static final String RELATIVE_FILENAMES = RELATIVE_INPUT_DIR + "filenames";

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		StringBuilder toWrite = new StringBuilder();
		toWrite.append(RELATIVE_INPUT_DIR);
		toWrite.append("rickroll1");
		toWrite.append(LINE_SEPARATOR);
		toWrite.append(RELATIVE_INPUT_DIR);
		toWrite.append("rickroll2");
		toWrite.append(LINE_SEPARATOR);
		toWrite.append(RELATIVE_INPUT_DIR);
		toWrite.append("toto1");
		toWrite.append(LINE_SEPARATOR);
		toWrite.append(RELATIVE_INPUT_DIR);
		toWrite.append("toto2");
		toWrite.append(LINE_SEPARATOR);
		toWrite.append(RELATIVE_INPUT_DIR);
		toWrite.append("stranger1");
		writeToFile(RELATIVE_FILENAMES, toWrite.toString());
	}

	@AfterClass
	public static void tearDownAfterClass() {
		File toDelete = new File(RELATIVE_FILENAMES);
		toDelete.delete();
	}

	@Before
	public void setUp() throws Exception {
		shell = new ShellImpl();
		Environment.setDefaultDirectory();
	}

	@After
	public void tearDown() throws Exception {
		Environment.setDefaultDirectory();
	}

	@Test
	public void testIntegrateEcho()
			throws AbstractApplicationException, ShellException, IOException {
		String command = "cat `echo \"" + RELATIVE_RICK1 + " " + RELATIVE_RICK2 + " "
				+ RELATIVE_TOTO1 + " " + RELATIVE_TOTO2 + " " + RELATIVE_STRANGER1 + "\"`";
		String expected = new String(Files.readAllBytes(Paths.get(RELATIVE_EXP_DIR + "catAll")));
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateEcho2() throws AbstractApplicationException, ShellException {
		String command = "cat `echo  " + RELATIVE_FILENAMES + "`";
		String expected = RELATIVE_RICK1 + LINE_SEPARATOR + RELATIVE_RICK2 + LINE_SEPARATOR
				+ RELATIVE_TOTO1 + LINE_SEPARATOR + RELATIVE_TOTO2 + LINE_SEPARATOR
				+ RELATIVE_STRANGER1 + LINE_SEPARATOR;
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testNonSdinApps() throws AbstractApplicationException, ShellException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
		String command = "cat " + RELATIVE_RICK2 + " ; cd " + RELATIVE_INPUT_DIR + "; pwd ; date";
		String expected = "Never gonna give you up" + LINE_SEPARATOR + "Never gonna let you down"
				+ LINE_SEPARATOR + "Never gonna run around and desert you" + LINE_SEPARATOR
				+ "Never gonna make you cry" + LINE_SEPARATOR + "Never gonna say goodbye"
				+ LINE_SEPARATOR + "Never gonna tell a lie and hurt you" + LINE_SEPARATOR
				+ LINE_SEPARATOR + Environment.currentDirectory + PATH_SEPARATOR
				+ RELATIVE_INPUT_DIR.substring(0, RELATIVE_INPUT_DIR.length() - 1) + LINE_SEPARATOR
				+ ZonedDateTime.now().format(formatter) + LINE_SEPARATOR;
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateTail()
			throws AbstractApplicationException, ShellException, IOException {
		String command = "cat `cat " + RELATIVE_FILENAMES + "` | tail";
		String expected = new String(Files.readAllBytes(Paths.get(RELATIVE_EXP_DIR + "catTail")));
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateHead()
			throws AbstractApplicationException, ShellException, IOException {
		String command = "cat `cat " + RELATIVE_FILENAMES + "` | head";
		String expected = new String(Files.readAllBytes(Paths.get(RELATIVE_EXP_DIR + "catHead")));
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateSort() throws AbstractApplicationException, ShellException {
		String command = "cat " + RELATIVE_FILENAMES + " | sort";
		String expected = RELATIVE_RICK1 + LINE_SEPARATOR + RELATIVE_RICK2 + LINE_SEPARATOR
				+ RELATIVE_STRANGER1 + LINE_SEPARATOR + RELATIVE_TOTO1 + LINE_SEPARATOR
				+ RELATIVE_TOTO2 + LINE_SEPARATOR;
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateCal()
			throws AbstractApplicationException, ShellException, IOException {
		String command = "cat " + RELATIVE_FILENAMES + "; cal 3 2017";
		String expected = RELATIVE_RICK1 + LINE_SEPARATOR + RELATIVE_RICK2 + LINE_SEPARATOR
				+ RELATIVE_TOTO1 + LINE_SEPARATOR + RELATIVE_TOTO2 + LINE_SEPARATOR
				+ RELATIVE_STRANGER1 + LINE_SEPARATOR
				+ new String(Files.readAllBytes(Paths.get(RELATIVE_EXP_DIR + "currentMonth")))
				+ LINE_SEPARATOR;
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateSed() throws AbstractApplicationException, ShellException {
		String command = "cat " + RELATIVE_RICK2 + "| sed s/Never/Always/g";
		String expected = "Always gonna give you up" + LINE_SEPARATOR + "Always gonna let you down"
				+ LINE_SEPARATOR + "Always gonna run around and desert you" + LINE_SEPARATOR
				+ "Always gonna make you cry" + LINE_SEPARATOR + "Always gonna say goodbye"
				+ LINE_SEPARATOR + "Always gonna tell a lie and hurt you" + LINE_SEPARATOR;
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateWc() throws AbstractApplicationException, ShellException {
		String command = "cat " + RELATIVE_FILENAMES + " " + RELATIVE_RICK2 + " | wc";
		String expected = "     376      38       9" + LINE_SEPARATOR;
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateGrep() throws AbstractApplicationException, ShellException {
		String command = "cat " + RELATIVE_RICK2 + " | grep you";
		String expected = "Never gonna give you up" + LINE_SEPARATOR + "Never gonna let you down"
				+ LINE_SEPARATOR + "Never gonna run around and desert you" + LINE_SEPARATOR
				+ "Never gonna make you cry" + LINE_SEPARATOR
				+ "Never gonna tell a lie and hurt you" + LINE_SEPARATOR;
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateMultiple() throws AbstractApplicationException, ShellException {
		String command = "cd " + RELATIVE_INPUT_DIR
				+ ";    cat `echo \"foobar\"\' \'` `echo \"\"``echo \"foobar\"` `echo \" foobar \"` `echo foobar` | sort | sed suffoourabug";
		String expected = LINE_SEPARATOR
				+ "foobarraboorabbarraboorabbarraboorabbarraboorabbarraboorabbarraboorabbarraboo"
				+ "rabbarraboorabbarraboorabbarraboorabbarraboorabbarraboorabbarraboorabbarraboo"
				+ "rabbarraboorabbarraboorabbarraboorabbarraboorabbarraboorabbarraboorabbarraboo"
				+ "rabbarraboorabbarraboorabbarraboorabbarraboorabbarraboorabbarraboorabbarraboof"
				+ LINE_SEPARATOR;
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	public static void writeToFile(String filename, String toWrite) throws IOException {
		try {
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));
			bw.write(toWrite);
			bw.close();
		} catch (IOException e) {
			throw e;
		}

	}
}
