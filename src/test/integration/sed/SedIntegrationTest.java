package test.integration.sed;

import static org.junit.Assert.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class SedIntegrationTest {
	private static ShellImpl shell;
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String PATH_SEPARATOR = File.separator;
	private static final String RELATIVE_DIR = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "integration"
			+ PATH_SEPARATOR + "sed" + PATH_SEPARATOR + "input" + PATH_SEPARATOR;

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
	public void testIntegrateDate() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sed s/sub/a/g " + RELATIVE_DIR + "sedIntegration; date");
		String expected = "a a string" + LINE_SEPARATOR + "replacement astring" + LINE_SEPARATOR + "astitution";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		assertEquals(expected + LINE_SEPARATOR + dateFormat.format(cal.getTime()).toString() + LINE_SEPARATOR, output);
	}

	@Test
	public void testIntegratePwd() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sed s/sub/' '/g " + RELATIVE_DIR + "sedIntegration; pwd");
		String expected = "    string" + LINE_SEPARATOR + "replacement  string" + LINE_SEPARATOR + " stitution";
		assertEquals(expected + LINE_SEPARATOR + Environment.currentDirectory + LINE_SEPARATOR, output);
	}

	@Test
	public void testIntegrateTail() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sed s/sub/many/g " + RELATIVE_DIR + "sedIntegration | tail -n 1");
		String expected = "manystitution" + LINE_SEPARATOR;
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateHead() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sed s/sub/many/ " + RELATIVE_DIR + "sedIntegration | head -n 1");
		String expected = "many sub string" + LINE_SEPARATOR;
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateEcho() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sed s/sub/`echo many`/ " + RELATIVE_DIR + "sedIntegration | head -n 1");
		String expected = "many sub string" + LINE_SEPARATOR;
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateCat() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sed s/sub/many/ " + RELATIVE_DIR + "sedIntegration > " + RELATIVE_DIR
				+ "sedIntegration2.txt ; cat " + RELATIVE_DIR + "*");
		String expected = "sub sub string" + LINE_SEPARATOR + "replacement substring" + LINE_SEPARATOR + "substitution"
				+ "many sub string" + LINE_SEPARATOR + "replacement manystring" + LINE_SEPARATOR + "manystitution"
				+ LINE_SEPARATOR;

		File file = new File(RELATIVE_DIR + "sedIntegration2.txt");
		if (file.exists()) {
			file.delete();
		}

		assertEquals(expected, output);

	}

	@Test(expected = AbstractApplicationException.class)
	public void testIntegrateCd() throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("sed s/sub/a/g " + RELATIVE_DIR + "sedIntegration; cd pathThatDoesNotExist");
	}

	@Test
	public void testIntegrateSort() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sed s/sub/sub/ " + RELATIVE_DIR + "sedIntegration | sort");
		String expected = "replacement substring" + LINE_SEPARATOR + "sub sub string" + LINE_SEPARATOR + "substitution"
				+ LINE_SEPARATOR;
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateGrep() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sed s/sub/sub/ " + RELATIVE_DIR + "sedIntegration | grep 'sub'");
		String expected = "sub sub string" + LINE_SEPARATOR + "replacement substring" + LINE_SEPARATOR + "substitution"
				+ LINE_SEPARATOR;
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateCal() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sed s/sub/sub/ " + RELATIVE_DIR + "sedIntegration ; cal 3 2017");
		String expected = "sub sub string" + LINE_SEPARATOR + "replacement substring" + LINE_SEPARATOR + "substitution"
				+ LINE_SEPARATOR + "     March 2017     " + LINE_SEPARATOR + "Su Mo Tu We Th Fr Sa" + LINE_SEPARATOR
				+ "         1  2  3  4 " + LINE_SEPARATOR + "5  6  7  8  9  10 11" + LINE_SEPARATOR
				+ "12 13 14 15 16 17 18" + LINE_SEPARATOR + "19 20 21 22 23 24 25" + LINE_SEPARATOR
				+ "26 27 28 29 30 31   " + LINE_SEPARATOR;
		;
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateWc() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sed s/sub/sub/ " + RELATIVE_DIR + "sedIntegration | wc -lw");
		String expected = "       6       2" + LINE_SEPARATOR;
		assertEquals(expected, output);
	}

	@Test
	public void testIntegrateSed() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sed s/sub/sub1/ " + RELATIVE_DIR
				+ "sedIntegration | sed s/sub1/sub2/ | sed s/sub2/sub3/  | sed s/sub3/sub4/");
		String expected = "sub4 sub string" + LINE_SEPARATOR + "replacement sub4string" + LINE_SEPARATOR
				+ "sub4stitution" + LINE_SEPARATOR;
		assertEquals(expected, output);
	}
}
