<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='form'   uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
<title><s:message code="video.history"/> <s:message code="video.search"/></title>
<script type="text/javascript">
$(document).ready(function(){
	$(window).bind("resize", resizeDivHeight);
	
	$("#query").bind("keyup click", function(event) {
		var keyword = $(this).val();
		var queryUrl = context + 'video/history.json?q=' + keyword; 
		$("#debug").html(queryUrl);
		$("#opus").val(keyword);
		$.getJSON(queryUrl ,function(data) {
			$('#foundList').empty();
			var row = data['historyList'];
			
 			$.each(row, function(entryIndex, entry) {
				
				var date 		   = entry['date'];
				var opus 		   = entry['opus'];
				var act 		   = entry['act'];
				var desc 	   = entry['desc'];
				
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

				$('#foundList').append(li);
			}); 
 			$('#foundList').append("<LI><DIV>EOF : " + keyword + "</DIV></LI>");
			searchAndHighlight($("#query").val());
			resizeDivHeight();
		});
	});
	
	$("#addVideoBtn").css("cursor", "pointer").bind("click", function() {
		var html = "<p>["+$("#studio").val()+"]["+$("#opus").val().toUpperCase()+"]["+$("#title").val()+"]["+$("#actress").val()+"]["+$("#etcInfo").val()+"]</p>";
		$("#newVideo").append(html);
	});	
	$("#query").click();
	resizeDivHeight();
});

function resizeDivHeight() {
	var windowHeight = $(window).height();
	var debugDivHeight = $("#debugDiv").outerHeight();
	var queryDivHeight = $("#queryDiv").outerHeight();
	var calculatedDivHeight = windowHeight - debugDivHeight - queryDivHeight - 20 * 2; 
	$("#resultDiv").outerHeight(calculatedDivHeight);	
}
function searchAndHighlight(searchTerm) {
    var rexp = eval('/' + searchTerm + "/gi");

	$("#resultDiv span.history-item").each(function() {
		$(this).html($(this).html().replace(rexp,"<em>"+searchTerm+"</em>"));
	});
	
   	$(window).scrollTop(0);
}
</script>
</head>
<body>
<div id="debugDiv"></div>
<div id="queryDiv" class="div-box">
	<label for="query"><s:message code="video.history"/> <s:message code="video.search"/></label>
	<input type="search" name="query" id="query" style="width:200px;" class="searchInput" placeHolder="<s:message code="video.search"/>"/>
	<span id="debug"></span>
</div>

<div id="resultDiv" class="div-box" style="overflow:auto">
	<ol id="foundList"></ol>
</div>

</body>
</html>