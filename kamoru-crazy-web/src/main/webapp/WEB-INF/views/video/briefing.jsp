<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s"   uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="jk"     tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="${locale}">
<head>
<title><s:message code="video.briefing"/></title>
<style type="text/css">
div#content_div.div-box section {
	margin: 2px;
	border-radius:5px; 
	border: 1px solid orange;
}
div#content_div.div-box h3 {
	margin: 2px;
	padding: 5px;
	cursor: pointer;
	text-shadow: 1px 1px 1px white;
	/* background-color: linen;
	border-radius: 5px;
	opacity: 0.75; */
}
div#content_div.div-box h3:hover {
	border-radius:5px; 
	background-color:rgba(255,165,0,.5);
}
div#content_div.div-box article {
	margin: 10px;
	display: none;
}
.h3-toggle-on {
	border: 1px solid blue;
	border-radius:5px; 
	background-color:rgba(255,165,0,.25);
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	
	$("h3").bind("click", function() {
		$(this).next().slideToggle("slow", function() {
			$(this).prev().toggleClass("h3-toggle-on");
		});
	});
	
	$("input[type=radio]").bind("click", function() {
		location.href= "?view=" + $(this).val();
	}).css("display","none");

});

</script>
</head>
<body>

<div id="header_div" class="div-box">
	<s:message code="video.briefing"/>
	<label class="item sort-item"><input type="radio" name="viewType" value="normal" ${param.view eq 'normal' || empty param.view ? 'checked' : ''}/><span>Normal</span></label>
	<label class="item sort-item"><input type="radio" name="viewType" value="simple" ${param.view eq 'simple' ? 'checked' : ''}/><span>Simple</span></label>

	<div style="float:right">
		<span class="label-large"><a onclick="actionFrame('<c:url value="/video/manager/moveWatchedVideo"/>')"><s:message code="video.mng.move"/></a></span>
		<span class="label-large"><a onclick="actionFrame('<c:url value="/video/manager/removeLowerRankVideo"/>')"><s:message code="video.mng.rank"/></a></span>
		<span class="label-large"><a onclick="actionFrame('<c:url value="/video/manager/removeLowerScoreVideo"/>')"><s:message code="video.mng.score"/></a></span>
	</div>
</div>

<div id="content_div" class="div-box" style="overflow:auto; text-align:left;">

<section>
	<h3><s:message code="video.video-by-folder"/></h3>
	<article>
		<table class="video-table" style="background-color:lightgray">
			<tr>
				<th style="text-align:left;"><s:message code="video.folder"/></th>
				<th style="text-align:right;"><s:message code="video.size"/></th>
				<th style="text-align:right;"><s:message code="video.length"/></th>
			</tr>
			<c:set var="ONE_GB" value="${1024*1024*1024}"/>
			<c:forEach items="${pathMap}" var="path" varStatus="status">
				<c:choose>
					<c:when test="${path.key ne 'Total'}">
			<tr>
				<td style="text-align:left;">${path.key}</td>
				<td style="text-align:right;"><fmt:formatNumber value="${path.value[0]}" groupingUsed="true" type="NUMBER"/></td>
				<td style="text-align:right;"><fmt:formatNumber value="${path.value[1] / ONE_GB}" pattern="#,##0 GB"/></td>
			</tr>
					</c:when>
					<c:otherwise>
						<c:set var="totalSize"   value="${path.value[0]}"/>
						<c:set var="totalLength" value="${path.value[1]}"/>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<tr>
				<td style="text-align:left; border-top:1px solid blue;"><s:message code="video.total"/></td>
				<td style="text-align:right; border-top:1px solid blue;"><fmt:formatNumber value="${totalSize}" groupingUsed="true" type="NUMBER"/></td>
				<td style="text-align:right; border-top:1px solid blue;"><fmt:formatNumber value="${totalLength / ONE_GB}" pattern="#,##0 GB"/></td>
			</tr>
		</table>
	</article>
</section>

<section>
	<h3><s:message code="video.video-by-date"/></h3>
	<article>
		<table class="video-table" style="background-color:lightgray">
			<tr>
				<th class="nowrap"><s:message code="video.date"/></th>
				<th class="nowrap"><s:message code="video.size"/></th>
				<th class="nowrap"><s:message code="video.video"/></th>
			</tr>
			<c:forEach items="${dateMap}" var="date" varStatus="status">
			<tr>
				<td class="nowrap">${date.key}</td>
				<td class="nowrap" style="text-align:right;">${fn:length(date.value)}</td>
				<td class="nowrap">
					<c:forEach items="${date.value}" var="video" varStatus="status">
						<jk:video video="${video}" view="label" mode="${param.view}"/>
					</c:forEach>
				</td>
			</tr>
			</c:forEach>		
		</table>
	</article>
</section>

<section>
	<h3><s:message code="video.video-by-rank"/></h3>
	<article>
		<table class="video-table" style="background-color:lightgray">
			<tr>
				<th class="nowrap"><s:message code="video.rank"/></th>
				<th class="nowrap"><s:message code="video.size"/></th>
				<th class="nowrap"><s:message code="video.video"/></th>
			</tr>
			<c:forEach items="${rankMap}" var="rank" varStatus="status">
			<tr>
				<td class="nowrap">${rank.key}</td>
				<td class="nowrap" style="text-align:right;">${fn:length(rank.value)}</td>
				<td class="nowrap">
					<c:forEach items="${rank.value}" var="video" varStatus="status">
						<jk:video video="${video}" view="label" mode="${param.view}"/>
					</c:forEach>
				</td>
			</tr>
			</c:forEach>		
		</table>
	</article>
</section>

<section>
	<h3><s:message code="video.video-by-play"/></h3>
	<article>
		<table class="video-table" style="background-color:lightgray">
			<tr>
				<th class="nowrap"><s:message code="video.play"/></th>
				<th class="nowrap"><s:message code="video.size"/></th>
				<th class="nowrap"><s:message code="video.video"/></th>
			</tr>
			<c:forEach items="${playMap}" var="play" varStatus="status">
			<tr>
				<td class="nowrap">${play.key}</td>
				<td class="nowrap" style="text-align:right;">${fn:length(play.value)}</td>
				<td class="nowrap">
					<c:forEach items="${play.value}" var="video" varStatus="status">
						<jk:video video="${video}" view="label" mode="${param.view}"/>
					</c:forEach>
				</td>
			</tr>
			</c:forEach>		
		</table>
	</article>
</section>

<section>
	<h3><s:message code="video.play"/> &amp; <s:message code="video.rank"/> </h3>
	<article>
		<table class="video-table" style="background-color:lightgray">
			<tr>
				<th class="nowrap">Play</th>
				<th class="nowrap">Rank 1</th>
				<th class="nowrap">Rank 2</th>
				<th class="nowrap">Rank 3</th>
				<th class="nowrap">Rank 4</th>
				<th class="nowrap">Rank 5</th>
			</tr>
			<c:forEach items="${playMap}" var="play" varStatus="status">
			<tr>
				<td>${play.key}</td>
				<td id="rank1-${play.key}"></td>
				<td id="rank2-${play.key}"></td>
				<td id="rank3-${play.key}"></td>
				<td id="rank4-${play.key}"></td>
				<td id="rank5-${play.key}"></td>
			</tr>
			<c:forEach items="${play.value}" var="video" varStatus="status">
				<script type="text/javascript">
					var vItem = $("<span>");
					vItem.addClass("label");
					vItem.attr("onclick", "fnViewVideoDetail('${video.opus}')");
					if ('${param.view}' == 'simple') {
						vItem.attr("title", "${video.fullname}");
						vItem.html("O");
					}
					else {
						vItem.attr("title", "${video.title}");
						vItem.html("${video.opus}");
					}
					$("#rank${video.rank}-${play.key}").append(vItem);
				</script>
			</c:forEach>
			</c:forEach>		
		</table>
	</article>
</section>

<section>
	<h3><s:message code="video.video-by-score"/></h3>
	<article>
		<table class="video-table" style="background-color:lightgray">
			<tr>
				<th class="nowrap"><s:message code="video.score"/></th>
				<th class="nowrap"><s:message code="video.size"/></th>
				<th class="nowrap"><s:message code="video.length"/></th>
				<th class="nowrap"><s:message code="video.video"/></th>
			</tr>
			<c:set var="totalLength" value="0"/>
			<c:forEach items="${scoreMap}" var="score" varStatus="status">
			<tr>
				<td class="nowrap">${score.key}</td>
				<td class="nowrap" style="text-align:right;">${fn:length(score.value)}</td>
				<td class="nowrap" style="text-align:right;">
					<c:forEach items="${score.value}" var="video" varStatus="status">
						<c:set var="totalLength" value="${totalLength + video.length}"/>
					</c:forEach>				
					<fmt:formatNumber value="${totalLength / ONE_GB}" pattern="#,##0 GB"/>
				</td>
				<td class="nowrap">
					<c:forEach items="${score.value}" var="video" varStatus="status">
						<em>${video.rank}</em>
						<jk:video video="${video}" view="label" mode="${param.view}" tooltip="
${video.scoreDesc}"/>
					</c:forEach>
				</td>
			</tr>
			</c:forEach>		
		</table>
	</article>
</section>

<section>
	<h3><s:message code="video.video-by-length"/></h3>
	<article>
		<table class="video-table" style="background-color:lightgray">
			<tr>
				<th class="nowrap"><s:message code="video.length"/></th>
				<th class="nowrap"><s:message code="video.size"/></th>
				<th class="nowrap"><s:message code="video.video"/></th>
			</tr>
			<c:set var="totalLength" value="0"/>
			<c:forEach items="${lengthMap}" var="length" varStatus="status">
			<tr>
				<td class="nowrap" style="text-align:right;">${length.key}</td>
				<td class="nowrap" style="text-align:right;">${fn:length(length.value)}</td>
				<td class="nowrap">
					<c:forEach items="${length.value}" var="video" varStatus="status">
						<jk:video video="${video}" view="label" mode="${param.view}"/>
					</c:forEach>
				</td>
			</tr>
			</c:forEach>		
		</table>
	</article>
</section>

<section>
	<h3><s:message code="video.video-by-extension"/></h3>
	<article>
		<table class="video-table" style="background-color:lightgray">
			<tr>
				<th class="nowrap"><s:message code="video.extension"/></th>
				<th class="nowrap"><s:message code="video.size"/></th>
				<th class="nowrap"><s:message code="video.length"/></th>
				<th class="nowrap"><s:message code="video.video"/></th>
			</tr>
			<c:set var="totalLength" value="0"/>
			<c:forEach items="${extensionMap}" var="extension" varStatus="status">
			<tr>
				<td class="nowrap" style="text-align:right;">${extension.key}</td>
				<td class="nowrap" style="text-align:right;">${fn:length(extension.value)}</td>
				<td class="nowrap" style="text-align:right;">
					<c:set var="totalLength" value="0"/>
					<c:forEach items="${extension.value}" var="video" varStatus="status">
						<c:set var="totalLength" value="${totalLength + video.length}"/>
					</c:forEach>
					<fmt:formatNumber value="${totalLength / ONE_GB}" pattern="#,##0 GB"/>
				</td>
				<td class="nowrap">
					<c:forEach items="${extension.value}" var="video" varStatus="status">
						<jk:video video="${video}" view="label" mode="${param.view}"/>
					</c:forEach>
				</td>
			</tr>
			</c:forEach>		
		</table>
	</article>
</section>

<section>
	<h3><s:message code="video.total"/> <s:message code="video.video"/> : ${fn:length(videoList)}</h3>
	<article id="videoDiv" class="div-box">
		<c:forEach items="${videoList}" var="video" varStatus="status">
			<jk:video video="${video}" view="label" mode="${param.view}"/>
		</c:forEach>
	</article>
</section>

<section>
	<h3><s:message code="video.total"/> <s:message code="video.studio"/> : ${fn:length(studioList)}</h3>
	<article id="studioDiv" class="div-box">
	<c:forEach var="studio" items="${studioList}">
		<jk:studio studio="${studio}" view="span"/>
	</c:forEach>
	</article>
</section>

<section>
	<h3><s:message code="video.total"/> <s:message code="video.actress"/> : ${fn:length(actressList)}</h3>
	<article id="actressDiv" class="div-box">
	<c:forEach items="${actressList}" var="actress">
		<jk:actress actress="${actress}" view="span"/>
	</c:forEach>
	</article>
</section>


</div>

</body>
</html>