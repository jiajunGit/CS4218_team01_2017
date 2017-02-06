package sg.edu.nus.comp.cs4218.test;

import static org.junit.Assert.*;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.app.CalApplication;

public class CalApplicationTest {

	@Test
	public void testPrintCal() {
		CalApplication cal = new CalApplication();
		
		assertEquals("", cal.printCal("cal"));
	}
	
	@Test
	public void testPrintCalWithMondayFirst(){
		CalApplication cal = new CalApplication();
		
		assertEquals("", cal.printCalWithMondayFirst("cal -m"));
	}
	
	@Test
	public void testPrintCalForMonthYear(){
		CalApplication cal = new CalApplication();
		
		assertEquals("", cal.printCalForMonthYear("cal 5 2017"));
	}

	@Test
	public void testPrintCalForMonthYearMondayFirst(){
		CalApplication cal = new CalApplication();
		
		assertEquals("", cal.printCalForMonthYearMondayFirst("cal -m 1 2017"));
	}
}
