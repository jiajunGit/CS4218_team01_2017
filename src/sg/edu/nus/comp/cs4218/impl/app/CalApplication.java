package sg.edu.nus.comp.cs4218.impl.app;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Calendar;

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
	
	private static final String CAL_COMMAND = "cal";
	private static final int ROW_LENGTH = 20;
	private static final int MAXIMUM_SUPPORTED_YEAR = 999999999;
	private static final int MINIMUM_SUPPORTED_YEAR = -999999999;
	private static final String[] MONTH_NAME = {"January","February","March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private static final String EMPTY_SPACE = " ";
	private static final String EMPTY_CELL = "   ";
	private static final int MONTH_OFFSET = 1;
	
	public CalApplication() {
	}

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws CalException {
		if (args.length ==  0){
			printCal(CAL_COMMAND);
		}
	}

	/**
	 * Returns a  the calendar of the current month
	 * @param args String array containing command and arguments to print the calendar of the current month
	 */
	public String printCal(String args) {
		StringBuilder calOutput = new StringBuilder();
		LocalDate now = LocalDate.now();
		LocalDate currMonth = LocalDate.of(now.getYear(), now.getMonthValue(), 1);
		
		calOutput.append(printMonthYear(currMonth.getMonthValue(), currMonth.getYear()));
		calOutput.append(System.lineSeparator());
		calOutput.append(printDaysHeader(false));
		calOutput.append(System.lineSeparator());
		calOutput.append(printDaysOfMonth(currMonth, false));
		
		return calOutput.toString();
	}

	
	/**
	 * Returns a String for the month
	 * @param currMonth specifies a LocalDate for the first day of the month 
	 * @param isMon specifies whether the month calendar starts with Mon
	 */
	private String printDaysOfMonth(LocalDate currMonth, boolean isMon) {
		int day = 1;
		int counter = 0;
		int daysInMonth = numberOfDaysInMonth(currMonth.getMonthValue(), currMonth.getYear());
		int offset = currMonth.getDayOfWeek().getValue();
		StringBuilder calendar = new StringBuilder();
		
		if (!isMon){
			if (offset == 7){
				offset = 1;
			}
			else{
				offset++;
			}
		}
		
		// First week
		for (int i = 1; i < offset; i++){
			calendar.append(EMPTY_CELL);
		}
		for (int i = offset ; i <= 7; i++){
			calendar.append(day);
			calendar.append(EMPTY_SPACE);
			calendar.append(EMPTY_SPACE);
			day++;
		}
		calendar.append(System.lineSeparator());
		
		while (day <= daysInMonth){
			calendar.append(day);
			if (day < 10){
				calendar.append(EMPTY_SPACE);
			}
			calendar.append(EMPTY_SPACE);
			day++;
			counter++;
			if (counter == 7){
				counter = 0;
				calendar.append(System.lineSeparator());
			}
		}
		
		if (counter != 0){
			offset = 7 - counter;
			for ( int i = 0; i < offset; i++){
				calendar.append(EMPTY_CELL);
			}
		}
		
		
		return calendar.toString();
	}

	/**
	 * Returns the string to print the calendar of the current month with Monday
	 * as the first day of the week
	 * @param args String array containing command and arguments
	 */
	public String printCalWithMondayFirst(String args) {
		StringBuilder calOutput = new StringBuilder();
		LocalDate now = LocalDate.now();
		LocalDate currMonth = LocalDate.of(now.getYear(), now.getMonthValue(), 1);
		
		calOutput.append(printMonthYear(currMonth.getMonthValue(), currMonth.getYear()));
		calOutput.append(System.lineSeparator());
		calOutput.append(printDaysHeader(true));
		calOutput.append(System.lineSeparator());
		calOutput.append(printDaysOfMonth(currMonth, true));
		
		return calOutput.toString();
		
	}

	/**
	 * Returns the string to print the calendar for specified month and year
	 * @param args String array containing command and arguments
	 */
	public String printCalForMonthYear(String args) {
		LocalDate currMonth;
		StringBuilder calOutput = new StringBuilder();
		String[] argsArr = parseArgs(args);
		
		currMonth = LocalDate.of(Integer.parseInt(argsArr[2]), Integer.parseInt(argsArr[1]), 1);
		
		calOutput.append(printMonthYear(currMonth.getMonthValue(), currMonth.getYear()));
		calOutput.append(System.lineSeparator());
		calOutput.append(printDaysHeader(false));
		calOutput.append(System.lineSeparator());
		calOutput.append(printDaysOfMonth(currMonth, false));
		
		return calOutput.toString();
	}

	/**
	 * Returns the string to print the calendar for specified year
	 * @param args String array containing command and arguments
	 */
	public String printCalForYear(String args) {
		return null;
	}

	/**
	 * Returns the string to print the calendar for specified month and year
	 * with Monday as the first day of the week
	 * @param args String array containing command and arguments
	 */
	public String printCalForMonthYearMondayFirst(String args) {
		LocalDate currMonth;
		StringBuilder calOutput = new StringBuilder();
		String[] argsArr = parseArgs(args);
		
		currMonth = LocalDate.of(Integer.parseInt(argsArr[3]), Integer.parseInt(argsArr[2]), 1);
		
		calOutput.append(printMonthYear(currMonth.getMonthValue(), currMonth.getYear()));
		calOutput.append(System.lineSeparator());
		calOutput.append(printDaysHeader(true));
		calOutput.append(System.lineSeparator());
		calOutput.append(printDaysOfMonth(currMonth, true));
		
		return calOutput.toString();
	}

	/**
	 * Returns the string to print the calendar for specified year with Monday
	 * as the first day of the week
	 * @param args String array containing command and arguments
	 */
	public String printCalForYearMondayFirst(String args) {
		return null;
	}
	
	/**
	 * Returns a String array containing cal as first string and 
	 * @param args
	 * @return
	 */
	private String[] parseArgs(String args){
		String[] argsToStringArray = args.split(EMPTY_SPACE);
		
		System.out.println(argsToStringArray);
		
		return argsToStringArray;
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
	
	/**
	 * Returns the header showing month and year centralized
	 * @param month specifies the month
	 * @param year specifies the year
	 */
	private String printMonthYear(int month, int year){
//		if (year > MAXIMUM_SUPPORTED_YEAR || year < MINIMUM_SUPPORTED_YEAR){
//			throws new CalException("Year given is out of range");
//		}
		StringBuilder monthYear = new StringBuilder();
		monthYear.append(MONTH_NAME[(month - MONTH_OFFSET)]);
		monthYear.append(EMPTY_SPACE);
		monthYear.append(year);
		
		int excessLength = (ROW_LENGTH - monthYear.length()) / 2;
		
		for (int i = 0 ; i < excessLength; i++){
			monthYear.insert(0, EMPTY_SPACE);
		}
		
		int remainLength = ROW_LENGTH - monthYear.length(); 
		
		for (int i = 0; i < remainLength; i++){
			monthYear.append(EMPTY_SPACE);
		}
		
		return monthYear.toString();
	}
	
	/** 
	 * Returns an int representing the index of the day 0 being first and 6 being last 
	 * @param month A LocalDate object for the first day of the month 
	 * @param isMon specifies whether Monday starts first
	 */
	private int getFirstDayOfWeek(LocalDate month, boolean isMon){
		int index = month.getDayOfWeek().getValue();
		
		if (isMon){
			return index;
		}
		else{
			// Week starts from Sun
			return (index + 1) % 8;
			
		}
	}
}
