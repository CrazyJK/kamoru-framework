package jk.kamoru.crazy;

import jk.kamoru.crazy.CrazyException;
import jk.kamoru.crazy.domain.Video;

/**video에서 발생하는 에러
 * @author kamoru
 */
public class VideoException extends CrazyException {

	private static final long serialVersionUID = CRAZY.SERIAL_VERSION_UID;

	private static final String KIND = "Video";

	private Video video;
	
	public VideoException(String message, Throwable cause) {
		super(message, cause);
	}

	public VideoException(String message) {
		super(message);
	}

	public VideoException(Throwable cause) {
		super(cause);
	}

	public VideoException(Video video, String message, Throwable cause) {
		super(String.format("[%s] %s", video.getOpus(), message), cause);
		this.video = video;
	}

	public VideoException(Video video, String message) {
		super(String.format("[%s] %s", video.getOpus(), message));
		this.video = video;
	}

	public VideoException(Video video, Throwable cause) {
		super(String.format("[%s]", video.getOpus()), cause);
		this.video = video;
	}

	public Video getVideo() {
		return video;
	}
	
	@Override
	public String getKind() {
		return KIND;
	}

}
