package jk.kamoru.core.storage;

import jk.kamoru.KAMORU;
import jk.kamoru.KamoruException;

public class StorageException extends KamoruException {

	private static final long serialVersionUID = KAMORU.SERIAL_VERSION_UID;

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}

	public StorageException(String message) {
		super(message);
	}

	public StorageException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getKind() {
		return "Storage";
	}

	
}
