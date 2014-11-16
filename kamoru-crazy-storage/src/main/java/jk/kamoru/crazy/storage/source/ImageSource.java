package jk.kamoru.crazy.storage.source;

import java.util.List;

import jk.kamoru.crazy.domain.Image;

public interface ImageSource {

	Image getImage(int idx);
	
	List<Image> getImageList();

	int getImageSourceSize();
	
	void reload();

	void delete(int idx);
	
}
