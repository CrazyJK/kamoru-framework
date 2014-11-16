<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="jk" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html>
<head>
<title><s:message code="error.kamoru.title" arguments="${exception.kind}"/></title>
<script type="text/javascript">
if (self.innerHeight == 0)
	alert('<s:message code="error.kamoru.message" arguments="${exception.kind},${exception.message}"/>');
</script>
</head>
<body>

	<header id="error-header">
		<h1><s:message code="error.kamoru.header" arguments="${exception.kind}"/></h1>
		<h2><s:message code="error.kamoru.message" arguments="${exception.kind},${exception.message}"/></h2>
	</header>

	<article id="error-article">
		<jk:error/>
	</article>

</body>
</html>