<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<title><s:message code="image.googleSearch" text="Google image search"/></title>
<style type="text/css">
li.thumb {display: inline-block;}
li.thumb > img {width:200px;}
</style>
<script type="text/javascript">
$(document).ready(function(){
	
	$("#query").bind("keyup", function(e) {
		
		var event = window.event || e;
		$("#debug").html(event.keyCode);
		
		if (event.keyCode != 13) {
			return;
		}
		
		var keyword = $(this).val();
		var queryUrl = context + 'image/google.json?q=' + keyword; 
		$("#url").html(queryUrl);

		$.getJSON(queryUrl ,function(data) {
			$('#foundList').empty();
			$('#foundList').slideUp();
			
			var videoRow = data['URLList'];
			$.each(videoRow, function(entryIndex, entry) {
				var url = entry;
				var li  = $("<li>");
				li.addClass("thumb");
				var img = $("<img>");
				img.attr("src", url);
				img.bind("click", function() {
					popupImage(url);
				});
				li.append(img);
				$('#foundList').append(li);
			});

 		    $('#foundList').slideDown();
		});
	});
});
</script>
</head>
<body>

<div id="header_div" class="div-box">
	<label for="query"><s:message code="default.image"/> <s:message code="default.search"/></label>
	<input type="search" name="query" id="query" style="width:200px;" class="searchInput" placeHolder="<s:message code="video.search"/>"/>
	<span id="url"></span>
	<span id="debug"></span>
</div>

<div id="content_div">
	<div id="resultImageDiv" class="div-box" style="overflow:auto">
		<h4 class="item-header"><s:message code="default.image"/> <s:message code="default.result"/></h4>
		<ul id="foundList" class="items"></ul>
	</div>
</div>
</body>
</html>