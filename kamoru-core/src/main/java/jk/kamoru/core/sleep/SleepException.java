package jk.kamoru.core.sleep;

import jk.kamoru.KAMORU;
import jk.kamoru.KamoruException;

public class SleepException extends KamoruException {

	private static final long serialVersionUID = KAMORU.SERIAL_VERSION_UID;

	public SleepException(String message, Throwable cause) {
		super(message, cause);
	}

	public SleepException(String message) {
		super(message);
	}

	public SleepException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getKind() {
		return "Sleep";
	}

	
}
