package jk.kamoru.crazy.storage.source;

import java.util.List;
import java.util.Map;

import jk.kamoru.crazy.domain.Actress;
import jk.kamoru.crazy.domain.Studio;
import jk.kamoru.crazy.domain.Video;

public interface VideoSource {

	/**
	 * 전체 Video 맵. &lt;opus, video&gt;
	 * @return map of video
	 */
	Map<String, Video> getVideoMap();
	
	/**
	 * 전체 Studio 맵. &lt;opus, studio&gt;
	 * @return map of studio
	 */
	Map<String, Studio> getStudioMap();
	
	/**
	 * 전체 Actress 맵. &lt;opus, actress&gt;
	 * @return map of actress
	 */
	Map<String, Actress> getActressMap();

	/**
	 * @return total video list
	 */
	List<Video> getVideoList();
	
	/**
	 * @return total studio list
	 */
	List<Studio> getStudioList();

	/**
	 * @return total actress list
	 */
	List<Actress> getActressList();

	
	/**
	 * 비디오 리로드.
	 */
	void reload();
	
	/**
	 * 비디오 삭제
	 */
	void removeVideo(String opus);
	
	Video getVideo(String opus);
	
	Studio getStudio(String name);
	
	Actress getActress(String name);

	/**
	 * move video file to destination path
	 * @param opus
	 * @param destPath
	 */
	void moveVideo(String opus, String destPath);

	/**
	 * arrange video
	 * @param opus
	 */
	void arrangeVideo(String opus);
}
