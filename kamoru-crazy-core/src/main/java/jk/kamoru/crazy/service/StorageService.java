package jk.kamoru.crazy.service;

import java.util.List;

import jk.kamoru.crazy.ActressNotFoundException;
import jk.kamoru.crazy.ImageNotFoundException;
import jk.kamoru.crazy.StudioNotFoundException;
import jk.kamoru.crazy.VideoNotFoundException;
import jk.kamoru.crazy.domain.Actress;
import jk.kamoru.crazy.domain.History;
import jk.kamoru.crazy.domain.Image;
import jk.kamoru.crazy.domain.Search;
import jk.kamoru.crazy.domain.Studio;
import jk.kamoru.crazy.domain.Video;

public interface StorageService {

	// about Video
	
	Video getVideo(String opus) throws VideoNotFoundException;
	Studio getStudio(String name) throws StudioNotFoundException;
	Actress getActress(String name) throws ActressNotFoundException;
	
	List<Video> findVideo(Search search);
	List<Studio> findStudio(Search search);
	List<Actress> findActress(Search search);
	
	void mergeVideo(Video video);
	void mergeStudio(Studio studio);
	void mergeActress(Actress actress);
	
	// about Image
	
	Image getImage(Integer idx) throws ImageNotFoundException;
	Image getImageByRandom() throws ImageNotFoundException;
	List<Image> getImageList();
	Integer getImageSize();
	void removeImage(Integer idx);
	
	// about History
	
	List<History> findHistory(Search search);
	List<History> getHistoryList();
	
}
