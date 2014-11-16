package jk.kamoru.crazy;


public class StudioNotFoundException extends VideoException {

	private static final long serialVersionUID = CRAZY.SERIAL_VERSION_UID;

	public StudioNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public StudioNotFoundException(String message) {
		super(message);
	}

	public StudioNotFoundException(Throwable cause) {
		super(cause);
	}

}
