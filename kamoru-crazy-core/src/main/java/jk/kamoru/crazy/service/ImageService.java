package jk.kamoru.crazy.service;

import java.util.List;

import jk.kamoru.crazy.domain.Image;

public interface ImageService {

	Image getImage(int idx);

	int getImageSourceSize();

	void reload();

	Image getImageByRandom();

	List<Image> getImageList();

	String getImageNameJSON();

	void delete(int idx);

	int getRandomImageNo();
}
