package sg.edu.nus.comp.cs4218.impl.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Calendar;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.app.Cal;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CalException;
import sg.edu.nus.comp.cs4218.exception.EchoException;

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
	 * Runs the cal application with the specified arguments.
	 * 
	 * @param args
	 *            Array of arguments for the application.
	 * @param stdin
	 *            An InputStream, not used.
	 * @param stdout
	 *            An OutputStream. Calendar of the specified options will be output
	 * @throws CalException
	 *             If a Cal exception occurs.
	 */
	
	private static final String CAL_COMMAND = "cal";
	private static final int ROW_LENGTH = 20;
	private static final int MAXIMUM_SUPPORTED_YEAR = 9999;
	private static final int MINIMUM_SUPPORTED_YEAR = 1;
	private static final String[] MONTH_NAME = {"January","February","March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private static final String EMPTY_SPACE = " ";
	private static final String EMPTY_BORDER = "  ";
	private static final String EMPTY_CELL = "   ";
	private static final String INVALID_ARG = "Invalid argument detected";
	private static final String MONDAY_OPT = "-m";
	private static final int MONTH_OFFSET = 1;
	private static final int YEAR_WIDTH = 64;
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	public CalApplication() {
	}

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws CalException {
		String outputString = "";
		String cmd = "";
		boolean validArg;
		
		if (args.length ==  0){
			// cal
			cmd = CAL_COMMAND;
			outputString = printCal(cmd);
		}
		else if (args.length == 1){
			if (args[0].equals( MONDAY_OPT)){
				//cal -m
				cmd = CAL_COMMAND + " " + "-m";
				outputString = printCalWithMondayFirst(cmd);
			}
			else {
				// cal year
				validArg = checkYearInput(args[0]);
				if (!validArg){
					throw new CalException(INVALID_ARG);
				}
				else{
					cmd = CAL_COMMAND + " " + args[0];
					outputString = printCalForYear(cmd);
				}
			}
		}
		else if (args.length == 2){
			if (args[0].equals(MONDAY_OPT)){
				// cal -m year
				validArg = checkYearInput(args[1]);
				if (!validArg){
					throw new CalException(INVALID_ARG);
				}
				else{
					cmd = CAL_COMMAND + " " + MONDAY_OPT + " " + args[1];
					outputString = printCalForYearMondayFirst(cmd);
				}
			}
			else if (args[1].equals(MONDAY_OPT)){
				//cal year -m
				validArg = checkYearInput(args[0]);
				if (!validArg){
					throw new CalException(INVALID_ARG);
				}
				else{
					cmd = CAL_COMMAND + " " + MONDAY_OPT + " " + args[0];
					outputString = printCalForYearMondayFirst(cmd);
				}
			}
			else{
				// cal month year
				validArg = checkMonthInput(args[0]);
				validArg &= checkYearInput(args[1]);
				if (!validArg){
					throw new CalException(INVALID_ARG);
				}
				else{
					cmd = CAL_COMMAND + EMPTY_SPACE + args[0] + EMPTY_SPACE + args[1];
					outputString = printCalForMonthYear(cmd);
				}
			}
		}
		else if (args.length == 3){
			if (args[0].equals(MONDAY_OPT) && checkMonthInput(args[1]) && checkYearInput(args[2])){
				// cal -m month year
				cmd = CAL_COMMAND + EMPTY_SPACE + args[0] + EMPTY_SPACE + args[1] + EMPTY_SPACE + args[2];
				
				outputString = printCalForMonthYearMondayFirst(cmd);
			}
			else if (args[2].equals(MONDAY_OPT) && checkMonthInput(args[0]) && checkYearInput(args[1])){
				cmd = CAL_COMMAND + EMPTY_SPACE + args[2] + EMPTY_SPACE + args[0] + EMPTY_SPACE + args[1];
				
				outputString = printCalForMonthYearMondayFirst(cmd);
			}
			else{
				throw new CalException(INVALID_ARG);
			}
		}
		else{
			throw new CalException(INVALID_ARG);
		}
		
		
		try {
			stdout.write(outputString.getBytes());
		} catch (IOException e) {
			throw new CalException("Unable to write output to stdout");
		}
	}
	
	/**
	 * Returns true if month is within [1, 12], false otherwise.
	 * @param monthStr
	 */
	private boolean checkMonthInput(String monthStr) throws CalException{
		Integer month;
		
		try{
			month = Integer.parseInt(monthStr);
		}catch (NumberFormatException ex){
			throw new CalException(INVALID_ARG);
		}
		
		if (month >= 1 ){
			if (month <= 12){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns true if year is within [1,9999], false otherwise. 
	 * @param yearStr Specifies the year as a string
	 */
	private boolean checkYearInput(String yearStr) throws CalException{
		Integer year;
		
		try{
			year = Integer.parseInt(yearStr);
		}catch (NumberFormatException ex){
			throw new CalException(INVALID_ARG);
		}
		if (year <= MAXIMUM_SUPPORTED_YEAR){
			if (year >= MINIMUM_SUPPORTED_YEAR){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Returns the calendar of the current month
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
		calOutput.append(LINE_SEPARATOR);
		
		return calOutput.toString();
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
		calOutput.append(LINE_SEPARATOR);
		
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
		calOutput.append(LINE_SEPARATOR);
		
		return calOutput.toString();
	}

	/**
	 * Returns the string to print the calendar for specified year
	 * @param args String array containing command and arguments
	 */
	public String printCalForYear(String args) {
		StringBuilder calOutput = new StringBuilder();
		String[] argArr = parseArgs(args);
		int[] months = new int[3];
		LocalDate[] monthDates = new LocalDate[3];
		int year = Integer.parseInt(argArr[1]);
		
		calOutput.append(printYear(year));
		calOutput.append(System.lineSeparator());
		for (int i = 0 ; i < 4; i++){
			months = new int[3];
			for (int j = 0 ; j < months.length; j++){
				months[j] = i * 3 + j + 1;
				monthDates[j] = LocalDate.of(year, months[j], 1);
			}
			calOutput.append(printMonth(months));
			calOutput.append(System.lineSeparator());
			for (int j = 0; j < months.length; j++){
				calOutput.append(printDaysHeader(false));
				if ( (j+1) != months.length){
					calOutput.append(EMPTY_BORDER);
				}
			}
			calOutput.append(System.lineSeparator());
			calOutput.append(printThreeMonths(monthDates, year, false));
		}
		
		return calOutput.toString();
	
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
		calOutput.append(LINE_SEPARATOR);
		
		return calOutput.toString();
	}
	
	/**
	 * Returns a String containing 3 months of dates
	 * @param  
	 * @param isMon specifies if the header starts with Monday
	 */
	private String printThreeMonths(LocalDate[] months, int year, boolean isMon){
		int[] days = {1, 1, 1};
		int[] counters = new int[3];
		int[] daysInMonths = new int[3];
		int[] offset = {0, 0, 0};
		StringBuilder cal = new StringBuilder();
		
		for (int i = 0 ; i < daysInMonths.length; i++){
			daysInMonths[i] = numberOfDaysInMonth(months[i].getMonthValue() ,year);
			offset[i] = months[i].getDayOfWeek().getValue();
			if (!isMon){
				if (offset[i] == 7){
					offset[i] = 1;
				}
				else{
					offset[i]++;
				}
			}
		}
		
		//First Week
		for (int month = 0; month < months.length; month++){
			for (int i = 1; i < offset[month]; i++){
				cal.append(EMPTY_CELL);
			}
			for (int i = offset[month]; i <= 7; i++){
				cal.append(days[month]);
				cal.append(EMPTY_SPACE);
				if ( i != 7){
					cal.append(EMPTY_SPACE);
				}
				days[month]++;
			}
			//Not the last month
			if (month + 1 != months.length){
				cal.append(EMPTY_BORDER);
			}
			else{
				cal.append(System.lineSeparator());
			}
		}
		
		while (days[0] <= daysInMonths[0] || days[1] <= daysInMonths[1] || days[2] <= daysInMonths[2]){
			for (int month = 0; month < months.length; month++){
				while (counters[month] < 7 && days[month] <= daysInMonths[month]){
					cal.append(days[month]);
					if (days[month] < 10){
						cal.append(EMPTY_SPACE);	
					}
					days[month]++;
					counters[month]++;
					if (counters[month] != 7){ //Not the last cell
						cal.append(EMPTY_SPACE);
					}
				}
				
				if (counters[month] == 7){
					if ((month + 1) != months.length){
						cal.append(EMPTY_BORDER);
					}
					else{
						cal.append(System.lineSeparator());
					}
					counters[month] = 0;
				}
				else{
					for (int i = 0; i < (7-counters[month]); i++){
						if ((i+1) == (7 - counters[month])){
							cal.append(EMPTY_BORDER);
							if ((month+1) != months.length ){
								cal.append(EMPTY_BORDER);
							}
							else{
								cal.append(System.lineSeparator());
							}

						}
						else{
							cal.append(EMPTY_CELL);
						}
					}
					counters[month] = 0;
				}
			}
		}
		
//		cal.append(System.lineSeparator());
		
		return cal.toString();
	}
	
	/**
	 * Returns the string to print the calendar for specified year with Monday
	 * as the first day of the week
	 * @param args String array containing command and arguments
	 */
	public String printCalForYearMondayFirst(String args) {
		StringBuilder calOutput = new StringBuilder();
		String[] argArr = parseArgs(args);
		int[] months = new int[3];
		LocalDate[] monthDates = new LocalDate[3];
		int year = Integer.parseInt(argArr[2]);
		
		calOutput.append(printYear(year));
		calOutput.append(System.lineSeparator());
		for (int i = 0 ; i < 4; i++){
			months = new int[3];
			for (int j = 0 ; j < months.length; j++){
				months[j] = i * 3 + j + 1;
				monthDates[j] = LocalDate.of(year, months[j], 1);
			}
			calOutput.append(printMonth(months));
			calOutput.append(System.lineSeparator());
			for (int j = 0; j < months.length; j++){
				calOutput.append(printDaysHeader(true));
				if ( (j+1) != months.length){
					calOutput.append(EMPTY_BORDER);
				}
			}
			calOutput.append(System.lineSeparator());
			calOutput.append(printThreeMonths(monthDates, year, true));
		}
		
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
			if (i != 7){
				calendar.append(EMPTY_SPACE);	
			}
			day++;
		}
		calendar.append(System.lineSeparator());
		
		while (day <= daysInMonth){
			calendar.append(day);
			if (day < 10){
				calendar.append(EMPTY_SPACE);
			}
			day++;
			counter++;
			if (counter == 7){
				if (day <= daysInMonth){
					calendar.append(System.lineSeparator());
				}
				counter = 0;
			}
			else{
				calendar.append(EMPTY_SPACE);
			}
		}
		
		if (counter != 0){
			offset = 7 - counter;
			for ( int i = 0; i < offset; i++){
				if ((i+1) == offset){
					calendar.append(EMPTY_BORDER);
				}
				else{
					calendar.append(EMPTY_CELL);	
				}
			}
		}
		
		return calendar.toString();
	}
	
	/**
	 * Returns a String array containing cal as first string and 
	 * @param args
	 */
	private String[] parseArgs(String args){
		String[] argsToStringArray = args.split(EMPTY_SPACE);
		
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
	 * Returns the year header being centralized
	 * @param year specifies the year to print for the header
	 */
	private String printYear(int year){
		int offset;
		StringBuilder sb = new StringBuilder();
		
		sb.append(year);
		offset = YEAR_WIDTH - sb.length();
		offset = offset / 2;
		for (int i = 0; i < offset; i++){
			sb.insert(0, EMPTY_SPACE);
		}
		offset = YEAR_WIDTH - sb.length();
		for (int i = 0; i < offset; i++){
			sb.append(EMPTY_SPACE);
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns the month header for one row
	 * @param months specifies the months to return in a row
	 */
	public String printMonth(int[] months){
		StringBuilder sb = new StringBuilder();
		StringBuilder singleMon;
		int offset;
		
		for (int i = 0; i < months.length; i++){
			singleMon = new StringBuilder();
			singleMon.append(MONTH_NAME[months[i] - 1]);
			offset = (ROW_LENGTH - singleMon.length()) / 2;
			for (int j = 0; j < offset; j++){
				singleMon.insert(0, EMPTY_SPACE);
			}
			offset = ROW_LENGTH - singleMon.length();
			for (int j = 0; j < offset; j++){
				singleMon.append(EMPTY_SPACE);
			}
			sb.append(singleMon.toString());
			if ((i+1) != months.length){
				sb.append(EMPTY_BORDER);
			}
		}
		
		return sb.toString();
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

}
