package jk.kamoru.crazy.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jk.kamoru.crazy.CrazyException;
import jk.kamoru.crazy.ImageException;
import jk.kamoru.crazy.VideoException;
import jk.kamoru.crazy.service.ImageService;
import jk.kamoru.crazy.service.VideoService;
import jk.kamoru.spring.ReloadableResourceBundleMessageSource;
import jk.kamoru.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller
@Slf4j
@RequestMapping("/manager")
public class ManagerController extends AbstractController {

	@RequestMapping("/hitMessageCodeList")
	public String hitMessageCodeList(Model model) {
		log.trace("hit Message Code List");
		model.addAttribute("hitMessageCodeMap", ReloadableResourceBundleMessageSource.hitMessageCodeMap);
		return "manager/hitMessageCodeList";
	}

	@RequestMapping(value = "/requestMappingList")
	public String requestMapping(HttpServletRequest request, Model model, @RequestParam(value="sort", required=false, defaultValue="P") final String sort) {
		log.trace("Request mapping list");

		ConfigurableApplicationContext cac = (ConfigurableApplicationContext) request
				.getSession()
				.getServletContext()
				.getAttribute(FrameworkServlet.SERVLET_CONTEXT_PREFIX + "appServlet");
		RequestMappingHandlerMapping rmhm = cac.getBean(RequestMappingHandlerMapping.class);

		List<Map<String, String>> mappingList = new ArrayList<Map<String, String>>();
		
		for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : rmhm.getHandlerMethods().entrySet()) {
			Map<String, String> mappingData = new HashMap<String, String>();
			mappingData.put("reqPattern", StringUtils.substringBetween(entry.getKey().getPatternsCondition().toString(), "[", "]"));
			mappingData.put("reqMethod",  StringUtils.substringBetween(entry.getKey().getMethodsCondition().toString(), "[", "]"));
			mappingData.put("beanType",   StringUtils.substringAfterLast(entry.getValue().getBeanType().getName(), "."));
			mappingData.put("beanMethod", entry.getValue().getMethod().getName());

			mappingList.add(mappingData);
		}
		Collections.sort(mappingList, new Comparator<Map<String, String>>() {
			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				if (sort.equals("P")) {
					return StringUtils.compareTo(o1.get("reqPattern"), o2.get("reqPattern"));
				}
				else if (sort.equals("M")) {
					return StringUtils.compareTo(o1.get("reqMethod"), o2.get("reqMethod"));
				}
				else if (sort.equals("C")) {
					int firstCompare = StringUtils.compareTo(o1.get("beanType"), o2.get("beanType"));
					int secondCompare = StringUtils.compareTo(o1.get("beanMethod"), o2.get("beanMethod"));
					return firstCompare == 0 ? secondCompare : firstCompare;
				}
				else {
					return StringUtils.compareTo(o1.get("reqPattern"), o2.get("reqPattern"));
				}
			}
		});
		model.addAttribute("mappingList", mappingList);
		return "manager/requestMappingList";
	}
	
	@Autowired VideoService videoService;
	@Autowired ImageService imageService;
	
	@RequestMapping("/error")
	public String error(Model model, @RequestParam(value="k", required=false, defaultValue="") String kind) {
		if (StringUtils.equals(kind, "default"))
			throw new RuntimeException("default error");
		else if (StringUtils.equals(kind, "kamoru"))
			throw new CrazyException("kamoru error", new Exception("kamoru error"));
		else if (StringUtils.equals(kind, "video"))
			throw new VideoException(videoService.getVideoList().get(0), "video error");
		else if (StringUtils.equals(kind, "image"))
			throw new ImageException(imageService.getImageList().get(0), "image error");
		
		return "manager/occurError";
	}

}
