<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script type="text/javascript">
if ('anonymousUser' != '<security:authentication property="principal" />')
	location.href = "home";
</script>
</head>
<body>
	<h1>Welcome <security:authentication property="principal" /> </h1>
	
	<section>
		<h5>App list</h5>
		<article>
		<%@ include file="/WEB-INF/views/menu.jspf" %>	
		</article>	
	</section>
	
	
	<a href="<c:url value="/auth/login"/>">Login here</a>
</body>
</html>