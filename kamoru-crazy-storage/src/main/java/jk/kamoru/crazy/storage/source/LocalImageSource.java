package jk.kamoru.crazy.storage.source;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jk.kamoru.crazy.CrazyException;
import jk.kamoru.crazy.domain.Image;
import jk.kamoru.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.math.NumberUtils;

@Slf4j
public class LocalImageSource implements ImageSource {

	private List<Image> imageList;

	private String[] imageStoragePaths;
	
	public void setImageStoragePaths(String... imageStoragePaths) {
		this.imageStoragePaths = imageStoragePaths;
	}

	private void listImages() {
		List<File> imageFileList = new ArrayList<File>();
		for (String path : this.imageStoragePaths) {
			File dir = new File(path);
			if (dir.isDirectory()) {
				log.debug("directory scanning : {}", dir);
				Collection<File> found = FileUtils.listFiles(dir, new String[] {"jpg", "jpeg", "gif", "png" }, true);
				imageFileList.addAll(found);
			}
		}

		imageList = new ArrayList<Image>();
		int idx = 0;
		for (File file : imageFileList) {
			imageList.add(new Image(file, idx++));
		}
		
		try {
			Collections.sort(imageList, new Comparator<Image>() {
				@Override
				public int compare(Image o1, Image o2) {
					return NumberUtils.compare(o1.getLastModified(), o2.getLastModified());
					/* 아래처럼 비교할 경우 에러 발생
					   java.lang.IllegalArgumentException: Comparison method violates its general contract! 
					return (int) (o1.getLastModified() - o2.getLastModified()); */
//					return o1.getLastModified() - o2.getLastModified() > 0 ? 1 : -1;
				}
			});
		}
		catch (Exception e) {
			log.warn("Error: {}", e.getMessage());
		}
		log.info("Total found image {}", imageList.size());
	}

	@Override
	public void reload() {
		log.trace("attempt to reload");
		listImages();
	}

	private List<Image> createImageSource() {
		if (imageList == null)
			reload();
		return imageList;
	}

	@Override
	public Image getImage(int idx) {
		try {
			return createImageSource().get(idx);
		}
		catch(IndexOutOfBoundsException  e) {
			throw new CrazyException("Image not found " + idx, e);
		}
	}

	@Override
	public List<Image> getImageList() {
		return createImageSource();
	}

	@Override
	public int getImageSourceSize() {
		return createImageSource().size();
	}

	@Override
	public void delete(int idx) {
		Image image = createImageSource().get(idx);
		image.delete();
		createImageSource().remove(idx);
		log.info("DELETE - {}", image.getName());
	}

}
