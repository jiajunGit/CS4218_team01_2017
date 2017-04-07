package integration.echo;

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

public class EchoIntegrationTest {
	private static ShellImpl shell;
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String PATH_SEPARATOR = File.separator;
	private static final String RELATIVE_INPUT_DIR =
			"src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "integration" + PATH_SEPARATOR
					+ "echo" + PATH_SEPARATOR + "input" + PATH_SEPARATOR;
	private static final String RELATIVE_EXP_DIR =
			"src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "integration" + PATH_SEPARATOR
					+ "echo" + PATH_SEPARATOR + "expected" + PATH_SEPARATOR;
	private static final String ABS_DIR = Environment.currentDirectory + PATH_SEPARATOR;
	private static final String RELATIVE_RICK2 = RELATIVE_INPUT_DIR + "rickroll2";
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
	public void testIntegrateCat()
			throws AbstractApplicationException, ShellException, IOException {
		String command = "echo abc`cat " + RELATIVE_RICK2 + "`def";
		String expected = "abcNever gonna give you up " + "Never gonna let you down "
				+ "Never gonna run around and desert you " + "Never gonna make you cry "
				+ "Never gonna say goodbye " + "Never gonna tell a lie and hurt youdef"
				+ LINE_SEPARATOR;
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateNonStdin()
			throws AbstractApplicationException, ShellException, IOException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
		String command = "echo hello" + " ; cd " + RELATIVE_INPUT_DIR + "; pwd ; date";;
		String expected = "hello" + LINE_SEPARATOR + LINE_SEPARATOR + Environment.currentDirectory
				+ PATH_SEPARATOR + RELATIVE_INPUT_DIR.substring(0, RELATIVE_INPUT_DIR.length() - 1)
				+ LINE_SEPARATOR + ZonedDateTime.now().format(formatter) + LINE_SEPARATOR;
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateHead()
			throws AbstractApplicationException, ShellException, IOException {
		String command = "echo 123`head -n 2 " + RELATIVE_STRANGER1 + "`456";
		String expected = "123Darlin\' you got to let me know " + "Should I stay or should I go?456"
				+ LINE_SEPARATOR;
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateTail()
			throws AbstractApplicationException, ShellException, IOException {
		String command = "echo gg`tail -n 3 " + RELATIVE_STRANGER1 + "`gg";
		String expected = "ggI'll be here 'til the end of time " + "So you got to let me know "
				+ "Should I stay or should I go?gg" + LINE_SEPARATOR;
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateCal()
			throws AbstractApplicationException, ShellException, IOException {
		String command = "echo \"`cal 3 2017`\"";
		String expected =
				"     March 2017      Su Mo Tu We Th Fr Sa          1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31   "
						+ LINE_SEPARATOR;
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateGrep()
			throws AbstractApplicationException, ShellException, IOException {
		String command = "echo \"Somebody once told me the world is gonna roll me "
				+ "I aint the sharpest tool in the shed\" | grep world";
		String expected = "Somebody once told me the world is gonna roll me "
				+ "I aint the sharpest tool in the shed" + LINE_SEPARATOR;
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateSort()
			throws AbstractApplicationException, ShellException, IOException {
		String command = "echo gg`sort " + RELATIVE_TOTO2 + "`mu";
		String expected = "ggGonna take some time to do the things we never have "
				+ "I bless the rains down in Africa "
				+ "It\'s gonna take a lot to drag me away from you "
				+ "There's nothing that a hundred men or more could ever domu" + LINE_SEPARATOR;
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateDate()
			throws AbstractApplicationException, ShellException, IOException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
		String command = "echo \"This is the date: `date`\"";
		String expected =
				"This is the date: " + ZonedDateTime.now().format(formatter) + LINE_SEPARATOR;;
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateSed()
			throws AbstractApplicationException, ShellException, IOException {
		String command =
				"echo \'Hey now youre an All Star get your game on, go play\' | sed sarahag";
		String expected = "Hey now youhe an All Stah get youh game on, go play" + LINE_SEPARATOR;
		String actual = shell.parseAndEvaluate(command);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateWc() throws AbstractApplicationException, ShellException {
		String command = "echo `wc " + RELATIVE_FILENAMES + "`";
		String expected = "205 5 4" + LINE_SEPARATOR;
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
