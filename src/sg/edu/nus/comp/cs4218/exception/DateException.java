package sg.edu.nus.comp.cs4218.exception;

public class DateException extends AbstractApplicationException {

	private static final long serialVersionUID = -6297822670731803186L;

	public DateException(String message) {
		super("date: " + message);
	}
}
