<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String[] colors = {"aqua", "black", "blue", "fuchsia", "gray", "green", "lime", "maroon", "navy", "olive", "orange", "purple", "red", "silver", "teal", "white", "yellow"};
	String rgba = request.getParameter("c");
%>
<!DOCTYPE html>
<html>
<head>
<title>Standard colors</title>
<style type="text/css">
ul#color li {
	display:inline-block;
	margin: 10px;
}
ul#color li div {
	width:100px; height:100px;
	text-shadow: 1px 1px 1px white;
	border-radius: 10px;
}
</style>
</head>
<body>
<h2>If you see custom color, input parameter by <code>?c=rgba(123,123,123,0.5)</code></h2>
<ul id="color">
<%	for (String color : colors) { %>
	<li><div style="background-color:<%=color %>;"><%=color %></div>
<%	} %>
	<li><div style="background-color:<%=rgba %>;"><%=rgba %></div>
</ul>
</body>
</html>