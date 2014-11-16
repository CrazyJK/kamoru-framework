<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s"      uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri='http://www.springframework.org/tags/form'%>
<%@ taglib prefix="jk"     tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<title><s:message code="video.video"/></title>
<link  href="<c:url value="http://vjs.zencdn.net/c/video-js.css"/>" rel="stylesheet"/>
<link  href="<c:url value="/res/css/video-slides.css"/>" rel="stylesheet"/>
<script src="<c:url value="/res/js/videoMain.js"/>" type="text/javascript"></script>
<script src="<c:url value="http://vjs.zencdn.net/c/video.js"/>"></script>
<script src="<c:url value="http://slidesjs.com/examples/standard/js/jquery.slides.min.js"/>"></script>
<script type="text/javascript">
var opusArray = ${opusArray};
var bgImageCount = ${bgImageCount};
var totalVideoSize = parseInt('${fn:length(videoList)}');
var currentVideoIndex = getRandomInteger(1, totalVideoSize);
var listViewType = '${videoSearch.listViewType}';
var currBGImageUrl;

/** 바탕화면 보기 */
function fnViewBGImage() {
	$("#contentContainer").toggle();
	$("#bgActionGroup").toggle();
}
</script>
</head>
<body>

<div id="header_div">
	<form:form method="POST" commandName="videoSearch">
	<div id="searchDiv" class="div-box">
		<span class="group">
			<!-- Search : Text -->
			<form:label path="searchText"><span title="<s:message code="video.search"/>">S</span></form:label>
			<form:input path="searchText" cssClass="searchInput" placeHolder="Search"/>
			
			<!-- Search : Additional condition. video, subtitles -->
			<span class="checkbox" id="checkbox-addCond" title="<s:message code="video.addCondition"/>">
				<s:message code="video.addCondition-short"/>
			</span><form:hidden path="addCond"/>
			<span class="checkbox" id="checkbox-existVideo" title="<s:message code="video.existVideo"/>">V</span>			    
				<form:hidden path="existVideo"/>
			<span class="checkbox" id="checkbox-existSubtitles" title="<s:message code="video.existSubtitles"/>">S</span>			
				<form:hidden path="existSubtitles"/>

			<span class="separator">|</span>
			
			<%-- 
			<!-- Search : never play video --> 
			<span class="checkbox" id="checkbox-neverPlay" title="<s:message code="video.unseenVideo"/>">P</span>			
				<form:hidden path="neverPlay"/>
			<!-- Search : zero rank video -->
			<span class="checkbox" id="checkbox-zeroRank" title="<s:message code="video.zeroRankVideo"/>">R</span>			
				<form:hidden path="zeroRank"/>
			<!-- Search : rank lt, gt, eq -->			
			<form:select path="rankSign" items="${rankSign}" multiple="false" itemLabel="desc" cssClass="selectBox" cssStyle="width:60px;"/>	
			<form:select path="rank" items="${rankRange}" cssStyle="width:35px; text-align:center;"/>
			<!-- Search : old video -->
			<span class="checkbox" id="checkbox-oldVideo" title="<s:message code="video.oldVideo"/>">O</span>
				<form:hidden path="oldVideo"/>
			 --%>

			<!-- Search : rank -->
			<c:forEach items="${rankRange}" var="rank">
			<label>
				<form:checkbox path="rankRange" value="${rank}" cssClass="item-checkbox"/>
				<span title="<s:message code="video.rank"/>">${rank}</span>
			</label>
			</c:forEach>
			<!-- Search : play count -->
			<form:select path="playCount" items="${playRange}" cssClass="item-selectBox" title="Play Count"/>

			<span class="separator">|</span>

			<!-- Search submit -->			
			<span class="button" onclick="fnSearch()">
				<s:message code="video.search"/> [<c:out value="${fn:length(videoList)}"/>]
			</span>
		</span>
		<span class="group">
			<form:select path="listViewType" items="${views}" itemLabel="desc" cssClass="item-selectBox" title="View type"/> 
		</span>
		<span class="group">
			<span class="checkbox" id="checkbox-sortReverse" title="<s:message code="video.reverseSort"/>">R</span><form:hidden path="sortReverse"/>
			<form:select path="sortMethod" items="${sorts}" itemLabel="desc" cssClass="item-selectBox" title="Sort method"/>
		</span>
		<span class="group">
			<span class="checkbox" id="checkbox-viewStudioDiv"  title="<s:message code="video.viewStudioPanel"/>"  onclick="fnStudioDivToggle()">S</span>
				<form:hidden path="viewStudioDiv"/> 
			<span class="checkbox" id="checkbox-viewActressDiv" title="<s:message code="video.viewActressPanel"/>" onclick="fnActressDivToggle()">A</span>	
				<form:hidden path="viewActressDiv"/>
		</span>
		<span class="group">
			<span class="button" onclick="fnRandomPlay()" title="<s:message code="video.random-play.title"/>"><s:message code="video.random-play"/></span>
		</span>
		<span class="group">
			<span class="button" onclick="fnViewBGImage();" title="<s:message code="video.bgimage.title"/>"><s:message code="video.bgimage"/></span>
		</span>
		<span class="group">
			<span class="button" onclick="fnReloadVideoSource();" title="<s:message code="video.reload.title"/>"><s:message code="video.reload"/></span>
		</span>
		<span id="debug" style="display:none;"></span>
	</div>
	<div id="studioDiv" class="div-box">
		<span onclick="fnUnchecked(this)">[${fn:length(studioList)}]</span>
		<c:forEach items="${studioList}" var="studio">
			<jk:studio studio="${studio}" view="label"/>
		</c:forEach>
	</div>
	<div id="actressDiv" class="div-box">
		<span onclick="fnUnchecked(this)">[${fn:length(actressList)}]</span>
		<c:forEach items="${actressList}" var="actress">
			<jk:actress actress="${actress}" view="label"/>
		</c:forEach>
	</div>
	</form:form>
</div>

<div id="content_div" class="div-box">

	<div id="contentContainer">
	<c:choose>
		<c:when test="${videoSearch.listViewType eq 'C'}">
		<ul>
			<c:forEach items="${videoList}" var="video">
			<%@ include file="/WEB-INF/views/video/videoCard.jspf" %>
			</c:forEach>
		</ul>
		</c:when>
		<c:when test="${videoSearch.listViewType eq 'B'}">
		<ul>
			<c:forEach items="${videoList}" var="video" varStatus="status">
			<li id="opus-${video.opus}" class="li-box">
				<div class="video-box">     <!-- ${status.count} -->             
					<dl class="video-box-bg" style="background-image:url('<c:url value="/video/${video.opus}/cover" />');">
						<dt><jk:video video="${video}" view="title"/></dt>
						<dd><jk:video video="${video}" view="studio"/></dd>
						<dd><jk:video video="${video}" view="opus"/></dd>
						<dd><jk:video video="${video}" view="actress"/></dd>
						<dd><jk:video video="${video}" view="video"/></dd>
						<dd><jk:video video="${video}" view="cover"/></dd>
						<dd><jk:video video="${video}" view="subtitles"/>
							<jk:video video="${video}" view="overview"/>
							<jk:video video="${video}" view="rank"/>
							<jk:video video="${video}" view="score"/></dd>	
					</dl>
				</div>
			</li>
			</c:forEach>
		</ul>
		</c:when>
		<c:when test="${videoSearch.listViewType eq 'T'}">
		<table class="video-table">
			<c:forEach items="${videoList}" var="video" varStatus="status">
			<tr id="opus-${video.opus}">
				<td><jk:video video="${video}" view="studio"/></td>		
				<td><jk:video video="${video}" view="opus"/></td>		
				<td><jk:video video="${video}" view="title"/></td>		
				<td><jk:video video="${video}" view="actress"/></td>	
				<td><jk:video video="${video}" view="video" mode="s"/>
					<jk:video video="${video}" view="cover" mode="s"/>
					<jk:video video="${video}" view="subtitles" mode="s"/>
					<jk:video video="${video}" view="overview" mode="s"/></td>
				<td><jk:video video="${video}" view="rank"/>
					<jk:video video="${video}" view="score"/></td>
			</tr>
			</c:forEach>
		</table>
		</c:when>
		<c:when test="${videoSearch.listViewType eq 'S'}">
		<div id="video-slide-wrapper">
			<div id="slides" class="slides">
			<c:forEach items="${videoList}" var="video" varStatus="status">
				<div id="opus-${video.opus}" tabindex="${status.count}" class="video-slide" style="display:none;">             
					<dl class="video-slide-bg" style="background-image:url('<c:url value="/video/${video.opus}/cover" />');">
						<dt>
							<jk:video video="${video}" view="title" mode="l"/>
							<jk:video video="${video}" view="score" mode="l"/><br/>
							<jk:video video="${video}" view="rank" mode="l"/>
						</dt>
						<dd><jk:video video="${video}" view="studio" mode="l"/></dd>
						<dd><jk:video video="${video}" view="opus" mode="l"/></dd>
						<dd><jk:video video="${video}" view="actress" mode="l"/></dd>
						<dd><jk:video video="${video}" view="release" mode="l"/></dd>
						<dd><jk:video video="${video}" view="download" mode="l"/></dd>
						<dd><jk:video video="${video}" view="video" mode="l"/></dd>
						<dd><jk:video video="${video}" view="cover" mode="l"/></dd>
						<dd><jk:video video="${video}" view="subtitles" mode="l"/></dd>
						<dd><jk:video video="${video}" view="overview" mode="l"/></dd>
					</dl>
				</div>
			</c:forEach>
			</div>
		</div>
		</c:when>
		<c:when test="${videoSearch.listViewType eq 'L'}">
		<div id="video-slide-wrapper">
			<div id="slides" class="slides">
			<c:forEach items="${videoList}" var="video" varStatus="status">
				<div id="opus-${video.opus}" tabindex="${status.count}" class="video-slide" style="display:none;">             
					<dl class="video-slide-bg" style="background-image:url('<c:url value="/video/${video.opus}/cover" />');">
						<dt>
							<jk:video video="${video}" view="title" mode="l"/>
							<jk:video video="${video}" view="score" mode="l"/><br/>
							<jk:video video="${video}" view="rank" mode="l"/>
						</dt>
						<dd><jk:video video="${video}" view="studio" mode="l"/></dd>
						<dd><jk:video video="${video}" view="opus" mode="l"/></dd>
						<dd><jk:video video="${video}" view="actress" mode="l"/></dd>
						<dd><jk:video video="${video}" view="download" mode="l"/></dd>
						<dd><jk:video video="${video}" view="release" mode="l"/></dd>
						<dd><jk:video video="${video}" view="video" mode="l"/>
							<jk:video video="${video}" view="cover" mode="l"/>
							<jk:video video="${video}" view="subtitles" mode="l"/>
							<jk:video video="${video}" view="overview" mode="l"/></dd>
					</dl>
				</div>
			</c:forEach>
			</div>
			<div><span id="slideNumber" class="label-large"></span></div>
			<div id="video_slide_bar" style=""></div>
		</div>
		</c:when>
		<c:when test="${videoSearch.listViewType eq 'V'}">
		<div id="video-slide-wrapper">
			<div id="slides" class="slides">
			<c:forEach items="${videoList}" var="video" varStatus="status">
				<div id="opus-${video.opus}" tabindex="${status.count}" class="video-slide" style="display:none;">    
					<dl class="video-slide-bg">
						<dt style="height:40px;padding-top:3px;">
							<jk:video video="${video}" view="studio"/>
							<jk:video video="${video}" view="opus"/>
							<jk:video video="${video}" view="title"/>
							<jk:video video="${video}" view="score"/>
							<jk:video video="${video}" view="actress"/>
							<br/>
							<jk:video video="${video}" view="video"/>
							<jk:video video="${video}" view="cover"/>
							<jk:video video="${video}" view="subtitles"/>
							<jk:video video="${video}" view="overview"/>
							<jk:video video="${video}" view="rank"/>
						</dt>
						<dd>
							<video poster="<c:url value="/video/${video.opus}/cover" />" 
								preload="none" width="800" height="480"
								controls="controls" class="video-js vjs-default-skin" data-setup="{}"
								src="<c:url value="${video.videoURL}" />"
								><%-- <source src="<c:url value="${video.videoURL}" />" type="video/mp4" ></source> --%></video>
						</dd>
						<dd><a href="<c:url value="${video.videoURL}" />"><span class="label">${video.videoURL}</span></a></dd>
					</dl>				         
				</div>
			</c:forEach>
			</div>
		</div>
		</c:when>
		<c:otherwise>
		<ol>
			<c:forEach items="${videoList}" var="video">
			<li>${video.studio.name} ${video.opus} ${video.title} 
			</c:forEach>
		</ol>		
		</c:otherwise>
	</c:choose>
	</div>

	<div id="bgActionGroup" style="display:none;">
		<span onclick="setBackgroundImage();">NEXT</span>
		<span onclick="fnBGImageView();">VIEW</span>
		<span onclick="fnBGImageDELETE();">DELETE</span>
	</div>

</div>

</body>
</html>