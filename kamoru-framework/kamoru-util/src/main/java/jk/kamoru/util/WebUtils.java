package jk.kamoru.util;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {
	
	private static final String UTF8 = "UTF-8";
	private static final String Latin1 = "8859_1";
	private static final String POST = "POST";
	private static final String GET  = "GET";
	
	/**
	 * UTF-8 인코딩을 거친 파라미터 value 반환
	 * @param request
	 * @param name
	 * @return parameter value
	 */
	public static String getParameter(HttpServletRequest request, String name) {
		return getParameter(request, name, null);
	}
	
	/**
	 * UTF-8 인코딩을 거친 파라미터 value 반환
	 * @param request
	 * @param name
	 * @param defaultValue
	 * @return parameter value
	 */
	public static String getParameter(HttpServletRequest request, String name, String defaultValue) {
		String value = null;
		String method = request.getMethod();
		try {
			if(POST.equalsIgnoreCase(method)) {
				request.setCharacterEncoding(UTF8);
			}
			value = request.getParameter(name);
			if(value == null)
				value = defaultValue;
			else if(GET.equalsIgnoreCase(method))
				value = new String(value.getBytes(Latin1), UTF8);
			return value;
		} catch (UnsupportedEncodingException e) {
			// Do nothing
			return null;
		}
	}

	/**
	 * 파라미터 value을 숫자로 리턴
	 * @param request
	 * @param name
	 * @param defaultValue
	 * @return parameter value
	 */
	public static int getParameterInt(HttpServletRequest request, String name, int defaultValue) {
		try {
			return Integer.parseInt(getParameter(request, name, String.valueOf(defaultValue)));
		}
		catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	/**
	 * 파라미터 value를 숫자로 리턴. 숫자가 아닐경우 0 리턴
	 * @param request
	 * @param name
	 * @return parameter value
	 */
	public static int getParameterInt(HttpServletRequest request, String name) {
		return getParameterInt(request, name, 0);
	}

	/**
	 * 파라미터 value를 구분자로 나누어 배열로 리턴
	 * @param request
	 * @param name
	 * @param separator
	 * @return parameter value
	 */
	public static String[] getParameterArray(HttpServletRequest request, String name, String separator) {
		String value = getParameter(request, name, "");
		if (value.trim().length() > 0) {
			return StringUtils.splitByWholeSeparator(value, separator);
		}
		else {
			return null;
		}
	}

	/**
	 * 전체 파라미터를 GET방식 포멧으로 리턴
	 * @param request
	 * @return GET방식 표현 문자열
	 */
	@SuppressWarnings("rawtypes")
	String getParamters(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		Enumeration enu = request.getParameterNames();
		boolean first = true;
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = request.getParameter(name);
			if (first)
				sb.append("?");
			else
				sb.append("&");
			sb.append(name).append("=").append(value);
		}
		return sb.toString();
	}

}
