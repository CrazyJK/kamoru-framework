package jk.kamoru.crazy.web;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping("/auth") 
public class AuthController extends AbstractController {
	
	@RequestMapping("/login") 
	public String loginForm(Model model, Locale locale, HttpSession session,
			@RequestParam(value="error", required=false, defaultValue="false") boolean error) {
		log.info("show login form");
		
		if (error) {
			AuthenticationException exception = (AuthenticationException)session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			if (exception != null)
				model.addAttribute("exception", exception);
		}
		model.addAttribute("error", error);
		model.addAttribute(locale);
		
		return "auth/loginForm";
	}

	@RequestMapping("/accessDenied")
	public String accessDenied() {
		log.warn("show access denied page");
		return "auth/accessDenied";
	}
	
	@RequestMapping("/expiredSession")
	public String expiredSession() {
		log.warn("show expired session page");
		return "auth/expiredSession";
	}

	@RequestMapping("/invalidSession")
	public String invalidSession() {
		log.warn("show invalid session page");
		return "auth/invalidSession";
	}
}
