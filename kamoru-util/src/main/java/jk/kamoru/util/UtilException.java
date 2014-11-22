package jk.kamoru.util;

import jk.kamoru.KAMORU;
import jk.kamoru.KamoruException;

public class UtilException extends KamoruException {

	private static final long serialVersionUID = KAMORU.SERIAL_VERSION_UID;

	public UtilException(String message, Throwable cause) {
		super(message, cause);
	}

	public UtilException(String message) {
		super(message);
	}

	public UtilException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getKind() {
		return "Util";
	}
	
}
