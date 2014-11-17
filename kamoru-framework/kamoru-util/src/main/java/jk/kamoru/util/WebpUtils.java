package jk.kamoru.util;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebpUtils {

	/**
	 * image를 webp로 변환
	 * @param cwebp cwebp 모듈 경로
	 * @param imageFile 원본 이미지 파일
	 * @param destPath 저장 폴더
	 */
	public static void convert(String cwebp, File imageFile, File destPath) {
		log.trace("{} {} {}", cwebp, imageFile.getPath(), destPath.getPath());
		Assert.isTrue(imageFile.isFile(), "image is not file!");
		Assert.isTrue(destPath.exists(), "destination path is not exist!");
		
		String command = String.format("%s \"%s\" -q 80 -o \"%s\"", 
				cwebp,  
				imageFile.getAbsolutePath(), 
				new File(destPath, FileUtils.getNameExceptExtension(imageFile) + ".webp").getAbsolutePath());
		
		RuntimeUtils.exec(command);
	}

	
	/** image를 webp로 변환. image와 같은 경로에 만든다.
	 * @param cwebp
	 * @param imageFile
	 */
	public static void convert(String cwebp, File imageFile) {
		log.trace("{} {}", cwebp, imageFile.getPath());
		convert(cwebp, imageFile, imageFile.getParentFile());
	}
	
}
