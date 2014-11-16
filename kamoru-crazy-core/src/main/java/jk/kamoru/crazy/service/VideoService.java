package jk.kamoru.crazy.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import jk.kamoru.crazy.domain.Actress;
import jk.kamoru.crazy.domain.SortActress;
import jk.kamoru.crazy.domain.SortVideo;
import jk.kamoru.crazy.domain.Studio;
import jk.kamoru.crazy.domain.SortStudio;
import jk.kamoru.crazy.domain.TitlePart;
import jk.kamoru.crazy.domain.Video;
import jk.kamoru.crazy.domain.Search;

/**
 * Video Service Interface
 * @author kamoru
 */
/**
 * @author kamoru
 *
 */
public interface VideoService {

	/**Delete video
	 * @param opus
	 */
	void deleteVideo(String opus);

	/**Call subtitles editor
	 * @param opus
	 */
	void editVideoSubtitles(String opus);

	/**find history by query 
	 * @param query
	 * @return history result
	 */
	List<Map<String, String>> findHistory(String query);

	/**find video by query
	 * @param query
	 * @return video list
	 */
	List<Map<String, String>> findVideoList(String query);

	/**get actress by name
	 * @param actressName
	 * @return actress
	 */
	Actress getActress(String actressName);

	/**all actress list
	 * @return actress list
	 */
	List<Actress> getActressList();

	/**actress list in video
	 * @param videoList
	 * @return actress list
	 */
	List<Actress> getActressListInVideoList(List<Video> videoList);

	/** get default cover file byte array
	 * @return image byte array
	 */
	byte[] getDefaultCoverFileByteArray();

	/**get studio by name
	 * @param studioName
	 * @return studio
	 */
	Studio getStudio(String studioName);

	/**get all studio list
	 * @return studio list
	 */
	List<Studio> getStudioList();
	
	/** get stuio ilst in video
	 * @param videoList
	 * @return studio list
	 */
	List<Studio> getStudioListInVideoList(List<Video> videoList);
	
	/** get video by opus
	 * @param opus
	 * @return video
	 */
	Video getVideo(String opus);

	/**get video cover byte array
	 * @param opus
	 * @param isChrome Whether or not request's client is chrome
	 * @return cover byte array
	 */
	byte[] getVideoCoverByteArray(String opus);

	/**get video cover file
	 * @param opus
	 * @param isChrome Whether or not request's client is chrome
	 * @return cover file
	 */
	File getVideoCoverFile(String opus);

	/**get all video list
	 * @return video list
	 */
	List<Video> getVideoList();

	/**play video
	 * @param opus
	 */
	void playVideo(String opus);

	/**set video rank
	 * @param opus
	 * @param rank
	 */
	void rankVideo(String opus, int rank);

	/**save video overvire
	 * @param opus
	 * @param overViewTxt
	 */
	void saveVideoOverview(String opus, String overViewTxt);

	/**get video by search bean
	 * @param videoSearch
	 * @return video list
	 */
	List<Video> searchVideo(Search videoSearch);

	/**get info group by path
	 * @return map of path, length array
	 */
	Map<String, Long[]> groupByPath();

	/**save actress info
	 * @param name actress name
	 * @param params map of actress info
	 */
	void saveActressInfo(String name, Map<String, String> params);

	/**get info group by date
	 * @return map of date, video list
	 */
	Map<String, List<Video>> groupByDate();
	
	/**get info group by rank
	 * @return map of rank, video list
	 */
	Map<Integer, List<Video>> groupByRank();
	
	/**get info group by play count
	 * @return map of play count, video list
	 */
	Map<Integer, List<Video>> groupByPlay();

	/**move video files
	 * @param opus
	 * @param path destination path
	 */
	void moveVideo(String opus, String path);

	/**
	 * reload video source
	 */
	void reload();

	/**save studio info
	 * @param studio
	 * @param params map of studio info
	 */
	void saveStudioInfo(String studio, Map<String, String> params);

	/**get all sorted actress list
	 * @param sort
	 * @param reverse 
	 * @return actress list
	 */
	List<Actress> getActressList(SortActress sort, boolean reverse);

	/**get all sorted studio list
	 * @param sort
	 * @param reverse 
	 * @return studio list
	 */
	List<Studio> getStudioList(SortStudio sort, boolean reverse);

	/**get all sorted video list
	 * @param sort
	 * @param reverse 
	 * @return video list
	 */
	List<Video> getVideoList(SortVideo sort, boolean reverse);

	/**get play count range
	 * @return play count list
	 */
	List<Integer> getPlayRange();

	/**get minimum rank in properties
	 * @return rank
	 */
	Integer minRank();

	/**get maximum rank in properties
	 * @return rank
	 */
	Integer maxRank();

	/**get rank range
	 * @return rank list
	 */
	List<Integer> getRankRange();

	/**
	 * remove lower rank video
	 */
	void removeLowerRankVideo();

	/**
	 * remove lower socore video
	 */
	void removeLowerScoreVideo();

	/**
	 * delete garbage file
	 */
	void deleteGarbageFile();

	/**
	 * move watched video
	 */
	void moveWatchedVideo();

	/**
	 * arrange video. move files of video to same directory  
	 */
	void arrangeVideo();

	/**get video list with torrent info
	 */
	List<Video> torrent();

	/**move video file. rename to fullname
	 * @param opus
	 * @param path current video file
	 */
	void confirmCandidate(String opus, String path);

	/**get info group by score
	 * @return map of score, video list
	 */
	Map<Integer, List<Video>> groupByScore();

	void rename(String opus, String newName);

	void renameOfActress(String name, String newName);
	
	void renameOfStudio(String name, String newName);

	List<TitlePart> parseToTitleData(String titleData);

	/**get groups by length
	 * @return map of length, video list
	 */
	Map<Float, List<Video>> groupByLength();

	Map<String, List<Video>> groupByExtension();
	
}
