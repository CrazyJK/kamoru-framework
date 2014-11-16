<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@ page import="java.util.*, java.io.*, org.apache.commons.io.FileUtils" 
%><%

File imageFile = (File)request.getAttribute("imageFile");

byte[] b = FileUtils.readFileToByteArray(imageFile);

out = pageContext.pushBody();

response.setContentType(getServletContext().getMimeType(imageFile.getName()));

if(!(Boolean)request.getAttribute("isBGImage")) {
	response.setDateHeader("Expires", new Date().getTime() + 86400*1000l);
	response.setHeader("Cache-Control", "max-age=" + 86400);
}
response.getOutputStream().write(b);
response.getOutputStream().flush();
response.getOutputStream().close();%>