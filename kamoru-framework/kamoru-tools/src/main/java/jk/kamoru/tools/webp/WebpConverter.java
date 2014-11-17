package jk.kamoru.tools.webp;

import java.io.File;
import java.util.Collection;

import jk.kamoru.util.WebpUtils;

import org.apache.commons.io.FileUtils;

public class WebpConverter {

//	private static final String cwebp = "/home/kamoru/DevTools/libwebp-0.3.0-linux-x86-32/cwebp"; 
//	private static final String imgPath = "/home/kamoru/DaumCloud/MyPictures/Entertainer";
//	private static final String destPath = "/home/kamoru/Enter/";

	private static final String cwebp = "E:\\tools\\libwebp-0.3.0-windows-x86\\cwebp.exe"; 
	private static final String imgPath = "E:\\Unwatched_video";
	private static final String destPath = "E:\\av_cover\\";

	private static final String[] imgExt = {"jpg", "jpng", "png", "JPG", "JPNG", "PNG"};

	public static void main(String[] args) throws InterruptedException {
		File dest = new File(destPath);
		
		Collection<File> found = FileUtils.listFiles(new File(imgPath), imgExt, true);
		for(File file : found) {
			WebpUtils.convert(cwebp, file, dest);
			Thread.sleep(1000);
		}
	}

}
