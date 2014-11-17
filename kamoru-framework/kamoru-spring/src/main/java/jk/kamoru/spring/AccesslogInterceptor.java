package jk.kamoru.spring;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jk.kamoru.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

/**
 * handler 실행 전, 후, 완료 시점에 accesslog형식으로 로그에 기록한다.
 * <p> 시점은 {@link #setWhen(WHEN)}으로 결정. default는 {@link WHEN#AFTER}
 * <pre>
 * &lt;mvc:interceptors&gt;
 *   &lt;beans:bean class="{@link jk.kamoru.spring.AccesslogInterceptor}" &gt;
 *     &lt;beans:property name="when" value="#{T({@link jk.kamoru.spring.AccesslogInterceptor}${@link WHEN}).{@link WHEN#AFTER AFTER}}"/&gt;
 *   &lt;/beans:bean&gt;
 * &lt;/mvc:interceptors&gt;
 * </pre>
 * @author kamoru
 *
 */
@Slf4j
public class AccesslogInterceptor implements HandlerInterceptor {

	private long startTime;
	
	/**
	 * {@link #PRE}, {@link #POST}, {@link #AFTER}
	 * @author kamoru
	 */
	enum WHEN {
		PRE, POST, AFTER
	}
	private WHEN when = WHEN.AFTER;
	
	/**
	 * 로그를 찍을 시점.
	 * @param when
	 */
	public void setWhen(WHEN when) {
		this.when = when;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		startTime = System.currentTimeMillis();
		if (when == WHEN.PRE)
			log.info("pre : {}", getAccesslog(request, response, handler, null, null));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (when == WHEN.POST)
			log.info("post : {}", getAccesslog(request, response, handler, modelAndView, null));
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		if (when == WHEN.AFTER)
			log.info("{}", getAccesslog(request, response, handler, null, ex));
	}

	private String getAccesslog(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView, Exception ex) {
		long elapsedtime = System.currentTimeMillis() - startTime;

		String handlerlInfo = "";
		// for Controller
		if (handler instanceof org.springframework.web.method.HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			handlerlInfo = String.format("[%s.%s]", method.getBean().getClass().getSimpleName(), method.getMethod().getName());
		} 
		// for static resources. No additional information
		else if (handler instanceof ResourceHttpRequestHandler) {
			// do nothing
		}
		// another handler 
		else {
			handlerlInfo = String.format("[%s]", handler);
		}

		// for modelAndView
		String modelAndViewInfo = "";
		if (modelAndView != null) {
			String viewName = modelAndView.getViewName();
			String modelNames = Arrays.toString(modelAndView.getModel().keySet().toArray(new String[0]));
			modelAndViewInfo = String.format("view=%s model=%s", viewName, modelNames);
		}
		
		String exceptionInfo = "";
		if (ex != null) {
			exceptionInfo = "Error : " + ex.getMessage();
		}
		
		String accesslog = String.format("[%s] %s %s %s %s %sms %s %s", 
				request.getRemoteAddr(), 
				request.getMethod(), 
				request.getRequestURI(),
				handlerlInfo,
				StringUtils.trimToEmpty(response.getContentType()), 
				elapsedtime,
				exceptionInfo,
				modelAndViewInfo
				);

		return accesslog;
	}
}
