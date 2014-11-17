package jk.kamoru.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * <pre>
 * entry-point-ref
 * GET이외의 요청에 실패했을 때 마지막 요청 METHOD를 loginform에 넘겨 유저에게 알릴수 있도록
 * AuthenticationEntryPoint를 재정의
 * Spring security의 EntryPoint에서 누락된게 많으니, 차후 체크해 봐야함
 * </pre>
 * 
 * @author kamoru
 *
 */
@Slf4j
public class KamoruAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private String defaultFailureUrl;

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

		String method = request.getMethod();
		if (!method.equalsIgnoreCase("GET")) {
			request.setAttribute("LAST_REQUEST_METHOD", method);
			request.setAttribute("authException", authException);
			log.error("{} request Error : {}", method, authException.getMessage());
		}
		response.sendRedirect(request.getContextPath() + defaultFailureUrl);

	}

}
