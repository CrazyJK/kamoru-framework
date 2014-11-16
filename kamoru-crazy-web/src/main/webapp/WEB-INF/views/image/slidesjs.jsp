<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><s:message code="image.image-viewer"/> by SlidesJS</title>
<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/res/img/video-favicon.ico" />">
<style type="text/css">
.slides {
	width:800px;
	margin:0 auto;
	text-align: center;
}
.slidesjs-container {
}
.slidesjs-control {
}
.slidesjs-slide {
	border-radius: 10px;
}

.slidesjs-navigation {
}
.slidesjs-previous {
	float:left;
}
.slidesjs-next {
	float:right;
}
.slidesjs-play,
.slidesjs-stop {
	margin-left:20px;
	margin-right:20px;
}

ul.slidesjs-pagination {
	text-shadow: 1px 1px 1px red;
}
li.slidesjs-pagination-item {
	display:inline-block;
}
li.slidesjs-pagination-item a {
	border:1px solid orange;
	width: 30px;
	margin: 3px;
}
li.slidesjs-pagination-item a.active {
	padding: 5px;
	margin: 3px;
}
.bg-image {
	height: 600px; 
	background-position: center center; 
	background-size: contain; 
	background-repeat: no-repeat;
}
</style>
<!--[if lt IE 9]><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="http://slidesjs.com/examples/standard/js/jquery.slides.min.js"></script>
<script src="<c:url value="/res/js/common.js" />"></script>
<script type="text/javascript">
var imagepath = '<s:url value="/image/" />';
var selectedNumber = <c:out value="${selectedNumber}" />;
var selectedImgUrl;
var imageCount = <c:out value="${imageCount}" />;
var windowWidth  = $(window).width();
var windowHeight = $(window).height();

$(document).ready(function(){
	$(function() {
		$('#slides').slidesjs({
			start: selectedNumber == -1 ? getRandomInteger(0, imageCount) : selectedNumber,
	        width: 800,
	        height: 600,
	        navigation: {active: true},
	        /* pagination: false, */
	        play: {active: true, interval:5000, auto: false},
	        callback: {
	        	loaded: function(number) {
	        		debug("loaded callback : " + number);	 
	        		rePagination();
	        	},
	        	start: function(number) {
	        		debug("start callback : " + number);	        
	        		rePagination();
	        	},
	        	complete: function(number) {
	        		debug("complete callback : " + number);
	        	}
	        }
		});
	});
	
	$(window).bind("mousewheel DOMMouseScroll", function(e) {
		var delta = 0;
		var event = window.event || e;
		if (event.wheelDelta) {
			delta = event.wheelDelta/120;
			if (window.opera) delta = -delta;
		} 
		else if (event.detail)  
			delta = -event.detail/3;
		else
			delta = parseInt(event.originalEvent.wheelDelta || -event.originalEvent.detail);
		if (delta) {
			if (delta > 0) 
				$(".slidesjs-previous").click(); //alert("마우스 휠 위로~");
		    else 	
		    	$(".slidesjs-next").click(); //alert("마우스 휠 아래로~");
		}
	});
	$(window).bind("keyup", function(e) {
		var event = window.event || e;
		//alert(event.keyCode);
		switch(event.keyCode) {
		case 37: // left
		case 40: // down
			$(".slidesjs-previous").click(); break;
		case 39: // right
		case 38: // up
			$(".slidesjs-next").click(); break;
		case 32: // space
			fnRandomImageView(); break;
		case 13: // enter
			break;
		};
	});
	$(".bg-image").bind("click", function(e){
		var event = window.event || e;
		//alert(event.type + " - " + event.button + ", keyValue=" + event.keyCode);
		event.stopImmediatePropagation();
		event.preventDefault();
		event.stopPropagation();
		if(event.button == 0) {
			fnRandomImageView();
		};
	});


});
function fnRandomImageView() {
	selectedNumber = getRandomInteger(0, imageCount);
	$("a[data-slidesjs-item='" + selectedNumber + "']").click();
}
function rePagination() {
    var index = parseInt($(".active").attr("data-slidesjs-item"));
    debug(index);
    $(".slidesjs-pagination-item").each(function() {
    	var itemIdx = parseInt($(this).children().attr("data-slidesjs-item"));
    	if (itemIdx > index + 10 || itemIdx < index - 10) {
    		$(this).hide();
    	}
    	else {
    		$(this).show();
    	}
    });
}
function debug(msg) {
	$("#debug").html(msg);
}

</script>
</head>
<body>

<div id="slides" class="slides">
<c:forEach begin="0" end="${imageCount -1}" step="1" var="idx">
	<div class="bg-image" style="background-image:url('<s:url value="/image/${idx}" />'); display:none;">&nbsp;
	</div>
	<%-- <img src="<s:url value="/image/${idx}" />"> --%>
</c:forEach>
</div>

<div id="debug"></div>

</body>
</html>