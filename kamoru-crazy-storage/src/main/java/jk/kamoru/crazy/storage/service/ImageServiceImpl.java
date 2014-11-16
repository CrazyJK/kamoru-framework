package jk.kamoru.crazy.storage.service;

import java.util.List;

import jk.kamoru.crazy.domain.Image;
import jk.kamoru.crazy.service.ImageService;
import jk.kamoru.crazy.storage.source.ImageSource;

import org.springframework.beans.factory.annotation.Autowired;

//@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageSource imageSource;
	
	@Override
	public Image getImage(int idx) {
		return imageSource.getImage(idx);
	}

	@Override
	public int getImageSourceSize() {
		return imageSource.getImageSourceSize();
	}

	@Override
	public void reload() {
		imageSource.reload();
	}

	@Override
	public Image getImageByRandom() {
		return imageSource.getImage(getRandomImageNo());
	}

	@Override
	public List<Image> getImageList() {
		return imageSource.getImageList();
	}

	@Override
	public String getImageNameJSON() {
		StringBuilder sb = new StringBuilder("{");
		int index = 0;
		for (Image image : imageSource.getImageList()) {
			if (index > 0)
				sb.append(",");
			sb.append(String.format("\"%s\":\"%s\"", index++, image.getName()));
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public void delete(int idx) {
		imageSource.delete(idx);
	}

	@Override
	public int getRandomImageNo() {
		return (int)(Math.random() * imageSource.getImageSourceSize());
	}
}
