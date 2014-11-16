package jk.kamoru.crazy;


public class ActressNotFoundException extends VideoException {

	private static final long serialVersionUID = CRAZY.SERIAL_VERSION_UID;

	public ActressNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ActressNotFoundException(String message) {
		super(message);
	}

	public ActressNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
