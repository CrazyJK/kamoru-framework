package jk.kamoru.spring;

import jk.kamoru.KamoruException;

public class SpringException extends KamoruException {

	private static final long serialVersionUID = SPRING.SERIAL_VERSION_UID;

	public SpringException(String message, Throwable cause) {
		super(message, cause);
	}

	public SpringException(String message) {
		super(message);
	}

	public SpringException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getKind() {
		return "Spring";
	}
	
}
