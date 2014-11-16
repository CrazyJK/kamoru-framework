
/**
 * div container 높이 조정
 */
function resizeDivHeight() {
	var windowHeight = $(window).height();
	var header = $("#header_div").outerHeight();
	var calculatedDivHeight = windowHeight - header - 20 * 2; 
	$("#content_div").outerHeight(calculatedDivHeight);	
	try {
		resizeSecondDiv();
	} catch (e) {}
}

function setBackgroundImage(imgIdx) {
	if (imgIdx)
		currBGImageNo = imgIdx;
	else 
		currBGImageNo = getRandomInteger(0, bgImageCount);
	
	currBGImageUrl = context + "image/" + currBGImageNo;
	$("#content_div").hide();
	$("#content_div").css("background-image", "url(" + currBGImageUrl + ")");
	$("#content_div").fadeIn();
}

function fnVideoDivToggle() {
	$("#videoDiv").toggle();
}
function fnStudioDivToggle() {
	$("#studioDiv").toggle();
	resizeDivHeight();
}
function fnActressDivToggle() {
	$("#actressDiv").toggle();
	resizeDivHeight();
}
function fnSearch(txt) {
	if(txt)
		$("#searchText").val(txt);
	var frm = document.forms[0];
	frm.submit();
}
function fnDeleteOpus(selectedOpus) {
	if(confirm("Really? Are you sure to delete this opus?")) 
		if(confirm("Are you kidding? D.E.L.E.T.E [" + selectedOpus + "]?")) {
			$("#hiddenHttpMethod").val("delete");
			// hide it's box
			$("#opus-" + selectedOpus).hide();
			// remove element
			for(var i=0; i<opusArray.length; i++) 
				if(selectedOpus == opusArray[i]) {
					opusArray.splice(i, 1);
					break;
				}
			
			var frm = document.forms["actionFrm"];
			frm.action = context + "video/" + selectedOpus;
			frm.submit();
			debug("delete " + selectedOpus);
		}
	
}
function fnEditSubtitles(selectedOpus) {
	debug("edit subtitles " + selectedOpus);
	$("#actionIframe").attr("src", context + "video/" + selectedOpus + "/subtitles");
}
function fnPlay(selectedOpus) {
	debug("Video play " + selectedOpus);
	$("#actionIframe").attr("src", context + "video/" + selectedOpus + "/play");
	if (listViewType != 'S' && listViewType != 'L' && listViewType != 'V') {
		fnVideoDetail(selectedOpus);
	}  
}
function fnRandomPlay() {
	debug("Random play start");
	if(opusArray.length == 0) {
		alert("다 봤슴당");
		return;
	}
	var selectedNumber = getRandomInteger(0, opusArray.length); // Math.floor(Math.random() * opusArray.length);
	var selectedOpus = opusArray[selectedNumber];
	opusArray.splice(selectedNumber, 1);
	fnOpusFocus(selectedOpus);
	fnPlay(selectedOpus);
}
function fnOpusFocus(opus) {
	if (listViewType == 'L') {
		var idx = $("#opus-" + opus).attr("tabindex");
		fnHideVideoSlise(currentVideoIndex);
		currentVideoIndex = idx;
		fnShowVideoSlise();
	}
	else if (listViewType == 'S' || listViewType == 'V') {
		var idx = $("#opus-" + opus).attr("slidesjs-index");
		$("a[data-slidesjs-item='" + idx + "']").click();
	}
	else {
		$("#opus-" + opus).animate({opacity: 0.5}, 1000, function(){
			$(this).addClass("li-box-played");
		});
		var topValue = $("#opus-" + opus).position().top - $("#headerDiv").outerHeight() - 20;
		$("#content_div").scrollTop(topValue);
	}
}
function fnBGImageView() {
	popup(currBGImageUrl, currBGImageUrl, 800, 600);
}
function fnBGImageDELETE() {
	$("#hiddenHttpMethod").val("DELETE");
	var actionFrm = document.forms['actionFrm'];
	actionFrm.action = currBGImageUrl;
	actionFrm.submit();
}
function fnImageView(opus) {
	debug("Cover image view : " + opus);
	popupImage(context + "video/" + opus + "/cover");
}
function fnEditOverview(opus) {
	debug("Overview Popup : " + opus);
    popup(context + "video/" + opus + "/overview", "overview-"+opus, 400, 300, 'Mouse');
}
function fnVideoDetail(opus) {
    popup(context + "video/" + opus, "detailview-"+opus, 850, 800);
}
function fnRank(opus) {
	var rank = $("#Rank-"+opus);
	fnRankColor(rank);
	var frm;
	if(opener) {
		try {
		$("#Rank-"+opus, opener.document).val(rank.val());
		$("#Rank-"+opus+"-label", opener.document).html(rank.val());
		opener.fnRankColor($("#Rank-"+opus, opener.document));
		} catch(e) {/*opener가 이상하더라도 submit은 해야하므로*/}
	}
	$("#hiddenHttpMethod").val("put");
	frm = document.forms["actionFrm"];
	frm.action = context + "video/" + opus + "/rank/" + rank.val();
	frm.submit();
}
function fnRankColor(rank) {
	if(rank.val() == 0) {
		rank.css("background-color", "cyan");
	}
	else if(rank.val() > 0) {
		rank.css("background-color", "red");
	}
	else {
		rank.css("background-color", "blue");
	}
}
function fnViewActressDetail(name) {
	popup(context + "video/actress/" + name, "actressDetail-" + name, 850, 600);
}

function fnViewStudioDetail(name) {
	popup(context + "video/studio/" + name, "studioDetail-" + name, 800, 600);
}

function fnViewVideoDetail(opus) {
	popup(context + "video/" + opus, "videoDetail-" + opus, 800, 600);
}

function debug(msg) {
	$("#debug").html(msg);
}

// for large view
function fnPrevVideoView() {
	fnHideVideoSlise(currentVideoIndex);
	if (currentVideoIndex == 1)
		currentVideoIndex = totalVideoSize + 1;
	currentVideoIndex--;
	fnShowVideoSlise();
}
function fnNextVideoView() {
	fnHideVideoSlise(currentVideoIndex);
	if (currentVideoIndex == totalVideoSize)
		currentVideoIndex = 0;
	currentVideoIndex++;
	fnShowVideoSlise();
}
function fnRandomVideoView() {
	fnHideVideoSlise(currentVideoIndex);
	currentVideoIndex = getRandomInteger(1, totalVideoSize);
	fnShowVideoSlise();
}
function fnShowVideoSlise() {
	$("div[tabindex='" + currentVideoIndex + "']").fadeIn();
	$("#slideNumber").html(currentVideoIndex + " / " + totalVideoSize);
	
	$("#video_slide_bar").empty();
	var startIdx = parseInt(currentVideoIndex) - 1;
	var endIdx = parseInt(currentVideoIndex) + 1;
	for (var i=startIdx; i<=endIdx; i++) {
		var previewIndex = i;
		if (previewIndex == 0)
			previewIndex = totalVideoSize;
		else if (previewIndex == totalVideoSize + 1)
			previewIndex = 1;
		
		var item = $("<div class='video-box' style='display:inline-block;'>");
		item.append($("div[tabindex='" + previewIndex + "']").html());
		item.children("dl").removeClass("video-slide-bg").addClass("video-box-bg");
		item.children().children().children().each(function() {
			$(this).removeClass("label-large").addClass("label");
		});
		//item.append("<span style='color:red;'>" + startIdx + ":" + previewIndex + ":" + i + ":" + endIdx + "</span>");
		$("#video_slide_bar").append(item);
	}
}
function fnHideVideoSlise(idx) {
	$("div[tabindex='" + idx + "']").hide();
}

// for slides view
function rePagination() {
	var index = parseInt($(".active").attr("data-slidesjs-item"));
    var debugHtml = "index=" + index;
    $(".slidesjs-pagination-item").each(function() {
    	var itemIdx = parseInt($(this).children().attr("data-slidesjs-item"));
    	debugHtml += ". " + itemIdx;
    	if ((itemIdx < index + 5 && itemIdx > index - 5) || itemIdx == 0 || itemIdx == totalVideoSize-1) {
    		$(this).show();
    	}
    	else {
    		$(this).hide();
    	}
    });
    debug(debugHtml);

}
function fnRandomVideoView_Slide() {
	var selectedNumber = getRandomInteger(1, totalVideoSize);
	$("a[data-slidesjs-item='" + selectedNumber + "']").click();
}

function searchContent(keyword) {
	$("div#content_div table tr").each(function() {
		var found = false;
		$(this).children().each(function() {
			if ($(this).text().toLowerCase().indexOf(keyword.toLowerCase()) > -1) {
				found = true;
			}
		});
		if (found)
			$(this).show();
		else
			$(this).hide();
	});
}

function fnUnchecked(obj) {
	$(obj).parent().children().each(function() {
		if ($(this).is("label")) {
			$(this).children(':checked').parent().click();
		}
	});
}

function fnReloadVideoSource() {
	var frm = document.forms["actionFrm"];
	frm.action = context + "video/reload";
	frm.submit();
}

/**
 * background-size:contain; Scale the image to the largest size such that both its width and its height can fit inside the content area
 * 이 설정과 같이 움직이도록 하는 함수 
function resizeBackgroundImage() {
	currBGImageUrl = context + "image/" + selectedNumber;
	
	var img = $("<img />");
	img.hide();
	img.attr("src", currBGImageUrl);
	img.bind('load', function(){
		var imgWidth  = $(this).width();
		var imgHeight = $(this).height();
		var divWidth  = $("#contentDiv").width() + 30;
		var divHeight = $("#contentDiv").height() + 20;
		var width  = 0;
		var height = 0;
		
		if(imgWidth <= divWidth && imgHeight <= divHeight) { // 1. x:- y:-
			width  = imgWidth;
			height = imgHeight;
		}else if(imgWidth <= divWidth && imgHeight > divHeight) { // 2. x:- y:+
			width  = ratioSize(divHeight, imgWidth, imgHeight);
			height = divHeight;
		}else if(imgWidth > divWidth && imgHeight <= divHeight) { // 3. x:+ y:-
			width  = divWidth;
			height = ratioSize(divWidth, imgHeight, imgWidth);
		}else if(imgWidth > divWidth && imgHeight > divHeight) { // 4. x:+ y:+
			width  = divWidth;
			height = ratioSize(width, imgHeight, imgWidth);
			if(height > divHeight) {
				width  = ratioSize(divHeight, imgWidth, imgHeight);
				height = divHeight;
			}
		}
		//debug("background-image resize :{"+imgWidth+","+imgHeight+"}->{"+width+","+height+"}");
		$("#contentDiv").css("background-image", "url(" + currBGImageUrl + ")");
		$("#contentDiv").css("background-size", width + "px " + height + "px");
	});
	$("body").append(img);
}
function ratioSize(numerator1, numerator2, denominator) {
	return parseInt(numerator1 * numerator2 / denominator);
}
 */
