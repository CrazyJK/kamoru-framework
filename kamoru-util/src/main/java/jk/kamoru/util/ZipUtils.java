package jk.kamoru.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUtils {

	protected static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);

	/**
	 * zip파일로 압축한다
	 * @param zipFile zip으로 만들 파일
	 * @param baseDir 폴더 압축시 기본 위치. 파일 리스트가 없을 경우 이 경로의 모든 파일을 압축한다.
	 * @param fileList 압축할 파일 리스트
	 * @return 압축한 파일 개수
	 */
	public static int zip(File zipFile, File baseDir, List<File> fileList) {

		// arguments check
		if (zipFile == null || !zipFile.getParentFile().exists()) 
			throw new IllegalArgumentException("zipfile is null or parent dir is not exist : zipFile=" + (zipFile == null ? null : zipFile.getAbsolutePath()));
		if (baseDir == null && (fileList == null || fileList.size() ==0 ))
			throw new IllegalArgumentException("baseDir and fileList, one pf the two must not be null");

		// 파일 리스트가 없으면, 폴더 검색을 해서 파일을 찾는다. 
		if (baseDir != null && (fileList == null || fileList.size() == 0)) {
			if (!baseDir.isDirectory())
				throw new IllegalArgumentException("baseDir[" + baseDir + "] is not directory");

			Collection<File> foundFiles =  FileUtils.listFiles(baseDir, null, true);
			if (foundFiles == null || foundFiles.size() == 0)
				throw new IllegalArgumentException("baseDir[" + baseDir + "], file is not in");
			fileList = new ArrayList<File>(foundFiles);
		}
		
		ZipOutputStream out = null;
		int count = 0;
		try {
			logger.debug("zip start : {}", zipFile.getAbsolutePath());
			out = new ZipOutputStream(new FileOutputStream(zipFile));

			for (File file : fileList) {
				String entryName = null;
				if (baseDir != null) {
					if (file.getAbsolutePath().startsWith(baseDir.getAbsolutePath()))
						entryName = StringUtils.removeStart(file.getAbsolutePath(), baseDir.getAbsolutePath());
					else
						entryName = file.getName();
				}
				else {
					entryName = file.getName();
				}
				ZipEntry entry = new ZipEntry(entryName);
				try {
					out.putNextEntry(entry);
					
					BufferedInputStream bis  = new BufferedInputStream(new FileInputStream(file));
					int bytesRead;
					byte[] buffer = new byte[1024];
					while ((bytesRead = bis.read(buffer)) != -1) {
						out.write(buffer, 0, bytesRead);
					}
					bis.close();
					out.closeEntry();
					count++;
				} catch (IOException e) {
					logger.error("Error in archiving [" + entryName + "]  ", e);
					continue;
				}
			}
			logger.debug("zip end : total file count {}", count);
			return count;
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("zipfile not found : zipFile=" + zipFile.getAbsolutePath());
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					throw new IllegalStateException("zipFile close error", e);
				}
		}
	}
	
	/**
	 * 폴더를 zip파일로 압축한다.
	 * @param zipFile zip 파일
	 * @param baseDir 압축할 폴더
	 * @return 압축한 파일 개수
	 */
	public static int zip(File zipFile, File baseDir) {
		return zip(zipFile, baseDir, null);
	}
	
	/**파일 리스트를 zip으로 압축한다.
	 * @param zipFile zip 파일
	 * @param fileList 압축할 파일 리스트
	 * @return 압축한 파일 개수
	 */
	public static int zip(File zipFile, List<File> fileList) {
		return zip(zipFile, null, fileList);
	}
	
	/**
	 * 압축을 푼다.
	 * @param zipFile zip file
	 * @param destDir 압축을 풀 폴더
	 */
	public static void unzip(File zipFile, File destDir) {
		// check
		if (zipFile == null || !zipFile.exists()) 
			throw new IllegalArgumentException("zipFile is null or not exist");
		
		try {
			ZipFile zip = new ZipFile(zipFile);
			Enumeration<? extends ZipEntry> entries = zip.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				String entryName = entry.getName();
				File file = new File(destDir, entryName);
				FileUtils.copyInputStreamToFile(zip.getInputStream(entry), file);

				logger.debug("{} [{}]", entry.getName(), entry.getSize());
			}
			zip.close();
		} catch (IOException e) {
			throw new IllegalStateException("unzip error", e);
		}
	}
	
}
