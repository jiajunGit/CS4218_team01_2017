
package integration.cat;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

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
			"test" + PATH_SEPARATOR + "integration" + PATH_SEPARATOR 
					+ "cat" + PATH_SEPARATOR + "input" + PATH_SEPARATOR;
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
		shell.parseAndEvaluate(command);
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
		String command = "cat " + RELATIVE_RICK2 + " ; cd " + RELATIVE_INPUT_DIR + "; pwd ; date";
		shell.parseAndEvaluate(command);
	}

	@Test
	public void testIntegrateTail()
			throws AbstractApplicationException, ShellException, IOException {
		String command = "cat `cat " + RELATIVE_FILENAMES + "` | tail";
		shell.parseAndEvaluate(command);
	}

	@Test
	public void testIntegrateHead()
			throws AbstractApplicationException, ShellException, IOException {
		String command = "cat `cat " + RELATIVE_FILENAMES + "` | head";
		shell.parseAndEvaluate(command);
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
		shell.parseAndEvaluate(command);
	}

	@Test
	public void testIntegrateSed() throws AbstractApplicationException, ShellException {
		String command = "cat " + RELATIVE_RICK2 + "| sed s/Never/Always/g";
		shell.parseAndEvaluate(command);
	}

	@Test
	public void testIntegrateWc() throws AbstractApplicationException, ShellException {
		String command = "cat " + RELATIVE_FILENAMES + " " + RELATIVE_RICK2 + " | wc";
		shell.parseAndEvaluate(command);
	}

	@Test
	public void testIntegrateGrep() throws AbstractApplicationException, ShellException {
		String command = "cat " + RELATIVE_RICK2 + " | grep you";
		shell.parseAndEvaluate(command);
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
