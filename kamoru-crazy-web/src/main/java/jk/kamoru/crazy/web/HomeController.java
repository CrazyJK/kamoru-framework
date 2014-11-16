package jk.kamoru.crazy.web;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles requests for the application home page.
 */
@Controller
@Slf4j
public class HomeController extends AbstractController {

	@ModelAttribute("serverTime")
	public String serverTime(Locale locale) {
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale);
		return dateFormat.format(new Date());
	}
	
	@RequestMapping("/")
	public String root() {
		return "home";
	}
	
	@RequestMapping("/home")
	public String home(Locale locale, Model model) {
		log.info("Welcome home! The client locale is {}.", locale);

		return "home";
	}
	
	@RequestMapping("/error")
	public void error() {
		throw new RuntimeException("error");
	}

}
