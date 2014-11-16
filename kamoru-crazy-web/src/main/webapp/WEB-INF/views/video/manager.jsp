<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s"   uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="${locale}">
<head>
<title><s:message code="video.manager"/></title>
<style type="text/css">
ul {
	background-color: linen;
	list-style-type: none;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
});
</script>
</head>
<body>

<div id="header_div" class="div-box">
	<s:message code="video.manager"/>
</div>

<div id="content_div" class="div-box" style="overflow:auto; text-align:left;">

	<ul class="video-table">
		<li><a onclick="actionFrame('<c:url value="/video/manager/moveWatchedVideo"/>')"><s:message code="video.mng.move"/></a>
		<li><a onclick="actionFrame('<c:url value="/video/manager/removeLowerRankVideo"/>')"><s:message code="video.mng.rank"/></a>
		<li><a onclick="actionFrame('<c:url value="/video/manager/removeLowerScoreVideo"/>')"><s:message code="video.mng.score"/></a>
		<li><a onclick="actionFrame('<c:url value="/error"/>')">Error</a>
	</ul>

</div>

</body>
</html>