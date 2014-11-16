<%@ tag language="java" pageEncoding="UTF-8" body-content="tagdependent"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"   uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s"    uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri='http://www.springframework.org/tags/form'%>

<%@ attribute name="studio" required="true" type="jk.kamoru.crazy.domain.Studio"%>
<%@ attribute name="view"   required="true"%>

<%
	String itemCssClass = "item";
	int size = studio.getVideoList().size();
	if (size >= 100)
		itemCssClass += "100";
	else if (size >= 50)
		itemCssClass += "50";
	else if (size >= 30)
		itemCssClass += "30";
	else if (size >= 10)
		itemCssClass += "10";
	else if (size >= 5)
		itemCssClass += "5";
	else
		itemCssClass += "1";

	if (view.equalsIgnoreCase("label")) {
%>
<label 
	class="item <%=itemCssClass %>" 
	title="${studio.homepage} ${studio.company} Actress:${fn:length(studio.actressList)}">
	<form:checkbox path="selectedStudio" value="${studio.name}" cssClass="item-checkbox"/>
	<span>${studio.name}(${fn:length(studio.videoList)})</span>
</label>
<%
	} else if (view.equalsIgnoreCase("span")) {
%>
<span	
	onclick="fnViewStudioDetail('${studio.name}')" 
	class="<%=itemCssClass %>" 
	title="${studio.homepage} ${studio.company} Actress:${fn:length(studio.actressList)}">
	${studio.name}(${fn:length(studio.videoList)})
</span>
<%
	} else {
%>
${view} is invalid argement
<%
	}
%>

