package jk.kamoru.crazy;


public class ImageNotFoundException extends CrazyException {

	private static final long serialVersionUID = CRAZY.SERIAL_VERSION_UID;

	public ImageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageNotFoundException(String message) {
		super(message);
	}

	public ImageNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
