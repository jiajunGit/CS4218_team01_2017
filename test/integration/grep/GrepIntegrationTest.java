package integration.grep;

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

public class GrepIntegrationTest {
	private static ShellImpl shell;
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String PATH_SEPARATOR = File.separator;
	private static final String RELATIVE_INPUT_DIR = "test" + PATH_SEPARATOR + "integration" + PATH_SEPARATOR
					+ "grep" + PATH_SEPARATOR + "input" + PATH_SEPARATOR;
	private static final String RELATIVE_EXP_DIR = "test" + PATH_SEPARATOR + "integration" + PATH_SEPARATOR
					+ "grep" + PATH_SEPARATOR + "expected" + PATH_SEPARATOR;
	private static final String ABS_DIR = Environment.currentDirectory + PATH_SEPARATOR;
	private static final String RELATIVE_RICK1 = RELATIVE_INPUT_DIR + "rickroll1";
	private static final String RELATIVE_RICK2 = RELATIVE_INPUT_DIR + "rickroll2";
	private static final String RELATIVE_TOTO1 = RELATIVE_INPUT_DIR + "toto1";
	private static final String RELATIVE_TOTO2 = RELATIVE_INPUT_DIR + "toto2";
	private static final String RELATIVE_CRAWL = RELATIVE_INPUT_DIR + "crawl";
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
	public void testIntegrateNonStdinApps() throws AbstractApplicationException, ShellException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
		String command =
				"grep that " + RELATIVE_STRANGER1 + "; cd " + RELATIVE_INPUT_DIR + "; pwd; date";
		String expected = "If you say that you are mine" + LINE_SEPARATOR + LINE_SEPARATOR
				+ Environment.currentDirectory + PATH_SEPARATOR
				+ RELATIVE_INPUT_DIR.substring(0, RELATIVE_INPUT_DIR.length() - 1) + LINE_SEPARATOR
				+ ZonedDateTime.now().format(formatter) + LINE_SEPARATOR;
		String output = shell.parseAndEvaluate(command);
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateCat() throws AbstractApplicationException, ShellException {
		String command = "grep that `cat " + RELATIVE_FILENAMES + "`";
		String expected = Environment.getAbsPath(RELATIVE_TOTO1)
				+ ": The moonlit wings reflect the stars that guide me towards salvation"
				+ LINE_SEPARATOR + Environment.getAbsPath(RELATIVE_TOTO2)
				+ ": There's nothing that a hundred men or more could ever do" + LINE_SEPARATOR
				+ Environment.getAbsPath(RELATIVE_STRANGER1) + ": If you say that you are mine"
				+ LINE_SEPARATOR;
		String output = shell.parseAndEvaluate(command);
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateEcho() throws AbstractApplicationException, ShellException {
		String command = "grep down `echo " + RELATIVE_RICK2 + "`";
		String expected = "Never gonna let you down" + LINE_SEPARATOR;
		String output = shell.parseAndEvaluate(command);
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateHead() throws AbstractApplicationException, ShellException {
		String command = "grep you `head -n 2 " + RELATIVE_FILENAMES + "`";
		String expected = Environment.getAbsPath(RELATIVE_RICK1)
				+ ": Your heart's been aching but you're too shy to say it" + LINE_SEPARATOR
				+ Environment.getAbsPath(RELATIVE_RICK2) + ": Never gonna give you up"
				+ LINE_SEPARATOR + Environment.getAbsPath(RELATIVE_RICK2)
				+ ": Never gonna let you down" + LINE_SEPARATOR
				+ Environment.getAbsPath(RELATIVE_RICK2) + ": Never gonna run around and desert you"
				+ LINE_SEPARATOR + Environment.getAbsPath(RELATIVE_RICK2)
				+ ": Never gonna make you cry" + LINE_SEPARATOR
				+ Environment.getAbsPath(RELATIVE_RICK2) + ": Never gonna tell a lie and hurt you"
				+ LINE_SEPARATOR;
		String output = shell.parseAndEvaluate(command);
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateTail() throws AbstractApplicationException, ShellException {
		String command = "grep gonna `tail -n 3 " + RELATIVE_FILENAMES + "`";
		String expected = Environment.getAbsPath(RELATIVE_TOTO2)
				+ ": It's gonna take a lot to drag me away from you" + LINE_SEPARATOR;
		String output = shell.parseAndEvaluate(command);
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateCal()
			throws AbstractApplicationException, ShellException, IOException {
		String command = "grep Never " + RELATIVE_RICK2 + "; cal 3 2017";
		String expected = "Never gonna give you up" + LINE_SEPARATOR + "Never gonna let you down"
				+ LINE_SEPARATOR + "Never gonna run around and desert you" + LINE_SEPARATOR
				+ "Never gonna make you cry" + LINE_SEPARATOR + "Never gonna say goodbye"
				+ LINE_SEPARATOR + "Never gonna tell a lie and hurt you" + LINE_SEPARATOR;
		expected = expected
				+ new String(Files.readAllBytes(Paths.get(RELATIVE_EXP_DIR + "currentMonth")))
				+ LINE_SEPARATOR;
		String output = shell.parseAndEvaluate(command);
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateSort() throws AbstractApplicationException, ShellException {
		String command = "grep that `cat " + RELATIVE_FILENAMES + "` | sort";
		String expected = Environment.getAbsPath(RELATIVE_STRANGER1)
				+ ": If you say that you are mine" + LINE_SEPARATOR
				+ Environment.getAbsPath(RELATIVE_TOTO1)
				+ ": The moonlit wings reflect the stars that guide me towards salvation"
				+ LINE_SEPARATOR + Environment.getAbsPath(RELATIVE_TOTO2)
				+ ": There's nothing that a hundred men or more could ever do" + LINE_SEPARATOR;
		String output = shell.parseAndEvaluate(command);
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateSed() throws AbstractApplicationException, ShellException {
		String command = "grep that `cat " + RELATIVE_FILENAMES + "` | sed s/that/this/g";
		String expected = Environment.getAbsPath(RELATIVE_TOTO1)
				+ ": The moonlit wings reflect the stars this guide me towards salvation"
				+ LINE_SEPARATOR + Environment.getAbsPath(RELATIVE_TOTO2)
				+ ": There's nothing this a hundred men or more could ever do" + LINE_SEPARATOR
				+ Environment.getAbsPath(RELATIVE_STRANGER1) + ": If you say this you are mine"
				+ LINE_SEPARATOR;
		String output = shell.parseAndEvaluate(command);
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateWc() throws AbstractApplicationException, ShellException {
		String command = "grep toto " + RELATIVE_FILENAMES + " | wc";
		String expected = "      68       2       1" + LINE_SEPARATOR;
		String output = shell.parseAndEvaluate(command);
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateMultiple() throws AbstractApplicationException, ShellException {
		String command = "cd " + RELATIVE_INPUT_DIR
				+ ";grep \"`echo \"DEATH STAR\"`\" `echo crawl` | sed s/\" \"//g | wc -m";
		String expected = LINE_SEPARATOR + "     152" + LINE_SEPARATOR;
		String output = shell.parseAndEvaluate(command);
		assertEquals(expected, output);
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
