package sg.edu.nus.comp.cs4218.exception;

public class HeadException extends AbstractApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3608957686975123140L;

	public HeadException(String message) {
		super("head:" + message);
	}
}
