<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><s:message code="video.studio"/> <s:message code="video.list"/></title>
<script type="text/javascript">
$(document).ready(function(){
	
	$("input[type=radio]").bind("click", function(){
		var selectedSort = $(this).val();
		var reverseOrder = '${sort}' == selectedSort ? !${reverse} : false;
		location.href = "?sort=" + selectedSort + "&r=" + reverseOrder;
	}).css("display","none");

});
</script>
</head>
<body>
<div id="header_div" class="div-box">
	<s:message code="video.total"/> <s:message code="video.studio"/> : ${fn:length(studioList)}
	<input type="search" name="search" id="search" style="width:200px;" 
		class="searchInput" placeHolder="<s:message code="video.search"/>" onkeyup="searchContent(this.value)"/>
	<c:forEach items="${sorts}" var="s">
	<label class="item sort-item">
		<input type="radio" name="sort" value="${s}" ${s eq sort ? 'checked' : ''}>
		<span><s:message code="studio.sort.${s}"/></span></label>
	</c:forEach>
</div>

<div id="content_div" class="div-box">
	<table class="video-table">
		<c:forEach items="${studioList}" var="studio" varStatus="status">
		<tr class="nowrap">
			<td class="number">${status.count}</td>
			<td class="${sort eq 'NAME' ? 'label' : ''}" onclick="fnViewStudioDetail('${studio.name}')">${studio.name}</td>
			<td class="${sort eq 'HOMEPAGE' ? 'label' : ''}"><a href="<s:url value="${studio.homepage}" />" target="_blank">${studio.homepage}</a></td>
			<td class="${sort eq 'COMPANY' ? 'label' : ''}">${studio.company}</td>
			<td class="number ${sort eq 'VIDEO' ? 'label' : ''}">${fn:length(studio.videoList)}</td>
			<td class="number ${sort eq 'SCORE' ? 'label' : ''}">${studio.score}</td>
			<td>
			<c:forEach items="${studio.videoList}" var="video">
				<span class="label" title="${video.title}" onclick="fnViewVideoDetail('${video.opus}')">${video.opus}</span>
			</c:forEach>
			</td>
		</tr>
		</c:forEach>
	</table>
</div>
</body>
</html>
