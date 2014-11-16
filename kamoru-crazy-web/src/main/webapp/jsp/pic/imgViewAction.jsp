<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.File" %>
<%
	String path = request.getParameter("p");
	if (null == path) {
		out.println("[]");
		return;
	}

	String realpath = request.getSession().getServletContext().getRealPath(path);
	File dir = new File(realpath);
	if (!dir.exists()) {
		out.println("[]");
		return;
	}
	File[] f = dir.listFiles();
	
	StringBuilder outStr = new StringBuilder("[");
	for (int i=0; i<f.length; i++) {
		outStr.append("\"" + f[i].getName() + "\"");
		if (i < f.length -1) {
			outStr.append(", ");
		}
	}
	outStr.append("]");
	out.println("outStr=" + outStr.toString());
%>