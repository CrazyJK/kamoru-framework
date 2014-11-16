<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><s:message code="video.actress"/> <s:message code="video.list"/></title>
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
	<s:message code="video.total"/> <s:message code="video.actress"/> : ${fn:length(actressList)}
	<input type="search" name="search" id="search" style="width:200px;" 
		class="searchInput" placeHolder="<s:message code="video.search"/>" onkeyup="searchContent(this.value)"/>
	<c:forEach items="${sorts}" var="s">
	<label class="item sort-item">
		<input type="radio" name="sort" value="${s}" ${s eq sort ? 'checked' : ''}>
		<span><s:message code="actress.sort.${s}"/></span></label>
	</c:forEach>
</div>

<div id="content_div" class="div-box">
	<table class="video-table">
		<c:forEach items="${actressList}" var="actress" varStatus="status">
		<tr class="nowrap">
			<td class="number">${status.count}</td>
			<td class="${sort eq 'NAME' ? 'label' : ''}" onclick="fnViewActressDetail('${actress.name}')">${actress.name}</td>
			<td class="${sort eq 'BIRTH' ? 'label' : ''}">${actress.birth}</td>
			<td class="${sort eq 'BODY' ? 'label' : ''}">${actress.bodySize}</td>
			<td class="${sort eq 'HEIGHT' ? 'label' : ''}">${actress.height}</td>
			<td class="${sort eq 'DEBUT' ? 'label' : ''}">${actress.debut}</td>
			<td class="number ${sort eq 'VIDEO' ? 'label' : ''}">${fn:length(actress.videoList)}</td> 
			<td class="number ${sort eq 'SCORE' ? 'label' : ''}">${actress.score}</td>
			<td>
				<c:forEach items="${actress.videoList}" var="video">
				<span class="label" title="${video.title}" onclick="fnViewVideoDetail('${video.opus}')">${video.opus}</span>
				</c:forEach>
			</td>
		</tr>
		</c:forEach>
	</table>
</div>
</body>
</html>
