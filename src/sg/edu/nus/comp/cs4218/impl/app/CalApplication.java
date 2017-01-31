package sg.edu.nus.comp.cs4218.impl.app;

import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.app.Cal;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CalException;

/**
 * The cal command prints the calendar of the month or year if it is specified, else
 * only the current month is shown
 * 
 * <p>
 * <b>Command format:</b> <code> cal [-m] [MONTH] [YEAR]
 * <dl>
 * <dt>-m</dt>
 * <dd>Calendar of the month starts with Monday</dd>
 * <dt>MONTH</dt>
 * <dd>the month to display, if no month and year is specified, display current month.</dd>
 * <dt>YEAR</dt>
 * <dd>the year to display, if no year and month is specified, display current month.</dd>
 * </dl>
 * </p>
 */
public class CalApplication implements Cal{

	/**
	 * 
	 */
	public CalApplication() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws CalException {
		if (args.length ==  0){
			printCal(args);
		}
		
	}

	/**
	 * Print the calendar of the current month
	 * @param args String array containing command and arguments to print the calendar of the current month
	 */
	public String printCal(String[] args) {
		
		
		return null;
	}

	/**
	 * Returns the string to print the calendar of the current month with Monday
	 * as the first day of the week
	 * @param args String array containing command and arguments
	 */
	public String printCalWithMondayFirst(String[] args) {
		return null;
	}

	/**
	 * Returns the string to print the calendar for specified month and year
	 * @param args String array containing command and arguments
	 */
	public String printCalForMonthYear(String[] args) {
		return null;
	}

	/**
	 * Returns the string to print the calendar for specified year
	 * @param args String array containing command and arguments
	 */
	public String printCalForYear(String[] args) {
		return null;
	}

	/**
	 * Returns the string to print the calendar for specified month and year
	 * with Monday as the first day of the week
	 * @param args String array containing command and arguments
	 */
	public String printCalForMonthYearMondayFirst(String[] args) {
		return null;
	}

	/**
	 * Returns the string to print the calendar for specified year with Monday
	 * as the first day of the week
	 * @param args String array containing command and arguments
	 */
	public String printCalForYearMondayFirst(String[] args) {
		return null;
	}
	
	/**
	 * Returns the header string for the month and year
	 * @param isMon boolean representing whether Monday starts first
	 * @param 	
	 */
	private String printDaysHeader(boolean isMon){
		String header;
		
		if (isMon){
			header = "Mo Tu We Th Fr Sa Su";
		}
		else{
			header = "Su Mo Tu We Th Fr Sa";
		}
		
		return header;
	}
	
	
	private String printMonthHeader(int mon){
		return null;
	}
	
	/**
	 * Returns whether the year is a leap year
	 * @params year int representing the year
	 */
	private boolean isLeapYear(int year){
		if (year % 4 == 0){
			if (year % 100 == 0){
				if (year % 400 == 0){
					return true;
				}
			}
			else{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns number of days in a month
	 * @param month specifies the month
	 * @param year specifies the year
	 */
	private int numberOfDaysInMonth(int month, int year){
		
		switch(month){
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			case 2: 
				if (isLeapYear(year)){
					return 29;
				}
				else{
					return 28;
				}
			default:
				return 30;
		}
	}
}
