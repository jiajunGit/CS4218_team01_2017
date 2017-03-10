package test.integration.date;

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

public class DateIntegrationTest {
	private ShellImpl shell;
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
		String output = shell.parseAndEvaluate("date;echo hi");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		assertEquals(DEFAULT_DATE_FORMAT.format(cal.getTime()).toString()+LINE_SEPARATOR+"hi"+LINE_SEPARATOR,output);
	}
	
	@Test
	public void testWithEchoCmdSub() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("date;echo `date`");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		assertEquals(DEFAULT_DATE_FORMAT.format(cal.getTime()).toString()+LINE_SEPARATOR+DEFAULT_DATE_FORMAT.format(cal.getTime()).toString()+LINE_SEPARATOR,output);
	}
	
	@Test
	public void testWithHead() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("date|head -n 10");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		assertEquals(DEFAULT_DATE_FORMAT.format(cal.getTime()).toString()+LINE_SEPARATOR,output);
	}
	
	@Test
	public void testWithTail() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("date|tail -n 10");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		assertEquals(DEFAULT_DATE_FORMAT.format(cal.getTime()).toString()+LINE_SEPARATOR,output);
	}
	
	@Test
	public void testWithCd() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("date;cd ../;pwd");
		String CURRENT_DIRECTORY = Environment.currentDirectory;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		assertEquals(DEFAULT_DATE_FORMAT.format(cal.getTime()).toString()+LINE_SEPARATOR+CURRENT_DIRECTORY+LINE_SEPARATOR,output);
	}
	
	@Test
	public void testWithPwd() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("date;pwd");
		String CURRENT_DIRECTORY = Environment.currentDirectory;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		assertEquals(DEFAULT_DATE_FORMAT.format(cal.getTime()).toString()+LINE_SEPARATOR+CURRENT_DIRECTORY+LINE_SEPARATOR,output);
	}
	
	@Test
	public void testWithCat() throws AbstractApplicationException, ShellException {
		String output = shell.parseAndEvaluate("date>input/test;cat input/test");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		assertEquals(DEFAULT_DATE_FORMAT.format(cal.getTime()).toString()+LINE_SEPARATOR,output);
	}
	
	@Test
	public void testWithCal() throws AbstractApplicationException, ShellException {
		String output= shell.parseAndEvaluate("date ;cal");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "calendar"
				+ PATH_SEPARATOR;
		String expectedCalendar = "";
		try {
			expectedCalendar = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "currentMonth")));
		} catch (IOException e) {
			System.out.println(e);
		}
		assertEquals(DEFAULT_DATE_FORMAT.format(cal.getTime()).toString()+LINE_SEPARATOR+expectedCalendar, output);
	}

}
