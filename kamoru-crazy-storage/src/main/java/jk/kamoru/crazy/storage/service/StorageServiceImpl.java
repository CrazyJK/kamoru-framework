package jk.kamoru.crazy.storage.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

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
import jk.kamoru.crazy.service.StorageService;
import jk.kamoru.crazy.storage.source.ImageSource;
import jk.kamoru.crazy.storage.source.VideoSource;

public class StorageServiceImpl implements StorageService {

	@Autowired VideoSource videoSource;
	@Autowired ImageSource imageSource;
	
	@Override
	public Video getVideo(String opus) throws VideoNotFoundException {
		return videoSource.getVideo(opus);
	}

	@Override
	public Studio getStudio(String name) throws StudioNotFoundException {
		return videoSource.getStudio(name);
	}

	@Override
	public Actress getActress(String name) throws ActressNotFoundException {
		return videoSource.getActress(name);
	}

	@Override
	public List<Video> findVideo(Search search) {
		List<Video> found = videoSource.getVideoList();
		// 검색어
		if (StringUtils.isNotBlank(search.getSearchText()))
			for (Video video : found)
				if (!video.containsQuery(search.getSearchText()))
					found.remove(video);
		// 추가 검색 조건 : 파일, 자막 존재여부
		if (search.isAddCond())
			for (Video video : found)
				if (!video.checkExistCondition(search.isExistVideo(), search.isExistSubtitles()))
					found.remove(video);
		// rank
		if (search.getRankRange().size() > 0)
			for (Video video : found)
				if (!search.getRankRange().contains(video.getRank()))
					found.remove(video);
		// play count
		if (search.getPlayCount() > -1)
			for (Video video : found)
				if (video.getPlayCount() != search.getPlayCount())
					found.remove(video);
		// studio
		if (search.getSelectedStudio().size() > 0)
			for (Video video : found)
				if (!search.getSelectedStudio().contains(video.getStudio().getName()))
					found.remove(video);
		// actress
		if (search.getSelectedActress().size() > 0)
			for (Video video : found)
				if (!video.containsAnyActressList(search.getSelectedActress()))
					found.remove(video);
		return found;
	}

	@Override
	public List<Studio> findStudio(Search search) {
		List<Studio> found = videoSource.getStudioList();
		if (StringUtils.isNotBlank(search.getSearchText()))
			for (Studio studio : found)
				if (!StringUtils.equalsIgnoreCase(studio.getName(), search.getSearchText()))
					found.remove(studio);
		if (search.getSelectedStudio().size() > 0)
			for (Studio studio : found)
				if (!search.getSelectedStudio().contains(studio.getName()))
					found.remove(studio);
		return found;
	}

	@Override
	public List<Actress> findActress(Search search) {
		List<Actress> found = videoSource.getActressList();
		if (StringUtils.isNotBlank(search.getSearchText()))
			for (Actress actress : found)
				if (!StringUtils.equalsIgnoreCase(actress.getName(), search.getSearchText()))
					found.remove(actress);
		if (search.getSelectedActress().size() > 0)
			for (Actress actress : found)
				if (!search.getSelectedActress().contains(actress.getName()))
					found.remove(actress);
		return found;
	}

	@Override
	public void mergeVideo(Video video) {
		Video originalVideo = videoSource.getVideo(video.getOpus());
		originalVideo.merge(video);
	}

	@Override
	public void mergeStudio(Studio studio) {
		Studio originalStudio = videoSource.getStudio(studio.getName());
		originalStudio.merge(studio);
	}

	@Override
	public void mergeActress(Actress actress) {
		Actress originalActress = videoSource.getActress(actress.getName());
		originalActress.merge(actress);
	}
	
	@Override
	public Image getImage(Integer idx) throws ImageNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getImageByRandom() throws ImageNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Image> getImageList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getImageSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeImage(Integer idx) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<History> findHistory(Search search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<History> getHistoryList() {
		// TODO Auto-generated method stub
		return null;
	}

}
