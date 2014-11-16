package jk.kamoru.crazy;

import jk.kamoru.crazy.CrazyException;
import jk.kamoru.crazy.domain.Image;

public class ImageException extends CrazyException {

	private static final long serialVersionUID = CRAZY.SERIAL_VERSION_UID;

	private static final String KIND = "Image";

	private Image image; 
	
	public ImageException(Image image, String message, Throwable cause) {
		super(String.format("[%s] %s", image.getName(), message), cause);
		this.image = image;
	}

	public ImageException(Image image, String message) {
		super(String.format("[%s] %s", image.getName(), message));
		this.image = image;
	}

	public ImageException(Image image, Throwable cause) {
		super(String.format("[%s]", image.getName()), cause);
		this.image = image;
	}
	
	public Image getImage() {
		return image;
	}
	
	@Override
	public String getKind() {
		return KIND;
	}

}
