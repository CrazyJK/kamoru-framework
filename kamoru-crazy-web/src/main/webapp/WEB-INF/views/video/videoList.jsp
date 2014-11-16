<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" 	uri="http://www.springframework.org/tags" %>
<c:set var="ONE_GB" value="${1024*1024*1024}"/>
<!DOCTYPE html>
<html>
<head>
<title><s:message code="video.video"/> <s:message code="video.list"/></title>
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
	<s:message code="video.total"/> <s:message code="video.video"/> : ${fn:length(videoList)}
	<input type="search" name="search" id="search" style="width:100px;" 
		class="searchInput" placeHolder="<s:message code="video.search"/>" onkeyup="searchContent(this.value)"/>
	<c:forEach items="${sorts}" var="s">
	<label class="item sort-item">
		<input type="radio" name="sort" value="${s}" ${s eq sort ? 'checked' : ''} style="display:none;">
		<span><s:message code="video.sort.${s.desc}"/></span></label>
	</c:forEach>
	<span style="float:right;" title="Rank[<s:eval expression="@prop['score.ratio.rank']"/>] 
Play[<s:eval expression="@prop['score.ratio.play']"/>]
Actress[<s:eval expression="@prop['score.ratio.actress']"/>]
Subtitles[<s:eval expression="@prop['score.ratio.subtitles']"/>]
Unseen[<s:eval expression="@prop['score.ratio.unseen']"/>]">Ratio</span>
</div>

<div id="content_div" class="div-box">
	<table class="video-table">
		<c:forEach items="${videoList}" var="video" varStatus="status">
		<tr class="nowrap">
			<td class="number">${status.count}</td>
			<td class="${sort eq 'S' ? 'label' : ''}"><span onclick="fnViewStudioDetail('${video.studio.name}')">${video.studio.name}</span></td>
			<td class="${sort eq 'O' ? 'label' : ''}" width="60px"><span onclick="fnViewVideoDetail('${video.opus}')">${video.opus}</span></td>
			<td class="${sort eq 'T' ? 'label' : ''}">${video.title}</td>
			<td class="${sort eq 'A' ? 'label' : ''}"><c:forEach items="${video.actressList}" var="actress">
				<span onclick="fnViewActressDetail('${actress.name}')">${actress.name}</span>
				</c:forEach>
			</td>
			<td class="${sort eq 'M' ? 'label' : ''}" width="80px">${video.videoDate}</td>
			<td class="${sort eq 'P' ? 'label' : ''}" width="15px">${video.playCount}</td>
			<td class="${sort eq 'R' ? 'label' : ''}" width="10px">${video.rank}</td>
			<td class="number ${sort eq 'L' ? 'label' : ''}" width="45px"><fmt:formatNumber value="${video.length / ONE_GB}" pattern="#,##0.00G"/></td>
			<td class="number ${sort eq 'SC' ? 'label' : ''}"><span title="${video.scoreDesc}">${video.score}</span></td>
		</tr>
		</c:forEach>
	</table>
</div>
  
</body>
</html>
