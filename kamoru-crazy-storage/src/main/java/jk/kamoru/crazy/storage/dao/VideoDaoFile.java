package jk.kamoru.crazy.storage.dao;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import jk.kamoru.crazy.CRAZY;
import jk.kamoru.crazy.VideoException;
import jk.kamoru.crazy.domain.Actress;
import jk.kamoru.crazy.domain.Studio;
import jk.kamoru.crazy.domain.Video;
import jk.kamoru.crazy.storage.source.VideoSource;

@SuppressWarnings("unused")
@Repository
public class VideoDaoFile implements VideoDao {
	
	protected static final Logger logger = LoggerFactory.getLogger(VideoDaoFile.class);

	@Autowired
	private VideoSource videoSource;

	@Override
//	@Cacheable(value="videoCache")
	public List<Video> getVideoList() {
		logger.trace("getVideoList");
		return videoSource.getVideoList();
	}
	
	@Override
//	@Cacheable("studioCache")
	public List<Studio> getStudioList() {
		logger.trace("getStudioList");
		return videoSource.getStudioList();
	}

	@Override
//	@Cacheable("actressCache")
	public List<Actress> getActressList() {
		logger.trace("getActressList");
		return videoSource.getActressList();
	}

	@Override
//	@Cacheable(value="videoCache")
	public Video getVideo(String opus) {
		logger.trace(opus);
		return videoSource.getVideo(opus);
	}

	@Override
//	@Cacheable("studioCache")
	public Studio getStudio(String name) {
		logger.trace(name);
		return videoSource.getStudio(name);
	}

	@Override
//	@Cacheable("actressCache")
	public Actress getActress(String name) {
		logger.trace(name);
		return videoSource.getActress(name);
	}

	@Override
//	@CacheEvict(value = { "videoCache" }, allEntries=true)
	public void deleteVideo(String opus) {
		logger.trace(opus);
		videoSource.removeVideo(opus);
	}
	
	@Override
//	@CacheEvict(value = { "videoCache" }, allEntries=true)
	public void moveVideo(String opus, String destPath) {
		logger.trace(opus);
		videoSource.moveVideo(opus, destPath);
	}
	@Override
//	@CacheEvict(value = { "videoCache", "studioCache", "actressCache" }, allEntries=true)
	public void reload() {
		logger.trace("reload");
		videoSource.reload();
	}

	@Override
	public void arrangeVideo(String opus) {
		logger.trace(opus);
		videoSource.arrangeVideo(opus);
	}

	@Override
	public boolean contains(String opus) {
		try {
			videoSource.getVideo(opus);
			return true;
		} catch (VideoException e) {
			return false;
		}
	}


}

