<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="jk"     tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>[${video.opus}] ${video.title}</title>
<script type="text/javascript">
$(document).ready(function() {
	$("body").css("background-image","url('<c:url value="/video/${video.opus}/cover" />')");
	$("body").css("background-size", "contain");
	$("#renameForm").hide();
});
function fnToggleRenameForm() {
	$("#renameForm").toggle();
}
</script>
</head>
<body>
<c:set var="opus" value="${video.opus}"/>

<dl class="dl-detail">
	<dt><jk:video video="${video}" view="title" mode="l"/>
		<jk:video video="${video}" view="score" mode="l"/>
		<br/>
		<jk:video video="${video}" view="rank" mode="l"/>
	</dt>

	<dd><jk:video video="${video}" view="studio" mode="l"/></dd>
	<dd><jk:video video="${video}" view="opus" mode="l"/></dd>
	<dd><jk:video video="${video}" view="release" mode="l"/></dd>
	<dd><jk:video video="${video}" view="download" mode="l"/></dd>

	<c:if test="${video.etcInfo ne ''}">
	<dd><span class="label-large">ETC info : ${video.etcInfo}</span></dd>
	</c:if>
	
	<dd><span class="label" onclick="fnToggleRenameForm()">Rename</span>
		<form id="renameForm" method="post" action="<s:url value="/video/${video.opus}/rename"/>" target="ifrm">
			<input type="text" name="newname" value="${video.fullname}" style="width:600px; background-color:rgba(255,255,255,.5);"/>
			<input type="submit" value="rename"/>
		</form>
	</dd>
	
	<dd><span class="label" onclick="opener.fnPlay('${video.opus}')">VIDEO : ${video.videoFileListPath}</span></dd>
	<dd><span class="label">COVER : ${video.coverFilePath}</span></dd>
	<c:if test="${video.coverWebpFilePath ne ''}">	
	<dd><span class="label">WEBP : ${video.coverWebpFilePath}</span></dd>
	</c:if>
	<c:if test="${video.subtitlesFileListPath ne ''}">
	<dd><span class="label" onclick="opener.fnEditSubtitles('${video.opus}')">SMI : ${video.subtitlesFileListPath}</span></dd>
	</c:if>
	<dd><span class="label">INFO : ${video.infoFilePath}</span></dd>
	<c:if test="${video.etcFileListPath ne ''}">
	<dd><div  class="label-large">ETC : ${video.etcFileListPath}</div></dd>
	</c:if>
	<c:if test="${video.overviewText ne ''}">
	<dd><pre  class="label-large" onclick="opener.fnEditOverview('${video.opus}')" >${video.overviewText}</pre></dd>
	</c:if>
	
	<dd>
		<c:forEach items="${video.actressList}" var="actress">
		<span class="label-large actressSpan" onclick="fnViewActressDetail('${actress.name}')">
			${actress.name} (${fn:length(actress.videoList)}), Score ${actress.score}</span>
		<div style="padding-left:60px;">
			<ul>
			<c:forEach items="${actress.videoList}" var="video">
				<c:choose>
					<c:when test="${video.opus != opus }">
						<%@ include file="/WEB-INF/views/video/videoCard.jspf" %>
					</c:when>
				</c:choose>
			</c:forEach>
			</ul>
		</div>
		</c:forEach>
	</dd>
</dl>
</body>
</html>
