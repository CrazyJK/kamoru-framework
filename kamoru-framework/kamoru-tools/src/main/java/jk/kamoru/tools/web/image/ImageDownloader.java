package jk.kamoru.tools.web.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import jk.kamoru.util.FileUtils;
import jk.kamoru.util.StringUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web image downloader
 * @author kamoru
 */
public class ImageDownloader extends Thread {
	
	private static final Logger logger = LoggerFactory.getLogger(ImageDownloader.class);

	/**
	 * image suffix list. "png", "jpg", "jpeg", "gif", "webp", "bmp"
	 */
	private static final List<String> IMAGE_SUFFIX_LIST = Arrays.asList("png", "jpg", "jpeg", "gif", "webp", "bmp");
	
	/**
	 * default image suffix
	 */
	public static final String DEFAULT_IMAGE_SUFFIX = "jpg";
	
	private String imgSrc;
	private String title;
	private String destDir;
	private long minimumSize;
	
	/**
	 * Constructs a new <code>ImageDownloader</code> using image source, title, destination directory<br>
	 * execute - {@link #download()} or if Thread mode {@link #run()}
	 * @param imageSrc image source url
	 * @param title image title
	 * @param destDir destination directory
	 */
	public ImageDownloader(String imageSrc, String title, String destDir) {
		this(imageSrc, title, destDir, 0);
	}

	/**
	 * Constructs a new <code>ImageDownloader</code> using image source, title, destination directory<br>
	 * execute - {@link #download()} or if Thread mode {@link #run()}
	 * @param imgSrc image source url
	 * @param title image title
	 * @param destDir destination directory
	 * @param minimunSize minimum image size(bytes)
	 */
	public ImageDownloader(String imgSrc, String title, String destDir, long minimunSize) {
		super();
		this.imgSrc 	 = imgSrc;
		this.title 		 = title;
		this.destDir 	 = destDir;
		this.minimumSize = minimunSize;
	}
	
	public void run() {
		download();
	}
	
	/**
	 * execute download
	 */
	public void download() {

		// Execute a request of image
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		try {
			HttpGet httpGet = new HttpGet(imgSrc);
			httpResponse = httpClient.execute(httpGet);
			logger.debug("Execute a request of image {}", imgSrc);
		} catch (Exception e) {
			logger.error("fail to execute a request of image {} - {}", imgSrc, e.getMessage());
			return;
		}
		
		/* Test Code : All Header info
		Header[] headers = httpResponse.getAllHeaders();
		for (Header header : headers) {
			logger.debug("Header info : {}={}", header.getName(), header.getValue());
		}*/
		
		// save image 
		HttpEntity entity = httpResponse.getEntity();

		if (entity != null && entity.getContentLength() > minimumSize) {
			
			// define a suffix
			String imageSuffix = DEFAULT_IMAGE_SUFFIX;
			Header contentTypeHeader = httpResponse.getLastHeader("Content-Type");
			String[] contentType = StringUtils.split(contentTypeHeader.getValue(), '/');
			if (contentType != null && contentType.length > 1 && contentType[0].equals("image")) {
				imageSuffix = contentType[1];
			}
			else {
				String srcBasedPrefix = StringUtils.substringAfterLast(imgSrc, ".");
				if (IMAGE_SUFFIX_LIST.contains(srcBasedPrefix.toLowerCase()))
					imageSuffix = srcBasedPrefix;
			}

			File imageFile = new File(destDir, title + "." + imageSuffix);
			
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				inputStream = entity.getContent();
				outputStream = new FileOutputStream(imageFile);
				byte[] buffer = new byte[1024];
				int length = 0;
				while ((length = inputStream.read(buffer)) >0) {
					outputStream.write(buffer, 0, length);
				}
				logger.debug("save as {} from {}", imageFile.getAbsolutePath(), imgSrc);
			} 
			catch (IOException e) {
				logger.error("fail to save {} - {}", imgSrc, e.getMessage());
				FileUtils.deleteQuietly(imageFile);
				return;
			} 
			finally {
				if (outputStream != null)
					try {
						outputStream.close();
					} catch (IOException e) {
						logger.error("{}", e.getMessage());
					}
				if (inputStream != null)
					try {
						inputStream.close();
					} catch (IOException e) {
						logger.error("{}", e.getMessage());
					}
			}
		}
		else {
			logger.error("entity is null or smaill. {} - {}", imgSrc, (entity == null ? null : entity.getContentLength()));
		}		
	}

}
