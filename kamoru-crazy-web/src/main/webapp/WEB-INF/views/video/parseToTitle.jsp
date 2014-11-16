<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<c:set var="arzon" value="http://www.arzon.jp/itemlist.html?t=&m=all&s=&q="/>

<!DOCTYPE html>
<html>
<head>
<title>Parse to Title</title>
<style type="text/css">
.titleArea {
	width:100%;
	font-size:11px;
}
</style>
<script type="text/javascript">
/**
 * 비디오 확인을 기억하기 위해 css class를 변경한다.
 */
function fnMarkChoice(opus) {
	$("#check-" + opus).addClass("mark");
}
</script>
</head>
<body>
<div id="header_div" class="div-box">
	<form method="post">
		<table style="width:100%;">
			<tr>
				<td width="100px;"><input type="submit" value="Parse(${fn:length(titleList)})"/></td>
				<td><textarea name="titleData" class="titleArea">${titleData}</textarea></td>
				<td><textarea class="titleArea"><c:forEach items="${titleList}" var="title" varStatus="status">${title}
</c:forEach></textarea></td>
			</tr>
		</table>
	</form>
</div>

<div id="content_div" class="div-box" style="overflow:auto;">
	<table class="video-table">
		<c:forEach items="${titleList}" var="title" varStatus="status">
		<tr id="check-${title.opus}" style="font-size:11px; color:blue;">
			<td align="right"><span>${status.count}</span></td>
			<td>
				<span class="label">
					<input class="text" style="width:600px;" value="${title}"/>
					<a onclick="fnMarkChoice('${title.opus}')" href="${arzon}${title.opus}" target="_blank" class="link">Get Info</a>
				</span>
				<c:if test="${title.check}"><span>${title.checkDesc}</span></c:if>
			</td>
		</tr>
		</c:forEach>
	</table>
</div>

</body>
</html>