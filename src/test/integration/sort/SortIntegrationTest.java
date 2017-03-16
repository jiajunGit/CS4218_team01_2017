package test.integration.sort;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class SortIntegrationTest {
	private static ShellImpl shell;
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String PATH_SEPARATOR = File.separator;
	private static final String expectedOutput01 = "1" + LINE_SEPARATOR + "11" + LINE_SEPARATOR + "2" + LINE_SEPARATOR
			+ "22" + LINE_SEPARATOR + "3" + LINE_SEPARATOR + "33" + LINE_SEPARATOR + "4" + LINE_SEPARATOR + "44"
			+ LINE_SEPARATOR + "5" + LINE_SEPARATOR + "55" + LINE_SEPARATOR + "alice" + LINE_SEPARATOR + "bob"
			+ LINE_SEPARATOR + "charlie" + LINE_SEPARATOR;

	private static final String expectedOutput02 = "1" + LINE_SEPARATOR + "11" + LINE_SEPARATOR + "2" + LINE_SEPARATOR
			+ "22" + LINE_SEPARATOR + "3" + LINE_SEPARATOR + "33" + LINE_SEPARATOR + "4" + LINE_SEPARATOR + "44"
			+ LINE_SEPARATOR + "5" + LINE_SEPARATOR + "55" + LINE_SEPARATOR;

	private static final String expectedOutput03 = "11" + LINE_SEPARATOR + "22" + LINE_SEPARATOR + "33" + LINE_SEPARATOR
			+ "44" + LINE_SEPARATOR + "55" + LINE_SEPARATOR;

	private static final String expectedOutput04 = "1" + LINE_SEPARATOR + "2" + LINE_SEPARATOR + "3" + LINE_SEPARATOR
			+ "4" + LINE_SEPARATOR + "5" + LINE_SEPARATOR + "11" + LINE_SEPARATOR + "22" + LINE_SEPARATOR + "33"
			+ LINE_SEPARATOR + "44" + LINE_SEPARATOR + "55" + LINE_SEPARATOR;

	private static final String expectedOutput05 = "new" + LINE_SEPARATOR + "2" + LINE_SEPARATOR + "3" + LINE_SEPARATOR
			+ "4" + LINE_SEPARATOR + "5" + LINE_SEPARATOR + "new1" + LINE_SEPARATOR + "22" + LINE_SEPARATOR + "33"
			+ LINE_SEPARATOR + "44" + LINE_SEPARATOR + "55" + LINE_SEPARATOR;

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
	public void testWithEcho() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sort input/input01; echo hi");
		assertEquals(expectedOutput01 + "hi" + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithEchoCmdSub() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sort `echo input/input01`");
		assertEquals(expectedOutput01, output);
	}

	@Test
	public void testWithCat() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sort input/input01>input/test; cat<input/test");
		assertEquals(expectedOutput01 + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithCatCmdSub() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sort `cat<input/file`");
		assertEquals(expectedOutput01, output);
	}

	@Test
	public void testWithCd() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sort input/input01; cd ../; pwd");
		String CURRENT_DIRECTORY = Environment.currentDirectory;
		assertEquals(expectedOutput01 + LINE_SEPARATOR + CURRENT_DIRECTORY + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithCdCmdSub() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sort input/input01; cd `echo ../`; pwd");
		String CURRENT_DIRECTORY = Environment.currentDirectory;
		assertEquals(expectedOutput01 + LINE_SEPARATOR + CURRENT_DIRECTORY + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithHead() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sort input/input01 | head -n 10");
		assertEquals(expectedOutput02, output);
	}

	@Test
	public void testWithHeadCmdSub() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sort `head -n 10 input/file`");
		assertEquals(expectedOutput01, output);
	}

	@Test
	public void testWithTail() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sort -n input/input02 | tail -n 5");
		assertEquals(expectedOutput03, output);
	}

	@Test
	public void testWithTailCmdSub() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sort `tail -n 5 input/file`");
		assertEquals(expectedOutput01, output);
	}

	@Test
	public void testWithCal() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sort -n input/input02 ; cal");
		String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "calendar" + PATH_SEPARATOR;
		String expectedCalendar = "";
		try {
			expectedCalendar = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "currentMonth")));
		} catch (IOException e) {
			System.out.println(e);
		}
		assertEquals(expectedOutput04 + expectedCalendar + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithGrep() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sort -n input/input02 >input/test; grep 1 input/test");
		assertEquals("1" + LINE_SEPARATOR + "11" + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithGrepCmdSub() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sort `grep / input/file`");
		assertEquals(expectedOutput01, output);
	}

	@Test
	public void testWithDate() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sort -n input/input02 ; date");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		assertEquals(expectedOutput04 + DEFAULT_DATE_FORMAT.format(cal.getTime()).toString() + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithSed() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sort -n input/input02 >input/test; sed s/1/new/ input/test");
		assertEquals(expectedOutput05 + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithWc() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("sort -n input/input02 >input/test; wc -l input/test");
		assertEquals("      10" + LINE_SEPARATOR, output);
	}

}
