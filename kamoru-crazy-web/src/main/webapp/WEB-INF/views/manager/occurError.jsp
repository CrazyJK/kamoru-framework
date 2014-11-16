<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>occur Error</title>
</head>
<body>

	<div>
		<h3>occur Error</h3>
		<ol class="code-view">
			<li><a class="code-name" target="errorFrame" href="?k=default">default</a>
			<li><a class="code-name" target="errorFrame" href="?k=kamoru">kamoru</a>
			<li><a class="code-name" target="errorFrame" href="?k=video">video</a>
			<li><a class="code-name" target="errorFrame" href="?k=image">image</a>
		</ol>
	</div>

	<iframe name="errorFrame" style="width:100%; height:600px;"></iframe>
</body>
</html>