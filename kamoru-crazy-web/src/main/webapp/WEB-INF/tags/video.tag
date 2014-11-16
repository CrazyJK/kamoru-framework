<%@ tag language="java" pageEncoding="UTF-8" body-content="tagdependent"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s"   uri="http://www.springframework.org/tags"%>

<%@ attribute name="video"   required="true" type="jk.kamoru.crazy.domain.Video"%>
<%@ attribute name="view"    required="true"%>
<%@ attribute name="mode"    required="false"%><%-- mode : s(Small), l(Large) --%>
<%@ attribute name="tooltip" required="false"%><%-- tooltip test --%>

<c:set var="cssClass" value="${mode eq 'l' ? 'label-large' : 'label'}"/>
<c:set var="ONE_GB" value="${1024*1024*1024}"/>

<%
	if (view.equalsIgnoreCase("video")) {
%>
<span class="${cssClass} ${video.existVideoFileList ? 'exist' : 'nonExist'}" title="${video.playCount} played, <fmt:formatNumber value="${video.length / ONE_GB}" pattern="#,##0 GB"/>" onclick="fnPlay('${video.opus}')">${mode eq 's' ? 'V' : 'Video'}</span>
<%
	} else if (view.equalsIgnoreCase("cover")) {
%>
<span class="${cssClass} ${video.existCoverFile ? 'exist' : 'nonExist'}" onclick="fnImageView('${video.opus}')">${mode eq 's' ? 'C' : 'Cover'}</span>
<%
	} else if (view.equalsIgnoreCase("subtitles")) {
%>
<span class="${cssClass} ${video.existSubtitlesFileList ? 'exist' : 'nonExist'}" onclick="fnEditSubtitles('${video.opus}')">${mode eq 's' ? 's' : 'smi'}</span>
<%
	} else if (view.equalsIgnoreCase("overview")) {
%>
<span class="${cssClass} ${video.existOverview ? 'exist' : 'nonExist'}" onclick="fnEditOverview('${video.opus}')" title="${video.overviewText}">${mode eq 's' ? 'O' : 'Overview'}</span>
<%
	} else if (view.equalsIgnoreCase("download")) {
%>
<span class="${cssClass}" title="download date">${video.videoDate}</span>
<%
	} else if (view.equalsIgnoreCase("release")) {
%>
<span class="${cssClass}" title="release date">${video.releaseDate}</span>
<%
	} else if (view.equalsIgnoreCase("actress")) {
%>
<c:forEach items="${video.actressList}" var="actress" varStatus="status">
<span class="${cssClass}" onclick="fnSearch('${actress.name}')" title="${actress}">${actress.name}</span>
<img src="<c:url value="/res/img/magnify${status.count%2}.png"/>" onclick="fnViewActressDetail('${actress.name}')" width="12px">
</c:forEach>
<%
	} else if (view.equalsIgnoreCase("opus")) {
%>
<span class="${cssClass}">${video.opus}</span>
<c:if test="${mode eq 'l' && !video.existVideoFileList}">
<%-- <span class="${cssClass}"><a href="<s:eval expression="@prop['video.torrent.url']"/>${video.opus}" target="_blank" class="link">Get !t</a></span> --%>
</c:if>
<%
	} else if (view.equalsIgnoreCase("studio")) {
%>
<span class="${cssClass}" onclick="fnSearch('${video.studio.name}')" title="${video.studio}">${video.studio.name}</span>
<img src="<c:url value="/res/img/link.png"/>" onclick="fnViewStudioDetail('${video.studio.name}')">
<%
	} else if (view.equalsIgnoreCase("title")) {
%>
<span class="${cssClass}" onclick="fnVideoDetail('${video.opus}')">${video.title}</span>
<%
	} else if (view.equalsIgnoreCase("score")) {
%>
<span class="${cssClass} rangeLabel" title="${video.scoreDesc}">${video.score}</span>
<%
	} else if (view.equalsIgnoreCase("rank")) {
%>
<input type="range" id="Rank-${video.opus}" name="points" ${mode eq 's' ? 'style="width:70px;"' : ''} min="<s:eval expression="@prop['rank.minimum']"/>" max="<s:eval expression="@prop['rank.maximum']"/>" value="${video.rank}" onmouseup="fnRank('${video.opus}')" onchange="document.getElementById('Rank-${video.opus}-label').innerHTML = this.value;" />
<em id="Rank-${video.opus}-label" class="rangeLabel">${video.rank}</em>
<%
	} else if (view.equalsIgnoreCase("label")) {
%>
<span class="${cssClass}" onclick="fnVideoDetail('${video.opus}')" title="${mode eq 's' ? video.fullname : video.title} ${tooltip}">${mode eq 's' ? 'O' : video.opus}</span>
<%
	} else {
%>
${view} is invalid argement
<%
	}
%>