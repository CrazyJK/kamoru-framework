package jk.kamoru.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * 로그인 성공 후처리기<br>
 * 로그 남기기
 * 
 * @author kamoru
 *
 */
@Slf4j
public class KamoruAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

		log.info("log in {}. role={}", authentication.getName(), authentication.getAuthorities());

		log.debug("call super.onAuthenticationSuccess");
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
