<%--
Log viewer.
	log4j 로그를 구분하여 보여주며, keywords 복수 검색 가능
	
	lib. Apache Commons. IO, Lang3
	Author: kamoru
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.io.*" %>
<%@ page import="org.apache.commons.lang3.*" %>
<%
String logpath   = request.getParameter("logpath");
String delimeter = request.getParameter("delimeter");
String search    = request.getParameter("search");
String searchOper= request.getParameter("searchOper");

logpath   = logpath   == null ? "" : logpath.trim();
delimeter = delimeter == null ? "" : delimeter;
search    = search    == null ? "" : search.trim();
searchOper= searchOper== null ? "or" : searchOper;

List<String[]> lines = new ArrayList<String[]>();
int tdCount = 0;
String msg = "";
try {
	lines = readLines(logpath, delimeter, search, searchOper);
	for (String[] line : lines) {
		tdCount = line.length > tdCount ? line.length : tdCount;
	}
}
catch (Exception e) {
	msg = e.getMessage();
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Log View</title>
<style type="text/css">
.log-td {
	font-size: 9pt;
	border-bottom: 1px solid lightblue;
	font-family: "나눔고딕코딩";
}
.log-no {
	color: blue;
	text-align: right;
}
.log-text {
	white-space:nowrap;
}
#log-div tr:hover {
	background-color: lightblue;
}
.divBox {
	border: 1px solid blue;
	border-radius: 10px;
	margin: 3px;
}
#logview-container {
	margin: 5px;
}
#form-div {
	padding:2px;
}
#log-div {
	overflow: auto;
	height: 600px;
}
em {
	color: red;
	font-style: normal;
}
section#deco_section {
	width: 98%;
	overflow: hidden;
}
.error-msg {
	color: red;
}
</style>
</head>
<body>
<div id="logview-container">
	<div id="form-div" class="divBox">
		<form>
			<label>Log path
				<input type="text" id="logpath" name="logpath" size="50" value="<%=logpath %>" placeHolder="log file full path"/>
			</label>
			<label>Delimeter
				<input type="text" id="delimeter" name="delimeter" size="2" value="<%=delimeter %>" placeHolder="delimeter"/>
			</label>
			<label>Keywords
				<input type="text" id="search" name="search" size="40" value="<%=search %>" placeHolder="ex. http, error"/>
			</label>
			<label>and
				<input type="radio" name="searchOper" value="and" <%=searchOper.equals("and") ? "checked" : "" %>/>
			</label>
			<label>or
				<input type="radio" name="searchOper" value="or" <%=searchOper.equals("or") ? "checked" : "" %>/>
			</label>
			<input type="submit">
			<span class="error-msg"><%=msg %></span>
		</form>
	</div>
	
	<div id="log-div" class="divBox">
		<table>
			<%	int lineNo = 1;
				String colspan = "";
				for (String[] line : lines) { 
				colspan = line.length > 1 ? "" : "colspan=" +tdCount; 
			%>
			<tr>
				<td class="log-td log-no"><%=lineNo++ %></td>
				<%	for (String str : line) { %>
				<td class="log-td log-text" <%=colspan %>><%=str %></td>
				<%	} %>
			</tr>		
			<%	} %>
		</table>
	</div>
</div>
</body>
</html>
<%!
List<String[]> readLines(String logpath, String delimeter, String search, String searchOper) throws Exception {
	if (logpath.length() == 0)
		throw new Exception("log path is empty");
	File file = new File(logpath);
	if (file.isDirectory()) 
		throw new Exception("log path is directory");
	if (!file.exists())
		throw new Exception("log file not exist");
	
	List<String[]> lineArrayList = new ArrayList<String[]>();
	String[] searchArray = trimArray(StringUtils.splitByWholeSeparator(search, ","));
	int count = 0;
	System.out.println("logView readLines Start");
	for (String line : FileUtils.readLines(file)) {
		if (searchArray.length == 0 || containsAny(line, searchArray, searchOper)) {
			line = StringUtils.replaceEach(line, new String[]{"<", ">"}, new String[]{"&lt;", "&gt;"});
			line = StringUtils.replaceEach(line, searchArray, wrapString(searchArray, "<em>", "</em>"));
			lineArrayList.add(delimeter.length() > 0 ? StringUtils.splitByWholeSeparator(line, delimeter) : new String[]{line});
			if (++count % 100 == 0)
				System.out.println("logView readLines " + count);
		}
	}
	System.out.println("logView readLines End");
	return lineArrayList;
}
String[] wrapString(String[] strArr, String str1, String str2) {
	String[] retArr = Arrays.copyOf(strArr, strArr.length);
	for (int i=0; i < strArr.length; i++) {
		retArr[i] = str1 + strArr[i] + str2;
	}
	return retArr;
}
boolean containsAny(String str, String[] searchArr, String searchOper) {
	boolean result = true;
	for (String search : searchArr) {
		if (searchOper.equals("or")) {
			return StringUtils.contains(str, search);
		}
		else if (searchOper.equals("and")) {
			result = result && StringUtils.contains(str, search);
		}
	}
	return result;
}
String[] trimArray(String[] strArr) {
	for (int i=0; i < strArr.length; i++) {
		strArr[i] = strArr[i].trim();
	}
	return strArr;
}
%>