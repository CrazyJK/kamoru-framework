<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html> 
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/res/img/video-favicon.ico" />">
<title><s:message code="image.image-viewer"/></title>
<style type="text/css">
#imageThumbnailUL li {
	display:inline-block;
}
#navDiv {
	position:absolute; 
	left:0px; 
	top:0px; 
	margin:5px 5px 0px 5px; 
	cursor:pointer;
}
#imageThumbnailDiv {
	position:absolute; 
	bottom:0px; 
	height:100px; 
	width:100%; 
	margin:10px 5px 0px 0px; 
	text-align:center; 
	overflow:hidden;
}
#imageDiv {
	text-align:center;
}
.thumbDiv {
	width:150px; 
	height:100px;
}
.centerBG {
	background-size:contain; 
	background-repeat:no-repeat; 
	background-position:center center;
}
.opacity10 {
	opacity:1;
}
.opacity05 {
	opacity:0.5;
}
.label {
	display:inline-block; 
	text-overflow:ellipsis;
	font-size: 12px;
	overflow:hidden; 
	background-color:rgba(255, 255, 255, 0.5); 
	color:black; 
	text-shadow:1px 1px 1px white; 
	padding:0px 5px 0px 5px;
	border:1px solid orange; 
	border-radius:5px; 
	-moz-border-radius:5px; 
	-webkit-border-radius:5px;
}
</style>
<!--[if lt IE 9]><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="<c:url value="/res/js/common.js" />"></script>
<script type="text/javascript">
var imagepath = '<s:url value="/image/" />';
var selectedNumber = ${selectedNumber};
var selectedImgUrl;
var imageCount = <c:out value="${imageCount}" />;
var windowWidth  = $(window).width();
var windowHeight = $(window).height();
var imageMap = ${imageNameJSON};
/*{
		<c:forEach items="${imageList}" var="image" varStatus="status">
			"${status.index}":"${image.name}",
		</c:forEach>
};*/
$(document).ready(function(){
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
				fnPrevImageView(); //alert("마우스 휠 위로~");
		    else 	
				fnNextImageView(); //alert("마우스 휠 아래로~");
		}
		//alert("event=" + event + " delta=" + delta);
	});
	$(window).bind("keyup", function(e) {
		var event = window.event || e;
		//alert(event.keyCode);
		switch(event.keyCode) {
		case 37: // left
		case 40: // down
			fnPrevImageView(); break;
		case 39: // right
		case 38: // up
			fnNextImageView(); break;
		case 32: // space
			fnRandomImageView();
		case 13: // enter
			break;
		}
	});
	$("#imageDiv").bind("click", function(e){
		var event = window.event || e;
		//alert(event.type + " - " + event.button + ", keyValue=" + event.keyCode);
		event.stopImmediatePropagation();
		event.preventDefault();
		event.stopPropagation();
		if(event.button == 0) {
			fnRandomImageView();
		} 
	});
	$(window).bind("resize", resizeImage);
	resizeImage();
	if (selectedNumber > -1)
		fnViewImage(selectedNumber);
	else
		fnRandomImageView();
	$("#firstNo").html(0);
	$("#endNo").html(imageCount-1);
	
});

function resizeImage() {
	windowHeight = $(window).height();
	$("#imageDiv").height(windowHeight - 105);
	fnDisplayThumbnail();
}

function fnViewImage(current) {
	selectedNumber = current;
	selectedImgUrl = imagepath + selectedNumber;

	$("#imageDiv").hide();
	$("#imageDiv").css("background-image", "url('" + selectedImgUrl + "')");
	$("#imageDiv").fadeIn();

	$("#leftNo").html(getPrevNumber());
	$("#currNo").html(selectedNumber);
	$("#rightNo").html(getNextNumber());
	$("#imageTitle").html(imageMap[selectedNumber]);
	fnDisplayThumbnail();
}
function fnFullyImageView() {
	popupImage(selectedImgUrl);
}
function getPrevNumber() {
	return selectedNumber == 0 ? imageCount - 1 : selectedNumber - 1;
}
function getNextNumber() {
	return selectedNumber == imageCount -1 ? 0 : selectedNumber + 1;
}
function fnFirstImageView() {
	fnViewImage(0);
}
function fnPrevImageView() {
	fnViewImage(getPrevNumber());
}
function fnNextImageView() {
	fnViewImage(getNextNumber());
}
function fnEndImageView() {
	fnViewImage(imageCount-1);
}
function fnRandomImageView() {
	fnViewImage(Math.floor(Math.random() * imageCount));
}
function fnDisplayThumbnail() {
	var thumbnailRange = parseInt(parseInt($(window).width() / 200) / 2);
	$("#imageThumbnailUL").empty();
	for (var current = selectedNumber - thumbnailRange; current <= selectedNumber + thumbnailRange; current++) {
		var thumbNo = current;
		if (thumbNo < 0 )
			thumbNo = imageCount + thumbNo;
		if (thumbNo >= imageCount)
			thumbNo = thumbNo - imageCount;
		var li = $("<li>");
		var div = $("<div class='thumbDiv centerBG " + (thumbNo == selectedNumber ? "opacity10" : "opacity05") + "' onclick='fnViewImage("+thumbNo+")'>");
		div.css("background-image", "url('" + imagepath + thumbNo + "/thumbnail" + "')");
		li.append(div);
		$("#imageThumbnailUL").append(li);
	}
}
</script>
</head>
<body>
<span id="debug" style="display:none;"></span>
<div id="navDiv">
	<span class="label" onclick="fnFirstImageView();"><span id="firstNo"></span></span>
	<span class="label" onclick="fnPrevImageView();">&lt;&nbsp;<span id="leftNo"></span></span>
	<span class="label"><span id="currNo"></span></span>
	<span class="label" onclick="fnNextImageView();"><span id="rightNo"></span>&nbsp;&gt;</span>
	<span class="label" onclick="fnEndImageView();"><span id="endNo"></span></span>
	<br/>
	<span class="label" id="imageTitle" onclick="fnFullyImageView();"></span>
</div>
<div id="imageThumbnailDiv"><ul id="imageThumbnailUL"></ul></div>
<div id="imageDiv" class="centerBG"></div>
</body>
</html>
