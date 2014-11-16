package jk.kamoru.crazy.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import jk.kamoru.crazy.domain.PictureType;
import jk.kamoru.crazy.service.ImageService;
import jk.kamoru.crazy.CRAZY;
import jk.kamoru.crazy.util.VideoUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/image")
@Slf4j
public class ImageController extends AbstractController {

	private static final String LAST_IMAGE_INDEX_CACHE = "lastImageNo";

	private static final String LAST_RANDOM_IMAGE_INDEX_CACHE = "lastRandomImageNo";;

	@Autowired
	private ImageService imageService;

	@RequestMapping(method = RequestMethod.GET)
	public String viewImageList(Model model, @RequestParam(value = "n", required = false, defaultValue = "-1") int n) {
		int count = imageService.getImageSourceSize();
		model.addAttribute("imageCount", count);
		model.addAttribute("selectedNumber", n > count ? count - 1 : n);
		model.addAttribute("imageNameJSON", imageService.getImageNameJSON());
		return "image/slide";
	}

	@RequestMapping(value = "/slides", method = RequestMethod.GET)
	public String slides(Model model, @RequestParam(value = "n", required = false, defaultValue = "-1") int n) {
		int count = imageService.getImageSourceSize();
		model.addAttribute("imageCount", count);
		model.addAttribute("selectedNumber", n > count ? count - 1 : n);
		return "image/slidesjs";
	}

	@RequestMapping(value = "/canvas", method = RequestMethod.GET)
	public String canvas(Model model, HttpServletResponse response, @RequestParam(value = "n", required = false, defaultValue = "-1") int firstImageIndex,
			@RequestParam(value = "d", required = false, defaultValue = "-1") int deleteImageIndex,
			@CookieValue(value = LAST_IMAGE_INDEX_CACHE, defaultValue = "-1") int lastViewImageIndex) {
		int total = imageService.getImageSourceSize();

		if (deleteImageIndex > -1) {
			imageService.delete(deleteImageIndex);
		}

		if (firstImageIndex > -1) {
			firstImageIndex = firstImageIndex > total ? total - 1 : firstImageIndex;
		} else {
			if (deleteImageIndex > -1) {
				firstImageIndex = deleteImageIndex;
			} else if (lastViewImageIndex > -1) {
				firstImageIndex = lastViewImageIndex;
			}
		}

		if (firstImageIndex > -1) {
			response.addCookie(new Cookie(LAST_IMAGE_INDEX_CACHE, String.valueOf(firstImageIndex)));
		}

		model.addAttribute("imageCount", total);
		model.addAttribute("selectedNumber", firstImageIndex);
		model.addAttribute("imageNameJSON", imageService.getImageNameJSON());
		return "image/canvas";
	}

	@RequestMapping(value = "/{idx}/thumbnail")
	public HttpEntity<byte[]> viewImageThumbnail(@PathVariable int idx) {
		return getImageEntity(imageService.getImage(idx).getImageBytes(PictureType.THUMBNAIL), MediaType.IMAGE_GIF);
	}

	@RequestMapping(value = "/{idx}/WEB")
	public HttpEntity<byte[]> viewImageWEB(@PathVariable int idx) {
		return getImageEntity(imageService.getImage(idx).getImageBytes(PictureType.WEB), MediaType.IMAGE_JPEG);
	}

	@RequestMapping(value = "/{idx}")
	public HttpEntity<byte[]> viewImage(@PathVariable int idx, HttpServletResponse response) {
		response.addCookie(new Cookie(LAST_IMAGE_INDEX_CACHE, String.valueOf(idx)));
		return getImageEntity(imageService.getImage(idx).getImageBytes(PictureType.MASTER), MediaType.IMAGE_JPEG);
	}

	@RequestMapping(value = "/{idx}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int idx) {
		log.info("Delete image {}", idx);
		imageService.delete(idx);
	}

	@RequestMapping(value = "/random")
	public HttpEntity<byte[]> viewImageByRandom(HttpServletResponse response) throws IOException {

		int randomNo = imageService.getRandomImageNo();
		byte[] imageBytes = imageService.getImage(randomNo).getImageBytes(PictureType.MASTER);

		response.addCookie(new Cookie(LAST_RANDOM_IMAGE_INDEX_CACHE, String.valueOf(randomNo)));

		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("max-age=1");
		headers.setContentLength(imageBytes.length);
		headers.setContentType(MediaType.IMAGE_JPEG);

		return new HttpEntity<byte[]>(imageBytes, headers);
	}

	@RequestMapping(value = "/google")
	public String searchGoogle(Model model, @RequestParam(value = "q", required = false, defaultValue = "") String query) {
		model.addAttribute(VideoUtils.getGoogleImage(query));
		return "image/google";
	}

	private HttpEntity<byte[]> getImageEntity(byte[] imageBytes, MediaType type) {
		long today = new Date().getTime();

		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("max-age=" + CRAZY.WEBCACHETIME_SEC);
		headers.setContentLength(imageBytes.length);
		headers.setContentType(type);
		headers.setDate(today + CRAZY.WEBCACHETIME_MILI);
		headers.setExpires(today + CRAZY.WEBCACHETIME_MILI);
		headers.setLastModified(today - CRAZY.WEBCACHETIME_MILI);

		return new HttpEntity<byte[]>(imageBytes, headers);
	}

}
