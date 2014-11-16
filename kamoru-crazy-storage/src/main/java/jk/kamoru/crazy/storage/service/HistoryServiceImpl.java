package jk.kamoru.crazy.storage.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jk.kamoru.crazy.CrazyException;
import jk.kamoru.crazy.domain.Action;
import jk.kamoru.crazy.domain.Actress;
import jk.kamoru.crazy.domain.History;
import jk.kamoru.crazy.domain.Studio;
import jk.kamoru.crazy.domain.Video;
import jk.kamoru.crazy.service.HistoryService;
import jk.kamoru.crazy.storage.dao.HistoryDao;
import jk.kamoru.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class HistoryServiceImpl implements HistoryService {

	@Autowired HistoryDao historyDao;
	
	@Override
	public void persist(History history) {
		try {
			historyDao.persist(history);
		} catch (IOException e) {
			throw new CrazyException("history persist error", e);
		}
	}

	@Override
	public List<History> findByOpus(String opus) {
		log.info("find opus {}", opus);
		if (StringUtils.isBlank(opus))
			return dummyList();
		return historyDao.findByOpus(opus);
	}

	@Override
	public List<History> findByVideo(Video video) {
		return historyDao.findByVideo(video);
	}

	@Override
	public List<History> findByStudio(Studio studio) {
		return historyDao.findByVideo(studio.getVideoList());
	}

	@Override
	public List<History> findByActress(Actress actress) {
		return historyDao.findByVideo(actress.getVideoList());
	}

	@Override
	public List<History> findByQuery(String query) {
		if (StringUtils.isBlank(query))
			return dummyList();
		return historyDao.find(query);
	}

	@Override
	public List<History> getAll() {
		return historyDao.getList();
	}

	@Override
	public List<History> findByDate(Date date) {
		return historyDao.findByDate(date);
	}

	@Override
	public List<History> findByAction(Action action) {
		return historyDao.findByAction(action);
	}
	
	@Override
	public boolean contains(String opus) {
		return historyDao.findByOpus(opus).size() > 0;
	}
	
	private List<History> dummyList() {
		return new ArrayList<History>();
	}

	@Override
	public List<History> getDeduplicatedList() {
		Map<String, History> found = new HashMap<String, History>();
		for (History history : historyDao.getList()) {
			if (!found.containsKey(history.getOpus()))
				found.put(history.getOpus(), history);
		}
		return new ArrayList<History>(found.values());
	}
	
}
