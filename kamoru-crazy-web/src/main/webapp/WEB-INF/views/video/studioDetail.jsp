<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" 	uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>${studio.name}</title>
<script type="text/javascript">
function fnRenameTo() {
	var actressForm = document.forms['studioForm'];
	actressForm.action = "<s:url value="/video/studio/${studio.name}/renameTo/"/>" + $("#newName").val();
	actressForm.submit();
}

function fnPutStudioInfo() {
	var actressForm = document.forms['studioForm'];
	actressForm.action = "<s:url value="/video/studio/${studio.name}"/>";
	actressForm.submit();
}
</script>
</head>
<body>

<form id="studioForm" action="<s:url value="/video/studio/${studio.name}"/>" method="post">
<input type="hidden" name="_method" id="hiddenHttpMethod" value="put"/>
<dl class="dl-detail">
	<dt class="label-large center">
		<input class="studioInfo" type="text" name="newname" value="${studio.name}" id="newName" />
		<span>Score ${studio.score}</span>
		<span  class="button" style="float:right" onclick="fnRenameTo()">Rename</span>
	</dt>
	<dd>
		<span class="label-title">Homepage : <input class="studioInfo" type="text" name="homepage" value="${studio.homepage}" /></span>
		<span class="label-title">Company : <input class="studioInfo" type="text" name="company" value="${studio.company}" /></span>
		<span class="button" style="float:right" onclick="fnPutStudioInfo()">Save</span>
	</dd>
	<dd>
		<span class="label-title">Actress(${fn:length(studio.actressList)})</span>
	</dd>
	<dd>
		<div style="padding-left:60px;">
		<c:forEach items="${studio.actressList}" var="actress">
			<span class="label" onclick="fnViewActressDetail('${actress.name}')">
				${actress.name}(${fn:length(actress.videoList)}), Score ${actress.score}</span>
		</c:forEach>
		</div>
	</dd>
	<dd><span class="label-title">Video(${fn:length(studio.videoList)})</span></dd>
</dl>
</form>

<div style="padding-left:60px;">
	<ul>
		<c:forEach items="${studio.videoList}" var="video">
			<%@ include file="/WEB-INF/views/video/videoCard.jspf" %>
		</c:forEach>
	</ul>
</div>

</body>
</html>
