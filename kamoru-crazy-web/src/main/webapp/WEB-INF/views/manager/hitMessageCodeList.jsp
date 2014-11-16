<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title><s:message code="default.hitMessageCodeList"/></title>
</head>
<body>

	<div>
		<h3>
			<s:message code="default.hitMessageCodeList"/>
			<span style="float: right" onclick='$(".exist").toggle()'>Filter
				: no value code</span>
		</h3>
		<ol class="code-view">
			<c:forEach items="${hitMessageCodeMap}" var="code">
				<li class="${code.value eq null ? 'notExist' : 'exist' }">
					<code class="code-name">${code.key}</code> = <code class="code-value">${code.value}</code>
				</li>
			</c:forEach>
		</ol>
	</div>

</body>
</html>