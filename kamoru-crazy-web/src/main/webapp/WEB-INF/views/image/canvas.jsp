<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/res/img/video-favicon.ico" />">
<title>Canvas <s:message code="image.image-viewer"/></title>
<style type="text/css">
section#img-section {
	text-align:center;
	margin:0;
}
canvas#cv {
	border: 0px solid #888;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	border-radius: 10px;	
	background-color: rgba(0, 0, 0, 0.5);
}
canvas#cv:hover {
	cursor:pointer;
}
nav#img-nav {
	text-align:center;
	margin:0;
}
nav#img-nav table {
	width:100%; 
	padding:0; 
	margin:0;
	table-layout:fixed;
}
nav#img-nav table td {
	border:0px solid orange;
}
nav#img-nav ul {
	list-style: none;
	padding: 0;
	display: block;
	clear: right;
	text-align: center;
	margin-top:3px;
	margin-bottom:0;
	background-color: rgba(0, 0, 0, 0.5);
	border-radius: 10px;
}
nav#img-nav ul li {
	display: inline-block;
	padding: 0;
	border-right: 0px solid #ccc;
	width: 55px;
}
nav#img-nav a, input ~ span {
	color: #EFD3D3;
	text-decoration: none;
	font-size: 13px;
	font-weight: bold;
	cursor:pointer;
}
nav#img-nav a:hover, input ~ span:hover {
	color: #fff;
   	text-shadow: 2px 2px 2px #123;
}
nav#img-nav a.selected, :checked ~ span {
	color: #D11E11;
	border-radius: 10px;
	padding: 0 10px;
}
input[type=text] {
	color: #EFD3D3;
	margin:0;
	padding:0;
	border:0;
	font-size: 12px;
	text-align:center;
	height:15px;
	background-color: rgba(0, 0, 0, 0.5);
	border-radius: 10px;
}
input#goNumber {
	width:25pt;
}
.moveBtn, input ~ span {
	background-color: rgba(0, 0, 0, 0.5);
	border-radius: 10px;
	padding: 0 10px;
}
.EllipsText{
	overflow : hidden;
	white-space: nowrap;
	text-overflow:ellipsis;
	-o-text-overflow:ellipsis;
	-ms-text-overflow:ellipsis;
	-moz-binding:url(/xe/ellipsis.xml#ellipsis)
}
input[type=radio] {
	display:none;
}
</style>	
<script>
var canvas, context, tool;

var imagepath = '<s:url value="/image/" />';
var selectedNumber = ${selectedNumber};
var imageCount = ${imageCount};
var imageMap = ${imageNameJSON};
var imageRatio = 1.0;
var xPositionOffset = 0;
var yPositionOffset = 0;
var playSlide = false;

$(document).ready(function() {

	setBackgroundImage();
	setInterval(function() {setBackgroundImage();},	60*1000);
	
	// Pencil tool 객체를 생성 한다.
    tool = new tool_pencil();
    $("#cv").bind("mousedown", ev_canvas);
    $("#cv").bind("mousemove", ev_canvas);
    $("#cv").bind("mouseup", ev_canvas);
	
	//$("#cv").bind("click", viewNextImage);
	
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
				viewPrevImage(); //alert("마우스 휠 위로~");
		    else 	
		    	viewNextImage(); //alert("마우스 휠 아래로~");
		}
	});
	$(window).bind("keyup", function(e) {
		var event = window.event || e;
		switch (event.keyCode) {
			case 37: // left
			//case 40: // down
				viewPrevImage(); 
				break;
			case 39: // right
			//case 38: // up
				viewNextImage(); 
				break;
			case 32: // space
				viewRandomImage(); 
				break;
			case 100: // numpad 4
				//imgScrollLeft(); 
				viewPrevImage(); 
				break;
			case 102: // numpad 6
				//imgScrollRight(); 
				viewNextImage(); 
				break;
			case 98: // numpad 2
				imgScrollDown(); 
				break;
			case 104: // numpad 8
				imgScrollUp(); 
				break;
			case 107: // numpad +
				imgPlus(); 
				break;
			case 109: // numpad -
				imgNinus(); 
				break;
			case 101: // numpad 5
				imgOriginView(); 
				break;
			case 105: // numpad 9
				imgLandscape(); 
				break;
			case 103: // numpad 7
				togglePlay(); 
				break;
			case 46: // delete
				fnDelete();
				break;
			case 13: // enter
				break;
		}
	});


	canvas = document.getElementById("cv");
	context = canvas.getContext("2d");

	resizeSecondDiv();

	setInterval(function() {
		if (playSlide) {
			viewNextImage();
		}
	},	10*1000);
	
	
});	

function setBackgroundImage() {
	var bgImgUrl = "<c:url value="/image/random"/>?_t=" + new Date().getTime();
	$("body").css("background-image", "url(" + bgImgUrl + ")");
	$("body").css("background-repeat", "repeat");
	$("body").css("background-position", "center center");
	$("body").css("background-size", "contain");
}
function imageURL() {
	return imagepath + selectedNumber + "?_t=" + new Date().getTime();
}
function loadImage(nextNumber) {
	if (parseInt(nextNumber) > -1)
		selectedNumber = parseInt(nextNumber);
	var image = new Image();
	image.src = imagepath + selectedNumber;
	image.onload = function(){
		fnDrawImage(image);
	};
}
function imgOriginView() {
	xPositionOffset = 0;
	yPositionOffset = 0;
	imageRatio = 1.0;
	var image = new Image();
	image.src = imageURL();
	image.onload = function(){
		var canW = canvas.width;
		var canH = canvas.height;
		var imgW = parseInt(image.width);
		var imgH = parseInt(image.height);
		var xRatio = 1.0;
		var yRatio = 1.0;
		if(canW < imgW){
			xRatio = canW / imgW;
			imgH = parseInt(imgH * xRatio);
		}
		if(canH < imgH){
			yRatio = canH / imgH;
		}
		imageRatio = Math.min(xRatio, yRatio);
		fnDrawImage(image);
	};
}
function imgLandscape() {
	xPositionOffset = 0;
	yPositionOffset = 0;
	imageRatio = 1.0;
	var image = new Image();
	image.src = imageURL();
	image.onload = function(){
		var canW = canvas.width;
		var canH = canvas.height;
		var imgW = parseInt(image.width);
		var imgH = parseInt(image.height);
		var landscape = false;
		if(canW < imgW){
			imageRatio = canW / imgW;
			imgH = parseInt(imgH * imageRatio);
		}
		if(canH < imgH){
			landscape = true;
		}
		fnDrawImage(image, landscape);
	};
}
function fnDrawImage(image, landscape) {
	var imgW = parseInt(image.width * imageRatio);
	var imgH = parseInt(image.height * imageRatio);

	var xPos = parseInt((canvas.width  - imgW) / 2 + xPositionOffset);
	var yPos = parseInt((canvas.height - imgH) / 2 + yPositionOffset);
	if (landscape) {
		yPositionOffset = -yPos;
		yPos = 0;
	}

	resetCanvas();
	context.drawImage(image, xPos, yPos, imgW, imgH);

	/*
	var debug = "x:" + xPos + " y:" + yPos + " w:" + imgW + " h:" + imgH 
		+ " R:" + imageRatio + " xOff:" + xPositionOffset + " yOff:" + yPositionOffset;
	context.font = "20px '맑은 고딕'";
	context.fillText(debug, 10 , 760);
	*/
	$(':text[name="ratio"]').val(imageRatio);
	
	displayImageNav();
}
function resetCanvas(){
	canvas.width = canvas.width;        
}

function getPrevNumber() {
	return selectedNumber == 0 ? imageCount - 1 : selectedNumber - 1;
}
function getNextNumber() {
	return selectedNumber == imageCount -1 ? 0 : selectedNumber + 1;
}
function getRandomNumber() {
	return Math.floor(Math.random() * imageCount);
}

function viewPrevImage() {
	selectedNumber = getPrevNumber();
	imgOriginView();
}
function viewNextImage() {
	selectedNumber = getNextNumber();
	imgOriginView();
}
function viewRandomImage() {
	selectedNumber = getRandomNumber();
	imgOriginView();
}
function displayImageNav() {
	$("#imageName").html(imageMap[selectedNumber]);
	$("#goNumber").val(selectedNumber+1);
	var ul = $("#navUL");
	ul.empty();

	var startIdx = 0;
	var endIdx = 13;
	if (selectedNumber >= 7) {
		startIdx = selectedNumber - 6;
		endIdx = selectedNumber + 7;
	}
	if (endIdx > imageCount) {
		startIdx = imageCount - 13;
		endIdx = imageCount;
	}
	ul.append("<li><a onclick='viewPrevImage();'>Prev</a></li>");
	ul.append("<li><a onclick='loadImage(0);'>First</a></li>");
	for (var i=startIdx; i<endIdx; i++) {
		ul.append("<li><a onclick='loadImage(" + i + ");' " + (i==selectedNumber ? "class='selected'" : "") + ">" + (i+1) + "</a></li>");
	}
	ul.append("<li><a onclick='loadImage(" + (imageCount-1) + ");'>Last</a></li>");
	ul.append("<li><a onclick='viewNextImage();'>Next</a></li>");

}
function goPage() {
	var pNo = $("#goNumber").val();
	if (pNo > 0 || pNo < imageCount + 1) {
		selectedNumber = pNo - 1;
		imgOriginView();
	}
}
function resizeSecondDiv() {
	var contentDivHeight = $(document).outerHeight();
	if ($("#deco_section").outerHeight() != null)
		contentDivHeight = $("#deco_section").outerHeight();
	var imgNavHeight = $("#img-nav").outerHeight();
	var calculatedCanvasHeight = contentDivHeight - imgNavHeight - 40 - 15; // margin:16, padding:20 
	$("#cv").attr("height", calculatedCanvasHeight);
	var calculatedCanvasWidth = $("#img-nav").parent().outerWidth();
	$("#cv").attr("width", calculatedCanvasWidth);
	
	if (selectedNumber > -1) {
		imgOriginView();
	}
	else
		viewRandomImage();

}
function fnPopupView() {
	popupImage(imageURL());
}

function tool_pencil() {
    var tool = this;
    this.started = false;
    var pencil = null, diameter = null, color = null;
    // 마우스를 누르는 순간 그리기 작업을 시작 한다. 
    this.mousedown = function(ev) {
    	pencil = $(':radio[name="pencil"]:checked').val();
    	diameter = $(':text[name="diameter"]').val();
    	color = $(':text[name="color"]').val();
    	context.strokeStyle = color;
    	context.fillStyle = color;
        context.beginPath();
        if (pencil == 'cursor')
        	viewNextImage();
        tool.started = true;
    };
   // 마우스가 이동하는 동안 계속 호출하여 Canvas에 Line을 그려 나간다
    this.mousemove = function(ev) {
        if (tool.started) 
        {
            if (pencil == 'circle')
	            context.arc(ev._x, ev._y, diameter/2, 0, 2*Math.PI, true);
            else if (pencil == 'rubber')
            	context.clearRect(ev._x - diameter/2, ev._y - diameter/2, diameter, diameter);

            context.closePath();
            //context.lineWidth = 5;
            context.fill();
        }
    };
   // 마우스 떼면 그리기 작업을 중단한다
    this.mouseup = function(ev) {
      if (tool.started){
            tool.mousemove(ev);
            tool.started = false;
      }
    };
}        
// Canvas요소 내의 좌표를 결정 한다.
function ev_canvas(ev) {
    if (ev.layerX || ev.layerX == 0) {   	// Firefox 브라우저
      	ev._x = ev.layerX;
      	ev._y = ev.layerY;
    } 
    else if (ev.offsetX || ev.offsetX == 0) { // Opera 브라우저
      	ev._x = ev.offsetX;
      	ev._y = ev.offsetY;
    }
    // tool의 이벤트 핸들러를 호출한다.
    var func = tool[ev.type];        
    if (func) {
        func(ev);
    }
}
function imgPlus() {
	imageRatio = imageRatio + 0.1;
	loadImage();
}
function imgNinus() {
	imageRatio = imageRatio - 0.1;
	loadImage();
}
function imgScrollUp() {
	yPositionOffset = yPositionOffset - 100;
	loadImage();
}
function imgScrollDown() {
	yPositionOffset = yPositionOffset + 100;
	loadImage();
}
function imgScrollLeft() {
	xPositionOffset = xPositionOffset - 100;
	loadImage();
}
function imgScrollRight() {
	xPositionOffset = xPositionOffset + 100;
	loadImage();
}
function togglePlay() {
	if (playSlide) {
		playSlide = false;
		$("#play").html("Play");
		$("#play").css("background-color", "rgba(0, 0, 0, 0.5)");
	}
	else {
		playSlide = true;
		$("#play").html("Stop");
		$("#play").css("background-color", "rgba(255,165,0,0.75)");
	}
}
function fnDelete() {
	window.location.href = '<s:url value="/image/canvas" />?d=' + selectedNumber;
}
</script>
</head>
<body>

<section id="img-section">
	<canvas id="cv" width="900px" height="500px"></canvas>
</section>

<nav id="img-nav">
	<table>
		<colgroup>
			<col width="320px"/>
			<col/>
			<col width="320px"/>
		</colgroup>
		<tr>
			<td colspan="3" class="EllipsText" id="imageName" onclick="loadImage()"></td>
		</tr>
		<tr>
			<td align="left">
				<a class="moveBtn" onclick="fnPopupView()">Popup</a>
				<label><input type="radio" name="pencil" value="circle"/><span>Circle</span></label>
				<label><input type="radio" name="pencil" value="rubber"/><span>Rubber</span></label>
				<label><input type="radio" name="pencil" value="cursor" checked/><span>Cursor</span></label>
			</td>
			<td>
				<a class="moveBtn" onclick='imgScrollLeft()' title="Move left">←</a>
				<a class="moveBtn" onclick='imgScrollRight()' title="Move right">→</a>
				<a class="moveBtn" onclick='imgScrollUp()' title="Move up; Numpad 8">↑</a>
				<a class="moveBtn" onclick='imgScrollDown()' title="Move down; Numpad 2">↓</a>
				<a class="moveBtn" onclick='imgPlus()' title="Magnify; Numpad +">+</a>
				<a class="moveBtn" onclick='imgNinus()' title="Curtail; Numpad -">-</a>
				<a class="moveBtn" onclick='imgLandscape()' title="Landscape; Numpad 9">↔</a>
			</td>
			<td align="right">
				<a class="moveBtn" onclick="fnDelete()">Del</a>
				<a class="moveBtn" onclick="togglePlay()" id="play" title="Slide play; Numpad 7">Play</a>
				<input type="text" name="ratio" value="" style="width:40px" title="Ratio" readonly/>
				<input type="text" name="color" value="white" style="width:40px" title="Color"/>
				<input type="text" name="diameter" value="50" style="width:30px" title="Diameter"/>
				<input type="text" id='goNumber' title="Image No"><a class="moveBtn" onclick='goPage()'>Go</a>
			</td>
		</tr>
		<tr>
			<td colspan="3"><ul id="navUL"></ul></td>
		</tr>
	</table>
</nav>

</body>
</html>