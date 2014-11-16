package jk.kamoru.crazy.storage;

import jk.kamoru.crazy.CRAZY;
import jk.kamoru.crazy.service.VideoService;
import jk.kamoru.crazy.storage.source.ImageSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StorageScheduling {

	private static final Logger logger = LoggerFactory.getLogger(StorageScheduling.class);

	@Autowired VideoService videoService;
	@Autowired ImageSource imageSource;

	@Value("${task.move.watchedVideo}") 			private boolean MOVE_WATCHED_VIDEO;
	@Value("${task.delete.lowerRankVideo}") 		private boolean DELETE_LOWER_RANK_VIDEO;
	@Value("${task.delete.lowerScoreVideo}") 		private boolean DELETE_LOWER_SCORE_VIDEO;

	@Scheduled(cron="0 */5 * * * *")
	public void videoTask() {
		
		synchronized (CRAZY.class) {
			long startTime = System.currentTimeMillis();
			logger.info("BATCH START");

			logger.info("  BATCH : delete lower rank video [{}]", DELETE_LOWER_RANK_VIDEO);
			if (DELETE_LOWER_RANK_VIDEO)
				videoService.removeLowerRankVideo();
			
			logger.info("  BATCH : delete lower score video [{}]", DELETE_LOWER_SCORE_VIDEO);
			if (DELETE_LOWER_SCORE_VIDEO)
				videoService.removeLowerScoreVideo();
			
			logger.info("  BATCH : delete garbage file");
			videoService.deleteGarbageFile();
			
			logger.info("  BATCH : arrange to same folder");
			videoService.arrangeVideo();
			
			logger.info("  BATCH : move watched video [{}]", MOVE_WATCHED_VIDEO);
			if (MOVE_WATCHED_VIDEO)
				videoService.moveWatchedVideo();

			logger.info("  BATCH : reload");
			videoService.reload();
			
			long elapsedTime = System.currentTimeMillis() - startTime;
			logger.info("BATCH END. Elapsed time : {} ms", elapsedTime);
		}
	}
	
	@Scheduled(cron = "0 */7 * * * *")
	public void imageTask() {
		synchronized (CRAZY.class) {
			imageSource.reload();
		}
	}
	
}
