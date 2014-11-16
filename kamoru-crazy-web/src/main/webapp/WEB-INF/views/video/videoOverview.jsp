<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><s:message code="video.overview"/> [${video.opus}]</title>
<style type="text/css">
.overviewTxt {width:100%; height:275px;}
</style>
<script type="text/javascript">
function overviewSave()
{
	var overview = $(".overviewTxt");
	$("#overview-${video.opus}", opener.document).attr("title", overview.val());
	var frm = document.forms['overviewFrm'];
	frm.submit();
}
</script>
</head>
<body>
<form method="post" name="overviewFrm" action="<c:url value="/video/${video.opus}/overview" />">
<input type="hidden" name="opus" value="${video.opus}">
<textarea class="overviewTxt" name="overViewTxt">${video.overviewText}</textarea>
</form>
<div tabindex="1" style="position:absolute; right:0px; top:0px; margin:10px 5px 0px 0px; background-color:lightblue; cursor:pointer;" onclick="overviewSave()"><s:message code="video.save"/></div>
</body>
</html>