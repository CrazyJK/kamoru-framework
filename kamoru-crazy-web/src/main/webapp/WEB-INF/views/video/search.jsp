<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<title><s:message code="video.video"/> <s:message code="video.search"/></title>
<script type="text/javascript">
bgContinue = false;

$(document).ready(function(){
	
	$("#query").bind("keyup", function(e) {
		
		var event = window.event || e;
		$("#debug").html(event.keyCode);
		
		if (!(event.keyCode >= 48 && event.keyCode <= 57) // 0 ~ 9
				&& !(event.keyCode >= 65 && event.keyCode <= 90) // a ~ z
				&& !(event.keyCode >= 96 && event.keyCode <= 105) // keypad : 0 ~ 9
				&& event.keyCode != 109 // keypad : -
				&& event.keyCode != 189 // -
				&& event.keyCode != 8 // backspace
				&& event.keyCode != 13 // enter
				) {
			return;
		}

		
		var keyword = $(this).val();
		var queryUrl = context + 'video/search.json?q=' + keyword; 

		$.getJSON(queryUrl ,function(data) {
			$('#foundVideoList').empty();
			$('#foundHistoryList').empty();
			$('#foundVideoList').slideUp();
			$('#foundHistoryList').slideUp();
			$("#url").html(queryUrl);

			var videoRow = data['videoList'];
			$(".video-count").html(videoRow.length);
			$.each(videoRow, function(entryIndex, entry) {
				
				var studio 		   = entry['studio'];
				var opus 		   = entry['opus'];
				var title 		   = entry['title'];
				var actress 	   = entry['actress'];
				var existVideo 	   = entry['existVideo'];
				var existCover 	   = entry['existCover'];
				var existSubtitles = entry['existSubtitles'];
				
				var li  = $("<li>");
				var div = $("<div>");

				var studioDom 		  = $("<span>").addClass("search-item").attr("onclick", "fnViewStudioDetail('" + studio +"')").html(studio);				
				var opusDom 		  = $("<span>").addClass("search-item").html(opus);
				var titleDom 		  = $("<span>").addClass("search-item").attr("onclick", "fnViewVideoDetail('" + opus +"')").html(title);
				var actressDom 		  = $("<span>").addClass("search-item").attr("onclick", "fnViewActressDetail('" + actress +"')").html(actress);
				var existVideoDom 	  = $("<span>").addClass("search-item").addClass((existVideo == "true" ? "exist" : "nonexist" )).html("V");
				var existCoverDom 	  = $("<span>").addClass("search-item").addClass((existCover == "true" ? "exist" : "nonexist" )).html("C");
				var existSubtitlesDom = $("<span>").addClass("search-item").addClass((existSubtitles == "true" ? "exist" : "nonexist" )).html("S");

				div.append(studioDom);
				div.append(opusDom);
				div.append(titleDom);
				div.append(actressDom);
				div.append(existVideoDom);
				div.append(existCoverDom);
				div.append(existSubtitlesDom);
				li.append(div);
				$('#foundVideoList').append(li);
			});

			var historyRow = data['historyList'];
			$(".history-count").html(historyRow.length);
 			$.each(historyRow, function(entryIndex, entry) {
				
				var date = entry['date'];
				var opus = entry['opus'];
				var act  = entry['act'];
				var desc = entry['desc'];
				
				var li  = $("<li>");
				var div = $("<div>");

				var dateDom 		  = $("<span>").addClass("history-item").html(date);
				var opusDom 		  = $("<span>").addClass("history-item").attr("onclick", "fnViewVideoDetail('" + opus +"')").html(opus);
				var actDom	 		  = $("<span>").addClass("history-item").html(act);
				var descDom 		  = $("<span>").addClass("history-item").html(desc);

				div.append(dateDom);
				div.append(opusDom);
				div.append(actDom);
				div.append(descDom);
				li.append(div);
				$('#foundHistoryList').append(li);
			}); 

 		    var rexp = eval('/' + keyword + "/gi");
 		    $("div > ol > li > div > span").each(function() {
 				$(this).html($(this).html().replace(rexp,"<em>"+keyword+"</em>"));
 			});

 		    $('#foundVideoList').slideDown();
 			$('#foundHistoryList').slideDown();
			resizeDivHeight();
		});
	});
});
 
function resizeSecondDiv() {
	var contentDivHeight = $("#content_div").outerHeight();
	var calculatedDivHeight = (contentDivHeight) / 2 - 15; 

	$("#resultVideoDiv").outerHeight(calculatedDivHeight);	
	$("#resultHistoryDiv").outerHeight(calculatedDivHeight);	
}
</script>
</head>
<body>

<div id="header_div" class="div-box">
	<label for="query"><s:message code="video.video"/> <s:message code="video.search"/></label>
	<input type="search" name="query" id="query" style="width:200px;" class="searchInput" placeHolder="<s:message code="video.search"/>"/>
	<span id="url"></span>
	<span id="debug"></span>
</div>

<div id="content_div">
	<div id="resultVideoDiv" class="div-box" style="overflow:auto">
		<h4 class="item-header"><s:message code="video.video"/> <span class="video-count"></span></h4>
		<ol id="foundVideoList" class="items"></ol>
	</div>
	
	<div id="resultHistoryDiv" class="div-box" style="overflow:auto">
		<h4 class="item-header"><s:message code="video.history"/> <span class="history-count"></span></h4>
		<ol id="foundHistoryList" class="items"></ol>
	</div>
</div>
</body>
</html>