package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import sg.edu.nus.comp.cs4218.app.Date;
import sg.edu.nus.comp.cs4218.exception.DateException;

public class DateApplication implements Date {
	/**
	 * Assumption: if args contains some string, it will throw an exception
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws DateException {
		String outputStr;

		if (stdout == null) {
			throw new DateException("System.out is not found");
		} else if (args == null) {
			outputStr = printCurrentDate("");
		} else if (stdin != null) {
			throw new DateException("Date does not accept stdin");
		} 
		else {
			outputStr = printCurrentDate(args.toString());
		}

		try {
			stdout.write(outputStr.getBytes());
		} catch (IOException e) {
			throw new DateException("Unable to write output to stdout");
		}

	}

	@Override
	public String printCurrentDate(String args) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");

		return ZonedDateTime.now().format(formatter);
	}

}
