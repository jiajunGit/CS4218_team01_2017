package sg.edu.nus.comp.cs4218.exception;

public class PwdException extends AbstractApplicationException {

	private static final long serialVersionUID = -44412704558704575L;

	public PwdException(String message) {
		super("pwd: " + message);
	}
}
