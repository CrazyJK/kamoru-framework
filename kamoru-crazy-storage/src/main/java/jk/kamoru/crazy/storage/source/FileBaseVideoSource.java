package jk.kamoru.crazy.storage.source;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import jk.kamoru.crazy.ActressNotFoundException;
import jk.kamoru.crazy.CRAZY;
import jk.kamoru.crazy.StudioNotFoundException;
import jk.kamoru.crazy.VideoNotFoundException;
import jk.kamoru.crazy.domain.Actress;
import jk.kamoru.crazy.domain.Studio;
import jk.kamoru.crazy.domain.Video;
import jk.kamoru.crazy.util.VideoUtils;
import jk.kamoru.util.FileUtils;
import jk.kamoru.util.StringUtils;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class FileBaseVideoSource implements VideoSource {
	
	protected static final Logger logger = LoggerFactory.getLogger(FileBaseVideoSource.class);

	private final String UNKNOWN 			 = "_Unknown";
	private final String unclassifiedStudio  = UNKNOWN;
	private final String unclassifiedOpus 	 = UNKNOWN;

	// data source
	private Map<String, Video>     videoMap	= new HashMap<String, Video>();
	private Map<String, Studio>   studioMap	= new HashMap<String, Studio>();
	private Map<String, Actress> actressMap = new HashMap<String, Actress>();
	
	// Domain provider
	@Inject Provider<Video>     videoProvider;
	@Inject Provider<Studio>   studioProvider;
	@Inject Provider<Actress> actressProvider;

	// property
	private String[]   videoStoragePaths;
	private String 	     videoExtensions;
	private String 	     coverExtensions;
	private String 	 subtitlesExtensions;

	// logic variables
	private boolean loaded = false;
	
	// property setter
	public void setVideoStoragePaths(String... paths) {
		Assert.notNull(paths, "base paths must not be null");
		this.videoStoragePaths = paths;
	}
	public void setVideoExtensions(String video_extensions) {
		Assert.notNull(video_extensions, "video ext must not be null");
		this.videoExtensions = video_extensions;
	}
	public void setCoverExtensions(String cover_extensions) {
		Assert.notNull(cover_extensions, "cover ext must not be null");
		this.coverExtensions = cover_extensions;
	}
	public void setSubtitlesExtensions(String subtitles_extensions) {
		Assert.notNull(subtitles_extensions, "subtitles ext must not be null");
		this.subtitlesExtensions = subtitles_extensions;
	}
	
	/**
	 * video데이터를 로드한다.
	 */
	private synchronized void load() {
		logger.trace("load");

		// 1. data source initialize
//		videoMap = new HashMap<String, Video>();
//		studioMap = new HashMap<String, Studio>();
//		actressMap = new HashMap<String, Actress>();
		videoMap.clear();
		studioMap.clear();
		actressMap.clear();
		
		// 2. file find
		Collection<File> files = new ArrayList<File>();
		for (String path : videoStoragePaths) {
			File directory = new File(path);
			logger.debug("directory scanning : {}", directory.getAbsolutePath());
			if (directory.isDirectory()) {
				Collection<File> found = FileUtils.listFiles(directory, null, true);
				logger.debug("\tfound file size is {}", found.size());
				files.addAll(found);
			}
			else {
				logger.warn("\tIt is not directory. Pass!!!");
			}
		}
		logger.info("Total found file {}", files.size());

		// 3. domain create & data source   
		int unclassifiedNo = 1;
		for (File file : files) {
			try {
				String filename = file.getName();
				String     name = FileUtils.getNameExceptExtension(file);
				String      ext = FileUtils.getExtension(file).toLowerCase();
				
				// 연속 스페이스 제거
				name = StringUtils.normalizeSpace(name);
				// Unnecessary file exclusion
				if (filename.equals(CRAZY.HISTORY_LOG) 
						|| filename.equals(CRAZY.MAC_NETWORKSTORES)
						|| filename.equals(CRAZY.WINDOW_DESKTOPINI)
						|| ext.equals(CRAZY.EXT_ACTRESS) 
						|| ext.equals(CRAZY.EXT_STUDIO))
					continue;
				
				// 1       2     3      4        5     6
				// [studio][opus][title][actress][date]etc...
				String[] names 		= StringUtils.split(name, "]");
				String studioName  	= UNKNOWN;
				String opus    		= UNKNOWN;
				String title   		= filename;
				String actressNames = UNKNOWN;
				String releaseDate 	= "";
				String etcInfo 		= "";
				
				switch (names.length) {
				case 6:
					etcInfo 	= VideoUtils.removeUnnecessaryCharacter(names[5]);
				case 5:
					releaseDate = VideoUtils.removeUnnecessaryCharacter(names[4]);
				case 4:
					actressNames = VideoUtils.removeUnnecessaryCharacter(names[3], CRAZY.UNCLASSIFIEDACTRESS);
				case 3:
					title 		= VideoUtils.removeUnnecessaryCharacter(names[2], UNKNOWN);
				case 2:
					opus 		= VideoUtils.removeUnnecessaryCharacter(names[1], unclassifiedOpus);
					studioName 	= VideoUtils.removeUnnecessaryCharacter(names[0], unclassifiedStudio);
					break;
				case 1:
					studioName 	= unclassifiedStudio;
					opus 		= unclassifiedOpus + unclassifiedNo++;
					title 		= filename;
					actressNames = CRAZY.UNCLASSIFIEDACTRESS;
					break;
				default: // if names length is over 6
					logger.debug("File [{}] [{}] [{}]", filename, names.length, ArrayUtils.toString(names));
					studioName 	= VideoUtils.removeUnnecessaryCharacter(names[0], unclassifiedStudio);
					opus 		= VideoUtils.removeUnnecessaryCharacter(names[1], unclassifiedOpus);
					title 		= VideoUtils.removeUnnecessaryCharacter(names[2], UNKNOWN);
					actressNames = VideoUtils.removeUnnecessaryCharacter(names[3], CRAZY.UNCLASSIFIEDACTRESS);
					releaseDate = VideoUtils.removeUnnecessaryCharacter(names[4]);
					for (int i=5, iEnd=names.length; i<iEnd; i++)
						etcInfo = etcInfo + " " + VideoUtils.removeUnnecessaryCharacter(names[i]);
				}
				
				Video video = videoMap.get(opus.toLowerCase());
				if (video == null) {
					video = this.videoProvider.get();
					video.setOpus(opus.toUpperCase());
					video.setTitle(title);
					video.setReleaseDate(releaseDate);
					video.setEtcInfo(etcInfo);
					videoMap.put(opus.toLowerCase(), video);
					logger.trace("add video - {}", video);
				}
				// set video File
				if (videoExtensions.toLowerCase().contains(ext))
					video.addVideoFile(file);
				else if (coverExtensions.toLowerCase().contains(ext))
					video.setCoverFile(file);
				else if (subtitlesExtensions.toLowerCase().contains(ext))
					video.addSubtitlesFile(file);
				else if (CRAZY.EXT_INFO.equalsIgnoreCase(ext))
					video.setInfoFile(file);
				else if (CRAZY.EXT_WEBP.equalsIgnoreCase(ext))
					video.setCoverWebpFile(file);
				else
					video.addEtcFile(file);
				
				Studio studio = studioMap.get(studioName.toLowerCase());
				if (studio == null) {
					studio = this.studioProvider.get();
					studio.setName(studioName);
					studioMap.put(studioName.toLowerCase(), studio);
					logger.trace("add studio - {}", studio);
				}

				// inject reference
				studio.addVideo(video);
				video.setStudio(studio);
				
				for (String actressName : StringUtils.split(actressNames, ",")) { 
					String forwardActressName = VideoUtils.forwardNameSort(actressName);
					Actress actress = actressMap.get(forwardActressName);
					if (actress == null) {
						actress = actressProvider.get();
						actress.setName(actressName.trim());
						
						actressMap.put(forwardActressName, actress);
						logger.trace("add actress - {}", actress);
					}
					// inject reference
					actress.addVideo(video);
					actress.addStudio(studio);

					studio.addActress(actress);
					video.addActress(actress);
				}
			}
			catch (NullPointerException e) {
				logger.error("", e);
			}
			catch (Exception e) {
				logger.error("Error : {} - {}", file.getAbsolutePath(), e);
			}
		}
		loaded = true;
		logger.info("Total loaded video {}", videoMap.size());
	}

	/**
	 * 기존에 만든적이 없으면, video source를 로드를 호출한다.
	 */
	private final void createVideoSource() {
		logger.trace("createVideoSource");
		if (!loaded)
			load();
	}
	
	@Override
	public void reload() {
		logger.trace("attempt to reload source");
		loaded = false;
		load();
	}
	
	@Override
	public Map<String, Video> getVideoMap() {
		logger.trace("getVideoMap");
		createVideoSource();
		return videoMap;
	}
	@Override
	public Map<String, Studio> getStudioMap() {
		logger.trace("getStudioMap");
		createVideoSource();
		return studioMap;
	}
	@Override
	public Map<String, Actress> getActressMap() {
		logger.trace("getActressMap");
		createVideoSource();
		return actressMap;
	}
	@Override
	public void removeVideo(String opus) {
		logger.trace("remove {}" + opus);
		createVideoSource();
		videoMap.get(opus.toLowerCase()).removeVideo();
		videoMap.remove(opus.toLowerCase());
	}
	@Override
	public Video getVideo(String opus) {
		logger.trace(opus);
		createVideoSource();
		if (videoMap.containsKey(opus.toLowerCase()))
			return videoMap.get(opus.toLowerCase());
		else
			throw new VideoNotFoundException("Video not found : " + opus);
	}
	@Override
	public Studio getStudio(String name) {
		logger.trace(name);
		createVideoSource();
		if (studioMap.containsKey(name.toLowerCase()))
			return studioMap.get(name.toLowerCase());
		else
			throw new StudioNotFoundException("Studio not found : " + name);
	}
	@Override
	public Actress getActress(String name) {
		logger.trace(name);
		createVideoSource();
		if (actressMap.containsKey(VideoUtils.forwardNameSort(name)))
			return actressMap.get(VideoUtils.forwardNameSort(name));
		else
			throw new ActressNotFoundException("Actress not found : " + name);
	}
	@Override
	public List<Video> getVideoList() {
		logger.trace("getVideoList");
		createVideoSource();
		return new ArrayList<Video>(videoMap.values());
	}
	@Override
	public List<Studio> getStudioList() {
		logger.trace("getStudioList");
		createVideoSource();
		return new ArrayList<Studio>(studioMap.values());
	}
	@Override
	public List<Actress> getActressList() {
		logger.trace("getActressList");
		createVideoSource();
		return new ArrayList<Actress>(actressMap.values());
	}
	@Override
	public void moveVideo(String opus, String destPath) {
		logger.trace("moveVideo {} to {}", opus, destPath);
		createVideoSource();
		videoMap.get(opus.toLowerCase()).move(destPath);
	}
	@Override
	public void arrangeVideo(String opus) {
		logger.trace(opus);
		createVideoSource();
		videoMap.get(opus.toLowerCase()).arrange();
	}

}
