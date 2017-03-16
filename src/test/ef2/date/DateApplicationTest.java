package test.ef2.date;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.DateException;
import sg.edu.nus.comp.cs4218.impl.app.DateApplication;

public class DateApplicationTest {

	private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
	private static DateApplication app;
	static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	DateApplication dateApp;

	@Before
	public void setUpBeforeTest() throws Exception {
		outContent = new ByteArrayOutputStream();
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		app = new DateApplication();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.setOut(System.out);
	}

	@Test(expected = DateException.class)
	public void testDateWithNullStdout() throws DateException {
		app.run(null, System.in, null);
	}

	@Test(expected = DateException.class)
	public void testDateWithNullStdinAndStdout() throws DateException {
		app.run(null, null, null);
	}
	
	@Test(expected = DateException.class)
	public void testDateWithRandomArg() throws DateException {
		String[] args = {"random stuff"};
		app.run(args, null, null);
	}
	
	@Test
	public void testDateWithNullStdin() throws DateException {
		String message = "date - test with null stdin";
		String[] args = null;
		ZonedDateTime exactTime = ZonedDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
		app.run(args, null, outContent);

		assertEquals(message, exactTime.format(formatter), outContent.toString());
	}

	@Test
	public void testDateWithCurrentTimeDateWithNullStdin() throws DateException {
		String message = "date - test with current time and date with null stdin";
		app.run(null, null, outContent);
		Calendar cal = Calendar.getInstance();
		assertEquals(message, DEFAULT_DATE_FORMAT.format(cal.getTime()), outContent.toString());
	}

	@Test
	public void testDateWithCurrentTimeDate() throws DateException {
		String message = "date - test with current time and date";
		app.run(null, System.in, outContent);
		Calendar cal = Calendar.getInstance();
		assertEquals(message, DEFAULT_DATE_FORMAT.format(cal.getTime()), outContent.toString());
	}

	@Test
	public void testDateWithWaitOneSecond() throws DateException, InterruptedException {
		String message = "date - test with current time and wait seconds";
		app.run(null, System.in, outContent);
		Thread.sleep(1000);
		Calendar cal = Calendar.getInstance();
		assertNotSame(message, DEFAULT_DATE_FORMAT.format(cal.getTime()), outContent.toString());
	}
}
