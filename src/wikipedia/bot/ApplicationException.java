package wikipedia.bot;

/**
 * 
 * @author Mir4ik
 * @version 0.1 09.04.2014
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = -6984202151588583930L;

	public ApplicationException(String message) {
		super(message);
	}
	
	public ApplicationException(Throwable cause) {
		super(cause);
	}
	
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}
}