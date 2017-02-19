package test.ef2.date;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class DateApplicationTest {
	static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	DateApplication dateApp;
	
	@Before
	public void setUpBeforeTest() throws Exception {
		dateApp = new DateApplication();
		outContent = new ByteArrayOutputStream();
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setOut(new PrintStream(outContent));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.setOut(System.out);
	}
	

	@Test(expected=AbstractApplicationException.class)
	public void testRunNonNullStdinException(){
		String[] args = {};
		
		dateApp.run(args, System.in, null);
	}
	
	@Test(expected=AbstractApplicationException.class)
	public void testRunNullStdoutException(){
		String[] args = {};
		
		dateApp.run(args, null, null);
	}
	
	@Test(expected=AbstractApplicationException.class)
	public void testRunException(){
		String[] args = {"randomString"};
		
		dateApp.run(args, null, System.out);
	}
	
	public void testRun(){
		String[] args ={};
		
		ZonedDateTime exactTime = dateApp.getZonedDateTime();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
		dateApp.run(args, null, outContent);
		
		assertEquals(exactTime.format(formatter), outContent.toString());
	}
	
	/**
	 * Assume that dateApp exposes another additional API that returns a ZonedDateTime object used in date command for testing the exact time and date
	 */
	@Test
	public void testPrintCurrentDate() {
		ZonedDateTime exactTime = dateApp.getZonedDateTime();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
		
		assertEquals(exactTime.format(formatter), dateApp.printCurrentDate("date"));
	}
	
}
