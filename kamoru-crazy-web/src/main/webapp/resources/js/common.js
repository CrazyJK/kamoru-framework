/**
 * 팝업창을 띄운다. 
 * @param url
 * @param name '-'글자는 ''으로 바뀜
 * @param width if null, 화면 절반 크기
 * @param height if null, 화면 절반 크기
 * @param positionMethod if null, default is <code>Window.Center</code>. 1.<code>Window.Center</code> 화면 가운데 2.<code>Mouse</code> 마우스 위치. 
 * @param spec if null, default is <code>toolbar=0,location=0,directories=0,titlebar=0,status=0,menubar=0,scrollbars=1,resizable=1</code>
 */
function popup(url, name, width, height, positionMethod, spec) {
	var vUrl = url;
	var vName = name.replace(/-/gi, '');
	if (!width)
		width = window.screen.width/2;
	if (!height)
		height = window.screen.height/2;
	var left = (window.screen.width  - width)/2;
	var top  = (window.screen.height - height)/2;
	var specs = "toolbar=0,location=0,directories=0,titlebar=0,status=0,menubar=0,scrollbars=1,resizable=1";
	if (positionMethod) {
		if(positionMethod == 'Window.Center') {
			left = (window.screen.width  - width)/2;
			top  = (window.screen.height - height)/2;
		} 
		else if (positionMethod == 'Mouse') {
			try {
				var event = window.event || e;
				left = event.pageX;
				top  = event.pageY;
			} catch(e) {}
		}
	}
	else {
		left = (window.screen.width  - width)/2;
		top  = (window.screen.height - height)/2;
	}
	if(spec) {
		specs = spec;
	}
	specs = "width="+width+",height="+height+",top="+top+", left="+left + "," + specs;
//	alert(vUrl + " - " + vName + " - " + specs);
	var popupWindow = window.open(vUrl, vName, specs);
	popupWindow.focus();
}
function popupImage(url, name) {
	if (!name)
		name = url;
	var img =  new Image(); 
	img.src = url;
	$(img).ready(function(){ 
		var imgWidth  = img.width;
		var imgHeight = img.height;
//		alert("size method 3 = " + imgWidth + " x " + imgHeight);
		popup(url, name, imgWidth, imgHeight);
	});
}
function fnViewFullImage(image) {
	var img = $("<img />");
	img.hide();
	img.attr("src", image.src);
	img.bind('load', function(){
		var imgWidth  = $(this).width() + 20;
		var imgHeight = $(this).height() + 20;
		mw_image_window(image, imgWidth, imgHeight);
	});
}
/**
 * 이미지 팝업을 띄운다. 화면보다 큰 이미지는 마우스 드래그 가능하게 함.
 * FIXME safari에서 win.document 를 찾지 못하는 오류
 * @param img 이미지 객체
 * @param w 이미지 가로 길이
 * @param h 이미지 세로 길이
 */
function mw_image_window(img, w, h)
{
	if (!w || !h)
	{
        w = img.width; 
        h = img.height; 
	}

	var winl = (screen.width-w)/2; 
	var wint = (screen.height-h)/3; 

	if (w >= screen.width) { 
		winl = 0; 
		h = (parseInt)(w * (h / w)); 
	} 

	if (h >= screen.height) { 
		wint = 0; 
		w = (parseInt)(h * (w / h)); 
	} 

	var js_url = "<script language='JavaScript1.2'> \n"; 
		js_url += "<!-- \n"; 
		js_url += "var ie=document.all; \n"; 
		js_url += "var nn6=document.getElementById&&!document.all; \n"; 
		js_url += "var isdrag=false; \n"; 
		js_url += "var x,y; \n"; 
		js_url += "var dobj; \n"; 
		js_url += "function movemouse(e) \n"; 
		js_url += "{ \n"; 
		js_url += "  if (isdrag) \n"; 
		js_url += "  { \n"; 
		js_url += "    dobj.style.left = nn6 ? tx + e.clientX - x : tx + event.clientX - x; \n"; 
		js_url += "    dobj.style.top  = nn6 ? ty + e.clientY - y : ty + event.clientY - y; \n"; 
		js_url += "    return false; \n"; 
		js_url += "  } \n"; 
		js_url += "} \n"; 
		js_url += "function selectmouse(e) \n"; 
		js_url += "{ \n"; 
		js_url += "  var fobj      = nn6 ? e.target : event.srcElement; \n"; 
		js_url += "  var topelement = nn6 ? 'HTML' : 'BODY'; \n"; 
		js_url += "  while (fobj.tagName != topelement && fobj.className != 'dragme') \n"; 
		js_url += "  { \n"; 
		js_url += "    fobj = nn6 ? fobj.parentNode : fobj.parentElement; \n"; 
		js_url += "  } \n"; 
		js_url += "  if (fobj.className=='dragme') \n"; 
		js_url += "  { \n"; 
		js_url += "    isdrag = true; \n"; 
		js_url += "    dobj = fobj; \n"; 
		js_url += "    tx = parseInt(dobj.style.left+0); \n"; 
		js_url += "    ty = parseInt(dobj.style.top+0); \n"; 
		js_url += "    x = nn6 ? e.clientX : event.clientX; \n"; 
		js_url += "    y = nn6 ? e.clientY : event.clientY; \n"; 
		js_url += "    document.onmousemove=movemouse; \n"; 
		js_url += "    return false; \n"; 
		js_url += "  } \n"; 
		js_url += "} \n"; 
		js_url += "document.onmousedown=selectmouse; \n"; 
		js_url += "document.onmouseup=new Function('isdrag=false'); \n"; 
		js_url += "//--> \n"; 
		js_url += "</"+"script> \n"; 

	var settings;
	var g4_is_gecko = true;
	if (g4_is_gecko) {
		settings  ='width='+(w+20)+','; 
		settings +='height='+(h+20)+','; 
	} else {
		settings  ='width='+w+','; 
		settings +='height='+h+','; 
	}
	settings +='top='+wint+','; 
	settings +='left='+winl+','; 
	settings +='scrollbars=no,'; 
	settings +='resizable=yes,'; 
	settings +='status=no'; 

	var g4_charset = "UTF-8";
	var click;
	var titleTooltip;
	var size = w + " x " + h;
	if(w >= screen.width || h >= screen.height) { 
		titleTooltip = size+" \n\n 이미지 사이즈가 화면보다 큽니다. \n 왼쪽 버튼을 클릭한 후 마우스를 움직여서 보세요. \n\n 더블 클릭하면 닫혀요.";
		click = "ondblclick='window.close();' style='cursor:move' "; 
	} 
	else {
		titleTooltip = size+" \n\n 클릭하면 닫혀요.";
		click = "onclick='window.close();' style='cursor:pointer' ";
	}
	var title = img.src + " " + titleTooltip;

	
	
	var imgWin = window.open("","image_window",settings); 
	alert(imgWin);
	var doc = imgWin.document;
	alert(doc);
	doc.open(); 
	doc.write ("<html><head> \n<meta http-equiv='imagetoolbar' CONTENT='no'> \n<meta http-equiv='content-type' content='text/html; charset="+g4_charset+"'>\n"); 
	doc.write ("<title>"+title+"</title> \n");
	if(w >= screen.width || h >= screen.height) { 
		doc.write (js_url); 
	} 
	doc.write ("<style>.dragme{position:relative;}</style> \n"); 
	doc.write ("</head> \n\n"); 
	doc.write ("<body leftmargin=0 topmargin=0 bgcolor=#dddddd style='cursor:arrow;'> \n"); 
	doc.write ("<table width=100% height=100% cellpadding=0 cellspacing=0><tr><td align=center valign=middle><img src='"+img.src+"' width='"+w+"' height='"+h+"' border=0 class='dragme' "+click+"></td></tr></table>");
	doc.write ("</body></html>"); 
	doc.close(); 

	if(parseInt(navigator.appVersion) >= 4){win.window.focus();} 

}
/**
 * startInt부터 range사이의 random 정수 반환
 * @param startInt
 * @param range
 * @returns
 */
function getRandomInteger(startInt, range) {
	return Math.floor(Math.random() * parseInt(range)) + parseInt(startInt);
}
