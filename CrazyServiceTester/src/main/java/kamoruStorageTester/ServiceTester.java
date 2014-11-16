package kamoruStorageTester;

import jk.kamoru.crazy.domain.Video;
import jk.kamoru.crazy.service.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//@EnableScheduling
@Component
public class ServiceTester {

	private VideoService videoService;
	
	@Autowired
	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}
	
//	@Scheduled(fixedRate = 5000)
	public void getVideo() {
		Video video = videoService.getVideo("BBI-172");
		System.out.println(video.getOpus() + " " + video.getTitle());
	}
}
