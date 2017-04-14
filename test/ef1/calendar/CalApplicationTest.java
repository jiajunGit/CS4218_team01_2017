package ef1.calendar;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.app.CalApplication;
import sg.edu.nus.comp.cs4218.exception.CalException;

public class CalApplicationTest {

	private static final String PATH_SEPARATOR = File.separator;
	private static final String RELATIVE_TEST_DIRECTORY = "test" + PATH_SEPARATOR + "ef1" + PATH_SEPARATOR + "calendar"
			+ PATH_SEPARATOR;
	private ByteArrayOutputStream out = new ByteArrayOutputStream();

	@Test(expected = CalException.class)
	public void testNegativeYearException() throws CalException {
		String[] arg = { "-m", "-1" };
		System.setOut(new PrintStream(out));
		CalApplication cal = new CalApplication();

		cal.run(arg, System.in, System.out);

	}

	@Test(expected = CalException.class)
	public void testYearOutOfBoundException() throws CalException {
		String[] arg = { "10000" };
		System.setOut(new PrintStream(out));

		CalApplication cal = new CalApplication();

		cal.run(arg, System.in, System.out);
	}

	@Test(expected = CalException.class)
	public void testTooManyArgumentsException() throws CalException {
		String[] arg = { "-m", "1", "6", "2017" };
		System.setOut(new PrintStream(out));

		CalApplication cal = new CalApplication();

		cal.run(arg, System.in, System.out);
	}

	@Test(expected = CalException.class)
	public void testInvalidOptionPosition() throws CalException {
		String[] arg = { "6", "-m", "2017" };
		System.setOut(new PrintStream(out));

		CalApplication cal = new CalApplication();

		cal.run(arg, System.in, System.out);
	}

	@Test(expected = CalException.class)
	public void testInvalidArgumentsException() throws CalException {
		String[] arg = { "-mlw" };
		System.setOut(new PrintStream(out));

		CalApplication cal = new CalApplication();

		cal.run(arg, System.in, System.out);
	}

	@Test(expected = CalException.class)
	public void testMonthOutOfBound() throws CalException {
		String[] arg = { "13", "2017" };
		System.setOut(new PrintStream(out));

		CalApplication cal = new CalApplication();

		cal.run(arg, System.in, System.out);
	}

	@Test(expected = CalException.class)
	public void testYearOutOfBound() throws CalException {
		String[] arg = { "12", "0" };
		System.setOut(new PrintStream(out));

		CalApplication cal = new CalApplication();

		cal.run(arg, System.in, System.out);
	}

	@Test
	public void testRunPrintCal() throws CalException {
		String[] arg = new String[0];
		System.setOut(new PrintStream(out));
		CalApplication cal = new CalApplication();
		cal.run(arg, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunPrintCalMonday() throws CalException {
		String[] arg = { "-m" };
		System.setOut(new PrintStream(out));
		CalApplication cal = new CalApplication();
		cal.run(arg, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunPrintCalMonthYear() throws CalException {
		String[] arg = { "12", "1000" };
		System.setOut(new PrintStream(out));
		CalApplication cal = new CalApplication();
		cal.run(arg, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunPrintCalMonthYearMonday() throws CalException {
		String[] arg = { "-m", "2", "2000" };
		System.setOut(new PrintStream(out));
		CalApplication cal = new CalApplication();
		cal.run(arg, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunPrintCalMonthYearMondayAlt() throws CalException {
		String[] arg = { "2", "2000", "-m" };
		System.setOut(new PrintStream(out));
		CalApplication cal = new CalApplication();
		cal.run(arg, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunPrintCalYear() throws CalException {
		String[] arg = { "1900" };
		System.setOut(new PrintStream(out));
		CalApplication cal = new CalApplication();
		cal.run(arg, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunPrintCalYearMonday() throws CalException {
		String[] arg = { "-m", "2017" };
		System.setOut(new PrintStream(out));
		CalApplication cal = new CalApplication();
		cal.run(arg, System.in, System.out);
		out.toString();
	}

	@Test
	public void testRunPrintCalYearMondayAlt() throws CalException {
		String[] arg = { "2017", "-m" };
		System.setOut(new PrintStream(out));
		CalApplication cal = new CalApplication();
		cal.run(arg, System.in, System.out);
		out.toString();
	}

	/**
	 * Tests for interface
	 */
	@Test
	public void testPrintCal() {
		CalApplication cal = new CalApplication();
		cal.printCal("cal -m");
	}

	@Test
	public void testPrintCalWithMondayFirst() {
		CalApplication cal = new CalApplication();
		cal.printCalWithMondayFirst("cal -m");
	}

	@Test
	public void testPrintCalForMonthYear() {
		CalApplication cal = new CalApplication();
		cal.printCalForMonthYear("cal 12 1000");
	}

	@Test
	public void testPrintCalForMonthYearLeapYear() {
		CalApplication cal = new CalApplication();
		cal.printCalForMonthYear("cal 2 2020");
	}

	@Test
	public void testPrintCalForMonthYearMondayFirst() {
		CalApplication cal = new CalApplication();
		cal.printCalForMonthYearMondayFirst("cal -m 12 1000");
	}

	@Test
	public void testPrintCalForMonthYearMondayFirstLeapYear() {
		CalApplication cal = new CalApplication();
		cal.printCalForMonthYearMondayFirst("cal -m 2 2000");
	}

	@Test
	public void testPrintCalForYear() {
		CalApplication cal = new CalApplication();
		cal.printCalForYear("cal 2017");
	}

	@Test
	public void testPrintCalForYearNotLeapYear() {
		CalApplication cal = new CalApplication();
		cal.printCalForYear("cal 1900");
	}

	@Test
	public void testPrintCalForYearMondayFirst() {
		CalApplication cal = new CalApplication();
		cal.printCalForYearMondayFirst("cal -m 2017");
	}

}
