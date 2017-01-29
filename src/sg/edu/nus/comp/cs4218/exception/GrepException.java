package sg.edu.nus.comp.cs4218.exception;

public class GrepException extends AbstractApplicationException {

	private static final long serialVersionUID = 7494201967089104L;
	
	public GrepException(String message) {
		super("grep:" + message);
	}
}
