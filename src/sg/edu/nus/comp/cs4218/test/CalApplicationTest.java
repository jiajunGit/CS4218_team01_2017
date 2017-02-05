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

}
