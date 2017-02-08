package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.app.CalApplication;

public class CalApplicationTest {

	private static final String PATH_SEPARATOR = File.separator;
	private static final String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test" + PATH_SEPARATOR + "calendar"
			+ PATH_SEPARATOR;

	@Test
	public void testPrintCal() {
		String expectedCalendar = "";
		CalApplication cal = new CalApplication();
		try {
			expectedCalendar = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "currentMonth")));
		} catch (IOException e) {
			System.out.println(e);
		}

		assertEquals(expectedCalendar, cal.printCal("cal -m"));
	}

	@Test
	public void testPrintCalWithMondayFirst() {
		String expectedCalendar = "";
		CalApplication cal = new CalApplication();
		try {
			expectedCalendar = new String(
					Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "currentMonthMonday")));
		} catch (IOException e) {
			System.out.println(e);
		}

		assertEquals(expectedCalendar, cal.printCalWithMondayFirst("cal -m"));
	}

	@Test
	public void testPrintCalForMonthYear() {
		String expectedCalendar = "";
		CalApplication cal = new CalApplication();
		try {
			expectedCalendar = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "monthYear")));
		} catch (IOException e) {
			System.out.println(e);
		}

		assertEquals(expectedCalendar, cal.printCalForMonthYear("cal 12 1000"));
	}

	@Test
	public void testPrintCalForMonthYearMondayFirst() {
		String expectedCalendar = "";
		CalApplication cal = new CalApplication();
		try {
			expectedCalendar = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "monthYearMonday")));
		} catch (IOException e) {
			System.out.println(e);
		}

		assertEquals(expectedCalendar, cal.printCalForMonthYearMondayFirst("cal -m 12 1000"));
	}

	@Test
	public void testPrintCalForYear() {
		String expectedCalendar = "";
		CalApplication cal = new CalApplication();
		try {
			expectedCalendar = new String(Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "year2017")));
		} catch (IOException e) {
			System.out.println(e);
		}

		assertEquals(expectedCalendar, cal.printCalForYear("cal 2017"));
	}

	@Test
	public void testPrintCalForYearMondayFirst() {
		CalApplication cal = new CalApplication();

		assertEquals("", cal.printCalForYearMondayFirst("cal -m 2017"));
	}
}
