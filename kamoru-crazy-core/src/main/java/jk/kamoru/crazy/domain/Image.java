package jk.kamoru.crazy.domain;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import jk.kamoru.crazy.CRAZY;
import jk.kamoru.crazy.CrazyException;
import jk.kamoru.crazy.ImageException;
import jk.kamoru.util.FileUtils;
import lombok.Cleanup;
import lombok.Data;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

@Data
public class Image implements Serializable {

	private static final long serialVersionUID = CRAZY.SERIAL_VERSION_UID;

	private int idx;
	private String name;
	private String suffix;
	private long size;
	private long lastModified;
	private File file;

	public Image(File file) {
		this.file = file;
		init();
	}

	public Image(File file, int i) {
		this(file);
		this.idx = i;
	}

	private void init() {
		this.name = file.getName();
		this.suffix = FileUtils.getExtension(file);
		this.size = file.length();
		this.lastModified = file.lastModified();
	}

	public byte[] getImageBytes(PictureType type) {
		try {
			switch (type) {
			case MASTER:
				return FileUtils.readFileToByteArray(file);
			case WEB:
				return readBufferedImageToByteArray(Scalr.resize(ImageIO.read(file), Scalr.Mode.FIT_TO_WIDTH, 500));
			case THUMBNAIL:
				return readBufferedImageToByteArray(Scalr.resize(ImageIO.read(file), Method.SPEED, 100, Scalr.OP_ANTIALIAS, Scalr.OP_BRIGHTER));
			default:
				throw new CrazyException("wrong PictureType = " + type);
			}
		} catch (IOException e) {
			throw new ImageException(this, "image io error", e);
		}
	}

	private byte[] readBufferedImageToByteArray(BufferedImage bi) throws IOException {
		@Cleanup
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.setUseCache(false);
		ImageIO.write(bi, "gif", outputStream);
		return outputStream.toByteArray();
	}

	public void delete() {
		FileUtils.deleteQuietly(file);
	}

}
