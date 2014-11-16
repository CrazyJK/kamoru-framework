<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s"      uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>${actress.name}</title>
<script type="text/javascript">
$(document).ready(function(){
	
	var queryUrl = context + 'image/google.json?q=${actress.name}'; 
	$.getJSON(queryUrl ,function(data) {
		$('#foundList').empty();
		
		var videoRow = data['URLList'];
		$.each(videoRow, function(entryIndex, entry) {
			var url = entry;
			var li  = $("<li>");
			li.css("display", "inline-block");
			var img = $("<img>");
			img.attr("src", url);
			img.attr("width", "200px;");
			img.bind("click", function() {
				popupImage(url);
			});
			li.append(img);
			$('#foundList').append(li);
		});
	});
	
});

function fnRenameTo() {
	var actressForm = document.forms['actressForm'];
	actressForm.action = "<s:url value="/video/actress/${actress.name}/renameTo/"/>" + $("#newName").val();
	actressForm.submit();
}

function fnPutActressInfo() {
	var actressForm = document.forms['actressForm'];
	actressForm.action = "<s:url value="/video/actress/${actress.name}"/>";
	actressForm.submit();
}
</script>
</head>
<body>



<form id="actressForm" action="<s:url value="/video/actress/${actress.name}"/>" method="post">
<input type="hidden" name="_method" id="hiddenHttpMethod" value="put"/>
<input type="hidden" name="name" value="${actress.name}"/>
<dl class="dl-detail">
	<dt class="label-large center">
		<input class="actressInfo" type="text" name="newname" value="${actress.name}" id="newName" />
		<input class="actressInfo" type="text" name="localname" value="${actress.localName}" />
		<span>Score ${actress.score}</span>
		<span  class="button" style="float:right" onclick="fnRenameTo()">Rename</span>
	</dt>
	<dd style="text-align:center;">
		<div id="actressImageContainer">
			<ul id="foundList" class="items"></ul>
		</div>
		<%-- <c:forEach items="${actress.webImage}" var="url">
			<img src="${url}" width="190px" onclick="popupImage('${url}')"/>
		</c:forEach> --%>
	</dd>
	<dd>
		<span class="label-title">Birth : <input class="actressInfo" type="text" name="birth"    value="${actress.birth}"    /></span>
		<span class="label-title">Size :  <input class="actressInfo" type="text" name="bodySize" value="${actress.bodySize}" /></span>
		<span class="label-title">Height :<input class="actressInfo" type="text" name="height"   value="${actress.height}"   /></span>
		<span class="label-title">Debut : <input class="actressInfo" type="text" name="debut"    value="${actress.debut}"    /></span>
		<span class="button" style="float:right" onclick="fnPutActressInfo()">Save</span>
	</dd>
	<dd>
		<span class="label-title">Studio(${fn:length(actress.studioList)})</span>
	</dd>
	<dd>
		<div style="padding-left:60px;">
		<c:forEach items="${actress.studioList}" var="studio">
			<span class="label" onclick="fnViewStudioDetail('${studio.name}')">
				${studio.name}(${fn:length(studio.videoList)}), Score ${studio.score} </span>
		</c:forEach>
		</div>
	</dd>
	<dd>
		<span class="label-title">Video(${fn:length(actress.videoList)})</span>
	</dd>
</dl>
</form>

<div style="padding-left:60px;">
<ul>
	<c:forEach items="${actress.videoList}" var="video">
		<%@ include file="/WEB-INF/views/video/videoCard.jspf" %>
	</c:forEach>
</ul>
</div>
</body>
</html>
