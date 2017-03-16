package test.integration.wc;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class WcIntegrationTest {
	private static ShellImpl shell;
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String PATH_SEPARATOR = File.separator;
	private static final String RELATIVE_DIR = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "integration"
			+ PATH_SEPARATOR + "wc" + PATH_SEPARATOR + "input" + PATH_SEPARATOR;

	@Before
	public void setUp() throws Exception {
		shell = new ShellImpl();
		Environment.setDefaultDirectory();
	}

	@After
	public void tearDown() throws Exception {
		Environment.setDefaultDirectory();
	}

	@Test(expected = AbstractApplicationException.class)
	public void testIntegrateDate() throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("wc -m wcIntegrate | date");
	}

	@Test
	public void testIntegrateHead() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("wc -m " + RELATIVE_DIR + "wcIntegrate | head");
		String expected = "      29" + LINE_SEPARATOR;
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateTail() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("wc -m " + RELATIVE_DIR + "wcIntegrate | tail");
		String expected = "      29" + LINE_SEPARATOR;
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateEcho() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("wc `echo '-l'` " + RELATIVE_DIR + "wcIntegrate | tail");
		String expected = "       2" + LINE_SEPARATOR;
		assertEquals(expected, output);
	}

	@Test
	public void testIntegratePwd() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("wc `echo '-w'` " + RELATIVE_DIR + "wcIntegrate ; pwd");
		String expected = "       6" + LINE_SEPARATOR + Environment.currentDirectory + LINE_SEPARATOR;
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateCd() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("wc -w " + RELATIVE_DIR + "wcIntegrate ; pwd");
		String expected = "       6" + LINE_SEPARATOR + Environment.currentDirectory + LINE_SEPARATOR;
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateCal() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("wc -w " + RELATIVE_DIR + "wcIntegrate ; cal 4 2017");
		String expected = "       6" + LINE_SEPARATOR + "     April 2017     " + LINE_SEPARATOR + "Su Mo Tu We Th Fr Sa"
				+ LINE_SEPARATOR + "                  1 " + LINE_SEPARATOR + "2  3  4  5  6  7  8 " + LINE_SEPARATOR
				+ "9  10 11 12 13 14 15" + LINE_SEPARATOR + "16 17 18 19 20 21 22" + LINE_SEPARATOR
				+ "23 24 25 26 27 28 29" + LINE_SEPARATOR + "30                  " + LINE_SEPARATOR;
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateSed() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("wc -w " + RELATIVE_DIR + "wcIntegrate | sed s/6/six/");
		String expected = "       six" + LINE_SEPARATOR;
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateSortCat() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("wc " + RELATIVE_DIR + "wcIntegrate > " + RELATIVE_DIR
				+ "wcResults ; cat " + RELATIVE_DIR + "* | sort");
		String expected = "many wc" + "      29       6       2" + LINE_SEPARATOR + "wc wc" + LINE_SEPARATOR
				+ "wc:word count" + LINE_SEPARATOR;

		File file = new File(RELATIVE_DIR + "wcResults");
		if (file.exists()) {
			file.delete();
		}
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateGrep() throws AbstractApplicationException, ShellException {
		String output = shell
				.parseAndEvaluate("wc " + RELATIVE_DIR + "wcIntegrate | grep 'expression that does not exist'");
		String expected = LINE_SEPARATOR;

		assertEquals(expected, output);
	}
}
