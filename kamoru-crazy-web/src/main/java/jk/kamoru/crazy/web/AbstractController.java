package jk.kamoru.crazy.web;

import java.util.Locale;

import jk.kamoru.crazy.service.ImageService;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class AbstractController {

	@Autowired private ImageService imageService;

	@JsonIgnore
	@ModelAttribute("auth")
	public Authentication getAuth() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@JsonIgnore
	@ModelAttribute("bgImageCount")
	public Integer bgImageCount() {
		return imageService.getImageSourceSize();
	}
	
	@JsonIgnore
	@ModelAttribute("locale")
	public Locale locale(Locale locale) {
		return locale;
	}
}
