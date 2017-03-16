package test.integration.pwd;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class PwdIntegrationTest {
	private ShellImpl shell = new ShellImpl();
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String PATH_SEPARATOR = File.separator;

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
		String output = shell.parseAndEvaluate("pwd; echo hi");
		String currentDir = new File(Environment.currentDirectory).getAbsolutePath();
		assertEquals(currentDir + LINE_SEPARATOR + "hi" + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithEchoCmdSub() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("pwd ; echo `pwd`");
		String currentDir = new File(Environment.currentDirectory).getAbsolutePath();
		assertEquals(currentDir + LINE_SEPARATOR + currentDir + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithCd() throws AbstractApplicationException, ShellException {
		String currentDir1 = new File(Environment.currentDirectory).getAbsolutePath();
		String output = shell.parseAndEvaluate("pwd;cd .." + PATH_SEPARATOR + ";pwd");
		String currentDir2 = new File(Environment.currentDirectory).getAbsolutePath();
		assertEquals(currentDir1 + LINE_SEPARATOR + LINE_SEPARATOR + currentDir2 + LINE_SEPARATOR,
				output);
	}

	@Test
	public void testWithCat() throws AbstractApplicationException, ShellException { // todo:
																					// globbing
																					// cat
																					// input/*
		String currentDir = new File(Environment.currentDirectory).getAbsolutePath();
		String output = shell.parseAndEvaluate("pwd>input" + PATH_SEPARATOR + "test ; cat input" + PATH_SEPARATOR + "test");
		assertEquals(currentDir + LINE_SEPARATOR + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithHead() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("pwd | head");
		String currentDir = new File(Environment.currentDirectory).getAbsolutePath();
		assertEquals(currentDir + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithHeadOptions() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("pwd | head -n 10");
		String currentDir = new File(Environment.currentDirectory).getAbsolutePath();
		assertEquals(currentDir + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithTail() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("pwd | tail");
		String currentDir = new File(Environment.currentDirectory).getAbsolutePath();
		assertEquals(currentDir + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithTailOptions() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("pwd | tail -n 10");
		String currentDir = new File(Environment.currentDirectory).getAbsolutePath();
		assertEquals(currentDir + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithCal() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("pwd ;cal");
		String relativeTestDir = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "calendar" + PATH_SEPARATOR;
		String expectedCalendar = "";
		try {
			expectedCalendar = new String(Files.readAllBytes(Paths.get(relativeTestDir + "currentMonth")));
		} catch (IOException e) {
			System.out.println(e);
		}
		String currentDir = new File(Environment.currentDirectory).getAbsolutePath();
		assertEquals(currentDir + LINE_SEPARATOR + expectedCalendar + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithGrep() throws AbstractApplicationException, ShellException {
	    String pattern = Pattern.quote(PATH_SEPARATOR);
		String output = shell.parseAndEvaluate("pwd;pwd>input" + PATH_SEPARATOR + "test; grep " + pattern + " input" + PATH_SEPARATOR + "test");
		String currentDir = new File(Environment.currentDirectory).getAbsolutePath();
		assertEquals(currentDir + LINE_SEPARATOR + currentDir + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithSort() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("pwd;pwd>input" + PATH_SEPARATOR + "test; sort input" 
		                                       + PATH_SEPARATOR + "test");
		String currentDir = new File(Environment.currentDirectory).getAbsolutePath();
		assertEquals(currentDir + LINE_SEPARATOR + currentDir + LINE_SEPARATOR, output);
	}

	@Test
	public void testWithDate() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("pwd;date");
		String currentDir = new File(Environment.currentDirectory).getAbsolutePath();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat defaultDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		assertEquals(currentDir + LINE_SEPARATOR + defaultDateFormat.format(cal.getTime()).toString()
				+ LINE_SEPARATOR, output);
	}

	@Test
	public void testWithSed() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("pwd;pwd>input" + PATH_SEPARATOR + "test;sed s/2017/2017abc/ input" 
		                                        + PATH_SEPARATOR + "test");
		String currentDir = new File(Environment.currentDirectory).getAbsolutePath();
		assertEquals(currentDir + LINE_SEPARATOR + currentDir + "abc" + LINE_SEPARATOR + LINE_SEPARATOR,
				output);
	}

	@Test
	public void testWithWc() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("pwd;pwd>input" + PATH_SEPARATOR + "test;wc -l input" 
		                                       + PATH_SEPARATOR + "test");
		String currentDir = new File(Environment.currentDirectory).getAbsolutePath();
		assertEquals(currentDir + LINE_SEPARATOR + "       1" + LINE_SEPARATOR, output);
	}

}
